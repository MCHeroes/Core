package mcheroes.core.locale;

import mcheroes.core.utils.Formatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public final class LocaleParser {
    private LocaleParser() {
    }

    public static Component parse(@Nullable Player reference, String format, Object... args) {
        return MiniMessage.miniMessage().deserialize(Formatter.replace(reference, format, args), TagResolver.standard());
    }

    public static List<Component> parse(@Nullable Player reference, List<String> format, Object... args) {
        return format.stream().map(x -> Formatter.replace(reference, x, args)).map(x -> MiniMessage.miniMessage().deserialize(x, TagResolver.standard())).collect(Collectors.toList());
    }
}
