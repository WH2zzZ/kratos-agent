package com.oowanghan.rpc.transport.protocol.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @Author WangHan
 * @Create 2021/11/21 5:41 下午
 */
public class InvokerInfo implements Serializable {

    public static final long serialVersionUID = 1L;

    private String classFullName;
    private String methodName;
    private Class<?>[] argClasses;
    private Object[] argValues;

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getArgClasses() {
        return argClasses;
    }

    public void setArgClasses(Class<?>[] argClasses) {
        this.argClasses = argClasses;
    }

    public Object[] getArgValues() {
        return argValues;
    }

    public void setArgValues(Object[] argValues) {
        this.argValues = argValues;
    }
}
