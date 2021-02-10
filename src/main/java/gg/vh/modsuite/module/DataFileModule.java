package gg.vh.modsuite.module;

import com.google.inject.*;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Named;
import gg.vh.modsuite.ModSuite;
import gg.vh.modsuite.file.DataFile;
import gg.vh.modsuite.file.DataFileFactory;

import java.io.File;

public class DataFileModule extends AbstractModule {

    @Override
    protected void configure() {
        FactoryModuleBuilder builder = new FactoryModuleBuilder();
        Module module = builder.build(DataFileFactory.class);
        install(module);
    }

    @Provides
    @Singleton
    @Named("items.yml")
    DataFile provideItemsFile(ModSuite plugin) {
        File file = new File(plugin.getDataFolder(), "items.yml");
        return new DataFile(plugin, file);
    }

    @Provides
    @Singleton
    @Named("lang.yml")
    DataFile provideLangFile(ModSuite plugin) {
        File file = new File(plugin.getDataFolder(), "lang.yml");
        return new DataFile(plugin, file);
    }
}
