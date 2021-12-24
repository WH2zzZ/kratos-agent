package com.oowanghan.rpc.transport.netty;

import com.oowanghan.rpc.transport.common.handler.base.BaseHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author WangHan
 * @Create 2021/11/22 11:43 下午
 */
public class NettyServerHandler extends ChannelDuplexHandler {

    private final BaseHandler baseHandler;

    public NettyServerHandler(BaseHandler baseHandler) {
        this.baseHandler = baseHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        baseHandler.connected(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        baseHandler.disconnected(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        baseHandler.received(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        baseHandler.disconnected(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        baseHandler.caught(ctx, cause);
    }
}
