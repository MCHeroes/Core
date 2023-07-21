package mcheroes.core.locale;

import mcheroes.core.utils.Formatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.List;
import java.util.stream.Collectors;

public final class LocaleParser {
    private LocaleParser() {
    }

    public static Component parse(String format, Object... args) {
        return MiniMessage.miniMessage().deserialize(Formatter.replace(format, args), TagResolver.standard());
    }

    public static List<Component> parse(List<String> format, Object... args) {
        return format.stream().map(x -> Formatter.replace(x, args)).map(x -> MiniMessage.miniMessage().deserialize(x, TagResolver.standard())).collect(Collectors.toList());
    }
}
