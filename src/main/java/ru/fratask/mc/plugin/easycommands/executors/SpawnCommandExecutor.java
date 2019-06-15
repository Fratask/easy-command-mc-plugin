package ru.fratask.mc.plugin.easycommands.executors;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import ru.fratask.mc.plugin.easycommands.EasyCommandsPlugin;

public class SpawnCommandExecutor implements CommandExecutor {

    private Logger logger = EasyCommandsPlugin.getInstance().getLogger();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        ((Player) src).setLocation(((Player) src).getWorld().getSpawnLocation());
        return CommandResult.success();
    }
}
