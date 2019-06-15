package ru.fratask.mc.plugin.easycommands.executors.warp;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.entity.Warp;

import java.util.Set;

public class WarpListCommandExecutor implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        Set<Warp> warpSet = EasyCommandsPlugin.getInstance().getWarpSet();
        if (warpSet.size() == 0) {
            src.sendMessage(Text.of(TextColors.YELLOW, "Warps don't exist"));
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Warps [" + warpSet.size() + "]:");
            int counter = 1;
            for (Warp warp : warpSet) {
                stringBuilder.append("\n" + counter + ": " + warp.getName());
                counter++;
            }
            src.sendMessage(Text.of(TextColors.YELLOW, stringBuilder.toString()));
        }
        return CommandResult.success();
    }
}
