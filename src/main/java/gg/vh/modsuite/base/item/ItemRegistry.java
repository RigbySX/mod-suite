package gg.vh.modsuite.base.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import de.tr7zw.nbtapi.NBTItem;
import gg.vh.modsuite.Constants;
import gg.vh.modsuite.ModSuite;
import gg.vh.modsuite.base.item.base.SuiteItem;
import gg.vh.modsuite.base.item.impl.SampleItem;
import gg.vh.modsuite.file.DataFile;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;

@Log
@Singleton
public class ItemRegistry {
    private final Map<String, SuiteItem> items = new HashMap<>();

    private final DataFile dataFile;

    @Inject
    public ItemRegistry(@Named("items.yml") DataFile dataFile) {
        this.dataFile = dataFile;
    }

    @SneakyThrows
    public void register(Class<? extends SuiteItem> clazz) {
        SuiteItem item = clazz.getDeclaredConstructor(DataFile.class)
                .newInstance(this.dataFile);
        items.put(item.getId(), item);
        log.log(Level.INFO, String.format("Registered item: %s", item.getId()));
    }

    public Optional<SuiteItem> getItem(String id) {
        return Optional.ofNullable(items.get(id));
    }

    public Optional<SuiteItem> getItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (nbtItem.getKeys().contains(Constants.ITEM_NBT_KEY)) {
            String id = nbtItem.getString(Constants.ITEM_NBT_KEY);
            return getItem(id);
        }

        return Optional.empty();
    }

    public Collection<SuiteItem> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }
}
