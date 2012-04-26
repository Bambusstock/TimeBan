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
		log.info("Ban a player or more until [untilstring] because of [reason].");
		
		log.info("/timeban unban <[username,username2] [-a]>");
		log.info("Unban a player or more. Use \"-a\" to unban all.");
		
		log.info("/timeban rm <[username,username2] [-a]>");
		log.info("Remove a ban from the ban list. Use \"-a\" for all.");
		
		log.info("/timeban info");
		log.info("Display some information like ban amount.");
		
		log.info("/timeban list [search] [-rs]");
		log.info("List all bans. Use \"-r\" for reverse order. \"s for short display.");
		
		log.info("/timeban run");
		log.info("Check for unbans.");
	}
	
	public void help(Player receiver) {
		receiver.sendMessage(ChatColor.DARK_GREEN + "TimeBan Helptext");
		receiver.sendMessage(ChatColor.DARK_GREEN + "==============");
		
		receiver.sendMessage(ChatColor.DARK_GREEN + "/timeban ban <username,...> [untilstring] [reason]");
		receiver.sendMessage(ChatColor.GOLD + "Ban a player or more until [untilstring] because of [reason].");
		
		receiver.sendMessage(ChatColor.DARK_GREEN + "/timeban unban <[username,username2] [-a]>");
		receiver.sendMessage(ChatColor.GOLD + "Unban a player or more. Use \"-a\" to unban all.");
		
		receiver.sendMessage(ChatColor.DARK_GREEN + "/timeban rm <[username,username2] [-a]>");
		receiver.sendMessage(ChatColor.GOLD + "Remove a ban from the ban list. Use \"-a\" for all.");
		
		receiver.sendMessage(ChatColor.DARK_GREEN + "/timeban info");
		receiver.sendMessage(ChatColor.GOLD + "Display some information like ban amount.");
		
		receiver.sendMessage(ChatColor.DARK_GREEN + "/timeban list [search] [-rs]");
		receiver.sendMessage(ChatColor.GOLD + "List all bans. Use \"-r\" for reverse order. \"s for short display.");
		
		receiver.sendMessage(ChatColor.DARK_GREEN + "/timeban run");
		receiver.sendMessage(ChatColor.GOLD + "Check for unbans.");
	}
}
