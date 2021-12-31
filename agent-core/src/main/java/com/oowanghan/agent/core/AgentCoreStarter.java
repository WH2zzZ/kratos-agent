package com.oowanghan.agent.core;

import com.oowanghan.agent.core.entity.AppMetaInfo;
import com.oowanghan.agent.core.entity.SystemMetaInfo;
import com.oowanghan.agent.core.transfer.TraceClassFileTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @Author WangHan
 * @Create 2021/9/29 1:47 上午
 */
public class AgentCoreStarter {

    private Logger log = LoggerFactory.getLogger(AgentCoreStarter.class);

    public void start(Instrumentation ins) {
        ClassLoader classLoader = AgentCoreStarter.class.getClassLoader();
        if (log.isDebugEnabled()) {
            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
            log.info("[system] application.name : {}", System.getProperty("application.name"));
            log.info("[system] classpath : {}", runtimeBean.getSystemProperties());
            log.info("[system] boot class path: {}", runtimeBean.getBootClassPath());
            log.info("[system] lib path: {}", runtimeBean.getLibraryPath());
            log.info("[system] current classloader : {}", this.getClass().getClassLoader());

            log.info("[system] agent runtime mx bean : {}", runtimeBean.getName());
            //        user.name 用户的账户名称
            //        user.home 用户的主目录
            //        user.dir 用户的当前工作目录
            log.info("[system] user.name : {}", System.getProperty("user.name"));
            log.info("[system] user.home : {}", System.getProperty("user.home"));
            log.info("[system] user.dir : {}", System.getProperty("user.dir"));
            log.info("[system] system : {}", SystemMetaInfo.getInstance());
            log.info("[system] app : {}", AppMetaInfo.getInstance());
        }
        ins.addTransformer(new TraceClassFileTransformer("com.oowanghan"), true);
        log.info("agent end");
    }

}
