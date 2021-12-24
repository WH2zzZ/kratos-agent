package com.oowanghan.rpc.transport.common.util;

import com.oowanghan.rpc.transport.protocol.entity.InvokerInfo;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

/**
 * @Author WangHan
 * @Create 2021/12/21 1:03 上午
 */
public class MethodUtils {
    public static Object invokerMethod(ChannelHandlerContext ctx, InvokerInfo invokerInfo) {
        try {
            Class<?> invokerClass = Class.forName(invokerInfo.getClassFullName());
            Object instance = invokerClass.newInstance();
            Method method = invokerClass.getMethod(invokerInfo.getMethodName(), invokerInfo.getArgClasses());
            Object result = method.invoke(instance, invokerInfo.getArgValues());
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
