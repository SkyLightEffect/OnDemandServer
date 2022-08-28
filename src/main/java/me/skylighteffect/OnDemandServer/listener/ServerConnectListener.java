package me.skylighteffect.OnDemandServer.listener;

import me.skylighteffect.OnDemandServer.Main;
import me.skylighteffect.OnDemandServer.ServerOnDemand;
import me.skylighteffect.OnDemandServer.configs.MainCFG;
import me.skylighteffect.OnDemandServer.configs.MsgCFG;
import me.skylighteffect.OnDemandServer.enums.ServerStatus;
import me.skylighteffect.OnDemandServer.enums.StartingStatus;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectListener implements Listener {

    @EventHandler
    public void onServerConnect(ServerConnectEvent e) {
        ServerInfo target = e.getTarget();
        ProxiedPlayer p = e.getPlayer();

        ServerOnDemand server = Main.serverController.getServer(target);
        StartingStatus startingStatus = server.start(p);

        if (startingStatus == StartingStatus.STARTING) {
            if (p.getServer() == null) {
                TextComponent message = new TextComponent(MsgCFG.getContent("startup.proxy_join", target.getName()));
                p.disconnect(message);
            } else {
                TextComponent message = new TextComponent(MsgCFG.getContent("startup.change_server", target.getName()));
                p.sendMessage(ChatMessageType.CHAT, message);
            }
            e.setCancelled(true);
        } else if (startingStatus == StartingStatus.ALREADY_STARTING) {
            if (p.getServer() == null) {
                TextComponent message = new TextComponent(MsgCFG.getContent("already_starting.proxy_join", target.getName()));
                p.disconnect(message);
            } else {
                TextComponent message = new TextComponent(MsgCFG.getContent("already_starting.change_server", target.getName()));
                p.sendMessage(ChatMessageType.CHAT, message);
            }
            e.setCancelled(true);
        }
    }
}
