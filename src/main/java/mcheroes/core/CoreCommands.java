package mcheroes.core;


import mcheroes.core.locale.Messages;
import mcheroes.core.utils.Permissions;
import org.bukkit.command.CommandSender;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;

@Command("core")
class CoreCommands {
    private final CorePlugin plugin;

    CoreCommands(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Subcommand("reload")
    public void onReload(CommandSender sender) {
        if (checkAdmin(sender)) return;

        plugin.reloadConfig();
        plugin.getLocale().reload();
        sender.sendMessage(Messages.RELOAD_SUCCESS.build(plugin.getLocale()));
    }

    private boolean checkAdmin(CommandSender sender) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(plugin.getLocale()));
            return true;
        }
        return false;
    }
}
