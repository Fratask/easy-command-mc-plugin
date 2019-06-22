package ru.fratask.mc.plugin.easycommands.executors.home;

import com.google.common.collect.Table;
import org.slf4j.Logger;
import org.spongepowered.api.command.CommandException;
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
import ru.fratask.mc.plugin.easycommands.entity.Home;

import java.util.Set;
import java.util.UUID;

public class HomeCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    public static CommandSpec getHomeCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can teleport to home"))
                .arguments(
                        GenericArguments.optionalWeak(GenericArguments.string(Text.of("home")))
                )
                .executor(new HomeCommandExecutor())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player owner = ((Player) src);
            UUID uuid = owner.getUniqueId();
            Table<UUID, String, Home> homeTable = EasyCommandsPlugin.getInstance().getHomeTable();
            String homeName;
            if (args.hasAny("home")) {
                homeName = (String) args.getOne("home").get();
            } else {
                homeName = "default";
            }
            if (!homeTable.contains(uuid, homeName)) {
                owner.sendMessage(Text.of(TextColors.RED, "You don't have home with this name!"));
            } else {
                owner.setLocation(homeTable.get(uuid, homeName).getLoc());
                owner.sendMessage(Text.of(TextColors.YELLOW, "You teleported to home!"));
                logger.info("Player " + owner.getName() + " teleported to home: " + homeTable.get(uuid, homeName).toString());
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, "You must be Player to using home commands!"));
        }
        return CommandResult.success();
    }
}
