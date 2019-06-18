package ru.fratask.mc.plugin.easycommands.entity;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class Warp {
    private String name;
    private Location<World> loc;
    private Player owner;

    public Warp(Player owner, String warpName, Location<World> loc) {
        this.owner = owner;
        this.name = warpName;
        this.loc = loc;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Location<World> getLoc() {
        return loc;
    }
}
