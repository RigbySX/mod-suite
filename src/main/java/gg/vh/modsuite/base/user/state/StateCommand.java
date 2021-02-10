package gg.vh.modsuite.base.user.state;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.google.inject.Inject;
import gg.vh.modsuite.base.user.User;
import gg.vh.modsuite.base.user.UserManager;
import gg.vh.modsuite.lang.Language;
import org.bukkit.entity.Player;

@CommandAlias("modmode|staffmode")
public class StateCommand extends BaseCommand {
    static final String COMMAND_PERM = "modsuite.modmode";

    @Inject
    private UserManager um;
    @Inject
    private StateManager sm;
    @Inject
    private Language language;

    @Default
    @CommandPermission(COMMAND_PERM)
    public void toggle(Player sender) {
        User user = um.get(sender);
        final boolean toggle = !sm.isModMode(user);

        if (toggle) sm.enableModMode(user);
        else sm.disableModMode(user);

        String message = (toggle ? "mod-mode-on" : "mod-mode-off");
        user.message(language.translate(message));
    }
}
