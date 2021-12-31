package com.oowanghan.rpc.transport.common.handler.channel;

import com.oowanghan.rpc.transport.protocol.entity.Url;
import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author WangHan
 * @Create 2021/11/26 12:27 上午
 */
public class KratosServerChannelContainer {

    private static final ConcurrentHashMap<Url, KratosChannel> container = new ConcurrentHashMap<>(128);

    public static KratosChannel getOrAddChannel(Channel channel, Url url) {
        if (container.containsKey(url)) {
            return container.get(url);
        }
        KratosChannel kratosChannel = new KratosChannel(channel, url);
        container.putIfAbsent(url, kratosChannel);
        return kratosChannel;
    }

    public static KratosChannel get(Url url) {
        return container.get(url);
    }

    public static void remove(Channel channel) {
        container.remove(channel);
    }

}
