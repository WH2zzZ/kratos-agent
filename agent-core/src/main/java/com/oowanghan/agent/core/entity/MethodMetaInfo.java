package com.oowanghan.agent.core.entity;

import java.util.Map;

/**
 * 方法元信息
 *
 * @Author WangHan
 * @Create 2021/9/3 12:39 上午
 */
public class MethodMetaInfo {

    private String methodName;

    private int accessFlag;

    private String methodAnnotation;

    private String methodDescription;

    private BeanMetaInfo classInfo;

    private Map<Integer, String> invokeMethods;


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(int accessFlag) {
        this.accessFlag = accessFlag;
    }

    public String getMethodAnnotation() {
        return methodAnnotation;
    }

    public void setMethodAnnotation(String methodAnnotation) {
        this.methodAnnotation = methodAnnotation;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public BeanMetaInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(BeanMetaInfo classInfo) {
        this.classInfo = classInfo;
    }

    public Map<Integer, String> getInvokeMethods() {
        return invokeMethods;
    }

    public void setInvokeMethods(Map<Integer, String> invokeMethods) {
        this.invokeMethods = invokeMethods;
    }

    @Override
    public String toString() {
        return "MethodMetaInfo{" +
                "methodName='" + methodName + '\'' +
                ", accessFlag='" + accessFlag + '\'' +
                ", methodAnnotation='" + methodAnnotation + '\'' +
                ", methodDescription='" + methodDescription + '\'' +
                ", classInfo=" + classInfo +
                ", invokeMethods=" + invokeMethods +
                '}';
    }
}
