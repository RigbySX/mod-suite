package gg.vh.modsuite.base.item.base;

import gg.vh.modsuite.base.user.User;
import gg.vh.modsuite.file.DataFile;
import gg.vh.modsuite.util.ItemParser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public abstract class SuiteItem implements ISuiteItem {
    protected final ConfigurationSection cs;

    public SuiteItem(DataFile dataFile) {
        FileConfiguration config = dataFile.getConfiguration();
        cs = config.getConfigurationSection(this.getName());
    }

    @Override
    public String getId() {
        return cs.getString("id");
    }

    @Override
    public int getSlot() {
        return cs.getInt("slot");
    }

    @Override
    public ItemStack getItemStack() {
        return ItemParser.parse(getId(), cs.getConfigurationSection("item"));
    }

    private String getName() {
        final String name = this.getClass().getSimpleName();
        return name.substring(0, name.length() - "Item".length())
                .toLowerCase();
    }

    public abstract void onInteract(User user);
}
