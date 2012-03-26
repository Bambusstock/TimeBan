package me.Bambusstock.TimeBan;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanEvent;
import me.Bambusstock.TimeBan.event.TimeUnbanEvent;

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
		event.getBan().getPlayer().setBanned(false);
		this.plugin.banSet.remove(event.getBan());
		log.info("Unbaned and removed `" + event.getBan().getPlayer().getName() + "` from ban list.");	
	}
	
	@EventHandler
	public void onTimeBanEvent(TimeBanEvent event) {
		event.getBan().getPlayer().setBanned(true);
		this.plugin.banSet.add(event.getBan());
		log.info("Banned `"+ event.getBan().getPlayer().getName() + "` until " + event.getBan().until + " by " + event.getSender());
	}
}
