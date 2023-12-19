package mcheroes.core.minigames.utils;

import mcheroes.core.CoreProvider;
import mcheroes.core.teams.utils.TeamUtil;
import mcheroes.core.utils.CountdownColors;
import mcheroes.core.utils.Formatter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class MinigameIntroducer {
    private static final Title.Times COUNTDOWN_TIMES = Title.Times.times(Ticks.duration(2L), Ticks.duration(18L), Ticks.duration(2L));
    private final int delayBetweenMessages;
    private final List<String> messages;
    private final int endDelay;
    private Runnable endCallback;

    public MinigameIntroducer(int delayBetweenMessages, List<String> messages, int endDelay) {
        this.delayBetweenMessages = delayBetweenMessages * 20; // Convert from seconds to ticks
        this.messages = messages;
        this.endDelay = endDelay * 20; // Convert from seconds to ticks
    }

    private void broadcast(String message, boolean title) {
        final Component converted = MiniMessage.miniMessage().deserialize(Formatter.center(message));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == null || TeamUtil.getTeam(CoreProvider.get().getActionManager(), player) == null) continue;

            if (title) {
                player.showTitle(Title.title(converted, Component.empty(), COUNTDOWN_TIMES));
            } else {
                player.sendMessage(converted);
            }
        }
    }

    public void setEndCallback(Runnable endCallback) {
        this.endCallback = endCallback;
    }

    public void start() {
        new BukkitRunnable() {
            private int index = 0;

            @Override
            public void run() {
                if (index >= messages.size()) {
                    cancel();
                    Bukkit.getScheduler().runTaskLater(CoreProvider.get(), MinigameIntroducer.this::startCountdown, endDelay);
                    return;
                }

                broadcast(messages.get(index++), false);
            }
        }.runTaskTimer(CoreProvider.get(), 0, delayBetweenMessages);
    }

    private void startCountdown() {
        new BukkitRunnable() {
            private int countdown = 5;

            @Override
            public void run() {
                if (countdown <= 0) {
                    cancel();
                    broadcast(CountdownColors.get(0) + "GO!", true);
                    endCallback.run();
                    return;
                }

                broadcast(CountdownColors.get(countdown) + countdown, true);
                countdown--;
            }
        }.runTaskTimer(CoreProvider.get(), 0, 20);
    }
}
