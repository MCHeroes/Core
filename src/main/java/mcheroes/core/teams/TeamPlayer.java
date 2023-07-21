package mcheroes.core.teams;

import org.bukkit.OfflinePlayer;

// Wrapper for OfflinePlayer so auto complete suggestions can be separated.
public record TeamPlayer(OfflinePlayer player) {

}
