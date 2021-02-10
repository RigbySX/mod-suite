package gg.vh.modsuite.base.item;

import com.google.inject.Inject;
import gg.vh.modsuite.base.item.base.SuiteItem;
import gg.vh.modsuite.base.user.state.StateManager;
import gg.vh.modsuite.base.user.User;
import gg.vh.modsuite.base.user.UserManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemListener implements Listener {
    @Inject
    private ItemRegistry registry;
    @Inject
    private UserManager um;
    @Inject
    private StateManager sm;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) return;

        Optional<SuiteItem> suiteItemOptional = registry.getItem(item);
        if (!suiteItemOptional.isPresent()) return;

        User user = um.get(event.getPlayer());
        if (!sm.isModMode(user)) return;

        SuiteItem suiteItem = suiteItemOptional.get();
        suiteItem.onInteract(user);
    }
}
