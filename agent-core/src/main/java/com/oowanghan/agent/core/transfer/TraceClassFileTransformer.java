package com.oowanghan.agent.core.transfer;

import com.oowanghan.agent.core.visitor.MethodInvokeTraceVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @Author WangHan
 * @Create 2021/9/1 11:43 下午
 */
public class TraceClassFileTransformer implements ClassFileTransformer {

    private String pattern;

    public TraceClassFileTransformer(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        if (!className.contains(pattern)){
            return classfileBuffer;
        }
        System.out.println("agent:" + className);
        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ClassVisitor classVisitor = new MethodInvokeTraceVisitor(Opcodes.ASM9, classWriter, pattern);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        return classWriter.toByteArray();
    }
}
