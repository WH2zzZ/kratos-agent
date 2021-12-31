package com.oowanghan.rpc.transport.common.handler.base;

import com.oowanghan.rpc.transport.protocol.entity.Url;

import java.net.InetSocketAddress;

/**
 * @Author WangHan
 * @Create 2021/12/24 2:02 上午
 */
public abstract class KratosHandler implements BaseHandler, UrlHandler {

    private final Url url;

    public KratosHandler(Url url) {
        this.url = url;
    }

    public Url getUrl() {
        return url;
    }

    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(url.getIp(), url.getPort());
    }

    public abstract boolean isConnect();
}
