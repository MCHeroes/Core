package mcheroes.core.utils;

import org.jetbrains.annotations.NotNull;

public enum CountdownColors {
    FIVE(5, "<green>"),
    FOUR(4, "<green>"),
    THREE(3, "<yellow>"),
    TWO(2, "<yellow>"),
    ONE(1, "<red>"),
    GO(0, "<gold>");

    private final int number;
    private final String color;

    CountdownColors(int number, String color) {
        this.number = number;
        this.color = color;
    }

    @NotNull
    public static String get(int number) {
        for (CountdownColors value : values()) {
            if (value.number == number) return value.color;
        }

        return "<green>";
    }
}
