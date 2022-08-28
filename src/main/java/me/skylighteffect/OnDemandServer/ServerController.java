package me.skylighteffect.OnDemandServer;

import me.skylighteffect.OnDemandServer.enums.ServerStatus;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class ServerController {
    private final HashMap<ServerInfo, ServerOnDemand> servers;

    public ServerController() {
        this.servers = new HashMap<>();
        Map<String, ServerInfo> servers = Main.plugin.getProxy().getServers();

        for (Map.Entry<String, ServerInfo> entry : servers.entrySet()) {
            ServerInfo serverInfo = entry.getValue();
            ServerOnDemand server = new ServerOnDemand(entry.getKey(), serverInfo);

            this.servers.put(serverInfo, server);

            if (isServerStarted(serverInfo)) {
                server.setStatus(ServerStatus.STARTED);
            } else {
                server.setStatus(ServerStatus.STOPPED);
            }

        }
    }

    public ServerOnDemand getServer(ServerInfo serverInfo) {
        if (!this.servers.containsKey(serverInfo)) return null;
        return this.servers.get(serverInfo);
    }

    public boolean isServerStarted(ServerInfo serverInfo) {
        int port = ((InetSocketAddress) serverInfo.getSocketAddress()).getPort();
        boolean serverRunningOnPort = !isAvailable(port);

        boolean processRunning = this.getServer(serverInfo).getProcess() != null;

        return serverRunningOnPort || processRunning;
    }

    private static boolean isAvailable(int portNr) {
        boolean portFree;
        try (ServerSocket ignored = new ServerSocket(portNr)) {
            portFree = true;
        } catch (IOException e) {
            portFree = false;
        }
        return portFree;
    }
}
