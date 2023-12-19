package mcheroes.core.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Formatter {
    private static final int CENTER_PX = 154;
    private static final Pattern MATCH_PATTERN = Pattern.compile("<([^<]+)>|([^ <]+ ?)");

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

    public static String center(String str) {
        if (str == null || str.isEmpty()) return "";
        int messagePxSize = 0;
        boolean isBold = false;

        Matcher matcher = MATCH_PATTERN.matcher(str);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // We have a tag, check if it is bold
                String tag = matcher.group(1).toLowerCase(Locale.ROOT);
                if (tag.equals("b") || tag.equals("bold")) {
                    isBold = true;
                } else if (tag.equals("/b") || tag.equals("/bold")) {
                    isBold = false;
                }
            } else {
                for (char c : matcher.group(2).toCharArray()) {
                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    messagePxSize++;
                }
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        return sb + str;
    }

//    public static String center(String str) {
//        if (str == null || str.isEmpty()) return "";
//        Component component = MiniMessage.miniMessage().deserialize(str);
//
//        int messagePxSize = 0;
//        boolean previousCode = false;
//        boolean isBold;
//
//        List<Component> individualComponents = new ArrayList<>();
//        individualComponents.add(component);
//        for (List<Component> nested = component.children(); !nested.isEmpty(); nested = nested.get(0).children()) {
//            individualComponents.addAll(nested);
//        }
//        for (Component child : individualComponents) {
//            String message = ((TextComponent) child).content();
//            isBold = child.hasDecoration(TextDecoration.BOLD);
//            for (char c : message.toCharArray()) {
//                if (c == '§') {
//                    previousCode = true;
//                } else if (previousCode) {
//                    previousCode = false;
//                    isBold = c == 'l' || c == 'L';
//                } else {
//                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
//                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
//                    messagePxSize++;
//                }
//            }
//        }
//
//        int halvedMessageSize = messagePxSize / 2;
//        int toCompensate = CENTER_PX - halvedMessageSize;
//        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
//        int compensated = 0;
//        StringBuilder sb = new StringBuilder();
//        while (compensated < toCompensate) {
//            sb.append(" ");
//            compensated += spaceLength;
//        }
//
//        return sb + str;
//    }

    public enum DefaultFontInfo {
        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('&', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PERENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 3),
        DEFAULT('a', 4);

        private final char character;
        private final int length;

        DefaultFontInfo(char character, int length) {
            this.character = character;
            this.length = length;
        }

        public static DefaultFontInfo getDefaultFontInfo(char c) {
            for (DefaultFontInfo dFI : DefaultFontInfo.values()) {
                if (dFI.getCharacter() == c) return dFI;
            }
            return DefaultFontInfo.DEFAULT;
        }

        public char getCharacter() {
            return this.character;
        }

        public int getLength() {
            return this.length;
        }

        public int getBoldLength() {
            if (this == DefaultFontInfo.SPACE) return this.getLength();
            return this.length + 1;
        }
    }
}
