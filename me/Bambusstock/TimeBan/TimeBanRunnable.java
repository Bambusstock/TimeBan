package me.Bambusstock.TimeBan;

import java.util.*;
import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;

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
		synchronized (this.plugin.banSet) {
			if(this.plugin.banSet.isEmpty()) return;
			log.info("[TimeBan Scheduler] Go to check if there some guys to unban...");
			
			// go through list until unban date is in future
			Iterator<Ban> it = this.plugin.banSet.descendingIterator();
			while(it.hasNext()) {
				Ban next  = it.next();
				log.info("Check ban for ´" + next.player + "´ ...");
				if(next.until.before(Calendar.getInstance())) {
					TimeBanUnbanEvent event = new TimeBanUnbanEvent(next);
					this.plugin.getServer().getPluginManager().callEvent(event);
				} else {
					log.info("No unbans.");
					break;
				}
			}
			
		}
	}
}
