package de.endlesscraft.TimeBan;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BanListener implements Listener
{
	private TimeBan 		plugin;
	private Set<Ban> 		banSet;
	private TimeBanRunnable scheduler;
	
	/**
	 * Load the ban set from a json file
	 * @return Set of bans
	 */
	protected Set<Ban> loadSet() {
		// load from json ...
		return new TreeSet<Ban>();
	}
	
	/**
	 * Save the ban set to a json file...
	 */
	protected void saveSet() {
		// save to json ...
	}
	
	/**
	 * Set plugin instance, load ban list, start synch scheduler.
	 * checking for upcoming unbans. 
	 * @param instance
	 */
	public BanListener(TimeBan instance) {
		this.plugin = instance;
		this.banSet = this.loadSet();
		
		// Synchronous, using main thread every 10 sec
		TimeBanRunnable scheduler = new TimeBanRunnable();
		scheduler.setSet(this.banSet);
		this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, scheduler, 60L, 200L);
	}
		
	@EventHandler
	public void onTimeBanEvent(TimeBanEvent event) {
		this.banSet.add(event.getBan());
		this.scheduler.setSet(this.banSet);
		this.saveSet();
	}
}
