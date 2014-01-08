package me.Bambusstock.TimeBan.cmd;

import java.util.*;
import java.util.logging.*;
import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.cmd.TimeBanCommand.Commands;

import me.Bambusstock.TimeBan.util.*;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TimeBanExecutor implements CommandExecutor {

    private static final Logger log = Logger.getLogger("Minecraft");
    private TimeBan plugin;

    private String stdBanDuration;
    private String stdBanReason;

    public TimeBanExecutor(TimeBan instance) {
        this.plugin = instance;
        
        // Get standard ban duration
        stdBanDuration = plugin.getConfig().getString("stdBanDuration", "1h");
        stdBanReason = plugin.getConfig().getString("stdBanReason", "Standard Reason");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String subCommand = args[0];
        if(subCommand.equals(Commands.BAN.getName())) {
            ban(sender, args);
        }
//        FormatHelper helper = new FormatHelper();
//        ArrayList<String> formattedArgs = helper.preFormatArgs(args, "\"");
//
//        if (args[0].equalsIgnoreCase("ban")) {
//            if (sender instanceof Player && !sender.hasPermission("timeban.ban")) {
//                sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
//                return true;
//            }
//            this.ban(sender, helper, formattedArgs);
//            return true;
//        } else if (args[0].equalsIgnoreCase("help")) {
//            if (sender instanceof Player && !sender.hasPermission("timeban.help")) {
//                sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
//                return true;
//            }
//            this.help(sender);
//            return true;
//        } else if (args[0].equalsIgnoreCase("info")) {
//            if (sender instanceof Player && !sender.hasPermission("timeban.info")) {
//                sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
//                return true;
//            }
//            this.info(sender);
//            return true;
//        } else if (args[0].equalsIgnoreCase("list")) {
//            if (sender instanceof Player && !sender.hasPermission("timeban.list")) {
//                sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
//                return true;
//            }
//            this.list(sender, helper, formattedArgs);
//            return true;
//        } else if (args[0].equalsIgnoreCase("rm")) {
//            if (sender instanceof Player && !sender.hasPermission("timeban.rm")) {
//                sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
//                return true;
//            }
//            this.rm(sender, helper, formattedArgs);
//            return true;
//        } else if (args[0].equalsIgnoreCase("run")) {
//            if (sender instanceof Player && !sender.hasPermission("timeban.run")) {
//                sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
//                return true;
//            }
//            this.run(sender);
//            return true;
//        } else if (args[0].equalsIgnoreCase("unban")) {
//            if (sender instanceof Player && !sender.hasPermission("timeban.unban")) {
//                sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
//                return true;
//            }
//            this.unban(sender, helper, formattedArgs);
//            return true;
//        }
        return false;
    }

    /**
     * Provide logic to examine the parameters to ban a player.
     *
     * @param sender
     * @param helper
     * @param args
     */
    public void ban(CommandSender sender, String[] args) {
        List<String> players = CommandLineParser.getListOfString(args[1]);
        String reason;
        Calendar until;
        
        // Get parameters...
        if(args.length == 4 && args[3].contains("\"")) {
            reason = CommandLineParser.getPrettyString(args[3]);
            until = UntilStringParser.parse(args[2]);
        } else if(args.length == 3 && args[2].contains("\"")) {
            reason = CommandLineParser.getPrettyString(args[2]);
            until = UntilStringParser.parse(stdBanDuration);
        } else if(args.length == 3 && !args[2].contains("\"")) {
            until = UntilStringParser.parse(args[2]);
            reason = stdBanReason;
        } else {
            log.log(Level.SEVERE, "Error parsing command. Could not find <reason> and <until>");
            return;
        }
        
        // Send command
        TimeBanBanCommand ban = new TimeBanBanCommand(plugin);
        if (sender instanceof Player) {
            ban.ban((Player) sender, players, until, reason);
        } else {
            ban.ban(players, until, reason);
        }
    }

    /**
     * Display a helptext.
     *
     * @param sender
     * @param helper
     * @param args
     */
    public void help(CommandSender sender) {
        TimeBanHelpCommand help = new TimeBanHelpCommand(this.plugin);
        if (sender instanceof Player) {
            help.help((Player) sender);
        } else {
            help.help();
        }
    }

    /**
     * Display some information about TimeBan on the server.
     *
     * @param sender
     */
    public void info(CommandSender sender) {
        TimeBanInfoCommand info = new TimeBanInfoCommand(this.plugin);
        if (sender instanceof Player) {
            info.info((Player) sender);
        } else {
            info.info();
        }
    }

    /**
     * Provide logic to examine the parameters to list all bans.
     *
     * @param sender
     * @param args
     */
    public void list(CommandSender sender, String[] args) {
        String search = args[1];
        boolean listReverse = false;
        boolean listSimple = false;

        if (args.length > 1 && args[1].contains("\"")) { // with search
            if (args.length == 3) {
                listReverse = CommandLineParser.isOptionPresent(args[2], 'r');
                listSimple = CommandLineParser.isOptionPresent(args[2], 's');
            }
        } else if (args.length == 2) { // just with parameters
            listReverse = CommandLineParser.isOptionPresent(args[1], 'r');
            listSimple = CommandLineParser.isOptionPresent(args[1], 's');
        }

        TimeBanListCommand list = new TimeBanListCommand(this.plugin);
        if (sender instanceof Player) {
            list.list((Player) sender, search, listReverse, listSimple);
        } else {
            list.list(search, listReverse, listSimple);
        }
    }

//    /**
//     * Provide logic to examine the parameters to remove the ban of a player.
//     *
//     * @param sender
//     * @param helper
//     * @param args
//     */
//    public void rm(CommandSender sender, String[] args) {
//        TimeBanRmCommand rm = new TimeBanRmCommand(this.plugin);
//        if (helper.containsParameter(args, "a")) {
//            if (sender instanceof Player) {
//                rm.rmAll((Player) sender);
//            } else {
//                rm.rmAll();
//            }
//        } else {
//            String[] players = args.get(1).split(",");
//            if (sender instanceof Player) {
//                rm.rm((Player) sender, players);
//            } else {
//                rm.rm(players);
//            }
//        }
//    }
//
//    /**
//     * Provide logic to examine the parameters to remove the ban of a player.
//     *
//     * @param sender
//     */
//    public void run(CommandSender sender) {
//        new TimeBanRunCommand(this.plugin).run();
//    }
//
//    /**
//     * Provide logic to examine the parameters to unban a player.
//     *
//     * @param sender
//     * @param helper
//     * @param args
//     */
//    public void unban(CommandSender sender, FormatHelper helper, ArrayList<String> args) {
//        TimeBanUnbanCommand unban = new TimeBanUnbanCommand(this.plugin);
//        if (helper.containsParameter(args, "a")) {
//            if (sender instanceof Player) {
//                unban.unbanAll((Player) sender);
//            } else {
//                unban.unbanAll();
//            }
//        } else {
//            String[] players = args.get(1).split(",");
//            if (sender instanceof Player) {
//                unban.unban((Player) sender, players);
//            } else {
//                unban.unban(players);
//            }
//        }
//    }
}
