package gg.vh.modsuite.base.chat;

import com.google.inject.Singleton;
import gg.vh.modsuite.base.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Singleton
public class ChatManager {
    @Getter @Setter
    private boolean locked;
    @Getter @Setter
    private int delay;

    private final Map<UUID, Long> lastMessage = new HashMap<>();

    public ChatResponse canChat(User user) {
        if (locked && !user.exempt("lock")) return ChatResponse.LOCKED;
        if (delay > 0 && !hasDelayExpired(user)) return ChatResponse.DELAY;
        return ChatResponse.OK;
    }

    public void delay(User user) {
        lastMessage.put(user.getUniqueId(), System.currentTimeMillis());
    }

    public long getRemainingDelay(User user) {
        long current = System.currentTimeMillis();
        long in = lastMessage.get(user.getUniqueId());
        return (delay) - (current - in) / 1000;
    }

    public boolean hasDelayExpired(User user) {
        long current = System.currentTimeMillis();

        if (!lastMessage.containsKey(user.getUniqueId())) return true;

        long in = lastMessage.get(user.getUniqueId());
        long diff = (delay) - (current - in) / 1000;

        if (diff <= 0) {
            lastMessage.remove(user.getUniqueId());
            return true;
        }

        return false;
    }
}
