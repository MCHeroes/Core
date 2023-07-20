package mcheroes.core.locale;

import net.kyori.adventure.text.Component;

public final class Messages {
    public static final Args2<Component, Component> CHAT_FORMAT = (locale, component, component2) -> locale.getArgs("chat_format", "{player}", component, "{message}", component2);

    public static final Args0 HUB_SET_SUCCESSFULLY = (locale) -> locale.get("hub_set_successfully");
    public static final Args0 NO_PERMISSION = (locale) -> locale.get("no_permission");

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
