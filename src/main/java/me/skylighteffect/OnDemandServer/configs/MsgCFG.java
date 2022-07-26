package me.skylighteffect.OnDemandServer.configs;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.MessageFormat;

public class MsgCFG {
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

    public static String getContent(String path, Object... replace) {
        String content = config.getString(path);

        if (content != null && !content.equals("")) {
            String s = MessageFormat.format(content.replace('&', '§'), replace);
            if (path != "prefix") {
                s = s.replace("%PREFIX%", MsgCFG.getContent("prefix"));
            }
            return s;
        }

        return path;
    }
}
