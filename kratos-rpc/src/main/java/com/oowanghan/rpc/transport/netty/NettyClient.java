package com.oowanghan.rpc.transport.netty;

import com.oowanghan.rpc.transport.protocol.entity.Url;
import com.oowanghan.rpc.transport.common.handler.register.RegisterClientHandler;
import com.oowanghan.rpc.transport.common.util.Codec;
import com.oowanghan.rpc.transport.protocol.factory.ChannelHandlerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetSocketAddress;

/**
 * @Author WangHan
 * @Create 2021/11/20 9:47 下午
 */
public class NettyClient {

    private InetSocketAddress bindAddress;

    private Bootstrap bootstrap;

    private Channel channel;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private Url url;

    public NettyClient(Url url) {
        this.bindAddress = new InetSocketAddress(url.getIp(), url.getPort());
        this.url = url;
    }

    public void open() {
        bootstrap = new Bootstrap();

        workerGroup = new NioEventLoopGroup(
                Math.min(Runtime.getRuntime().availableProcessors() + 1, 32),
                new DefaultThreadFactory("KratosClientWorker", false));

        ChannelDuplexHandler serverHandler = ChannelHandlerFactory.getClientHandler(url);

        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("encoder", Codec.getEncoder(url));
                        pipeline.addLast("decoder", Codec.getDecode(url));
                        pipeline.addLast(serverHandler);
                    }
                }).option(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture channelFuture = bootstrap.connect(bindAddress);
        channelFuture.syncUninterruptibly();
        channel = channelFuture.channel();
    }

    public void close() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
