package ru.fratask.mc.plugin.easycommands.executors;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;
import ru.fratask.mc.plugin.easycommands.Home;

public class SetHomeCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        if (args.hasAny("home")){
            String homeName = (String) args.getOne("home").get();
            EasyCommandsPlugin.getInstance().getHomeSet().add(new Home((Player) src, homeName, ((Player) src).getLocation()));
            logger.info("Player " + src.getName() + " set home at " + ((Player) src).getLocation());
        } else {
            EasyCommandsPlugin.getInstance().getHomeSet().add(new Home((Player) src, " ", ((Player) src).getLocation()));
            logger.info("Player " + src.getName() + " set home at " + ((Player) src).getPosition());
        }
        return CommandResult.success();
    }
}
