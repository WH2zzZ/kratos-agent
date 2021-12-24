package com.oowanghan.rpc.transport.protocol.type;

/**
 * @Author WangHan
 * @Create 2021/12/19 2:40 下午
 */
public enum InvokeType {

    /**
     * 注册
     */
    REGISTER("register"),

    /**
     * 调用
     */
    INVOKER("invoker");

    private String type;

    InvokeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static InvokeType getByType(String type) {
        for (InvokeType value : InvokeType.values()) {
            if (value.getType().equalsIgnoreCase(type)) {
                return value;
            }
        }
        throw new UnsupportedOperationException("url invoke type is error");
    }
}
