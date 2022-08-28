package me.skylighteffect.OnDemandServer.listener;

import me.skylighteffect.OnDemandServer.enums.ServerStatus;
import me.skylighteffect.OnDemandServer.events.ServerStartedEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerStartedListener implements Listener {
    @EventHandler
    public void onServerStarted(ServerStartedEvent e) {
        long time = e.getPing().getTimeTook();

        e.getServer().setStatus(ServerStatus.STARTED);

        ProxiedPlayer p = e.getServer().getRequester();
        if (p != null && p.isConnected()) {
            p.connect(e.getServer().getServerInfo());
        }
    }
}
