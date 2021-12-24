package com.oowanghan.rpc.transport.common.handler.base;

import com.oowanghan.rpc.transport.protocol.entity.Url;

import java.net.InetSocketAddress;

/**
 * @Author WangHan
 * @Create 2021/12/24 1:57 上午
 */
public interface UrlHandler {

    Url getUrl();

    InetSocketAddress getInetSocketAddress();
}
