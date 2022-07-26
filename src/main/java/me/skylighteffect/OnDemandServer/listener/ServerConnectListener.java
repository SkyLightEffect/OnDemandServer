package me.skylighteffect.OnDemandServer.listener;

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

public class ServerConnectListener implements Listener {
    @EventHandler
    public void onServerConnect(ServerConnectEvent e) {
        ServerInfo target = e.getTarget();
        ProxiedPlayer p = e.getPlayer();

        if (e.getReason() != ServerConnectEvent.Reason.PLUGIN) {

            e.setCancelled(true);

            ServerConnectRequest.Builder builder = ServerConnectRequest.builder()
                    .retry(false)
                    .reason(ServerConnectEvent.Reason.PLUGIN)
                    .target(target)
                    .connectTimeout(e.getRequest().getConnectTimeout())
                    .callback(new Callback<ServerConnectRequest.Result>() {
                        @Override
                        public void done(ServerConnectRequest.Result result, Throwable error) {
                            if (result == ServerConnectRequest.Result.FAIL) {

                                ProcessBuilder pb = new ProcessBuilder(MainCFG.getScriptPath() + "/" + target.getName() +  "/start.sh");
                                try {
                                    pb.start();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                OnDemandServer.plugin.getLogger().info("START SERVER...");
                            }
                        }
                    });
            p.connect(builder.build());

        }
    }
}
