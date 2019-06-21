package ru.fratask.mc.plugin.easycommands.entity;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import java.util.UUID;

public class Warp {
    public static final String TABLE_NAME = "warps";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String XCOORD_COLUMN = "xcoord";
    public static final String YCOORD_COLUMN = "ycoord";
    public static final String ZCOORD_COLUMN = "zcoord";
    public static final String OWNER_COLUMN = "owner";

    private Long id;
    private String name;
    private Location<World> loc;
    private UUID owner;

    public Warp(Long id, String name, Location<World> loc, UUID owner) {
        this.id = id;
        this.name = name;
        this.loc = loc;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location<World> getLoc() {
        return loc;
    }

    public void setLoc(Location<World> loc) {
        this.loc = loc;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
