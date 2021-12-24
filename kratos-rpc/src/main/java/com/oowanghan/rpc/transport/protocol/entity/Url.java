package com.oowanghan.rpc.transport.protocol.entity;

import com.oowanghan.rpc.transport.protocol.type.InvokeType;

import java.io.Serializable;

/**
 *
 * @Author WangHan
 * @Create 2021/11/20 9:19 下午
 */
public class Url implements Serializable {

    public static final long serialVersionUID = 1L;

    private final String ip;

    private final int port;

    private final String address;

    private final int heartBeatMilliSecond = 60_000;

    private final InvokeType invokeType;

    protected String protocol;

    private Url(String address, String protocol, InvokeType invokerType) {
        this.address = address;
        this.protocol = protocol;
        this.invokeType = invokerType;
        int ipIndex = address.lastIndexOf(":");
        ip = address.substring(0, ipIndex);
        port = Integer.parseInt(address.substring(ipIndex + 1));
    }

    public static Url valueOf(String url) {
        // 后期做参数格式化校验
        if (url == null || url.isEmpty()) {
            throw new IllegalStateException("url format error : \"" + url + "\"");
        }
        String[] urlInfoArr = url.split("://");
        String[] path = urlInfoArr[1].split("/");

        String protocol = urlInfoArr[0];
        String address = path[0];
        InvokeType invokeType = InvokeType.getByType(path[1]);
        return new Url(address, protocol, invokeType);
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getHeartBeatMilliSecond() {
        return heartBeatMilliSecond;
    }

    public String getAddress() {
        return address;
    }

    public InvokeType getInvokeType() {
        return invokeType;
    }

    @Override
    public String toString() {
        return "Url{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", address='" + address + '\'' +
                ", heartBeatMilliSecond=" + heartBeatMilliSecond +
                ", invokeType=" + invokeType +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
