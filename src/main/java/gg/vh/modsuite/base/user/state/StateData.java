package gg.vh.modsuite.base.user.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class StateData {
    @Getter
    private final ItemStack[] inventoryContents;
    @Getter
    private final ItemStack[] armorContents;
    @Getter
    private final Location location;
}
