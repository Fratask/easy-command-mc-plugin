package ru.fratask.mc.plugin.easycommands.executors.warp;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.entity.Warp;

import java.util.Set;
import java.util.UUID;

public class WarpCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    public static CommandSpec getWarpCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can teleport to warp"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("warp")))
                )
                .executor(new WarpCommandExecutor())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = ((Player) src);
            UUID uuid = player.getUniqueId();
            String warpName = (String) args.getOne("warp").get();
            Set<Warp> warpSet = EasyCommandsPlugin.getInstance().getWarpSet();
            Warp targetWarp = null;
            for (Warp warp: warpSet){
                if (warp.getName().equals(warpName)){
                    targetWarp = warp;
                }
            }
            if (targetWarp != null) {
                player.setLocation(targetWarp.getLoc());
                logger.info("Player " + player + " teleported to warp: " + warpName + " Loc: " + targetWarp.getLoc());
                src.sendMessage(Text.of(TextColors.YELLOW, "You teleported to " + warpName));
            } else {
                src.sendMessage(Text.of(TextColors.RED, "This warp doesn't exist!"));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "You must be Player to using home commands!"));
        }
        return CommandResult.success();
    }
}
