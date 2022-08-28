package me.skylighteffect.OnDemandServer;

import me.skylighteffect.OnDemandServer.configs.MainCFG;
import me.skylighteffect.OnDemandServer.events.ServerStartFailedEvent;
import me.skylighteffect.OnDemandServer.events.ServerStartedEvent;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Ping {
    private final static long MAX_STARTUP_TIME = MainCFG.getMaxStartupTimeMillis();
    private final static long PING_INTERVAL = 100;

    private ServerOnDemand server;
    private final long timeout;
    private final long start;
    private long end;

    Ping(ServerOnDemand server) {
        this(server, MAX_STARTUP_TIME);
    }

    Ping(ServerOnDemand server, long timeout) {
        this.timeout = timeout;
        this.server = server;
        this.end = this.start = System.currentTimeMillis();
        ping();
    }

    private void ping() {

        // Ping timeout
        if (this.end - this.start > timeout) {
            Main.plugin.getLogger().log(Level.WARNING, "Maximum ping tries for " + server.getServerInfo().getName() + " server reached, aborting.");
            ProxyServer.getInstance().getPluginManager().callEvent(new ServerStartFailedEvent(server, this));
            return;
        }

        server.getServerInfo().ping((serverPing, throwable) -> {
            end = System.currentTimeMillis();

            if (serverPing != null) {
                ProxyServer.getInstance().getPluginManager().callEvent(new ServerStartedEvent(server, this));
                return;
            } else {
                Main.plugin.getProxy().getScheduler().schedule(Main.plugin, this::ping, PING_INTERVAL, TimeUnit.MILLISECONDS);
            }
        });
    }

    public long getTimeTook() {
        return this.end - this.start;
    }
}
