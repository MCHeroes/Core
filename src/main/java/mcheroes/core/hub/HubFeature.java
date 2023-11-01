package mcheroes.core.hub;

import mcheroes.core.api.data.DataStore;
import mcheroes.core.api.feature.CoreFeature;
import mcheroes.core.locale.LocaleAdapter;
import mcheroes.core.locale.Messages;
import mcheroes.core.utils.Permissions;
import mcheroes.core.utils.Position;
import mcheroes.core.utils.Scheduler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class HubFeature implements CoreFeature, Listener {
    private final BukkitCommandHandler commandHandler;
    private final DataStore dataStore;
    private final LocaleAdapter locale;
    private final Scheduler scheduler;
    private Location cachedLocation;

    public HubFeature(BukkitCommandHandler commandHandler, DataStore dataStore, LocaleAdapter locale, Scheduler scheduler) {
        this.commandHandler = commandHandler;
        this.dataStore = dataStore;
        this.locale = locale;
        this.scheduler = scheduler;
    }

    @Override
    public void load() {
        commandHandler.register(this);
        cachedLocation = dataStore.getHubSpawn().toBukkit();

        cachedLocation.getChunk().setForceLoaded(true);
        cachedLocation.getChunk().load();
    }

    @Override
    public void unload() {

    }

    @EventHandler
    public void on(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(cachedLocation);
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        scheduler.later(() -> event.getPlayer().teleport(cachedLocation), 1L);
    }

    @EventHandler
    public void on(PlayerRespawnEvent event) {
        // TODO: disable this event if current minigame overrides respawn mechanics
        event.setRespawnLocation(cachedLocation);
    }

    @EventHandler
    public void on(PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        if(player.getWorld().getName().equals(cachedLocation.getWorld().getName())) {
            // Reset some player attributes when they come back to hub
            player.setFoodLevel(20);
            player.setHealth(20);
            player.setFireTicks(0);
            player.setFreezeTicks(0);
        }
    }

    @EventHandler
    public void on(BlockBreakEvent event) {
        if(isRestricted(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        if(isRestricted(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player damager && isRestricted(damager)) event.setCancelled(true);
        if(event.getEntity() instanceof Player damagee && isRestricted(damagee)) event.setCancelled(true);
    }

    @EventHandler
    public void on(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player damagee && isRestricted(damagee)) event.setCancelled(true);
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if(isRestricted(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void on(FoodLevelChangeEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        
        if(isRestricted(player)) event.setCancelled(true);
    }

    private boolean isRestricted(Player player) {
        return cachedLocation != null && player.getWorld().getName().equals(cachedLocation.getWorld().getName()) && !player.hasPermission(Permissions.ADMIN);
    }

    @Command("hub")
    public void onHub(Player sender) {
        sender.teleport(cachedLocation);
    }

    @Command("sethub")
    public void onSetHub(Player sender) {
        if (!sender.hasPermission(Permissions.ADMIN)) {
            sender.sendMessage(Messages.NO_PERMISSION.build(locale));
            return;
        }

        if (cachedLocation != null) {
            cachedLocation.getChunk().setForceLoaded(false);
        }

        cachedLocation = sender.getLocation();
        cachedLocation.getChunk().setForceLoaded(true);

        dataStore.setHubSpawn(Position.of(cachedLocation));
        sender.sendMessage(Messages.HUB_SET_SUCCESSFULLY.build(locale));
    }
}
