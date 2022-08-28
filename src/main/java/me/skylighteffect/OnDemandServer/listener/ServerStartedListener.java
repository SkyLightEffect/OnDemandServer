package me.skylighteffect.OnDemandServer.listener;

import me.skylighteffect.OnDemandServer.Main;
import me.skylighteffect.OnDemandServer.configs.MsgCFG;
import me.skylighteffect.OnDemandServer.enums.ServerStatus;
import me.skylighteffect.OnDemandServer.events.ServerStartedEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerStartedListener implements Listener {
    @EventHandler
    public void onServerStarted(ServerStartedEvent e) {
        long time = e.getPing().getTimeTook();

        Main.plugin.getLogger().info(e.getServer().getServerInfo().getName() + " started successfully after " + time + "ms");

        e.getServer().setStatus(ServerStatus.STARTED);

        ProxiedPlayer p = e.getServer().getRequester();

        if (p != null && p.isConnected()) {
            p.sendMessage(new TextComponent(MsgCFG.getContent("start_successful", e.getServer().getServerInfo().getName(), time)));
            p.connect(e.getServer().getServerInfo());
        }
    }
}
