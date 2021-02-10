package gg.vh.modsuite;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import gg.vh.modsuite.base.chat.ChatCommands;
import gg.vh.modsuite.base.chat.ChatHandler;
import gg.vh.modsuite.base.item.ItemListener;
import gg.vh.modsuite.base.item.ItemRegistry;
import gg.vh.modsuite.base.item.impl.SampleItem;
import gg.vh.modsuite.base.user.state.StateCommand;
import gg.vh.modsuite.lang.Language;
import gg.vh.modsuite.module.DataFileModule;
import gg.vh.modsuite.module.PluginModule;
import gg.vh.modsuite.base.user.UserHandler;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class ModSuite extends JavaPlugin {
    @Getter
    private Injector injector;

    @Override
    public void onLoad() {
        saveDefaultConfig();

        PluginModule pluginModule = new PluginModule(this);
        DataFileModule dataFileModule = new DataFileModule();
        injector = Guice.createInjector(
                pluginModule,
                dataFileModule
        );
    }

    @Override
    public void onEnable() {
        Language language = injector.getInstance(Language.class);
        language.load();

        ItemRegistry registry = injector.getInstance(ItemRegistry.class);
        registry.register(SampleItem.class);

        registerCommands(
                ChatCommands.class,
                StateCommand.class
        );

        registerListeners(
                ChatHandler.class,
                UserHandler.class,
                ItemListener.class
        );
    }

    @SafeVarargs
    private final void registerListeners(Class<? extends Listener>... listeners) {
        Arrays.stream(listeners)
                .forEach(listener -> getServer().getPluginManager().registerEvents(injector.getInstance(listener), this));
    }

    @SafeVarargs
    private final void registerCommands(Class<? extends BaseCommand>... commands) {
        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        Arrays.stream(commands)
                .forEach(command -> commandManager.registerCommand(injector.getInstance(command)));
    }
}
