package com.oowanghan.rpc.transport.common.util;

import com.oowanghan.rpc.transport.protocol.entity.Url;
import com.oowanghan.rpc.transport.common.type.ProtocolType;
import io.netty.channel.ChannelHandler;

/**
 * @Author WangHan
 * @Create 2021/11/21 6:42 下午
 */
public class Codec {

    public static ChannelHandler getDecode(Url url) {
        return ProtocolType.getByType(url.getProtocol()).getDecoder();
    }

    public static ChannelHandler getEncoder(Url url) {
        return ProtocolType.getByType(url.getProtocol()).getEncoder();
    }
}
