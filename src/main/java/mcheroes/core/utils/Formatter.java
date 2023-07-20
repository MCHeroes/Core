package mcheroes.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class Formatter {
    private Formatter() {
    }

    public static String replace(String string, Object... replacements) {
        if (replacements == null || replacements.length == 0) return string;

        for (int i = 0; i < replacements.length; i += 2) {
            string = string.replace(convert(replacements[i]), convert(replacements[i + 1]));
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
