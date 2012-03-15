package de.endlesscraft.TimeBan;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BanListener implements Listener
{
	private TimeBan 		plugin;
	
	/**
	 * Set plugin instance, load ban list, start synch scheduler.
	 * checking for upcoming unbans. 
	 * @param instance
	 */
	public BanListener(TimeBan instance) {
		this.plugin = instance;
		
		
	}
		
	@EventHandler
	public void onTimeBanEvent(TimeBanEvent event) {
		this.plugin.banSet.add(event.getBan());
	}
}
