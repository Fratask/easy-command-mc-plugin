package ru.fratask.mc.plugin.easycommands.executors.home;

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
import ru.fratask.mc.plugin.easycommands.dao.home.HomeDAOImpl;
import ru.fratask.mc.plugin.easycommands.entity.Home;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SetHomeCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    public static CommandSpec getSetHomeCommand(){
        return CommandSpec.builder()
                .description(Text.of("You can create home"))
                .arguments(
                        GenericArguments.optionalWeak(GenericArguments.string(Text.of("home")))
                )
                .executor(new SetHomeCommandExecutor())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        Set<Home> homeSet = new HashSet<>();
        if (src instanceof Player) {
            Long id;
            String homeName;
            Location<World> location = ((Player) src).getLocation();
            UUID uuid = ((Player) src).getUniqueId();

            if (args.hasAny("home")) {
                homeName = (String) args.getOne("home").get();

            } else {
                homeName = "default";
            }

            if (EasyCommandsPlugin.getInstance().getHomeTable().contains(uuid, homeName)){
                id = EasyCommandsPlugin.getInstance().getHomeTable().get(uuid, homeName).getId();
            } else {
                id = (long) EasyCommandsPlugin.getInstance().getHomeTable().size();
            }

            EasyCommandsPlugin.getInstance().getHomeTable().put(uuid, homeName, new Home(id, homeName, location, uuid));
            logger.info("Player " + src.getName() + " set home " + homeName + " at " + ((Player) src).getLocation());
            src.sendMessage(Text.of(TextColors.YELLOW, "You set home " + homeName));
        } else {
            src.sendMessage(Text.of(TextColors.RED, "You must be Player to using home commands!"));
        }
        return CommandResult.success();
    }
}
