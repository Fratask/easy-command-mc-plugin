package ru.fratask.mc.plugin.easycommands.executors.home;

import com.google.common.collect.Table;
import org.slf4j.Logger;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.entity.Home;

public class DeleteHomeCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (args.hasAny("home")) {
            Player owner = (Player) src;
            String homeName = (String) args.getOne("home").get();
            Table<Player, String, Home> homeTable = EasyCommandsPlugin.getInstance().getHomeTable();
            if (!homeTable.contains(owner, homeName)){
                owner.sendMessage(Text.of(TextColors.RED, "You don't have home with this name!"));
            } else{
                homeTable.remove(owner, homeName);
                owner.sendMessage(Text.of(TextColors.YELLOW, "Home deleted!"));
                logger.info("Player " + owner.getName() + " removed " + homeName + " home!");
            }
        } else {
            Player owner = (Player) src;
            String homeName = "default";
            Table<Player, String, Home> homeTable = EasyCommandsPlugin.getInstance().getHomeTable();
            if (!homeTable.contains(owner, homeName)){
                owner.sendMessage(Text.of(TextColors.RED, "You don't have home with this name!"));
            } else{
                homeTable.remove(owner, homeName);
                owner.sendMessage(Text.of(TextColors.YELLOW, "Home deleted!"));
                logger.info("Player " + owner.getName() + " removed " + homeName + " home!");
            }
        }
        return CommandResult.success();
    }
}
