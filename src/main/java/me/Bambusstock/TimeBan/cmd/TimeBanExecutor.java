package me.Bambusstock.TimeBan.cmd;

import java.util.*;
import java.util.logging.Logger;
import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.cmd.configurator.AbstractConfigurator;
import me.Bambusstock.TimeBan.cmd.configurator.ConfiguratorFactory;

import me.Bambusstock.TimeBan.util.*;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

/**
 * Executor checking for incoming command calls. Processes incoming arguments
 * and call expected commands.
 */
public class TimeBanExecutor implements CommandExecutor {
    // plugin instance to create a new command
    private final TimeBan plugin;

    public TimeBanExecutor(TimeBan instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            TimeBanHelpCommand command = new TimeBanHelpCommand(plugin);
            command.setManPage("help");
            
            if(sender != null && sender instanceof Player) {
                command.setReceiver((Player) sender);
            }
            
            if(command.executionAllowed()) {
                command.execute();
            }
            return true;
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
        if (sender != null &&  sender instanceof Player) {
            command.setReceiver((Player) sender);
        }

        // execute command
        if (command.executionAllowed()) {
            AbstractConfigurator configurator = ConfiguratorFactory.getConfigurator(plugin.getConfig(), command);
            configurator.configure(command, commandArgs);
            command.execute();
        } else {
            HashMap<String, String> values = new HashMap<String, String>(1);
            values.put("{command}", command.getCommandType().getName());
            
            String message = MessagesUtil.formatMessage("no_permission", values);
            sender.sendMessage(message);
        }

        return true;
    }
}
