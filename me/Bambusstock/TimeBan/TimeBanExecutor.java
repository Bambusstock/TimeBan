package me.Bambusstock.TimeBan;

import java.util.Calendar;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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
		receiver.sendMessage(ChatColor.DARK_GREEN + "TimeBan Helptext.");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban ban <username,...> <seconds,...> <reason,...>");
		receiver.sendMessage(ChatColor.GOLD + "Ban a player or more for x seconds. For banning multiple users see documentation!");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban unban <username,username2>");
		receiver.sendMessage(ChatColor.GOLD + "Unban a player or more.");
		
		receiver.sendMessage(ChatColor.DARK_AQUA + "/timeban list [search] [-r]");
		receiver.sendMessage(ChatColor.GOLD + "List all bans. See docu for more information.");
	}
	
	public void clean(Player sender, String[] args) {
		
	}
	
	/**
	 * Ban a player and add him to the banSet.
	 * @param sender Player how send the cmd
	 * @param args Arguments: player to ban, until, reason
	 */
	public void ban(Player sender, String[] args) {
		String[] banPlayers = args[1].split(",");
		UntilStringParser parser = new UntilStringParser(args[2]);
		Calendar until = parser.getCalendar();
		
		if(args[3].isEmpty()) {
			args[3] = "";
		}
		
		for(String playerName : banPlayers) {
			OfflinePlayer banPlayer = this.plugin.getServer().getOfflinePlayer(playerName);
			banPlayer.setBanned(true);
			this.plugin.banSet.add(new Ban(this.plugin, banPlayer.getName(), until, args[3]));
		}
	}
	
	/**
	 * Unban a player.
	 * @param sender Player who send the command.
	 * @param args Arguments: player to unban, possible -a
	 */
	public void unban(Player sender, String[] args) {
		// with -a
		if(args[2].equalsIgnoreCase("-a")) {
			for(Ban ban : this.plugin.banSet) {
				OfflinePlayer unbanPlayer = ban.getPlayer();
				unbanPlayer.setBanned(false);
				this.plugin.banSet.remove(ban);
				log.info("Unbaned and removed `" + ban.getPlayer().getName() + "` from ban list.");
			}
		}
		else {
			String[] unbanPlayers = args[1].split(",");
			for(String playerName : unbanPlayers) {
				Ban ban = this.plugin.banSet.getBanByPlayerName(playerName);
				if(!ban.isEmpty()) {
					OfflinePlayer unbanPlayer = ban.getPlayer();
					unbanPlayer.setBanned(false);
					this.plugin.banSet.remove(ban);
					log.info("Unbaned and removed `" + ban.getPlayer().getName() + "` from ban list.");
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
		// entered: /timeban list '' ...
		if(args[1].equals("''")) {
			if(args[2].equalsIgnoreCase("-r")) {
				for(Ban ban : this.plugin.banSet.descendingSet()) {
					sender.sendMessage(ban.toString());
				}
			}
			else {
				for(Ban ban : this.plugin.banSet) {
					sender.sendMessage(ban.toString());
				}	
			}
		}
		else {
			
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
			this.list((Player)sender, args);
			return true;
		}
		return false;
	}

}