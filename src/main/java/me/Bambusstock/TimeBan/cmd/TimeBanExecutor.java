package me.Bambusstock.TimeBan.cmd;

import java.util.*;
import java.util.logging.*;
import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.cmd.TimeBanCommand.Commands;

import me.Bambusstock.TimeBan.util.*;
import org.bukkit.ChatColor;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

/**
 * Executor checking for incoming command calls. Processes incoming arguments
 * and call expected commands.
 */
public class TimeBanExecutor implements CommandExecutor {

    /**
     * Logger used by this class.
     */
    private static final Logger log = Logger.getLogger("Minecraft");
    
    /**
     * Message to be shown if a player misses a permission to use a command.
     */
    private static final String noPermissionMessage = ChatColor.RED + "You don't have the permission to use the %s command!";

    private TimeBan plugin;

    private final String stdBanDuration;
    private final String stdBanReason;

    public TimeBanExecutor(TimeBan instance) {
        this.plugin = instance;

        // Get standard ban duration
        stdBanDuration = plugin.getConfig().getString("stdBanDuration", "1h");
        stdBanReason = plugin.getConfig().getString("stdBanReason", "Standard Reason");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(args.length == 0) {
            return help(sender);
        }
        
        String subCommand = args[0];

        // fetch args for command
        String[] commandArgs = new String[0];
        if (args.length > 1) {
            commandArgs = Arrays.copyOfRange(args, 1, args.length);
        }

        if (subCommand.equals(Commands.BAN.getName())) {
            if (sender instanceof Player && !sender.hasPermission("timeban.ban")) {
                sender.sendMessage(String.format(noPermissionMessage, Commands.BAN.getName()));
                return true;
            }

            return ban(sender, commandArgs);
        } else if (subCommand.equals(Commands.UNBAN.getName())) {
            if (sender instanceof Player && !sender.hasPermission("timeban.unban")) {
                sender.sendMessage(String.format(noPermissionMessage, Commands.UNBAN.getName()));
                return true;
            }

            return unban(sender, commandArgs);
        } else if (subCommand.equals(Commands.LIST.getName())) {
            if (sender instanceof Player && !sender.hasPermission("timeban.list")) {
                sender.sendMessage(String.format(noPermissionMessage, Commands.LIST.getName()));
                return true;
            }

            return list(sender, commandArgs);
        } else if (subCommand.equals(Commands.INFO.getName())) {
            if (sender instanceof Player && !sender.hasPermission("timeban.info")) {
                sender.sendMessage(String.format(noPermissionMessage, Commands.INFO.getName()));
                return true;
            }

            return info(sender);
        } else if (args[0].equals(Commands.RM.getName())) {
            if (sender instanceof Player && !sender.hasPermission("timeban.rm")) {
                sender.sendMessage(String.format(noPermissionMessage, Commands.RM.getName()));
                return true;
            }
            
            return rm(sender, commandArgs);
        } else if (args[0].equals(Commands.HELP.getName())) {
            if (sender instanceof Player && !sender.hasPermission("timeban.help")) {
                sender.sendMessage(String.format(noPermissionMessage, Commands.HELP.getName()));
                return true;
            }
            
            return help(sender);
        }  else if (args[0].equals(Commands.RUN.getName())) {
            if (sender instanceof Player && !sender.hasPermission("timeban.run")) {
                sender.sendMessage(String.format(noPermissionMessage, Commands.RUN.getName()));
                return true;
            }
            
            return run(sender);
        }
        return false;
    }

    /**
     * Provide logic to examine the parameters to ban a player and call the
     * TimeBanBanEvent.
     *
     * @param sender Sender of the command (player or console)
     * @param args Arguments given for the command (doesn't include timeban
     * command (e.g. ban or unban).
     * 
     * @return true if ok, false if wrong syntax
     */
    public boolean ban(CommandSender sender, String[] args) {
        List<String> players = CommandLineParser.getListOfString(args[0]);
        String reason;
        Calendar until;

        // only username was given, all to standard
        if (args.length == 1) {
            reason = stdBanReason;
            until = UntilStringParser.parse(stdBanDuration);
        } else if (args.length == 2) {
            // What did we get? reason or duration...

            // got a reason
            if (args[1].contains("\"")) {
                reason = CommandLineParser.getPrettyString(args[1]);
                until = UntilStringParser.parse(stdBanDuration);
            } else {
                reason = stdBanReason;
                until = UntilStringParser.parse(args[1]);
            }
        } else if (args.length == 3) {
            // every parameter given

            reason = CommandLineParser.getPrettyString(args[2]);
            until = UntilStringParser.parse(args[1]);
        } else {
            log.log(Level.SEVERE, "Error. Wrong syntax!");
            return false;
        }

        // Send command
        TimeBanBanCommand ban = new TimeBanBanCommand(plugin);
        if (sender instanceof Player) {
            ban.ban((Player) sender, players, until, reason);
        } else {
            ban.ban(players, until, reason);
        }
        return true;
    }

