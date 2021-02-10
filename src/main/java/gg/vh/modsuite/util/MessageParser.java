package gg.vh.modsuite.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class MessageParser {
    public String parse(@NonNull String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
