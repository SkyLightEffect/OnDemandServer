package me.skylighteffect.OnDemandServer;

import me.skylighteffect.OnDemandServer.configs.MainCFG;
import me.skylighteffect.OnDemandServer.configs.MsgCFG;
import me.skylighteffect.OnDemandServer.listener.ServerConnectListener;
import me.skylighteffect.OnDemandServer.listener.ServerStartedListener;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author SkyLightEffect
 */
public final class Main extends Plugin {

    public static Plugin plugin;

    public static ServerController serverController;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;
        
        // Write default configuration folder
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        MainCFG.loadConfig(this);
        MsgCFG.loadConfig(this);

        Main.serverController = new ServerController();

        // Register events
        getProxy().getPluginManager().registerListener(this, new ServerConnectListener());
        getProxy().getPluginManager().registerListener(this, new ServerStartedListener());

        // Final message
        getLogger().info(MsgCFG.getContent("plugin_enabled", plugin.getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(MsgCFG.getContent("plugin_disabled", plugin.getDescription().getVersion()));
    }
}
