package gg.vh.modsuite.base.user.state;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import gg.vh.modsuite.base.item.ItemRegistry;
import gg.vh.modsuite.base.user.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Singleton
public class StateManager {
    private final Map<UUID, StateData> store = new HashMap<>();

    @Inject
    private ItemRegistry registry;

    public void enableModMode(User user) {
        StateData data = user.toStateData();
        store.put(user.getUniqueId(), data);

        Player player = requireNonNull(user.getPlayer());
        player.setGameMode(GameMode.CREATIVE);

        registry.getItems().forEach(item -> player.getInventory().setItem(item.getSlot(), item.getItemStack()));
    }

    public void disableModMode(User user) {
        StateData data = store.remove(user.getUniqueId());

        // restore the user old state
        user.fromStateData(data);

        Player player = requireNonNull(user.getPlayer());
        player.setGameMode(GameMode.SURVIVAL);
    }

    public boolean isModMode(User user) {
        return store.containsKey(user.getUniqueId());
    }
}
