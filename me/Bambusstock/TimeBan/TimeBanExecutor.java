package me.Bambusstock.TimeBan;

import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.Bambusstock.TimeBan.event.TimeBanEvent;
import me.Bambusstock.TimeBan.event.TimeUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.BanSet;
import me.Bambusstock.TimeBan.util.UntilStringParser;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Bambusstock
 * @TODO:
 * 	- throw exception if user not found
 *  - improve split regex
 *  - make onCommand user friendly
 */
public class TimeBanExecutor implements CommandExecutor
{
	Logger log = Logger.getLogger("Minecraft");
	private TimeBan plugin;
	
	public TimeBanExecutor(TimeBan instance) {
		this.plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		FormatHelper helper = new FormatHelper();
		
		if(args[0].equalsIgnoreCase("help")) {
			this.help((Player)sender);
			return true;
		}
		
		else if(args[0].equalsIgnoreCase("ban")) {
			String[] players = args[1].split(",");
			String reason;
			Calendar until;
			if(args.length < 3) {
				reason = Ban.stdReason;
				until = Calendar.getInstance();
				until.add(Calendar.SECOND, Ban.stdBanDuration);
			}
			else {
				reason = helper.getStringFromArray(args, 3, "\"");
				if(reason.isEmpty()) reason = Ban.stdReason;
				if(args.length < 4 && args[2].contains("\"")) { // no until but reason (check for ") --> standard until
					until = Calendar.getInstance();
					until.add(Calendar.SECOND, Ban.stdBanDuration);
				}
				else {
					until = new UntilStringParser(args[2]).getCalendar();
				}
			}
			
			if(sender instanceof Player) { this.ban((Player)sender, players, until, reason); }
			else { this.ban(players, until, reason); }
			
			return true;
		}
		
		else if(args[0].equalsIgnoreCase("unban")) {
			if(this.plugin.banSet.size() == 0) {
				if(sender instanceof Player) { ((Player)sender).sendMessage(ChatColor.RED + "No bans found!"); }
				else { log.info("No bans found!"); }
				return true;
			}
			
			if(helper.containsParameter(args, "a")) {
				if(sender instanceof Player) { this.unbanAll((Player)sender); }
				else { this.unbanAll(); }
			}
			else {
				String[] players = args[1].split(",");
				if(sender instanceof Player) { this.unban((Player)sender, players); }
				else { this.unban(players); }
			}
			return true;
		}
		
		else if(args[0].equalsIgnoreCase("list")) {
			if(this.plugin.banSet.size() == 0) {
				sender.sendMessage(ChatColor.RED + "Ban list is empty!");
				return true;
			}
			
			String search = "";
			boolean listReverse = false;
			boolean listSimple = false;
			
			if(args.length > 1 && args[1].contains("\"")) { // with search
				search = args[1];
				if(args.length == 3) {
					listReverse = helper.containsParameter(args[2], "r");			
					listSimple = helper.containsParameter(args[2], "s");
				}
			}
			else if(args.length == 2) { // just with parameters
				listReverse = helper.containsParameter(args[1], "r");			
				listSimple = helper.containsParameter(args[1], "s");
			}
			
			if(sender instanceof Player) { this.list((Player)sender, search, listReverse, listSimple); }
			else { this.list(search, listReverse, listSimple); }	

			return true;
		}
		
		return false;
	}
	
	public void help(Player receiver) {
		receiver.sendMessage(ChatColor.DARK_GREEN + "TimeBan Helptext");
		receiver.sendMessage(ChatColor.DARK_GREEN + "==============");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban ban <username,...> <seconds> <reason>");
		receiver.sendMessage(ChatColor.GOLD + "Ban a player or more for x seconds.");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban unban <username,username2>");
		receiver.sendMessage(ChatColor.GOLD + "Unban a player or more.");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban list [search] [-r]");
		receiver.sendMessage(ChatColor.GOLD + "List all bans. See docu for more information.");
	}
	
	/*
	 * --------------
	 * Ban
	 * --------------
	 */
	
	/**
	 * Create a ban and fire a ban event.
	 * @param sender
	 * @param players Array of player names
	 * @param until  Calendar until unban time
	 * @param reason Reason
	 */
	public void ban(Player sender, String[] players, Calendar until, String reason) {
		for(String playerName : players) {
			Ban ban = new Ban(this.plugin, playerName, until, reason);
			TimeBanEvent event = new TimeBanEvent(sender, ban);
			this.plugin.getServer().getPluginManager().callEvent(event);
		}
	}
	
	/**
	 * Create a ban and fire a ban event. Console version.
	 * @param players Array of player names
	 * @param until Calendar until unban time
	 * @param reason Reason
	 */
	public void ban(String[] players, Calendar until, String reason) {
		for(String playerName : players) {
			Ban ban = new Ban(this.plugin, playerName, until, reason);
			TimeBanEvent event = new TimeBanEvent(ban);
			this.plugin.getServer().getPluginManager().callEvent(event);
		}
	}
	
	/*
	 * -----------------
	 * Unban
	 * -----------------
	 */
	
	/**
	 * Search for a ban for the given players and fire a unban event.
	 * @param sender
	 * @param players
	 */
	public void unban(Player sender, String[] players) {
		for(String playerName : players) {
			Ban ban = this.plugin.banSet.getBanByPlayerName(playerName);
			if(!ban.isEmpty()) {
				TimeUnbanEvent event = new TimeUnbanEvent(sender, ban);
				this.plugin.getServer().getPluginManager().callEvent(event);
			}
			else {
				sender.sendMessage(ChatColor.RED + "No ban for player `" + playerName + "` found!");
			}
		}
	}
	
