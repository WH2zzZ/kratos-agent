package com.oowanghan.rpc.transport.netty;

import com.oowanghan.rpc.transport.common.handler.base.KratosHandler;
import com.oowanghan.rpc.transport.protocol.entity.Url;
import com.oowanghan.rpc.transport.common.util.Codec;
import com.oowanghan.rpc.transport.protocol.factory.ChannelHandlerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @Author WangHan
 * @Create 2021/11/20 9:47 下午
 */
public class NettyServer {

    private ServerBootstrap bootstrap;

    private Channel channel;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    public void open(KratosHandler handler) {
        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1,
                new DefaultThreadFactory("KratosServerBoss", false));
        workerGroup = new NioEventLoopGroup(
                Math.min(Runtime.getRuntime().availableProcessors() + 1, 32),
                new DefaultThreadFactory("KratosServerWorker", false));

        Url url = handler.getUrl();
        ChannelDuplexHandler duplexHandler = ChannelHandlerFactory.getServerHandler(url);
        bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("encoder", Codec.getEncoder(url));
                        pipeline.addLast("decoder", Codec.getDecode(url));
                        pipeline.addLast("idle-handler",
                                new IdleStateHandler(0, 0, url.getHeartBeatMilliSecond(), MILLISECONDS));
                        pipeline.addLast(new NettyServerHandler(handler));
                    }
                }).childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture channelFuture = bootstrap.bind(url.getPort()).syncUninterruptibly();
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
