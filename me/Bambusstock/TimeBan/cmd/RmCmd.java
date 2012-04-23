package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RmCmd extends Cmd
{
	public RmCmd(TimeBan plugin) {
		super(plugin);
	}

	/**
	 * Search for a ban for the given players and remove him. No event is fired.
	 * @param sender
	 * @param players
	 */
	public void rm(Player sender, String[] players) {
		for(String playerName : players) {
			Ban ban = this.plugin.banSet.getBanByPlayerName(playerName);
			if(!ban.isEmpty()) {
				this.plugin.banSet.remove(ban);
				sender.sendMessage("Removed.");
			}
			else {
				sender.sendMessage(ChatColor.RED + "No ban for player `" + playerName + "` found!");
			}
		}
	}

	/**
	 * Search for a ban for the given players and remove him. No event is fired. Console version.
	 * @param players
	 */
	public void rm(String[] players) {
		for(String playerName : players) {
			Ban ban = this.plugin.banSet.getBanByPlayerName(playerName);
			if(!ban.isEmpty()) {
				this.plugin.banSet.remove(ban);
				log.info("Removed.");
			}
			else {
				log.info("No ban for player `" + playerName + "` found!");
			}
		}
	}

	/**
	 * Remove all bans. No event is fired.
	 * @param sender
	 */
	public void rmAll(Player sender) {
		this.plugin.banSet.clear();
		sender.sendMessage("All bans are removed!");
	}

	/**
	 * Remove all bans. Console version.
	 */
	public void rmAll() {
		this.plugin.banSet.clear();
		log.info("All bans are removed!");
	}
}
