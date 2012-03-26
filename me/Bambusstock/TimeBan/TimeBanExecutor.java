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
 * @TODO: Use EventSystem!
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
		UntilStringParser parser = new UntilStringParser(args[2]);
		Calendar until = parser.getCalendar();
		
		for(String playerName : banPlayers) {
			Ban ban = new Ban(this.plugin, playerName, until, args[3]);			
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
		if(args[2].equalsIgnoreCase("-a")) {
			for(Ban ban : this.plugin.banSet) {
				TimeUnbanEvent event = new TimeUnbanEvent(sender, ban);
				this.plugin.getServer().getPluginManager().callEvent(event);
			}
		}
		else {
			String[] unbanPlayers = args[1].split(",");
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
		BanSet resultSet = new BanSet();
		// collect data
		// check for a search query
		if(!args[2].equalsIgnoreCase("''")) {
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
		
		// format output
		Iterator<Ban> iterator = this.plugin.banSet.iterator();
		if(args[3].contains("r")) {
			iterator = resultSet.descendingIterator(); 
		}
		
		if(args[2].equalsIgnoreCase("''")) {
			if(args[3].contains("s")) {
				while(iterator.hasNext()) sender.sendMessage(iterator.next().toString());
			}
			else {
				while(iterator.hasNext()) {
					Ban ban = iterator.next();
					sender.sendMessage("`" + ban.getPlayer().getName() + "` banned until " + ban.getUntil().getTime() + "because `" + ban.getReason() + "`");
				}
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(args[0].equalsIgnoreCase("help")) {
			this.help((Player)sender);
			return true;
		}
		else if(args[0].equalsIgnoreCase("ban")) {
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