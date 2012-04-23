package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCmd extends Cmd
{
	public HelpCmd(TimeBan plugin) {
		super(plugin);
	}
	
	public void help() {
		log.info("TimeBan Helptext");
		log.info("==============");
		
		log.info("/timeban ban <username,...> [untilstring] [reason]");
		log.info("Ban a player or more for [untilstring] because of [reason].");
		
		log.info("/timeban unban <username,username2>");
		log.info("Unban a player or more.");
		
		log.info("/timeban rm [username,username2] [-a]");
		log.info("Remove a ban from the ban list. Use \"-a\" for all.");
		
		log.info("/timeban list [search] [-r]");
		log.info("List all bans. Use \"-r\" for reverse order.");
	}
	
	public void help(Player receiver) {
		receiver.sendMessage(ChatColor.DARK_GREEN + "TimeBan Helptext");
		receiver.sendMessage(ChatColor.DARK_GREEN + "==============");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban ban <username,...> [untilstring] [reason]");
		receiver.sendMessage(ChatColor.GOLD + "Ban a player or more for [untilstring] because of [reason].");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban unban <username,username2>");
		receiver.sendMessage(ChatColor.GOLD + "Unban a player or more.");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban rm [username,username2] [-a]");
		receiver.sendMessage(ChatColor.GOLD + "Remove a ban from the ban list. Use \"-a\" for all.");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban list [search] [-r]");
		receiver.sendMessage(ChatColor.GOLD + "List all bans. Use \"-r\" for reverse order.");
	}
}
