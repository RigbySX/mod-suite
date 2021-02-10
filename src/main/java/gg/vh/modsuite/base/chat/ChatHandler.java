package gg.vh.modsuite.base.chat;

import com.google.inject.Inject;
import gg.vh.modsuite.lang.Language;
import gg.vh.modsuite.base.user.User;
import gg.vh.modsuite.base.user.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static java.util.Collections.singletonMap;

public class ChatHandler implements Listener {
    @Inject
    private ChatManager cm;
    @Inject
    private UserManager um;
    @Inject
    private Language language;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        User user = um.get(event.getPlayer());

        ChatResponse response = cm.canChat(user);

        // happens if the chat response is negative
        if (response != ChatResponse.OK) {
            event.setCancelled(true);

            String messageToSend;
            switch (response) {
                case LOCKED: {
                    messageToSend = language.translate("chat-locked");
                    break;
                }
                case DELAY: {
                    messageToSend = language.translate("chat-delay", singletonMap("time", cm.getRemainingDelay(user)));
                    break;
                }
                default: messageToSend = null;
            }

            user.message(messageToSend);
            return;
        }

        if (cm.getDelay() > 0) {
            cm.delay(user);
        }
    }
}
