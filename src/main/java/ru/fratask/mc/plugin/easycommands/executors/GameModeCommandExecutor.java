package ru.fratask.mc.plugin.easycommands.executors;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;


public class GameModeCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Integer gameMode = (Integer) args.getOne("gameMode").get();
        if (args.hasAny("player")){
            Player player = (Player) args.getOne("player").get();
            changeGameMode(player, gameMode);
            player.sendMessage(Text.of(TextColors.YELLOW, "Your gameMode %s now!", player.get(Keys.GAME_MODE).get().toString()));
        } else {
            changeGameMode((Player) src, gameMode);
            src.sendMessage(Text.of(TextColors.YELLOW, "Your gameMode %s now!", ((Player) src).get(Keys.GAME_MODE).get().toString()));
        }
        return CommandResult.success();
    }

    private void changeGameMode(Player player, Integer gameMode){
        switch (gameMode){
            case 0:
                player.offer(Keys.GAME_MODE, GameModes.SURVIVAL);
                break;
            case 1:
                player.offer(Keys.GAME_MODE, GameModes.CREATIVE);
                break;
            case 2:
                player.offer(Keys.GAME_MODE, GameModes.ADVENTURE);
                break;
            case 3:
                player.offer(Keys.GAME_MODE, GameModes.SPECTATOR);
                break;
        }
        logger.info("Player changed gameMode to " + player.get(Keys.GAME_MODE).get().toString());
    }
}
