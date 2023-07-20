package mcheroes.core.locale;

import mcheroes.core.utils.Formatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public final class LocaleParser {
    private LocaleParser() {
    }

    public static Component parse(String format, Object... args) {
        return MiniMessage.miniMessage().deserialize(Formatter.replace(format, args), TagResolver.standard());
    }
}
