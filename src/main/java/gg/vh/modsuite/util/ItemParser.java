package gg.vh.modsuite.util;

import de.tr7zw.nbtapi.NBTItem;
import gg.vh.modsuite.Constants;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ItemParser {

    public ItemStack parse(@NonNull Material material, int amount, @NonNull String name, @NonNull List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack parse(@NonNull String id, @NonNull ConfigurationSection itemConfig) {
        Material material = Material.valueOf(itemConfig.getString("material"));
        String name = MessageParser.parse(itemConfig.getString("name"));
        List<String> lore = itemConfig.getStringList("lore").stream()
                .map(MessageParser::parse)
                .collect(Collectors.toList());
        ItemStack itemStack = parse(material, 1, name, lore);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString(Constants.ITEM_NBT_KEY, id);
        return nbtItem.getItem();
    }
}
