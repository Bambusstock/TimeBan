package me.Bambusstock.TimeBan;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanEvent;
import me.Bambusstock.TimeBan.event.TimeUnbanEvent;
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
	public void onTimeUnbanEvent(TimeUnbanEvent event) {
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
	
	@EventHandler
	public void onTimeBanEvent(TimeBanEvent event) {
		Ban ban = event.getBan();
		OfflinePlayer player = ban.getPlayer();
		
		player.setBanned(true);
		((Player) player).kickPlayer(ban.getReason());
		this.plugin.banSet.add(ban);
		
		if(event.isSenderPlayer()) {
			log.info("Banned `"+ player.getName() + "` until " + ban.until.getTime() + " by " + event.getSender());
			event.getSender().sendMessage("Banned `"+ player.getName() + "` until " + ban.until.getTime());
		}
		else {
			log.info("Banned `"+ player.getName() + "` until " + ban.until.getTime());
		}
	}
}
