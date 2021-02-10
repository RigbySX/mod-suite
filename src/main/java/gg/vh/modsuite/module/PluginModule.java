package gg.vh.modsuite.module;

import com.google.inject.AbstractModule;
import gg.vh.modsuite.ModSuite;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PluginModule extends AbstractModule {
    private final ModSuite plugin;

    @Override
    protected void configure() {
        bind(ModSuite.class).toInstance(plugin);
    }
}
