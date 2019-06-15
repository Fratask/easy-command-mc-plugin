package ru.fratask.mc.plugin.easycommands;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class Home {

    private String name;
    private Location<World> loc;
    private Player owner;

    public Home(Player owner, String HomeName, Location<World> loc) {
        this.owner = owner;
        this.name = HomeName;
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
