package com.oowanghan.rpc.transport.demo;

import com.oowanghan.rpc.transport.common.handler.ServerHandler;
import com.oowanghan.rpc.transport.common.handler.channel.KratosChannelContainer;
import com.oowanghan.rpc.transport.netty.NettyServer;
import com.oowanghan.rpc.transport.protocol.entity.Url;

public class RegisterNettyServer {

    public static void main(String[] args) {
        new NettyServer().open(new ServerHandler(Url.valueOf("java://127.0.0.1:8081/register")));
    }
}