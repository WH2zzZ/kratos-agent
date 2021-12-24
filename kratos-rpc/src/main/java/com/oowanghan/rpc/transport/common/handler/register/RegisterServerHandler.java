package com.oowanghan.rpc.transport.common.handler.register;

import com.oowanghan.rpc.transport.common.handler.channel.KratosChannelContainer;
import com.oowanghan.rpc.transport.protocol.entity.ServerContainer;
import com.oowanghan.rpc.transport.protocol.entity.ServerInfo;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Author WangHan
 * @Create 2021/11/21 7:13 下午
 */
@ChannelHandler.Sharable
public class RegisterServerHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegisterServerHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().closeFuture().addListener(future -> {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String hostAddress = inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort();
            ServerContainer.getInstance().remove(hostAddress);
        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("recieve clent msg : {}", msg);
        if (!(msg instanceof ServerInfo)) {
            return;
        }
        ServerContainer.getInstance().put((ServerInfo) msg);
        ctx.channel().writeAndFlush(ServerContainer.getInstance());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(ServerContainer.getInstance());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
