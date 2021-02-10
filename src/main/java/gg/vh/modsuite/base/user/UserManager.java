package gg.vh.modsuite.base.user;

import com.google.inject.Singleton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class UserManager {
    private final Map<UUID, User> cache = new HashMap<>();

    public void register(Player player) {
        cache.put(player.getUniqueId(), new User(player.getUniqueId()));
    }

    public void register(UUID uuid) {
        cache.put(uuid, new User(uuid));
    }

    public void unregister(Player player) {
        cache.remove(player.getUniqueId());
    }

    public void unregister(UUID uuid) {
        cache.remove(uuid);
    }

    public User get(Player player) {
        return cache.get(player.getUniqueId());
    }

    public User get(UUID uniqueId) {
        return cache.get(uniqueId);
    }
}
