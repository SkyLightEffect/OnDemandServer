package me.skylighteffect.OnDemandServer;

import jdk.jpackage.internal.Log;
import me.skylighteffect.OnDemandServer.configs.MainCFG;
import me.skylighteffect.OnDemandServer.configs.MsgCFG;
import me.skylighteffect.OnDemandServer.listener.ServerConnectListener;
import net.md_5.bungee.api.plugin.Plugin;

public final class OnDemandServer extends Plugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;
        
        // Write default configuration folder
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        MainCFG.loadConfig(this);
        MsgCFG.loadConfig(this);

        // Register events
        getProxy().getPluginManager().registerListener(this, new ServerConnectListener());

        // Final message
        getLogger().info(MsgCFG.getContent("plugin_enabled", plugin.getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(MsgCFG.getContent("plugin_disabled", plugin.getDescription().getVersion()));
    }
}
