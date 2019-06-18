package ru.fratask.mc.plugin.easycommands.executors.warp;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.entity.Warp;

import java.util.Set;

public class WarpCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player owner = (Player) src;
        String warpName = (String) args.getOne("warp").get();
        Set<Warp> warpSet = EasyCommandsPlugin.getInstance().getWarpSet();
        boolean teleported = false;
        for (Warp warp: warpSet){
            if (warp.getName().equals(warpName) && warp.getOwner().getName().equals(owner.getName())){
                ((Player) src).setLocation(warp.getLoc());
                logger.info("Player " + src + " teleported to warp [homeName]: " + warp.getName() + " [homeLocation]: " + warp.getLoc());
                teleported = true;
                src.sendMessage(Text.of(TextColors.YELLOW, "You teleported to " + warpName + "!"));
            }
        }
        if (!teleported){
            src.sendMessage(Text.of(TextColors.RED, "Warp with this name doesn't exist!"));
        }
        return CommandResult.success();
    }
}
