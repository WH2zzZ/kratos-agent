package com.oowanghan.rpc.transport.common.handler.channel;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public interface BaseChannel {
    InetSocketAddress getRemoteAddress();

    Channel getChannel();

    boolean isConnected();

    boolean hasAttribute(String key);

    Object getAttribute(String key);

    void setAttribute(String key, Object value);
}
