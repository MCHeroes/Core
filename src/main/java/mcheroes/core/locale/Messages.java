package mcheroes.core.locale;

import mcheroes.core.teams.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;

public final class Messages {
    public static final Args2<Component, Component> CHAT_FORMAT = (locale, component, component2) -> locale.getArgs("chat_format", "{player}", component, "{message}", component2);

    public static final Args0 HUB_SET_SUCCESSFULLY = (locale) -> locale.get("hub_set_successfully");
    public static final Args0 NO_PERMISSION = (locale) -> locale.get("no_permission");
    public static final Args2<OfflinePlayer, Team> ADDED_TEAM_PLAYER = (locale, player, team) -> locale.getArgs("added_team_player", "{player}", player.getName(), "{team}", team.name());
    public static final Args2<OfflinePlayer, Team> REMOVED_TEAM_PLAYER = (locale, player, team) -> locale.getArgs("removed_team_player", "{player}", player.getName(), "{team}", team.name());
    public static final Args2<OfflinePlayer, Team> PLAYER_NOT_FOUND_IN_TEAM = (locale, player, team) -> locale.getArgs("player_not_found_in_team", "{player}", player.getName(), "{team}", team.name());

    public interface Args0 {
        Component build(LocaleAdapter locale);
    }

    public interface Args1<A> {
        Component build(LocaleAdapter locale, A a);
    }

    public interface Args2<A, B> {
        Component build(LocaleAdapter locale, A a, B b);
    }
}
