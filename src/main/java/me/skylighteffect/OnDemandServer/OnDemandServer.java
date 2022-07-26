package me.skylighteffect.OnDemandServer;

import me.skylighteffect.OnDemandServer.configs.MainCFG;
import me.skylighteffect.OnDemandServer.configs.MsgCFG;
import net.md_5.bungee.api.plugin.Plugin;

public final class OnDemandServer extends Plugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        MainCFG.loadConfig(this);
        MsgCFG.loadConfig(this);

        MsgCFG.getContent("plugin_enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
