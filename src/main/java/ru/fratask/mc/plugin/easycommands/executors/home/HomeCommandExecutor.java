package ru.fratask.mc.plugin.easycommands.executors.home;

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
import ru.fratask.mc.plugin.easycommands.entity.Home;

import java.util.Set;

public class HomeCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (args.hasAny("home")) {
            Player owner = (Player) src;
            String homeName = (String) args.getOne("home").get();
            Set<Home> homeSet = EasyCommandsPlugin.getInstance().getHomeSet();
            boolean teleported = false;
            for (Home home: homeSet){
                if (home.getName().equals(homeName) && home.getOwner().getName().equals(owner.getName())){
                    ((Player) src).setLocation(home.getLoc());
                    logger.info("Player " + src + " teleported to home [homeName]: " + home.getName() + " [homeLocation]: " + home.getLoc());
                    teleported = true;
                    src.sendMessage(Text.of(TextColors.YELLOW, "You teleported to " + homeName + "!"));
                }
            }
            if (!teleported){
                src.sendMessage(Text.of(TextColors.YELLOW, "You don't have home with this name!"));
            }
        } else {
            Player owner = (Player) src;
            String homeName = "default";
            Set<Home> homeSet = EasyCommandsPlugin.getInstance().getHomeSet();
            boolean teleported = false;
            for (Home home: homeSet){
                if (home.getName().equals(homeName) && home.getOwner().getName().equals(owner.getName())){
                    ((Player) src).setLocation(home.getLoc());
                    logger.info("Player " + src + " teleported to home [homeName]: default [homeLocation]: " + home.getLoc());
                    teleported = true;
                    src.sendMessage(Text.of(TextColors.YELLOW, "You teleported to default home"));
                }
            }
            if (!teleported){
                src.sendMessage(Text.of(TextColors.YELLOW, "You don't have home with this name!"));
            }
        }
        return CommandResult.success();
    }
}
