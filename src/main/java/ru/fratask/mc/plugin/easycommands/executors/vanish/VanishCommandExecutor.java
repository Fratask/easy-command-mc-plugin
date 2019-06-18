package ru.fratask.mc.plugin.easycommands.executors.vanish;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;

public class VanishCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        Player player = (Player) src;

        if (player.get(Keys.VANISH).get()) {
            player.offer(Keys.VANISH, false);
            logger.info(player.getName() + " Unvanished");
            player.sendMessage(Text.of(TextColors.YELLOW, "Players can see you now!"));
        } else {
            player.offer(Keys.VANISH, true);
            logger.info(player.getName() + " Vanished");
            player.sendMessage(Text.of(TextColors.YELLOW, "Players can't see you now!"));
        }


        return CommandResult.success();
    }
}
