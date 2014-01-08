package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TimeBanHelpCommand extends TimeBanCommand {

    private static final String banSyntax = "/timeban ban <username,...> [untilstring] [reason]";
    private static final String banInfo = "Ban a player or more until [untilstring] because of [reason].";
    
    private static final String unbanSyntax = "/timeban unban <[username,username2,...] [-a]>";
    private static final String unbanInfo = "Unban a player or more. Use \"-a\" to unban all.";
    
    private static final String rmSyntax = "/timeban rm <[username,username2,...] [-a]>";
    private static final String rmInfo = "Remove a ban from the ban list. Use \"-a\" for all.";
    
    private static final String infoSyntax = "/timeban info";
    private static final String infoInfo = "Display some information like ban amount.";
    
    private static final String listSyntax = "/timeban list [search] [-rs]";
    private static final String listInfo = "List all bans. Use \"-r\" for reverse order. \"s for short display.";
    
    private static final String runSyntax = "/timeban run";
    private static final String runInfo = "Check for unbans.";
            
    public TimeBanHelpCommand(TimeBan plugin) {
        super(plugin);
    }

    public void help() {
        log.info("TimeBan Helptext");
        log.info("==============");

        log.info(banSyntax);
        log.info(banInfo);

        log.info(unbanSyntax);
        log.info(unbanInfo);

        log.info(rmSyntax);
        log.info(rmInfo);

        log.info(infoSyntax);
        log.info(infoInfo);

        log.info(listSyntax);
        log.info(listInfo);

        log.info(runSyntax);
        log.info(runInfo);
    }

    public void help(Player receiver) {
        receiver.sendMessage(ChatColor.DARK_GREEN + "TimeBan Helptext");
        receiver.sendMessage(ChatColor.DARK_GREEN + "==============");

        receiver.sendMessage(ChatColor.DARK_GREEN + banSyntax);
        receiver.sendMessage(ChatColor.GOLD + banInfo);

        receiver.sendMessage(ChatColor.DARK_GREEN + unbanSyntax);
        receiver.sendMessage(ChatColor.GOLD + unbanInfo);

        receiver.sendMessage(ChatColor.DARK_GREEN + rmSyntax);
        receiver.sendMessage(ChatColor.GOLD + rmInfo);

        receiver.sendMessage(ChatColor.DARK_GREEN + infoSyntax);
        receiver.sendMessage(ChatColor.GOLD + infoInfo);

        receiver.sendMessage(ChatColor.DARK_GREEN + listSyntax);
        receiver.sendMessage(ChatColor.GOLD + listInfo);

        receiver.sendMessage(ChatColor.DARK_GREEN + runSyntax);
        receiver.sendMessage(ChatColor.GOLD + runInfo);
    }
}
