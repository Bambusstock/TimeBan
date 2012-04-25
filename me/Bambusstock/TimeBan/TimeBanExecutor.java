package me.Bambusstock.TimeBan;

import java.util.*;
import java.util.logging.*;

import me.Bambusstock.TimeBan.cmd.*;
import me.Bambusstock.TimeBan.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

/**
 * @author Bambusstock
 */
public class TimeBanExecutor implements CommandExecutor
{
	Logger log = Logger.getLogger("Minecraft");
	private TimeBan plugin;
	
	
	public int stdBanDuration;
	public String stdBanReason;
	
	public TimeBanExecutor(TimeBan instance) {
		this.plugin = instance;
		this.stdBanDuration = this.plugin.getConfig().getInt("stdBanDuration", 3600);
		this.stdBanReason = this.plugin.getConfig().getString("stdBanReason", "Standard Reason");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		FormatHelper helper = new FormatHelper();
		ArrayList<String> formattedArgs = helper.preFormatArgs(args, "\"");
		if(args[0].equalsIgnoreCase("ban")) {
			if(sender instanceof Player && !sender.hasPermission("timeban.ban")) {
				sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
				return true;
			}
			this.ban(sender, helper, formattedArgs);
			return true;
		}
		if(args[0].equalsIgnoreCase("help")) {
			if(sender instanceof Player && !sender.hasPermission("timeban.help")) {
				sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
				return true;
			}
			this.help(sender);
			return true;
		}
		if(args[0].equalsIgnoreCase("info")) {
			if(sender instanceof Player && !sender.hasPermission("timeban.info")) {
				sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
				return true;
			}
			this.info(sender);
			return true;
		}
		
		if(this.plugin.banSet.size() == 0) {
			if(sender instanceof Player) { ((Player)sender).sendMessage(ChatColor.RED + "No bans found!"); }
			else { log.info("No bans found!"); }
			return true;
		}
		if(args[0].equalsIgnoreCase("list")) {
			if(sender instanceof Player && !sender.hasPermission("timeban.list")) {
				sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
				return true;
			}
			this.list(sender, helper, formattedArgs);
			return true;
		}
		if(args[0].equalsIgnoreCase("rm")) {
			if(sender instanceof Player && !sender.hasPermission("timeban.rm")) {
				sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
				return true;
			}
			this.rm(sender, helper, formattedArgs);
			return true;
		}
		if(args[0].equalsIgnoreCase("run")) {
			if(sender instanceof Player && !sender.hasPermission("timeban.run")) {
				sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
				return true;
			}
			this.run(sender);
			return true;
		}
		if(args[0].equalsIgnoreCase("unban")) {
			if(sender instanceof Player && !sender.hasPermission("timeban.unban")) {
				sender.sendMessage(ChatColor.RED + "You don't have the permission to use this command!");
				return true;
			}
			this.unban(sender, helper, formattedArgs);			
			return true;
		}		
		return false;
	}
	
	/**
	 * Provide logic to examine the parameters to ban a player.
	 * @param sender
	 * @param helper
	 * @param args
	 */
	public void ban(CommandSender sender, FormatHelper helper, ArrayList<String> args) {
		String[] players = args.get(1).split(",");
		String reason = this.stdBanReason;
		Calendar until;
		if(args.size() < 3) {
			until = Calendar.getInstance();
			until.add(Calendar.SECOND, this.stdBanDuration);
		}
		else if(args.size() == 4) {
			reason = args.get(3).replace("\"", "");
			until = new UntilStringParser(args.get(2)).getCalendar();
		}
		else {
			if(args.size() < 4 && args.get(2).contains("\"")) { // no until but reason (check for ") --> standard until
				reason = args.get(2).replace("\"", "");
				until = Calendar.getInstance();
				until.add(Calendar.SECOND, this.stdBanDuration);
			}
			else { // no reason but until
				until = new UntilStringParser(args.get(2)).getCalendar();
			}
		}
		BanCmd ban = new BanCmd(this.plugin);
		if(sender instanceof Player) { ban.ban((Player) sender, players, until, reason); }
		else { ban.ban(players, until, reason); }
	}
	
	/**
	 * Provide logic to examine the parameters to display the help text.
	 * @param sender
	 * @param helper
	 * @param args
	 */
	public void help(CommandSender sender) {
		HelpCmd help = new HelpCmd(this.plugin);
		if(sender instanceof Player) { help.help((Player) sender); }
		else { help.help(); }
	}
	
