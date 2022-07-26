package me.skylighteffect.OnDemandServer.configs;

import me.skylighteffect.OnDemandServer.OnDemandServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MainCFG {
    private static Configuration config;
    private final static String filename = "config.yml";

    public static void loadConfig(Plugin plugin) {

        // read config

        File file = new File(plugin.getDataFolder(), filename);
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream(filename)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // load config
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getScriptPath() {
        return config.getString("start_scripts_path");
    }
}
