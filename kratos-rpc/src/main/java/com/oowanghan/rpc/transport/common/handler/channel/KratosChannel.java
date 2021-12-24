package com.oowanghan.rpc.transport.common.handler.channel;

import com.oowanghan.rpc.transport.protocol.entity.Url;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author WangHan
 * @Create 2021/11/23 12:10 上午
 */
public class KratosChannel extends StatusChannel {

    private final Channel channel;

    private final Url url;

    private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    public KratosChannel(Channel channel, Url url) {
        this.channel = channel;
        this.url = url;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public boolean isConnected() {
        return channel.isActive() && isConnected();
    }

    @Override
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        if (value != null) {
            attributes.put(key, value);
        } else {
            attributes.remove(key);
        }
    }
}
