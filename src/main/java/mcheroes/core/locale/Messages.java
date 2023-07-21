package mcheroes.core.locale;

import mcheroes.core.teams.Team;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;

import java.util.List;

public final class Messages {
    public static final Args3<Team, Component, Component> CHAT_FORMAT = (locale, team, player, msg) -> locale.getArgs("chat_format", "{player}", player, "{message}", msg, "{team}", team != null ? team.chatPrefix() : "");

    public static final Args0 HUB_SET_SUCCESSFULLY = (locale) -> locale.get("hub_set_successfully");
    public static final Args0 NO_PERMISSION = (locale) -> locale.get("no_permission");
    public static final Args2<OfflinePlayer, Team> ADDED_TEAM_PLAYER = (locale, player, team) -> locale.getArgs("added_team_player", "{player}", player.getName(), "{team}", team.name());
    public static final Args2<OfflinePlayer, Team> REMOVED_TEAM_PLAYER = (locale, player, team) -> locale.getArgs("removed_team_player", "{player}", player.getName(), "{team}", team.name());
    public static final Args2<OfflinePlayer, Team> PLAYER_NOT_FOUND_IN_TEAM = (locale, player, team) -> locale.getArgs("player_not_found_in_team", "{player}", player.getName(), "{team}", team.name());
    public static final Args0 POINTS_ACTION_SUCCESS = (locale) -> locale.get("points_action_success");
    public static final Args2<OfflinePlayer, Integer> POINTS_GET = (locale, player, points) -> locale.getArgs("points_get", "{player}", player.getName(), "{points}", points);
    public static final Args0 POINTS_GUI_TITLE = (locale) -> locale.get("points_gui_title");
    public static final Args2<Integer, OfflinePlayer> POINTS_GUI_HEAD_NAME = (locale, placement, player) -> locale.getArgs("points_gui_head_name", "{placement}", placement, "{player}", player.getName());
    public static final SplitArgs1<Integer> POINTS_GUI_HEAD_LORE = (locale, points) -> locale.getSplitArgs("points_gui_head_lore", "{points}", points);
    public static final Args0 RELOAD_SUCCESS = (locale) -> locale.get("reload_success");

    public interface Args0 {
        Component build(LocaleAdapter locale);
    }

    public interface Args1<A> {
        Component build(LocaleAdapter locale, A a);
    }

    public interface Args2<A, B> {
        Component build(LocaleAdapter locale, A a, B b);
    }

    public interface Args3<A, B, C> {
        Component build(LocaleAdapter locale, A a, B b, C c);
    }

    public interface SplitArgs1<A> {
        List<Component> build(LocaleAdapter locale, A a);
    }
}
