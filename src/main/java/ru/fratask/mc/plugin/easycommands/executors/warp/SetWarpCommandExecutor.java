package ru.fratask.mc.plugin.easycommands.executors.warp;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.entity.Warp;

import java.util.HashSet;
import java.util.Set;

public class SetWarpCommandExecutor implements CommandExecutor {


    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        String warpName = (String) args.getOne("warp").get();
        Set<Warp> warpSet = new HashSet<>();
        for (Warp warp: warpSet){
            if (warp.getName().equals(warpName) && warp.getOwner().equals(src)){
                warpSet.remove(warp);
            }
        }
        EasyCommandsPlugin.getInstance().getWarpSet().add(new Warp((Player) src, warpName, ((Player) src).getLocation()));
        logger.info("Player " + src.getName() + " set warp at " + ((Player) src).getLocation());
        src.sendMessage(Text.of(TextColors.YELLOW, "You set warp " + warpName));
        return CommandResult.success();
    }
}
