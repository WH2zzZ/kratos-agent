package com.oowanghan.rpc.transport.demo;

import com.oowanghan.rpc.transport.common.handler.ServerHandler;
import com.oowanghan.rpc.transport.netty.NettyServer;
import com.oowanghan.rpc.transport.protocol.entity.Url;

public class RegisterNettyServer {

    public static void main(String[] args) {
        ServerHandler handler = new ServerHandler(Url.valueOf("java://127.0.0.1:8081/one/register"));
        new NettyServer().open(handler);

    }
}