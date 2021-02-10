package gg.vh.modsuite.base.user;

import com.google.inject.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserHandler implements Listener {
    @Inject
    private UserManager um;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        um.register(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        um.unregister(event.getPlayer());
    }
}
