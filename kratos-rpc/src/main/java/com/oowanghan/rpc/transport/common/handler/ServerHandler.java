package com.oowanghan.rpc.transport.common.handler;

import com.oowanghan.rpc.transport.common.handler.base.KratosHandler;
import com.oowanghan.rpc.transport.common.handler.channel.KratosChannel;
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
public class ServerHandler extends KratosHandler {

    private Logger log = LoggerFactory.getLogger(ServerHandler.class);

    private final Url url;

    private boolean isConnected;

    public ServerHandler(Url url) {
        super(url);
        this.url = url;
    }

    @Override
    public boolean isConnect() {
        return false;
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
        KratosServerChannelContainer.getOrAddChannel(channel, url);
        this.isConnected = true;
    }

    @Override
    public void disconnected(ChannelHandlerContext context) {
        Channel channel = context.channel();
        KratosServerChannelContainer.remove(channel);
        channel.close();
        this.isConnected = false;
    }

    @Override
    public void sent(Object message, Url url) {
        log.info("sent url : {} message : {}", url, message);
        KratosChannel kratosChannel = KratosServerChannelContainer.get(url);
        kratosChannel.getChannel().writeAndFlush(message);
    }

    @Override
    public void received(ChannelHandlerContext context, Object message) {
        log.info("receive url : {}, message : {}", url, message);
        KratosChannel kratosChannel = KratosServerChannelContainer.get(url);
    }

    @Override
    public void caught(ChannelHandlerContext context, Throwable exception) {
        this.isConnected = false;
    }
}
