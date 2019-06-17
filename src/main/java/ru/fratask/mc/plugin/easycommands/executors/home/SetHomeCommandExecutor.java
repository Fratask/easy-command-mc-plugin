package ru.fratask.mc.plugin.easycommands.executors.home;

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

import java.util.HashSet;
import java.util.Set;

public class SetHomeCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        Set<Home> homeSet = new HashSet<>();
        if (args.hasAny("home")){
            String homeName = (String) args.getOne("home").get();
            EasyCommandsPlugin.getInstance().getHomeTable().put((Player) src, homeName, new Home((Player) src, homeName, ((Player) src).getLocation()));
            logger.info("Player " + src.getName() + " set home " + homeName + " at " + ((Player) src).getLocation());
            src.sendMessage(Text.of(TextColors.YELLOW, "You set home " + homeName));
        } else {
            String homeName = "default";
            EasyCommandsPlugin.getInstance().getHomeTable().put((Player) src, homeName, new Home((Player) src, homeName, ((Player) src).getLocation()));
            logger.info("Player " + src.getName() + " set home " + homeName + " at " + ((Player) src).getLocation());
            src.sendMessage(Text.of(TextColors.YELLOW, "You set default home!"));
        }
        return CommandResult.success();
    }
}
