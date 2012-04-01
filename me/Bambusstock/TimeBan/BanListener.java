package me.Bambusstock.TimeBan;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanEvent;
import me.Bambusstock.TimeBan.event.TimeUnbanEvent;

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
		Ban ban = event.getBan();
		ban.getPlayer().setBanned(false);
		this.plugin.banSet.remove(ban);
		if(event.isSenderPlayer()) {
			log.info("Unbaned and removed `" + event.getBan().getPlayer().getName() + "` from ban list by " + event.getSender());
		} 
		else {
			log.info("Unbaned and removed `" + event.getBan().getPlayer().getName() + " ` from ban list.");
		}
	}
	
	@EventHandler
	public void onTimeBanEvent(TimeBanEvent event) {
		Ban ban = event.getBan();
		ban.getPlayer().setBanned(true);
		this.plugin.banSet.add(ban);
		((Player)ban.getPlayer()).kickPlayer(ban.getReason());
		log.info("Banned `"+ ban.getPlayer().getName() + "` until " + ban.until.getTime() + " by " + event.getSender());
	}
}
