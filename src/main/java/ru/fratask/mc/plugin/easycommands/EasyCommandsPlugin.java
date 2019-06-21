package ru.fratask.mc.plugin.easycommands;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.event.world.LoadWorldEvent;
import org.spongepowered.api.event.world.SaveWorldEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import ru.fratask.mc.plugin.easycommands.dao.DataSource;
import ru.fratask.mc.plugin.easycommands.dao.home.HomeDAOImpl;
import ru.fratask.mc.plugin.easycommands.dao.warp.WarpDAOImpl;
import ru.fratask.mc.plugin.easycommands.entity.Home;
import ru.fratask.mc.plugin.easycommands.entity.Warp;
import ru.fratask.mc.plugin.easycommands.executors.gameMode.GameModeCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.home.DeleteHomeCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.home.HomeCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.home.HomeListCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.home.SetHomeCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.spawn.SpawnCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.vanish.VanishCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.warp.DeleteWarpCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.warp.SetWarpCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.warp.WarpCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.warp.WarpListCommandExecutor;

import java.util.*;

@Plugin(id = "easy-commands")
public class EasyCommandsPlugin {

    @Inject
    private Logger logger;

    private Table<UUID, String, Home> homeTable = HashBasedTable.create();
    private Set<Warp> warpSet = new HashSet<>();

    private static EasyCommandsPlugin instance;

    private static HomeDAOImpl homeDAO = new HomeDAOImpl();
    private static WarpDAOImpl warpDAO = new WarpDAOImpl();

    @Listener
    public void onServerStart(GameInitializationEvent event){
        instance = this;
        logger.info("EasyCommands plugin is loaded");

        Sponge.getCommandManager().register(instance, getVanishCommand(), "vanish");

        Sponge.getCommandManager().register(instance, getGameModeCommand(), "gm", "gamemode");

        Sponge.getCommandManager().register(instance, getHomeCommand(), "home");
        Sponge.getCommandManager().register(instance, getDeleteHomeCommand(), "deletehome");
        Sponge.getCommandManager().register(instance, getSetHomeCommand(), "sethome");
        Sponge.getCommandManager().register(instance, getHomeListCommand(), "homelist");

        Sponge.getCommandManager().register(instance, getWarpCommand(), "warp");
        Sponge.getCommandManager().register(instance, getDeleteWarpCommand(), "deletewarp");
        Sponge.getCommandManager().register(instance, getSetWarpCommand(), "setwarp");
        Sponge.getCommandManager().register(instance, getWarpListCommand(), "warplist");

        Sponge.getCommandManager().register(instance, getSpawnCommand(), "spawn");
    }

    @Listener
    public void onServerStarted(GameStartedServerEvent event){
        DataSource dataSource = DataSource.getInstance();
        dataSource.setUrl("jdbc:mysql://localhost:3306/minecraft");
        dataSource.setLogin("fratask");
        dataSource.setPassword("Forwolk95!");
        initHomes();
        initWarps();
    }

    @Listener
    public void onWorldSave(GameStoppedServerEvent event){
        uploadHomes();
        uploadWarps();
    }

    private CommandSpec getVanishCommand(){
        return CommandSpec.builder()
                .description(Text.of("Turns on/off your visible for players"))
                .executor(new VanishCommandExecutor())
                .build();
    }

    private CommandSpec getSpawnCommand(){
        return CommandSpec.builder()
                .description(Text.of("Teleporting you to spawn point!"))
                .executor(new SpawnCommandExecutor())
                .build();
    }

    private CommandSpec getHomeCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can teleport to home"))
                .arguments(
                        GenericArguments.optionalWeak(GenericArguments.string(Text.of("home")))
                )
                .executor(new HomeCommandExecutor())
                .build();
    }

    private CommandSpec getDeleteHomeCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can delete home"))
                .arguments(
                        GenericArguments.optionalWeak(GenericArguments.string(Text.of("home")))
                )
                .executor(new DeleteHomeCommandExecutor())
                .build();
    }

    private CommandSpec getSetHomeCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can create home"))
                .arguments(
                        GenericArguments.optionalWeak(GenericArguments.string(Text.of("home")))
                )
                .executor(new SetHomeCommandExecutor())
                .build();
    }

    private CommandSpec getHomeListCommand(){
        return CommandSpec.builder()
                .description(Text.of("You see your homes"))
                .executor(new HomeListCommandExecutor())
                .build();
    }

    private CommandSpec getWarpCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can teleport to warp"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("warp")))
                )
                .executor(new WarpCommandExecutor())
                .build();
    }

    private CommandSpec getDeleteWarpCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can delete warp"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("warp")))
                )
                .executor(new DeleteWarpCommandExecutor())
                .build();
    }

    private CommandSpec getSetWarpCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can create warp"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("warp")))
                )
                .executor(new SetWarpCommandExecutor())
                .build();
    }

    private CommandSpec getWarpListCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can see existing warps"))
                .executor(new WarpListCommandExecutor())
                .build();
    }

    private CommandSpec getGameModeCommand(){
        return CommandSpec.builder()
                .description(Text.of("Setup gameMode"))
                .arguments(
                        GenericArguments.optionalWeak(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("gameMode")))
                )
                .executor(new GameModeCommandExecutor())
                .build();
    }

    public Logger getLogger() {
        return logger;
    }

    public static EasyCommandsPlugin getInstance() {
        if (instance == null){
            instance = new EasyCommandsPlugin();
        }
        return instance;
    }

    public Table<UUID, String, Home> getHomeTable() {
        return homeTable;
    }

    public Set<Warp> getWarpSet() {
        return warpSet;
    }

    private void initHomes() {
        List<Home> homeList = homeDAO.findAll();
        int counter = 0;
        for (Home home: homeList){
            EasyCommandsPlugin.getInstance().getHomeTable().put(home.getOwner(), home.getName(), home);
            counter++;
        }
        EasyCommandsPlugin.getInstance().getLogger().info("Home initilized! [" + counter + "];");
    }

    private void uploadHomes(){
        int counter = 0;
        homeDAO.deleteALL();
        for (Home home: EasyCommandsPlugin.getInstance().getHomeTable().values()){
            homeDAO.insert(home);
            counter++;
        }
        EasyCommandsPlugin.getInstance().getLogger().info("Homes uploaded! [" + counter + "];");
    }

    private void initWarps() {
        warpSet = warpDAO.findAll();
        EasyCommandsPlugin.getInstance().getLogger().info("Warps initilized! [" + warpSet.size() + "];");
    }

    private void uploadWarps(){
        int counter = 0;
        warpDAO.deleteALL();
        for (Warp warp: warpSet){
            warpDAO.insert(warp);
            counter++;
        }
        EasyCommandsPlugin.getInstance().getLogger().info("Warps uploaded! [" + counter + "];");
    }
}
