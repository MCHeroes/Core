package mcheroes.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public final class Position {
    private final String world;
    private final double x, y, z;
    private final float yaw, pitch;

    private Position(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public static Position of(Location location) {
        return new Position(location.getWorld().getName(), location.x(), location.y(), location.z(), location.getYaw(), location.getPitch());
    }

    public static Position of(String world, double x, double y, double z, float yaw, float pitch) {
        return new Position(world, x, y, z, yaw, pitch);
    }

    public static Position of(String world, double x, double y, double z) {
        return new Position(world, x, y, z, 0, 0);
    }

    public static Position zero(String world) {
        return new Position(world, 0, 0, 0, 0, 0);
    }

    public Location toBukkit() {
        final World found = Bukkit.getWorld(world);
        if (found == null) throw new RuntimeException("Failed to find world: " + world);

        return new Location(found, x, y, z, yaw, pitch);
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