	public void info(CommandSender sender) {
		InfoCmd info = new InfoCmd(this.plugin);
		if(sender instanceof Player) { info.info((Player) sender); }
		else { info.info(); }
	}

	/**
	 * Provide logic to examine the parameters to list all bans.
	 * @param sender
	 * @param helper
	 * @param args
	 */
	public void list(CommandSender sender, FormatHelper helper, ArrayList<String> args) {
		String search = "";
		boolean listReverse = false;
		boolean listSimple = false;
		
		if(args.size() > 1 && args.get(1).contains("\"")) { // with search
			search = args.get(1).replace("\"", "");
			if(args.size() == 3) {
				listReverse = helper.containsParameter(args.get(2), "r");			
				listSimple = helper.containsParameter(args.get(2), "s");
			}
		}
		else if(args.size() == 2) { // just with parameters
			listReverse = helper.containsParameter(args.get(1).replace("\"", ""), "r");			
			listSimple = helper.containsParameter(args.get(1).replace("\"", ""), "s");
		}
		
		ListCmd list = new ListCmd(this.plugin);
		if(sender instanceof Player) { list.list((Player)sender, search, listReverse, listSimple); }
		else { list.list(search, listReverse, listSimple); }	
	}
	
	/**
	 * Provide logic to examine the parameters to remove the ban of a player.
	 * @param sender
	 * @param helper
	 * @param args
	 */
	public void rm(CommandSender sender, FormatHelper helper, ArrayList<String> args) {
		RmCmd rm = new RmCmd(this.plugin);
		if(helper.containsParameter(args, "a")) {
			if(sender instanceof Player) { rm.rmAll((Player)sender); }
			else { rm.rmAll(); }
		}
		else {
			String[] players = args.get(1).split(",");
			if(sender instanceof Player) { rm.rm((Player)sender, players); }
			else { rm.rm(players); }
		}
	}
	
	/**
	 * Provide logic to examine the parameters to remove the ban of a player.
	 * @param sender
	 */
	public void run(CommandSender sender) {
		new RunCmd(this.plugin).run();
	}
	
	/**
	 * Provide logic to examine the parameters to unban a player.
	 * @param sender
	 * @param helper
	 * @param args
	 */
	public void unban(CommandSender sender, FormatHelper helper, ArrayList<String> args) {
		UnbanCmd unban = new UnbanCmd(this.plugin);
		if(helper.containsParameter(args, "a")) {
			if(sender instanceof Player) { unban.unbanAll((Player)sender); }
			else { unban.unbanAll(); }
		}
		else {
			String[] players = args.get(1).split(",");
			if(sender instanceof Player) { unban.unban((Player)sender, players); }
			else { unban.unban(players); }
		}
	}
	
	class FormatHelper {
		
		public ArrayList<String> preFormatArgs(String[] input, String delimiter) {
			ArrayList<String> result = new ArrayList<String>();
			for(int i=0; i<input.length;i++) {
				if(!input[i].contains(delimiter) || (input[i].startsWith("\"") && input[i].endsWith("\""))) {
					result.add(input[i]);
					//log.info("(1) #" + i + " Add " + input[i]);
				}
				else {
					//log.info("(2) #" + i + " Add " + getStringFromArray(input, i, delimiter) + " and jumping over...");
					result.add(getStringFromArray(input, i, delimiter));
					int jumpOver = getJumpOver(input, i, delimiter) - i; 
					i += jumpOver;
				}
			}
			return result;			
		}
		
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
				log.info("Input: " + input[i]);
				if(input[i].contains(delimiter)) break;
				output = output.concat(" " + input[i]);
			}
			return output;
		}
		
		/**
		 * Check how many values could be jumped over if there would be used String getStringFromArray.
		 * @param input
		 * @param start
		 * @param delimiter
		 * @return
		 */
		public int getJumpOver(String[] input, int start, String delimiter) {
			if(input.length < start) return 0;
			int i = start+1;
			for(i = start+1; i<input.length; i++) {
				if(input[i].contains(delimiter)) break;
			}
			return i;
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
		public boolean containsParameter(ArrayList<String> input, String search) {
			Iterator<String> it = input.iterator();
			while(it.hasNext()) if(this.containsParameter(it.next(), search)) return true;
			return false;
		}
	}
}