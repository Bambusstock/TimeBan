package me.Bambusstock.TimeBan.cmd;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;

public class UnbanCmd extends Cmd
{
	public UnbanCmd(TimeBan plugin) {
		super(plugin);
	}
	
	/**
	 * Search for a ban for the given players and fire a unban event.
	 * @param sender
	 * @param players
	 */
	public void unban(Player sender, String[] players) {
		for(String playerName : players) {
			Ban ban = this.plugin.banSet.getBanByPlayerName(playerName);
			if(!ban.isEmpty()) {
				TimeBanUnbanEvent event = new TimeBanUnbanEvent(sender, ban);
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
				TimeBanUnbanEvent event = new TimeBanUnbanEvent(ban);
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
			TimeBanUnbanEvent event = new TimeBanUnbanEvent(sender, ban);
			this.plugin.getServer().getPluginManager().callEvent(event);
		}
	}
	
	/**
	 * Unban all banned players. Console version.s
	 */
	public void unbanAll() {
		for(Ban ban : this.plugin.banSet) {
			TimeBanUnbanEvent event = new TimeBanUnbanEvent(ban);
			this.plugin.getServer().getPluginManager().callEvent(event);
		}
	}
}