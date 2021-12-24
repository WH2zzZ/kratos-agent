package com.oowanghan.rpc.transport.common.handler.invoker;

import com.oowanghan.rpc.transport.common.util.MethodUtils;
import com.oowanghan.rpc.transport.protocol.entity.InvokerInfo;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @Author WangHan
 * @Create 2021/12/19 4:22 下午
 */
public class InvokerServerHandler extends ChannelDuplexHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof InvokerInfo)) {
            return;
        }
        InvokerInfo invokerInfo = (InvokerInfo) msg;
        Object result = MethodUtils.invokerMethod(ctx, invokerInfo);
        ctx.channel().writeAndFlush(result);
    }
}
