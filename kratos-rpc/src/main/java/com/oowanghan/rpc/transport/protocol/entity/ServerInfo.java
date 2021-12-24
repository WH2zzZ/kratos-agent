package com.oowanghan.rpc.transport.protocol.entity;

import java.io.Serializable;

/**
 * @Author WangHan
 * @Create 2021/11/20 9:44 下午
 */
public class ServerInfo implements Serializable {

    public static final long serialVersionUID = 1L;

    private final String serverName;

    private final Url url;

    public ServerInfo(String serverName, String url) {
        this.serverName = serverName;
        this.url = Url.valueOf(url);
    }

    public ServerInfo(String serverName, Url urlInfo) {
        this.serverName = serverName;
        this.url = urlInfo;
    }

    public String getServerName() {
        return serverName;
    }

    public Url getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "serverName='" + serverName + '\'' +
                ", url=" + url +
                '}';
    }
}
