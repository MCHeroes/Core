package mcheroes.core.teams;

import mcheroes.core.action.ActionManager;
import mcheroes.core.api.action.ActionHandler;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.points.actions.SetPointsAction;
import mcheroes.core.teams.actions.GetTeamAction;
import mcheroes.core.utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;
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

import static mcheroes.core.utils.ConfigParser.parseTeam;

@Command("teams")
public class TeamsFeature implements CoreFeature, ActionHandler<GetTeamAction, Team> {
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
        reloadTeams();

        actionManager.set(GetTeamAction.class, this);

        commandHandler.registerValueResolver(Team.class, context -> {
            final String id = context.pop();
            if (!teams.containsKey(id)) {
                throw new MissingArgumentException(context.parameter());
            }

            return teams.get(id);
        });
        commandHandler.registerValueResolver(TeamPlayer.class, context -> {
            final String username = context.pop();
            final OfflinePlayer found = Bukkit.getOfflinePlayer(username);
            if (!found.isOnline() && !found.hasPlayedBefore()) {
                throw new InvalidPlayerException(context.parameter(), username);
            }

            return new TeamPlayer(found);
        });
        commandHandler.getAutoCompleter().registerParameterSuggestions(Team.class, SuggestionProvider.of(teams::keySet));
        commandHandler.getAutoCompleter().registerParameterSuggestions(TeamPlayer.class, (args, sender, command) -> {
            if (args.isEmpty()) {
                return Collections.emptyList();
            }

            final String teamId = args.get(2);
            final Team team = teams.get(teamId);
            if (team == null) {
                return Collections.emptyList();
            }

            return team.members().stream().map(x -> Bukkit.getOfflinePlayer(x).getName()).collect(Collectors.toList());
        });
        commandHandler.register(this);
    }

    private void reloadTeams() {
        teams.clear();
        config.getConfigurationSection("teams").getValues(false).forEach((id, data) -> teams.put(id, parseTeam((ConfigurationSection) data)));
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

        final Team current = getCurrentTeam(target.getUniqueId());
        if (current != null) {
            current.members().remove(target.getUniqueId());
            saveTeam(current);
        }

        team.members().add(target.getUniqueId());
        saveTeam(team);

        sender.sendMessage(Messages.ADDED_TEAM_PLAYER.build(locale, target, team));
    }

    @Subcommand("removeplayer")
    public void onRemovePlayer(CommandSender sender, Team team, TeamPlayer target) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        if (team.members().remove(target.player().getUniqueId())) {
            saveTeam(team);

            sender.sendMessage(Messages.REMOVED_TEAM_PLAYER.build(locale, target.player(), team));
        } else {
            sender.sendMessage(Messages.PLAYER_NOT_FOUND_IN_TEAM.build(locale, target.player(), team));
        }
    }

    @Subcommand("resetpoints")
    public void onResetPoints(CommandSender sender, Team team) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        for (UUID member : team.members()) {
            actionManager.run(new SetPointsAction(member, 0));
        }
        sender.sendMessage(Messages.POINTS_ACTION_SUCCESS.build(locale));
    }

    @Subcommand("reload")
    public void onReload(CommandSender sender) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        try {
            config.load(configFile);
            reloadTeams();
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        sender.sendMessage(Messages.RELOAD_SUCCESS.build(locale));
    }

    private void saveTeam(Team team) {
        team.save(Objects.requireNonNull(config.getConfigurationSection("teams." + team.id()), "Failed to find team in config: " + team.id()));
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Team handle(GetTeamAction action) {
        return getCurrentTeam(action.player());
    }

    @Nullable
    private Team getCurrentTeam(UUID player) {
        for (Team team : teams.values()) {
            if (team.isInTeam(player)) return team;
        }

        return null;
    }
}
