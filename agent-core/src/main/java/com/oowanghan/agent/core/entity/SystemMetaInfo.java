package com.oowanghan.agent.core.entity;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @Author WangHan
 * @Create 2021/9/17 1:08 上午
 */
public class SystemMetaInfo {

    /**
     * 系统名称
     */
    private String osName;
    /**
     * 系统架构
     */
    private String osArch;
    /**
     * 系统版本
     */
    private String osVersion;

    private String localIp;

    private String hostName;

    private SystemMetaInfo(){
        initHostName();
        initLocalIp();
        initSystem();
    }

    private void initSystem() {
        osName = System.getProperty("os.name");
        osArch = System.getProperty("os.arch");
        osVersion = System.getProperty("os.version");
    }

    private static class SystemMetaInfoInstance{
        private static final SystemMetaInfo INSTANCE = new SystemMetaInfo();
    }

    public static SystemMetaInfo getInstance() {
        return SystemMetaInfoInstance.INSTANCE;
    }

    private void initHostName() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String[] split = name.split("@");
        if (split.length == 2) {
            hostName = split[1];
            return;
        }
        hostName = "unknown";
    }

    private void initLocalIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip instanceof Inet4Address && !ip.isLoopbackAddress()) {
                        this.localIp = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            localIp = "127.0.0.1";
        }
    }

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public String getLocalIp() {
        return localIp;
    }

    public String getHostName() {
        return hostName;
    }

    @Override
    public String toString() {
        return "SystemMetaInfo{" +
                "osName='" + osName + '\'' +
                ", osArch='" + osArch + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", localIp='" + localIp + '\'' +
                ", hostName='" + hostName + '\'' +
                '}';
    }
}
