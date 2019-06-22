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
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.entity.Warp;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SetWarpCommandExecutor implements CommandExecutor {


    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    public static CommandSpec getSetWarpCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can create warp"))
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("warp")))
                )
                .executor(new SetWarpCommandExecutor())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (src instanceof Player) {
            Long id = null;
            String warpName = (String) args.getOne("warp").get();
            Location<World> location = ((Player) src).getLocation();
            UUID owner = ((Player) src).getUniqueId();

            Set<Warp> warpSet = EasyCommandsPlugin.getInstance().getWarpSet();
            for (Warp warp : warpSet) {
                if (warp.getName().equals(warpName) && warp.getOwner().equals(owner)) {
                    id = warp.getId();
                    warpSet.remove(warp);
                }
                if (warp.getName().equals(warpName) && !warp.getOwner().equals(owner)) {
                    src.sendMessage(Text.of(TextColors.RED, "You don't creator of this warp!"));
                    return CommandResult.success();
                }
            }
            if (id == null){
                id = Long.valueOf(warpSet.size());
            }
            EasyCommandsPlugin.getInstance().getWarpSet().add(new Warp(id, warpName, location, owner));
            logger.info("Player " + src.getName() + " set warp at " + ((Player) src).getLocation());
            src.sendMessage(Text.of(TextColors.YELLOW, "You set warp " + warpName));
        } else {
            src.sendMessage(Text.of(TextColors.RED, "You must be Player to using warp commands!"));
        }
        return CommandResult.success();
    }
}
