package mcheroes.core.teams;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.points.actions.PointSetAction;
import mcheroes.core.utils.Permissions;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.bukkit.exception.InvalidPlayerException;
import revxrsal.commands.exception.MissingArgumentException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Command("teams")
public class TeamsFeature implements CoreFeature {
    private final Map<String, Team> teams = new HashMap<>();
    private final File configFile;
    private final YamlConfiguration config;
    private final BukkitCommandHandler commandHandler;
    private final LocaleAdapter locale;
    private final ActionManager actionManager;

    public TeamsFeature(File configFile, BukkitCommandHandler commandHandler, LocaleAdapter locale, ActionManager actionManager) {
        this.configFile = configFile;
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.commandHandler = commandHandler;
        this.locale = locale;
        this.actionManager = actionManager;
    }

    @Override
    public void load() {
        config.getConfigurationSection("teams").getValues(false).forEach((id, data) -> {
            final ConfigurationSection cs = (ConfigurationSection) data;

            teams.put(id, new Team(id, cs.getString("name", id), cs.getString("chat_prefix", id), TextColor.fromHexString(cs.getString("color", "000000")), cs.getStringList("members").stream().map(UUID::fromString).collect(Collectors.toSet())));
        });

        commandHandler.registerValueResolver(Team.class, context -> {
            final String id = context.pop();
            if (!teams.containsKey(id)) {
                throw new MissingArgumentException(context.parameter());
            }

            return teams.get(id);
        });
        commandHandler.registerValueResolver(TeamPlayer.class, context -> {
            final String username = context.pop();
            final OfflinePlayer found = Bukkit.getOfflinePlayerIfCached(username);
            if (found == null || !found.hasPlayedBefore()) {
                throw new InvalidPlayerException(context.parameter(), username);
            }

            return new TeamPlayer(found);
        });
        commandHandler.getAutoCompleter().registerParameterSuggestions(Team.class, SuggestionProvider.of(teams::keySet));
        commandHandler.getAutoCompleter().registerParameterSuggestions(TeamPlayer.class, (args, sender, command) -> {
            if (args.size() == 0) {
                return Collections.emptyList();
            }

            final String teamId = args.get(0);
            final Team team = teams.get(teamId);
            if (team == null) {
                return Collections.emptyList();
            }

            return team.members().stream().map(x -> Bukkit.getOfflinePlayer(x).getName()).collect(Collectors.toList());
        });
        commandHandler.register(this);
    }

    @Override
    public void unload() {

    }

    @Subcommand("addplayer")
    public void onAddPlayer(CommandSender sender, Team team, OfflinePlayer target) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        team.members().add(target.getUniqueId());
        saveTeam(team);

        sender.sendMessage(Messages.ADDED_TEAM_PLAYER.build(locale, target, team));
    }

    @Subcommand("removeplayer")
    public void onRemovePlayer(CommandSender sender, Team team, OfflinePlayer target) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        if (team.members().remove(target.getUniqueId())) {
            saveTeam(team);

            sender.sendMessage(Messages.REMOVED_TEAM_PLAYER.build(locale, target, team));
        } else {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND_IN_TEAM.build(locale, target, team));
        }
    }

    @Subcommand("resetpoints")
    public void onResetPoints(CommandSender sender, Team team) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        for (UUID member : team.members()) {
            actionManager.run(new PointSetAction(member, 0));
        }
        sender.sendMessage(Messages.POINTS_ACTION_SUCCESS.build(locale));
    }

    private void saveTeam(Team team) {
        team.save(Objects.requireNonNull(config.getConfigurationSection("teams." + team.id()), "Failed to find team in config: " + team.id()));
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
