package com.oowanghan.agent.core.visitor;

import com.oowanghan.agent.core.entity.BeanMetaInfo;
import com.oowanghan.agent.core.entity.MethodMetaInfo;
import com.oowanghan.agent.core.upload.UploadClient;
import com.oowanghan.agent.core.util.common.Matcher;
import com.oowanghan.agent.core.util.common.PatternFactory;
import com.oowanghan.agent.core.util.matcher.SpringMatcher;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.Printer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 方法调用trace类
 * @Author WangHan
 * @Create 6:12 下午 2021/9/12
 */
public class MethodInvokeTraceVisitor extends ClassVisitor {

    private final Logger log = LoggerFactory.getLogger(MethodInvokeTraceVisitor.class);

    private final Matcher matcher;

    private boolean isMatcher;

    private BeanMetaInfo currentBean;

    public MethodInvokeTraceVisitor(int api, ClassVisitor classVisitor, String pattern) {
        super(api, classVisitor);
        this.matcher = PatternFactory.getMatcher(pattern);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        currentBean = new BeanMetaInfo();
        currentBean.setBeanInternalName(name);
        currentBean.setBeanAnnotation(new ArrayList<>());
        currentBean.setInterfaceName(Arrays.asList(interfaces));
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (isMatcher) {
            log.info("[trace-info] bean-name : {}", currentBean);
            currentBean.getBeanAnnotation().add(descriptor);
            return super.visitAnnotation(descriptor, visible);
        }
        isMatcher = matcher.isMatch(descriptor) && new SpringMatcher().isMatch(descriptor);
        currentBean.getBeanAnnotation().add(descriptor);
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (isMatcher && !"<init>".equals(name)){
            MethodMetaInfo methodMetaInfo = new MethodMetaInfo();
            methodMetaInfo.setMethodName(name);
            methodMetaInfo.setAccessFlag(access);
            methodMetaInfo.setMethodDescription(descriptor);
//            methodMetaInfo.setClassInfo(currentBean);
            methodMetaInfo.setInvokeMethods(new HashMap<>(64));
            return new MethodFindInvokeAdapter(api, null, methodMetaInfo, matcher);
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    private static class MethodFindInvokeAdapter extends MethodVisitor {
        private final Logger log = LoggerFactory.getLogger(MethodFindInvokeAdapter.class);
        private final List<String> list = new ArrayList<>();

        private final MethodMetaInfo currentMethod;
        private final Matcher matcher;
        private int currentInvokeIndex = 0;

        public MethodFindInvokeAdapter(int api, MethodVisitor methodVisitor, MethodMetaInfo methodMetaInfo, Matcher matcher) {
            super(api, methodVisitor);
            this.currentMethod = methodMetaInfo;
            this.matcher = matcher;
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

            if (!matcher.isMatch(owner)){
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

            String info = String.format("%s %s.%s", Printer.OPCODES[opcode], owner, name);
            log.info("[trace-info] message str : {}", info);
            currentMethod.getInvokeMethods().put(currentInvokeIndex++, info);
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        @Override
        public void visitEnd() {
            UploadClient.upload(currentMethod);
            super.visitEnd();
        }
    }
}