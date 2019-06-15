package ru.fratask.mc.plugin.easycommands.executors;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.Home;

import java.util.Set;

public class DeleteHomeCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (args.hasAny("home")) {
            Player owner = (Player) src;
            String homeName = (String) args.getOne("home").get();
            Set<Home> homeSet = EasyCommandsPlugin.getInstance().getHomeSet();
            boolean deleted = false;
            for (Home home: homeSet){
                if (home.getName().equals(homeName) && home.getOwner().getName().equals(owner.getName())){
                    homeSet.remove(home);
                    logger.info("Player " + src + " deleted home [homeName]: " + home.getName());
                    deleted = true;
                    src.sendMessage(Text.of(TextColors.YELLOW, "You deleted home: " + homeName + "!"));
                }
            }
            if (!deleted){
                src.sendMessage(Text.of(TextColors.YELLOW, "You don't have home with this name!"));
            }
        } else {
            Player owner = (Player) src;
            String homeName = " ";
            Set<Home> homeSet = EasyCommandsPlugin.getInstance().getHomeSet();
            boolean deleted = false;
            for (Home home: homeSet){
                if (home.getName().equals(homeName) && home.getOwner().getName().equals(owner.getName())){
                    homeSet.remove(home);
                    logger.info("Player " + src + " deleted home [homeName]: default");
                    deleted = true;
                    src.sendMessage(Text.of(TextColors.YELLOW, "You deleted default home"));
                }
            }
            if (!deleted){
                src.sendMessage(Text.of(TextColors.YELLOW, "You don't have home with this name!"));
            }
        }
        return CommandResult.success();
    }
}
