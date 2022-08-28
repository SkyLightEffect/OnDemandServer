package me.skylighteffect.OnDemandServer.listener;

import me.skylighteffect.OnDemandServer.configs.MainCFG;
import me.skylighteffect.OnDemandServer.configs.MsgCFG;
import me.skylighteffect.OnDemandServer.enums.ServerStatus;
import me.skylighteffect.OnDemandServer.events.ServerStartFailedEvent;
import me.skylighteffect.OnDemandServer.events.ServerStartedEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerStartFailedListener implements Listener {
    @EventHandler
    public void onServerStartFailed(ServerStartFailedEvent e) {
        long time = e.getPing().getTimeTook();

        e.getServer().setStatus(ServerStatus.STOPPED);

        ProxiedPlayer p = e.getServer().getRequester();
        p.sendMessage(new TextComponent(MsgCFG.getContent("start_failed", e.getServer().getServerInfo().getName(), time)));
    }
}
