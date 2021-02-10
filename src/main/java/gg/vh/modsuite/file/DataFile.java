package gg.vh.modsuite.file;

import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import gg.vh.modsuite.ModSuite;
import lombok.Getter;
import lombok.extern.java.Log;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

@Log
public class DataFile {
    @Getter
    private FileConfiguration configuration;
    private final File file;

    private final ModSuite plugin;

    @Inject
    public DataFile(ModSuite plugin, @Assisted File file) {
        this.plugin = plugin;
        this.file = file;
        load();
    }

    public void load() {
        try {
            if (!file.exists()) {
                plugin.getDataFolder().mkdirs();

                try (InputStream is = plugin.getResource(file.getName());
                     OutputStream os = new FileOutputStream(file)) {
                    ByteStreams.copy(is, os);
                }
            }
            configuration = YamlConfiguration.loadConfiguration(file);
        } catch (IllegalArgumentException | IOException e) {
            log.log(Level.SEVERE, String.format("[ModSuite] Unable to read file %s!", file.getName()), e);
        }
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            log.log(Level.SEVERE, String.format("[ModSuite] Unable to save file %s!", file.getName()), e);
        }
    }
}
