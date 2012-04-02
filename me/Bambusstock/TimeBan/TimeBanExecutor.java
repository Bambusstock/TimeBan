package me.Bambusstock.TimeBan;

import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.Bambusstock.TimeBan.event.TimeBanEvent;
import me.Bambusstock.TimeBan.event.TimeUnbanEvent;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Bambusstock
 *
 */
public class TimeBanExecutor implements CommandExecutor
{
	Logger log = Logger.getLogger("Minecraft");
	private TimeBan plugin;
	protected String[][] commandsHelp = {
			{},
			{"/timeban list [search] [-r]", "List all"}
	};
	
	public TimeBanExecutor(TimeBan instance) {
		this.plugin = instance;
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
	
	public void clean(Player sender, String[] args) {
		
	}
	
	/**
	 * Create a ban for the given player and call the TimeBanEvent
	 * @param sender Player how send the cmd
	 * @param args Arguments: player to ban, until, reason
	 */
	public void ban(Player sender, String[] args) {
		String[] banPlayers = args[1].split(",");	
		Calendar until;
		
		if(args.length < 3) {
			until = Calendar.getInstance();
			until.set(Calendar.SECOND, until.get(Calendar.SECOND) + Ban.stdBanDuration);
		}
		else {
			UntilStringParser parser = new UntilStringParser(args[2]);
			 until = parser.getCalendar();
		}
		String reason = (args.length < 4 || args[3].equals("\"\"")) ? Ban.stdReason : args[3].replaceAll("\"", "");
		
		for(String playerName : banPlayers) {
			Ban ban = new Ban(this.plugin, playerName, until, reason);
			TimeBanEvent event = new TimeBanEvent(sender, ban);
			this.plugin.getServer().getPluginManager().callEvent(event);
		}
	}
	
	/**
	 * Get a ban for the given player and call the TimeUnbanEvent
	 * @param sender Player who send the command.
	 * @param args Arguments: player to unban, possible -a
	 */
	public void unban(Player sender, String[] args) {
		// with -a go through all
		if(args.length > 2 && args[2].equalsIgnoreCase("-a")) {
			for(Ban ban : this.plugin.banSet) {
				TimeUnbanEvent event = new TimeUnbanEvent(sender, ban);
				this.plugin.getServer().getPluginManager().callEvent(event);
			}
		}
		else {
			String[] unbanPlayers = {args[1]};
			if(args[1].contains(",")) unbanPlayers = args[1].split(",");
			for(String playerName : unbanPlayers) {
				Ban ban = this.plugin.banSet.getBanByPlayerName(playerName);
				if(!ban.isEmpty()) {
					TimeUnbanEvent event = new TimeUnbanEvent(sender, ban);
					this.plugin.getServer().getPluginManager().callEvent(event);
				}
			}
		}
	}
	
	/**
	 * List bans. 
	 * @TODO pagination!
	 * @param sender
	 * @param args
	 */
	public void list(Player sender, String[] args) {
		for (int i = 0; i < args.length; i++) log.info("#"+i + " --> " + args[i]);
		BanSet resultSet = new BanSet();

		/* 
		 * collect data
		 */
		// check for a search query
		if(args.length > 1 && !args[1].equals("\"\"")) {
			Pattern search = Pattern.compile(args[2]);
			for(Ban ban : this.plugin.banSet) {
				Matcher m = search.matcher(ban.getPlayer().getName());
				if(m.matches()) resultSet.add(ban);
			}
		}
		// all wanted
		else {
			resultSet = (BanSet)this.plugin.banSet.clone();
		}
		
		// check for reverse parameter
		Iterator<Ban> iterator = this.plugin.banSet.iterator();
		if(args.length >= 3 && args[2].contains("r")) {
			iterator = resultSet.descendingIterator(); 
		}
	
		/*
		 * no search pattern
		 */
		// [(more than 2 arguments and no search) + (parameter s given)]
		if( (args.length > 2 && args[1].equals("\"\"")) && (args[2].contains("s"))) {
				while(iterator.hasNext()) sender.sendMessage(iterator.next().toString());
		}
		// [(no search) || (no search was given explicit and there no parameters)] 
		else if( args.length < 2 || (args.length < 3 && args[1].equals("\"\""))){
			while(iterator.hasNext()) {
				Ban ban = iterator.next();
				sender.sendMessage("`" + ChatColor.RED + ban.getPlayer().getName() + ChatColor.WHITE 
						+ "` until " + ChatColor.AQUA + ban.getUntil().getTime() + ChatColor.WHITE
						+ " because `" + ChatColor.GOLD + ban.getReason() + ChatColor.WHITE + "`");
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args[0].equalsIgnoreCase("help")) {
			this.help((Player)sender);
			return true;
		}
		else if(args[0].equalsIgnoreCase("ban")) {
			for(int i = 4; i<args.length; i++) {
				args[3] = args[3].concat(" " + args[i]);
				args[i] = "";
				if(args[i].contains("\"")) break;
			}
			args[3].replaceAll("\"", "");
			
			this.ban((Player)sender, args);
			return true;
		}
		else if(args[0].equalsIgnoreCase("unban")) {
			this.unban((Player)sender, args);
			return true;
		}
		else if(args[0].equalsIgnoreCase("list")) {
			if(this.plugin.banSet.size() == 0) {
				sender.sendMessage(ChatColor.RED + "Ban list is empty!");		
			}
			else {
				this.list((Player)sender, args);
			}
			return true;
		}
		
		return false;
	}

}