	/**
	 * Search for a ban for the given players and fire a unban event. Console version.
	 * @param players
	 */
	public void unban(String[] players) {
		for(String playerName : players) {
			Ban ban = this.plugin.banSet.getBanByPlayerName(playerName);
			if(!ban.isEmpty()) {
				TimeUnbanEvent event = new TimeUnbanEvent(ban);
				this.plugin.getServer().getPluginManager().callEvent(event);
			}
			else {
				log.info("No ban for player `" + playerName + "` found!");
			}
		}
	}
	
	/**
	 * Unban all banned players.
	 * @param sender
	 */
	public void unbanAll(Player sender) {
		for(Ban ban : this.plugin.banSet) {
			TimeUnbanEvent event = new TimeUnbanEvent(sender, ban);
			this.plugin.getServer().getPluginManager().callEvent(event);
		}
	}
	
	/**
	 * Unban all banned players. Console version.s
	 */
	public void unbanAll() {
		for(Ban ban : this.plugin.banSet) {
			TimeUnbanEvent event = new TimeUnbanEvent(ban);
			this.plugin.getServer().getPluginManager().callEvent(event);
		}
	}
	
	
	/*
	 * ------------------
	 * List
	 * ------------------
	 */
	
	/**
	 * List all bans. 
	 * @param sender
	 * @param search Regex used for a search
	 * @param reverse True, then all bans are sorted reverse (descending time)
	 * @param simple True, then the ban information are compressed
	 */
	public void list(Player sender, String search, boolean reverse, boolean simple) {
		BanSet resultSet = new BanSet();
		if(search.isEmpty()) {
			Pattern searchPattern = Pattern.compile(search);
			for(Ban ban : this.plugin.banSet) {
				Matcher m = searchPattern.matcher(ban.getPlayer().getName());
				if(m.matches()) resultSet.add(ban);
			}
		}
		else {
			resultSet = (BanSet)this.plugin.banSet.clone();
		}
		
		Iterator<Ban> iterator = resultSet.iterator();
		if(reverse == true) {
			iterator = resultSet.descendingIterator(); 
		}
		
		if(simple == true) {
			while(iterator.hasNext()) sender.sendMessage(iterator.next().toString());
		}
		else {
			while(iterator.hasNext()) {
				Ban ban = iterator.next();
				sender.sendMessage("`" + ChatColor.RED + ban.getPlayer().getName() + ChatColor.WHITE 
						+ "` until " + ChatColor.AQUA + ban.getUntil().getTime() + ChatColor.WHITE
						+ " because `" + ChatColor.GOLD + ban.getReason() + ChatColor.WHITE + "`");
			}
		}
	}
	
	/**
	 * List all bans. Console version
	 * @param search Regex used for a search
	 * @param reverse True, then all bans are sorted reverse (descending time)
	 * @param simple True, then the ban information are compressed
	 */
	public void list(String search, boolean reverse, boolean simple) {
		BanSet resultSet = new BanSet();
		if(search.isEmpty()) {
			Pattern searchPattern = Pattern.compile(search);
			for(Ban ban : this.plugin.banSet) {
				Matcher m = searchPattern.matcher(ban.getPlayer().getName());
				if(m.matches()) resultSet.add(ban);
			}
		}
		else {
			resultSet = (BanSet)this.plugin.banSet.clone();
		}
		
		Iterator<Ban> iterator = resultSet.iterator();
		if(reverse == true) {
			iterator = resultSet.descendingIterator(); 
		}
		
		if(simple == true) {
			while(iterator.hasNext()) log.info(iterator.next().toString());
		}
		else {
			while(iterator.hasNext()) {
				Ban ban = iterator.next();
				log.info("`" + ban.getPlayer().getName() + "` until " + ban.getUntil().getTime() + " because `" + ban.getReason() + "`");
			}
		}
	}
	
	class FormatHelper {
		/**
		 * Extract a string from an array.
		 * @param String[] Input
		 * @return String
		 */
		public String getStringFromArray(String[] input) {
			String output = input[0];
			for(String str : input) output = output.concat(" " + str);
			return output;
		}
		
		/**
		 * Extract a string from an array.
		 * @param String[] Input
		 * @param start Start element (will be included in the output, so choose the element with start delimiter)
		 * @param delimiter Delimiter
		 * @return String without delimiters
		 */
		public String getStringFromArray(String[] input, int start, String delimiter) {
			if(input.length < start) return "";
			String output = input[start];
			for(int i = start+1; i<input.length; i++) {
				output = output.concat(" " + input[i]);
				if(input[i].contains(delimiter)) break;
			}
			output = output.replaceAll(delimiter, "");
			return output;
		}
		
		/**
		 * Check for an parameter like -a inside a given string.
		 * Attention: Just works with single parameters like "-ra" 
		 * where "r" and "a" are separate parameters.
		 * @param input String to search for your parameter.
		 * @param search Search like "a" you don't need to add "-"
		 * @return
		 */
		public boolean containsParameter(String input, String search) {
			if(input.contains("-")) {
				String[] split = input.split(""); // not optimum but works at the moment
				for(String str : split) if(str.equals(search)) return true;
			}
			return false;
		}
		
		/**
		 * Check for an parameter like -a inside a given array. 
		 * Attention: Just works with single parameters like "-ra" 
		 * where "r" and "a" are separate parameters.
		 * @param input Array to search for your parameter.
		 * @param search Search like "a" you don't need to add "-"
		 * @return
		 */
		public boolean containsParameter(String[] input, String search) {
			for(String str : input) if(this.containsParameter(str, search)) return true;
			return false;
		}
	}
}