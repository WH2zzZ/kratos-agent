package com.oowanghan.rpc.transport.common.handler;

import com.oowanghan.rpc.transport.common.handler.base.KratosHandler;
import com.oowanghan.rpc.transport.common.handler.channel.KratosChannel;
import com.oowanghan.rpc.transport.common.handler.channel.KratosClientChannelContainer;
import com.oowanghan.rpc.transport.common.handler.channel.KratosServerChannelContainer;
import com.oowanghan.rpc.transport.protocol.entity.ServerContainer;
import com.oowanghan.rpc.transport.protocol.entity.Url;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Author WangHan
 * @Create 2021/11/23 12:59 上午
 */
public class ClientHandler extends KratosHandler {

    private Logger log = LoggerFactory.getLogger(ClientHandler.class);

    private final Url url;
    private boolean isConnected = false;

    public ClientHandler(Url url) {
        super(url);
        this.url = url;
    }

    @Override
    public void connected(ChannelHandlerContext context) {
        log.info("connect url : {}", url);
        Channel channel = context.channel();
        // 注册通道关闭监听器
        channel.closeFuture().addListener(future -> {
            if (future.isSuccess()) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) context.channel().remoteAddress();
                String hostAddress = inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort();
                ServerContainer.getInstance().remove(hostAddress);
            }
        });
        KratosClientChannelContainer.getOrAddChannel(channel, url);
        this.isConnected = true;
    }

    @Override
    public void disconnected(ChannelHandlerContext context) {
        Channel channel = context.channel();
        KratosClientChannelContainer.remove(channel);
        channel.close();
    }

    @Override
    public void sent(Object message, Url url) {
        log.info("sent url : {} message : {}", url, message);
        KratosChannel kratosChannel = KratosClientChannelContainer.get(url);
        kratosChannel.getChannel().writeAndFlush(message);
    }

    @Override
    public void received(ChannelHandlerContext context, Object message) {
        KratosChannel kratosChannel = KratosClientChannelContainer.get(url);
        log.info("receive url : {} message : {}", url, message);
    }

    @Override
    public void caught(ChannelHandlerContext context, Throwable exception) {

    }

    @Override
    public boolean isConnect() {
        return isConnected;
    }
}
