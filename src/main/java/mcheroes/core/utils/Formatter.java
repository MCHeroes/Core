package mcheroes.core.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public final class Formatter {
    private Formatter() {
    }

    public static String replace(@Nullable Player reference, String string, Object... replacements) {
        if (replacements == null || replacements.length == 0) return placeholders(reference, string);

        for (int i = 0; i < replacements.length; i += 2) {
            string = string.replace(convert(replacements[i]), convert(replacements[i + 1]));
        }

        return placeholders(reference, string);
    }

    private static String placeholders(@Nullable Player reference, String string) {
        if (reference != null && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(reference, string);
        }

        return string;
    }

    private static String convert(Object object) {
        if (object instanceof Component component) {
            return MiniMessage.miniMessage().serialize(component);
        }

        return String.valueOf(object);
    }
}
