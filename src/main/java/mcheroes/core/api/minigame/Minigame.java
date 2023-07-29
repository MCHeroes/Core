package mcheroes.core.api.minigame;

public interface Minigame {
    String getId();

    void start();

    void stop();

    boolean hasStarted();

    boolean canStart();
}
