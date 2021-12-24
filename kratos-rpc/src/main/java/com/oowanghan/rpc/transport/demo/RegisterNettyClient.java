package com.oowanghan.rpc.transport.demo;

import com.oowanghan.rpc.transport.netty.NettyClient;
import com.oowanghan.rpc.transport.protocol.entity.Url;

public class RegisterNettyClient {

    public static void main(String[] args) {
        new NettyClient(Url.valueOf("java://127.0.0.1:8081/register")).open();
    }
}