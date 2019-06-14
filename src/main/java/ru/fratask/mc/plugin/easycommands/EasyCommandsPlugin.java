package ru.fratask.mc.plugin.easycommands;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.executors.GameModeCommandExecutor;
import ru.fratask.mc.plugin.easycommands.executors.VanishCommandExecutor;

import java.util.HashMap;
import java.util.Map;

@Plugin(id = "easy-commands")
public class EasyCommandsPlugin {

    @Inject
    private Logger logger;

    private static EasyCommandsPlugin instance;

    private Map<String, Boolean> playerHasVanish = new HashMap<String, Boolean>();

    @Listener
    public void onServerStart(GameInitializationEvent event){
        instance = this;
        logger.info("EasyCommands plugin is loaded");

        Sponge.getCommandManager().register(instance, getVanishCommand(), "vanish");
        Sponge.getCommandManager().register(instance, getGameModeCommand(), "gm", "gamemode");
    }

    private CommandSpec getVanishCommand(){
        return CommandSpec.builder()
                .description(Text.of("Turns on/off your visible for players"))
                .executor(new VanishCommandExecutor())
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

    public Map<String, Boolean> getPlayerHasVanish() {
        return playerHasVanish;
    }
}
