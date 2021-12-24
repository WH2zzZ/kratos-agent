package com.oowanghan.agent.core.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class MethodTimerVisitor extends ClassVisitor {
    private String owner;
    private boolean isInterface;

    public MethodTimerVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (isInterface || methodVisitor == null ||
                "<init>".equals(name) || "<clinit>".equals(name)) {
            return methodVisitor;
        }

        boolean isAbstract = (access & ACC_ABSTRACT) != 0;
        boolean isNativeMethod = (access & ACC_NATIVE) != 0;
        if (isAbstract || isNativeMethod) {
            return methodVisitor;
        }

        // 每遇到一个合适的方法，就添加一个相应的字段
        FieldVisitor fv = super.visitField(ACC_PUBLIC | ACC_STATIC, getFieldName(name),
                "J", null, null);
        if (fv != null) {
            fv.visitEnd();
        }
        methodVisitor = new MethodTimerAdapter(api, methodVisitor, owner, name);
        return methodVisitor;
    }

    private String getFieldName(String methodName) {
        return "timer_" + methodName;
    }

    private class MethodTimerAdapter extends MethodVisitor {
        private String methodName;
        private final String owner;

        public MethodTimerAdapter(int api, MethodVisitor mv, String owner, String methodName) {
            super(api, mv);
            this.owner = owner;
            this.methodName = methodName;
        }

        @Override
        public void visitCode() {
            super.visitFieldInsn(GETSTATIC, owner, getFieldName(methodName), "J"); // 注意，字段名字要对应
            super.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            super.visitInsn(LSUB);
            super.visitFieldInsn(PUTSTATIC, owner, getFieldName(methodName), "J"); // 注意，字段名字要对应
            super.visitCode();
        }

        @Override
        public void visitInsn(int opcode) {
            boolean isReturn = opcode >= IRETURN && opcode <= RETURN;
            boolean isThrow = opcode == ATHROW;
            if (isReturn || isThrow) {
                super.visitFieldInsn(GETSTATIC, owner, getFieldName(methodName), "J"); // 注意，字段名字要对应
                super.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                super.visitInsn(LADD);
                super.visitFieldInsn(PUTSTATIC, owner, getFieldName(methodName), "J"); // 注意，字段名字要对应

                super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                super.visitTypeInsn(NEW, "java/lang/StringBuilder");
                super.visitInsn(DUP);
                super.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
                super.visitLdcInsn("spend time:");
                super.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
                super.visitFieldInsn(GETSTATIC, owner, getFieldName(methodName), "J");
                super.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
                super.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

            }
            super.visitInsn(opcode);
        }
    }
}