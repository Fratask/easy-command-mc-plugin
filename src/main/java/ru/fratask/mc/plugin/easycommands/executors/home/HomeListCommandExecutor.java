package ru.fratask.mc.plugin.easycommands.executors.home;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.entity.Home;

public class HomeListCommandExecutor implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (src instanceof Player) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean haveHome = EasyCommandsPlugin.getInstance().getHomeTable().row(((Player) src).getUniqueId()).size() > 0;
            if (!haveHome) {
                src.sendMessage(Text.of(TextColors.RED, "You don't have home!"));
            } else {
                stringBuilder.append("Your homes [" + EasyCommandsPlugin.getInstance().getHomeTable().row(((Player) src).getUniqueId()).size() + "]:");
                int counter = 1;
                for (Home home : EasyCommandsPlugin.getInstance().getHomeTable().values()) {
                    if (home.getOwner().equals(((Player) src).getUniqueId())) {
                        stringBuilder.append("\n" + counter + ": " + home.getName());
                        counter++;
                    }
                }
                src.sendMessage(Text.of(TextColors.YELLOW, stringBuilder.toString()));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "You must be Player to using home commands!"));
        }
        return CommandResult.success();
    }
}
