package me.Bambusstock.TimeBan;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanBanEvent;
import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BanListener implements Listener
{
	Logger log = Logger.getLogger("Minecraft");
	private TimeBan 		plugin;
	
	/**
	 *
	 * @param instance
	 */
	public BanListener(TimeBan instance) {
		this.plugin = instance;
	}
	
	@EventHandler
	public void onTimeBanEvent(TimeBanBanEvent event) {
		Ban ban = event.getBan();
		OfflinePlayer player = ban.getPlayer();
		
		player.setBanned(true);
		if(player.isOnline()) {
			((Player) player).kickPlayer(ban.getReason());
		}
		if(ban.equals(this.plugin.banSet.last())) {
			log.info("meeeep");
		}
		log.info("Return: " + this.plugin.banSet.add(ban));
		
		if(event.isSenderPlayer()) {
			log.info("Banned `"+ player.getName() + "` until " + ban.until.getTime() + " by " + event.getSender());
			event.getSender().sendMessage("Banned `"+ player.getName() + "` until " + ban.until.getTime());
		}
		else {
			log.info("Banned `"+ player.getName() + "` until " + ban.until.getTime());
		}
	}
	
	@EventHandler
	public void onTimeUnbanEvent(TimeBanUnbanEvent event) {
		OfflinePlayer player = event.getBan().getPlayer();
		player.setBanned(false);
		this.plugin.banSet.remove(event.getBan());
		if(event.isSenderPlayer()) {
			log.info("Unbaned and removed `" + player.getName() + "` from ban list by " + event.getSender());
		} 
		else {
			log.info("Unbaned and removed `" + player.getName() + " ` from ban list.");
		}
	}
}
