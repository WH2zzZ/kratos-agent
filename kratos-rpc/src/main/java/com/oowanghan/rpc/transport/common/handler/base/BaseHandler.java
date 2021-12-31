package com.oowanghan.rpc.transport.common.handler.base;

import com.oowanghan.rpc.transport.protocol.entity.Url;
import io.netty.channel.ChannelHandlerContext;

import java.util.PrimitiveIterator;

public interface BaseHandler {

    void connected(ChannelHandlerContext context);

    void disconnected(ChannelHandlerContext context);

    void sent(Object message, Url url);

    void received(ChannelHandlerContext context, Object message);

    void caught(ChannelHandlerContext context, Throwable exception);
}
