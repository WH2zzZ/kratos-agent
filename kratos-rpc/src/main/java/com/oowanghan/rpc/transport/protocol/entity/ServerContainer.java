package com.oowanghan.rpc.transport.protocol.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author WangHan
 * @Create 2021/11/21 7:56 下午
 */
public class ServerContainer implements Serializable {

    public static final long serialVersionUID = 1L;

    private final ConcurrentHashMap<String, List<ServerInfo>> serverMap;
    private final ConcurrentHashMap<String, String> serverNameCache;

    private ServerContainer(ConcurrentHashMap<String, List<ServerInfo>> serverMap, ConcurrentHashMap<String, String> serverNameCache) {
        this.serverMap = serverMap;
        this.serverNameCache = serverNameCache;
    }


    private static class ServerContainerInstance {
        private static final ServerContainer INSTANCE =
                new ServerContainer(
                        new ConcurrentHashMap<String, List<ServerInfo>>(256),
                        new ConcurrentHashMap<String, String>(256));
    }

    public static ServerContainer getInstance() {
        return ServerContainerInstance.INSTANCE;
    }

    public void put(ServerInfo serverInfo) {
        synchronized (this) {
            List<ServerInfo> serverInfos = serverMap.get(serverInfo.getServerName());
            if (serverInfos == null || serverInfos.isEmpty()) {
                serverNameCache.put(serverInfo.getUrl().getAddress(), serverInfo.getServerName());
                serverMap.put(serverInfo.getServerName(), Collections.singletonList(serverInfo));
                return;
            }
            boolean isPresent = serverInfos
                    .stream()
                    .map(ServerInfo::getUrl)
                    .anyMatch(url -> url.equals(serverInfo.getUrl()));
            if (!isPresent) {
                serverInfos.add(serverInfo);
                serverNameCache.put(serverInfo.getUrl().getAddress(), serverInfo.getServerName());
            }
        }
    }

    public void remove(String address) {
        synchronized (this) {
            String serverName = serverNameCache.get(address);
            if (serverName == null || serverName.isEmpty()) {
                return;
            }
            List<ServerInfo> serverInfos = serverMap.get(serverName);
            if (serverInfos == null || serverInfos.isEmpty()) {
                return;
            }

            for (ServerInfo serverInfo : serverInfos) {
                if (serverInfo.getUrl().getAddress().equals(serverInfo.getUrl().getAddress())) {
                    serverInfos.remove(address);
                }
            }
        }
    }

    public ConcurrentHashMap<String, List<ServerInfo>> getServerInfos() {
        return serverMap;
    }

    @Override
    public String toString() {
        return "ServerContainer{" +
                "serverMap=" + serverMap +
                ", serverNameCache=" + serverNameCache +
                '}';
    }
}
