package ru.fratask.mc.plugin.easycommands.executors;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;

import java.lang.reflect.Field;

public class VanishCommandExecutor implements CommandExecutor {

    public CommandResult execute(CommandSource src, CommandContext args) {
        Player player = (Player) src;

        Field playerKeyVanishField = null;
        try {
            playerKeyVanishField = player.getClass().getDeclaredField("Vanish");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        playerKeyVanishField.setAccessible(true);
        if (EasyCommandsPlugin.getInstance().getPlayerHasVanish().get(player.getName()).equals(Boolean.TRUE)) {
            try {
                playerKeyVanishField.set(player.getClass(), new Boolean(false));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            EasyCommandsPlugin.getInstance().getPlayerHasVanish().put(player.getName(), Boolean.FALSE);
        } else {
            try {
                playerKeyVanishField.set(player.getClass(), new Boolean(true));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            EasyCommandsPlugin.getInstance().getPlayerHasVanish().put(player.getName(), Boolean.TRUE);
        }


        return CommandResult.success();
    }
}
