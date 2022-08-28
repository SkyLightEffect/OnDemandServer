package me.skylighteffect.OnDemandServer.listener;

import io.netty.channel.Channel;
import me.skylighteffect.OnDemandServer.OnDemandServer;
import me.skylighteffect.OnDemandServer.configs.MainCFG;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerConnectRequest;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.util.logging.Logger;

public class ServerConnectListener implements Listener {
    @EventHandler
    public void onServerConnect(ServerConnectEvent e) {
        ServerInfo target = e.getTarget();
        ProxiedPlayer p = e.getPlayer();

            target.ping((result, error) -> {
                if (error != null) {
                    OnDemandServer.plugin.getLogger().warning("SERVER NOT ONLINE");

                        TextComponent message = new TextComponent();
                        message.setText("Der Server wird nun für dich hochgefahren. Bitte habe einen Moment Geduld und versuche es erneut");

                        if (p.getServer() == null)
                            p.disconnect(message);
                        e.setCancelled(true);

                        startServer(target);
                }
            });
    }

    private static void startServer(ServerInfo target) {
        ProcessBuilder pb = new ProcessBuilder(MainCFG.getScriptPath() + "/" + target.getName() + "/start.sh");
        try {
            pb.start();
            OnDemandServer.plugin.getLogger().info("START " + target.getName() + " via " + MainCFG.getScriptPath() + "/" + target.getName() + "/start.sh");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
