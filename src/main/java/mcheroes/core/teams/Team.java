package mcheroes.core.teams;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record Team(String id, String name, String chatPrefix, TextColor color, Set<UUID> members) {
    public boolean isInTeam(UUID player) {
        return members.contains(player);
    }

    public void save(ConfigurationSection config) {
        config.set("name", name);
        config.set("chat_prefix", chatPrefix);
        config.set("color", color.asHexString());
        config.set("members", members.stream().map(UUID::toString).toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Team{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", chatPrefix='" + chatPrefix + '\'' + ", color=" + color + ", members=" + members + '}';
    }
}
