package com.oowanghan.agent.boot;

import com.oowanghan.agent.boot.classloader.AgentClassloader;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * 代理主类
 *
 * @Author WangHan
 * @Create 2021/9/1 11:40 下午
 */
public class AgentLauncher {

    /**
     * 主程序之前运行代理程序
     *
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        String path = AgentLauncher.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getFile();
        String parentPath = new File(path).getParent();
        String coreJarPath = parentPath + File.separator + "agent-core-1.0-SNAPSHOT-jar-with-dependencies.jar";
        String osJarPath = parentPath + File.separator + "oshi-core-5.6.1.jar";
        String jnaPlatformJarPath = parentPath + File.separator + "jna-platform-5.8.0.jar";
        String jnaJarPath = parentPath + File.separator + "jna-5.8.0.jar";
        URL[] urls = {
                new File(coreJarPath).toURI().toURL(),
                new File(osJarPath).toURI().toURL(),
                new File(jnaPlatformJarPath).toURI().toURL(),
                new File(jnaJarPath).toURI().toURL()
        };
        AgentClassloader agentClassloader = new AgentClassloader(urls);
        Class<?> coreStartClass = agentClassloader.loadClass("com.oowanghan.agent.core.AgenterCoreStarter");
        Object coreStartInstance = coreStartClass.getConstructors()[0].newInstance();

        Method startMethod = coreStartClass.getDeclaredMethod("start", Instrumentation.class);
        startMethod.invoke(coreStartInstance, inst);
    }

    /**
     * 在主程序运行之后的代理程序
     *
     * @param agentArgs
     * @param inst
     */
    public static void agentmain(String agentArgs, Instrumentation inst) {
    }
}
