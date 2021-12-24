package com.oowanghan.rpc.transport.common.handler.channel;

import com.oowanghan.rpc.transport.protocol.entity.Url;
import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author WangHan
 * @Create 2021/11/26 12:27 上午
 */
public class KratosChannelContainer {

    private static final ConcurrentHashMap<Channel, KratosChannel> container = new ConcurrentHashMap<>(128);

    public static KratosChannel getOrAddChannel(Channel channel, Url url) {
        KratosChannel kratosChannel = new KratosChannel(channel, url);
        container.putIfAbsent(channel, kratosChannel);
        return kratosChannel;
    }

    public static void remove(Channel channel) {
        container.remove(channel);
    }

}
