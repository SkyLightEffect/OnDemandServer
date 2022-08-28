package me.skylighteffect.OnDemandServer;

import me.skylighteffect.OnDemandServer.configs.MainCFG;
import me.skylighteffect.OnDemandServer.enums.ServerStatus;
import me.skylighteffect.OnDemandServer.enums.StartingStatus;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ServerOnDemand {
    private final ServerInfo serverInfo;

    private final String name;
    private ServerStatus status;
    private Process process;
    private ProxiedPlayer requester;


    public ServerOnDemand(String name, ServerInfo serverInfo) {
        this.name = name;
        this.serverInfo = serverInfo;
        this.status = ServerStatus.UNKNOWN;
    }

    public StartingStatus start(ProxiedPlayer p) {
        StartingStatus status = start();

        if (status == StartingStatus.STARTING) {
            this.requester = p;
        }
        return status;
    }

    private StartingStatus start() {
        switch (getStatus()) {
            case STARTED:
                return StartingStatus.ALREADY_STARTED;
            case STARTING:
                return StartingStatus.ALREADY_STARTING;
        }

        // Ping server until it is started
        ProxyServer.getInstance().getScheduler().runAsync(Main.plugin, () -> {
            new Ping(this);
        });

        ProcessBuilder pb = new ProcessBuilder(MainCFG.getScriptPath() + "/" + serverInfo.getName() + "/start.sh");
        try {
            process = pb.start();
            Main.plugin.getLogger().info("DEBUG #1: " + status);
            status = ServerStatus.STARTING;
            return StartingStatus.STARTING;
        } catch (Exception e) {
            e.printStackTrace();
            return StartingStatus.UNKNOWN;
        }
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public ServerStatus getStatus() {
        if (!Main.serverController.isServerStarted(serverInfo)) status = ServerStatus.STOPPED;
        return status;
    }

    public Process getProcess() {
        return process;
    }

    public ProxiedPlayer getRequester() {
        return requester;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
    }
}
