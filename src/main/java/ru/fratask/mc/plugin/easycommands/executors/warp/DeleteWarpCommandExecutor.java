package ru.fratask.mc.plugin.easycommands.executors.warp;

import org.slf4j.Logger;
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

public class DeleteWarpCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    public static CommandSpec getDeleteWarpCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can delete warp"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("warp")))
                )
                .executor(new DeleteWarpCommandExecutor())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (src instanceof Player) {
            String warpName = (String) args.getOne("warp").get();
            UUID owner = ((Player) src).getUniqueId();
            Set<Warp> warpSet = EasyCommandsPlugin.getInstance().getWarpSet();
            boolean deleted = false;
            for (Warp warp : warpSet) {
                if (warp.getName().equals(warpName) && warp.getOwner().equals(owner)) {
                    warpSet.remove(warp);
                    deleted = true;
                    src.sendMessage(Text.of(TextColors.YELLOW, "You deleted warp: " + warpName + "!"));
                    logger.info("Player " + src + " deleted warp [warpName]: " + warpName);
                    break;
                }
            }
            if (!deleted) {
                src.sendMessage(Text.of(TextColors.RED, "Warp with this name doesn't exist!"));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "You must be Player to using warp commands!"));
        }
        return CommandResult.success();
    }
}
