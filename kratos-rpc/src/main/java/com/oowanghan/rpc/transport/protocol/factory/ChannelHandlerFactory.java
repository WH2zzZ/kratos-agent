package com.oowanghan.rpc.transport.protocol.factory;

import com.oowanghan.rpc.transport.common.handler.invoker.InvokerClientHandler;
import com.oowanghan.rpc.transport.common.handler.invoker.InvokerServerHandler;
import com.oowanghan.rpc.transport.common.handler.register.RegisterClientHandler;
import com.oowanghan.rpc.transport.common.handler.register.RegisterServerHandler;
import com.oowanghan.rpc.transport.protocol.entity.Url;
import io.netty.channel.ChannelDuplexHandler;

/**
 * @Author WangHan
 * @Create 2021/12/19 4:38 下午
 */
public class ChannelHandlerFactory {

    public static ChannelDuplexHandler getServerHandler(Url url) {
        switch (url.getInvokeType()) {
            case INVOKER:
                return new InvokerServerHandler();
            case REGISTER:
                return new RegisterServerHandler();
            default:
                throw new UnsupportedOperationException("invoke type is unknow operation");
        }
    }

    public static ChannelDuplexHandler getClientHandler(Url url) {
        switch (url.getInvokeType()) {
            case INVOKER:
                return new InvokerClientHandler();
            case REGISTER:
                return new RegisterClientHandler();
            default:
                throw new UnsupportedOperationException("invoke type is unknow operation");
        }
    }
}
