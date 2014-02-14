package me.Bambusstock.TimeBan.cmd;

import java.util.*;
import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.cmd.configurator.AbstractConfigurator;
import me.Bambusstock.TimeBan.cmd.configurator.ConfiguratorFactory;

import me.Bambusstock.TimeBan.util.*;
import org.bukkit.ChatColor;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

/**
 * Executor checking for incoming command calls. Processes incoming arguments
 * and call expected commands.
 */
public class TimeBanExecutor implements CommandExecutor {
     // Message to be shown if a player misses a permission to use a command.
    private static final String noPermissionMessage = ChatColor.RED + "You don't have the permission to use the %s command!";

    private final TimeBan plugin;

    public TimeBanExecutor(TimeBan instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            new TimeBanHelpCommand(plugin).execute();
        }

        // fetch args for command
        String subCommand = args[0];
        String[] commandArgs = new String[0];
        if (args.length > 1) {
            commandArgs = Arrays.copyOfRange(args, 1, args.length);
            commandArgs = CommandLineParser.normalizeArgs(commandArgs);
        }

        // create command
        AbstractCommand command = CommandBuilder.createCommand(plugin, subCommand);
        if (sender != null) {
            command.setReceiver((Player) sender);
        }

        // execute command
        if (command.executionAllowed()) {
            AbstractConfigurator configurator = ConfiguratorFactory.getConfigurator(plugin.getConfig(), command);
            configurator.configure(command, commandArgs);
            command.execute();
        } else {
            sender.sendMessage(String.format(noPermissionMessage, command.getCommandType().getName()));
        }

        return false;
    }
}
