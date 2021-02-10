package gg.vh.modsuite.base.user;

import gg.vh.modsuite.base.user.state.StateData;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
public final class User {
    @Getter
    private final UUID uniqueId;

    public @Nullable Player getPlayer() {
        if (Bukkit.getPlayer(uniqueId) != null) {
            return Bukkit.getPlayer(uniqueId);
        }
        return null;
    }

    public void fromStateData(StateData stateData) {
        clear();
        Player player = requireNonNull(getPlayer());
        player.getInventory().setContents(stateData.getInventoryContents());
        player.getInventory().setArmorContents(stateData.getArmorContents());
        player.teleport(stateData.getLocation());
    }

    public StateData toStateData() {
        Player player = requireNonNull(getPlayer());

        ItemStack[] invContents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        Location location = player.getLocation();
        this.clear();
        return new StateData(invContents, armorContents, location);
    }

    public void message(@NonNull String value) {
        requireNonNull(getPlayer()).sendMessage(value);
    }

    public boolean exempt(String node) {
        return requireNonNull(getPlayer()).hasPermission("modsuite." + node + ".exempt");
    }

    private void clear() {
        Player player = requireNonNull(getPlayer());

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.teleport(player.getWorld().getSpawnLocation());
    }
}
