package com.oowanghan.rpc.transport.common.handler.register;

import com.oowanghan.rpc.transport.protocol.entity.ServerContainer;
import com.oowanghan.rpc.transport.protocol.entity.ServerInfo;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class RegisterClientHandler extends ChannelDuplexHandler {

    private Logger log = LoggerFactory.getLogger(RegisterClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ServerInfo serverInfo = new ServerInfo("test", "java://127.0.0.1:9090/register");
        ctx.channel().writeAndFlush(serverInfo);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ServerContainer) {
            ServerContainer serverContainer = (ServerContainer) msg;
            log.info("receive server info : {}", serverContainer);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}