package com.oowanghan.rpc.transport.common.handler;

import com.oowanghan.rpc.transport.common.handler.base.KratosHandler;
import com.oowanghan.rpc.transport.common.handler.channel.KratosChannelContainer;
import com.oowanghan.rpc.transport.protocol.entity.ServerContainer;
import com.oowanghan.rpc.transport.protocol.entity.Url;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

/**
 * @Author WangHan
 * @Create 2021/11/23 12:59 上午
 */
public class ServerHandler extends KratosHandler {

    private final Url url;

    public ServerHandler(Url url) {
        super(url);
        this.url = url;
    }

    @Override
    public void connected(ChannelHandlerContext context) {
        Channel channel = context.channel();
        // 注册通道关闭监听器
        channel.closeFuture().addListener(future -> {
            if (future.isSuccess()) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) context.channel().remoteAddress();
                String hostAddress = inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort();
                ServerContainer.getInstance().remove(hostAddress);
            }
        });
        KratosChannelContainer.getOrAddChannel(channel, url);
    }

    @Override
    public void disconnected(ChannelHandlerContext context) {
        Channel channel = context.channel();
        KratosChannelContainer.remove(channel);
        channel.close();
    }

    @Override
    public void sent(Object message) {

    }

    @Override
    public void received(ChannelHandlerContext context, Object message) {

    }

    @Override
    public void caught(ChannelHandlerContext context, Throwable exception) {

    }
}
