package gg.vh.modsuite.lang;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import gg.vh.modsuite.file.DataFile;
import gg.vh.modsuite.util.MessageParser;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class Language {
    private final Map<String, String> msgCache = new HashMap<>();

    private final DataFile dataFile;

    @Inject
    public Language(@Named("lang.yml") DataFile dataFile) {
        this.dataFile = dataFile;
    }

    public void load() {
        FileConfiguration config = dataFile.getConfiguration();

        for (String key : config.getKeys(false)) {
            if (!msgCache.containsKey(key)) {
                msgCache.put(key, config.getString(key));
            }
        }
    }

    public String translate(String key) {
        return MessageParser.parse(get(key));
    }

    public String translate(String key, Map<String, Object> placeholders) {
        String message = get(key);

        for (String placeholderKey : placeholders.keySet()) {
            Object value = placeholders.get(placeholderKey);
            message = message.replaceAll("%" + placeholderKey, String.valueOf(value));
        }

        return MessageParser.parse(message);
    }

    private String get(String key) {
        return msgCache.get(key);
    }
}
