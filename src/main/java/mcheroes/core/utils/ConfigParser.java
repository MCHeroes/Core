package mcheroes.core.utils;

import mcheroes.core.teams.Team;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;
import java.util.stream.Collectors;

public final class ConfigParser {
    public static Position parsePosition(ConfigurationSection config) {
        final String world = config.getString("world", "world");
        final double x = config.getDouble("x", 0.0);
        final double y = config.getDouble("y", 0.0);
        final double z = config.getDouble("z", 0.0);
        final float yaw = (float) config.getDouble("yaw", 0.0);
        final float pitch = (float) config.getDouble("pitch", 0.0);

        return Position.of(world, x, y, z, yaw, pitch);
    }

    public static Team parseTeam(ConfigurationSection config) {
        final String id = config.getName();
        return new Team(id, config.getString("name", id), config.getString("chat_prefix", id), TextColor.fromHexString(config.getString("color", "000000")), config.getStringList("members").stream().map(UUID::fromString).collect(Collectors.toSet()));
    }
}
