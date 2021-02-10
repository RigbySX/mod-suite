package gg.vh.modsuite.base.chat;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.google.inject.Inject;
import gg.vh.modsuite.lang.Language;
import gg.vh.modsuite.base.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Collections.singletonMap;

@CommandAlias("chat")
public class ChatCommands extends BaseCommand {
    static final String CHAT_CLEAR_PERM = "modsuite.chat.clear";
    static final String CHAT_CLEAR_EXEMPT = "modsuite.chat.clear.exempt";
    static final String CHAT_LOCK_PERM = "modsuite.chat.lock";
    static final String CHAT_DELAY_PERM = "modsuite.chat.delay";

    @Inject
    private UserManager um;
    @Inject
    private ChatManager cm;
    @Inject
    private Language language;

    @Subcommand("clear")
    @CommandAlias("chatclear|clearchat")
    @CommandPermission(CHAT_CLEAR_PERM)
    public void clear(CommandSender sender) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission(CHAT_CLEAR_EXEMPT)) {
                continue;
            }

            for (int i = 0; i <= 100; i++) {
                online.sendMessage("");
            }
        }

        Bukkit.broadcastMessage(language.translate("chat-clear-message", singletonMap("player", sender.getName())));
    }

    @Subcommand("delay")
    @CommandAlias("slowchat|chatslow")
    @CommandPermission(CHAT_DELAY_PERM)
    public void delay(CommandSender sender, Integer delay) {
        cm.setDelay(delay);

        Map<String, Object> placeholders = of("player", sender.getName(), "time", delay);
        if (delay <= 0) Bukkit.broadcastMessage(language.translate("chat-delay-disabled", placeholders));
        else Bukkit.broadcastMessage(language.translate("chat-delay-set", placeholders));
    }

    @Subcommand("lock")
    @CommandAlias("chatlock|lockchat")
    @CommandPermission(CHAT_LOCK_PERM)
    public void lock(CommandSender sender) {
        final boolean toggle = !cm.isLocked();
        cm.setLocked(toggle);
        Bukkit.broadcastMessage(language.translate("chat-lock-message", singletonMap("player", sender.getName())));
    }
}
