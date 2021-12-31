package com.oowanghan.rpc.transport.demo;

import com.oowanghan.rpc.transport.common.handler.ClientHandler;
import com.oowanghan.rpc.transport.common.handler.ServerHandler;
import com.oowanghan.rpc.transport.netty.NettyClient;
import com.oowanghan.rpc.transport.protocol.entity.Url;

public class RegisterNettyClient {

    public static void main(String[] args) {
        Url url = Url.valueOf("java://127.0.0.1:8081/client1/register");
        ClientHandler clientHandler = new ClientHandler(url);
        new NettyClient().connect(clientHandler);
        while (!clientHandler.isConnect()) {
            System.out.println("wait connect");
        }
        clientHandler.sent("123", url);
    }
}