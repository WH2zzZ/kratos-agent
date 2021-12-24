package com.oowanghan.rpc.transport.common.type;

import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @Author WangHan
 * @Create 2021/11/21 6:45 下午
 */
public enum ProtocolType {

    /**
     * java类型序列化方式
     */
    JAVA("java"){
        @Override
        public ChannelHandler getDecoder() {
            return new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null));
        }

        @Override
        public ChannelHandler getEncoder() {
            return new ObjectEncoder();
        }
    };

    private final String type;


    ProtocolType(String type) {
        this.type = type;
    }

    public abstract ChannelHandler getDecoder();
    public abstract ChannelHandler getEncoder();

    public static ProtocolType getByType(String value) {
        ProtocolType[] values = ProtocolType.values();
        for (ProtocolType protocolType : values) {
            if (protocolType.getType().equals(value)) {
                return protocolType;
            }
        }
        return ProtocolType.JAVA;
    }

    public String getType() {
        return type;
    }
}
