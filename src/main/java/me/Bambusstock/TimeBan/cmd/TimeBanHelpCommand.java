package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Command to display help for all TimeBan commands.
 */
public class TimeBanHelpCommand extends TimeBanCommand {

    private static final String[] text = {
        "TimeBan Help page",
        "===================================================\n",
        
        "/timeban ban <username,...> [untilstring] [reason]",
        "Ban a player or more until [untilstring] because of [reason].\n",
        
        "/timeban unban <[username,username2,...] [-a]>",
        "Unban a player or more. Use \"-a\" to unban all.\n",
        
        "/timeban rm <[username,username2,...] [-a]>",
        "Remove a ban from the ban list. Use \"-a\" for all.\n",
        
        "/timeban info",
        "Display some information like ban amount.\n",
        
        "/timeban list [search] [-rs]",
        "List all bans. Use \"-r\" for reverse order. \"s for short display.\n",
        
        "/timeban run",
        "Check for unbans."
    };

    public TimeBanHelpCommand(TimeBan plugin) {
        super(plugin);
    }

    /**
     * Display a help text on the console.
     */
    public void help() {
        for(String s : text) {
            log.info(s);
        }
    }

    /**
     * Display a help text to the player.
     *
     * @param receiver Receiver of the help text.
     */
    public void help(Player receiver) {
        for(int i = 0; i < text.length; i++) {
            String output = "";
            if(i % 2 == 0) {
                output += ChatColor.DARK_GREEN;
            } else {
                output += ChatColor.GOLD;
            }
            output += text[i];
            
            receiver.sendMessage(output);
        }
    }
}
