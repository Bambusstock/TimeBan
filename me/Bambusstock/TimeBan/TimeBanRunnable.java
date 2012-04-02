package me.Bambusstock.TimeBan;

import java.util.Calendar;
import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeUnbanEvent;

/**
 * Check if there is a player to unban.
 * @author Bambusstock
 *
 */
public class TimeBanRunnable implements Runnable
{
	Logger log = Logger.getLogger("Minecraft");
	private TimeBan plugin;
	
	public TimeBanRunnable(TimeBan instance) {
		this.plugin = instance;
	}
	
	public void run() {
		if(this.plugin.banSet.isEmpty()) return;
		log.info("[TimeBan Scheduler] Go to check if there some guys to unban...");
		
		// go through list until unban date is in future
		// should execute a command?
		while(this.plugin.banSet.last().until.after(Calendar.getInstance())) {
			TimeUnbanEvent event = new TimeUnbanEvent(this.plugin.banSet.last());
			this.plugin.getServer().getPluginManager().callEvent(event);
		}
	}
}
