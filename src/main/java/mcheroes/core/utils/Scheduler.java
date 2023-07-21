package mcheroes.core.utils;

import mcheroes.core.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public final class Scheduler {
    private final CorePlugin plugin;
    private final BukkitScheduler scheduler = Bukkit.getScheduler();

    public Scheduler(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public BukkitTask run(Runnable run) {
        return scheduler.runTask(plugin, run);
    }

    public BukkitTask later(Runnable run, long delay) {
        return scheduler.runTaskLater(plugin, run, delay);
    }

    public BukkitTask timer(Runnable run, long delay, long period) {
        return scheduler.runTaskTimer(plugin, run, delay, period);
    }

    public BukkitTask runAsync(Runnable run) {
        return scheduler.runTaskAsynchronously(plugin, run);
    }

    public BukkitTask laterAsync(Runnable run, long delay) {
        return scheduler.runTaskLaterAsynchronously(plugin, run, delay);
    }

    public BukkitTask timerAsync(Runnable run, long delay, long period) {
        return scheduler.runTaskTimerAsynchronously(plugin, run, delay, period);
    }
}