    /**
     * Calls the command to display a help text.
     *
     * @param receiver Receiver of the help text.
     * 
     * @return true
     */
    public boolean help(CommandSender receiver) {
        TimeBanHelpCommand help = new TimeBanHelpCommand(this.plugin);
        if (receiver instanceof Player) {
            help.help((Player) receiver);
        } else {
            help.help();
        }

        return true;
    }

    /**
     * Display some information about TimeBan on the server.
     *
     * @param receiver Receiver of the information
     *
     * @return true
     */
    public boolean info(CommandSender receiver) {
        TimeBanInfoCommand info = new TimeBanInfoCommand(this.plugin);
        if (receiver instanceof Player) {
            info.info((Player) receiver);
        } else {
            info.info();
        }

        return true;
    }

    /**
     * Fetch parameters and call TimeBanList command.
     *
     * @param receiver Receiver of the information.
     * @param args
     *
     * @return true if finished or false on error.
     */
    public boolean list(CommandSender receiver, String[] args) {
        String search = null;
        boolean listReverse = false;
        boolean listSimple = false;
        
        if (args.length == 2) {
            search = args[0];
            listReverse = CommandLineParser.isOptionPresent(args[1], 'r');
            listSimple = CommandLineParser.isOptionPresent(args[1], 's');
        } else if(args.length == 1) {
            listReverse = CommandLineParser.isOptionPresent(args[0], 'r');
            listSimple = CommandLineParser.isOptionPresent(args[0], 's');
            if(listReverse || listSimple) {
                search = null;
            } else {
                search = args[0];
            }
        }

        TimeBanListCommand list = new TimeBanListCommand(plugin);
        if (receiver instanceof Player) {
            list.list((Player) receiver, search, listReverse, listSimple);
        } else {
            list.list(search, listReverse, listSimple);
        }

        return true;
    }

    /**
     * Provide logic to examine the parameters to remove the ban of a player.
     *
     * @param sender
     * @param args
     * 
     * @return true
     */
    public boolean rm(CommandSender sender, String[] args) {
        TimeBanRmCommand rm = new TimeBanRmCommand(plugin);
        
        if(CommandLineParser.isOptionPresent(args[0], 'a')) {
            if(sender instanceof Player) {
                rm.rmAll((Player) sender);
            } else {
                rm.rmAll();
            }
        } else {
            List<String> players = CommandLineParser.getListOfString(args[0]);
            
            if(sender instanceof Player) {
                rm.rm((Player) sender, players);
            } else {
                rm.rm(players);
            }
        }
        
        return true;
    }

    /**
     * Provide logic to examine the parameters to remove the ban of a player.
     *
     * @param sender
     * 
     * @return true
     */
    public boolean run(CommandSender sender) {
        new TimeBanRunCommand(plugin).run();
        return true;
    }

    /**
     * Get the parameters to call the TimeBanUnbanEvent.
     *
     * @param sender Sender of the command
     * @param args arguments given with the command
     *
     * @return true if command executed, false if wrong syntax
     */
    public boolean unban(CommandSender sender, String[] args) {

        TimeBanUnbanCommand unban = new TimeBanUnbanCommand(this.plugin);
        if (CommandLineParser.isOptionPresent(args[0], 'a')) {
            if (sender instanceof Player) {
                unban.unbanAll((Player) sender);
            } else {
                unban.unbanAll();
            }
        } else if (args[0] != null && !args[0].isEmpty()) {
            List<String> players = CommandLineParser.getListOfString(args[0]);

            if (sender instanceof Player) {
                unban.unban((Player) sender, players);
            } else {
                unban.unban(players);
            }
        } else {
            log.log(Level.SEVERE, "Error. Wrong syntax!");
            return false;
        }

        return true;
    }
}
