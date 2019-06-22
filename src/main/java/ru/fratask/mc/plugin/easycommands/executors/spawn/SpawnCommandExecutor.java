package ru.fratask.mc.plugin.easycommands.executors.spawn;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;

public class SpawnCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    public static CommandSpec getSpawnCommand(){
        return CommandSpec.builder()
                .description(Text.of("Teleporting you to spawn point!"))
                .executor(new SpawnCommandExecutor())
                .build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        ((Player) src).setLocation(((Player) src).getWorld().getSpawnLocation());
        return CommandResult.success();
    }
}
