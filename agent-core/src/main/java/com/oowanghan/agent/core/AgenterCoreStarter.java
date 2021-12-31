package com.oowanghan.agent.core;

import com.oowanghan.agent.core.entity.AppMetaInfo;
import com.oowanghan.agent.core.entity.SystemMetaInfo;
import com.oowanghan.agent.core.transfer.TraceClassFileTransformer;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @Author WangHan
 * @Create 2021/9/29 1:47 上午
 */
public class AgenterCoreStarter {

    public void start(Instrumentation ins) {
        ClassLoader classLoader = AgenterCoreStarter.class.getClassLoader();
        System.out.println(classLoader);
        System.out.println("主程序运行前");
//        switch (agentArgs){
//            case PatternConstant.SPRING:
//            default:
//                inst.addTransformer(new TraceClassFileTransformer(agentArgs), true);
//        }
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("=========classpath:" + System.getProperty("application.name"));
        System.out.println("=========classpath:" + runtimeMXBean.getSystemProperties());
        System.out.println("boot class path: " + runtimeMXBean.getBootClassPath());
        System.out.println("lib path: " + runtimeMXBean.getLibraryPath());
        System.out.println("current classloader" + this.getClass().getClassLoader());

        System.out.println("agent runtime mx bean:" + runtimeMXBean.getName());
//        user.name 用户的账户名称
//        user.home 用户的主目录
//        user.dir 用户的当前工作目录
        System.out.println("user.name : " + System.getProperty("user.name"));
        System.out.println("user.home : " + System.getProperty("user.home"));
        System.out.println("user.dir : " + System.getProperty("user.dir"));
        System.out.println(SystemMetaInfo.getInstance());
        System.out.println(AppMetaInfo.getInstance());
        System.out.println("1");
        ins.addTransformer(new TraceClassFileTransformer("com.oowanghan"), true);
        System.out.println("agent end");
    }

}
