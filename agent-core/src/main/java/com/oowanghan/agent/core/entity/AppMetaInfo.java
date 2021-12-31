package com.oowanghan.agent.core.entity;

import com.oowanghan.agent.core.util.OsUtil;
import oshi.software.os.OSProcess;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用相关信息
 *
 * @Author WangHan
 * @Create 2021/9/17 1:09 上午
 */
public class AppMetaInfo {

    private String pattern;

    private Long startTime;

    private String containerType;

    private String jdkVersion;

    private int pid;

    private String processCommand;

    private String jarStartPath;

    private List<String> gcs;

    private List<String> dependenciesJarNames;

    private String startClass;

    private AppMetaInfo() {
        initStartTime();
        initJdkVersion();
        initPid();
        initGCs();
        initPattern();
        initDependenciesJarNames();
        initJarStartPath();
    }

    private static class AppMetaInfoInstance {
        private final static AppMetaInfo INSTANCE = new AppMetaInfo();
    }
    public static AppMetaInfo getInstance() {
        return AppMetaInfoInstance.INSTANCE;
    }

    private void initJarStartPath() {
        jarStartPath = System.getProperty("user.dir");
    }

    private void initPid() {
        this.pid = OsUtil.getCurrentProcess().getProcessID();
        OSProcess currentProcess = OsUtil.getCurrentProcess();
        processCommand = currentProcess.getCommandLine();
    }

    private void initJdkVersion() {
        jdkVersion = ManagementFactory.getRuntimeMXBean().getVmName() + " [" + System.getProperty("java.version") + "]";
    }

    private void initStartTime() {
        startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
    }

    private void initGCs() {
        gcs = new ArrayList<>();
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        if (garbageCollectorMXBeans.size() > 0) {
            for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
                gcs.add(garbageCollectorMXBean.getName());
            }
        }
    }

    private void initContainerType(List<String> dependenciesJarNames) {
        containerType = "SpringBoot";
        for (String dependenciesJarName : dependenciesJarNames) {
            if (dependenciesJarName.startsWith("spring-boot-starter-tomcat")) {
                containerType = containerType + "[ Tomcat ]";
            }
            if (dependenciesJarName.startsWith("spring-boot-starter-jetty")) {
                containerType = containerType + "[ Jetty ]";
            }
            if (dependenciesJarName.startsWith("spring-boot-starter-undertow")) {
                containerType = containerType + "[ Undertow ]";
            }
        }
    }

    private void initDependenciesJarNames() {
        String[] dependenciesJarPaths = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
        dependenciesJarNames = Arrays.stream(dependenciesJarPaths).map(jarPath -> {
            String[] jarPathSplits = jarPath.split(System.getProperty("file.separator"));
            return jarPathSplits[jarPathSplits.length - 1];
        }).collect(Collectors.toList());
        initContainerType(dependenciesJarNames);
    }

    private void initPattern() {

    }

    /**
     * 将应用依赖的的 Jar 包全路径切割成只有文件名
     */
    private String getJarName(String libPath) {
        return libPath.substring(libPath.lastIndexOf("/") + 1);
    }

    public String getPattern() {
        return pattern;
    }

    public Long getStartTime() {
        return startTime;
    }

    public String getContainerType() {
        return containerType;
    }

    public String getJdkVersion() {
        return jdkVersion;
    }

    public int getPid() {
        return pid;
    }

    public String getJarStartPath() {
        return jarStartPath;
    }

    public List<String> getGcs() {
        return gcs;
    }

    public List<String> getDependenciesJarNames() {
        return dependenciesJarNames;
    }

    @Override
    public String toString() {
        return "AppMetaInfo{" +
                "pattern='" + pattern + '\'' +
                ", startTime=" + startTime +
                ", containerType='" + containerType + '\'' +
                ", jdkVersion='" + jdkVersion + '\'' +
                ", pid=" + pid +
                ", jarStartPath='" + jarStartPath + '\'' +
                ", gcs=" + gcs +
                ", dependenciesJarNames=" + dependenciesJarNames +
                '}';
    }
}
