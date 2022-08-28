package me.skylighteffect.OnDemandServer.events;

import me.skylighteffect.OnDemandServer.Ping;
import me.skylighteffect.OnDemandServer.ServerOnDemand;
import net.md_5.bungee.api.plugin.Event;

public class ServerStartedEvent extends Event {
    private final ServerOnDemand server;

    private final Ping ping;

    public ServerStartedEvent(ServerOnDemand server, Ping ping) {
        this.ping = ping;
        this.server = server;
    }

    public ServerOnDemand getServer() {
        return this.server;
    }

    public Ping getPing() {
        return ping;
    }
}
