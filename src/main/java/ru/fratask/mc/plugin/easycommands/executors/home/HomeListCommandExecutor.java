package ru.fratask.mc.plugin.easycommands.executors.home;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.entity.Home;

import java.util.Set;

public class HomeListCommandExecutor implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        Set<Home> homeSet = EasyCommandsPlugin.getInstance().getHomeSet();
        StringBuilder stringBuilder = new StringBuilder();
        boolean haveHome = false;
        stringBuilder.append("Your homes [" + homeSet.size() + "]:");
        for (Home home: homeSet){
            int counter = 1;
            if (home.getOwner().getName().equals(src.getName())){
                haveHome = true;
                stringBuilder.append("\n" + counter + ": " + home.getName());
                counter++;
            }
        }
        if (!haveHome){
            src.sendMessage(Text.of(TextColors.YELLOW, "You don't have home!"));
        } else {
            src.sendMessage(Text.of(TextColors.YELLOW, stringBuilder.toString()));
        }
        return CommandResult.success();
    }
}
