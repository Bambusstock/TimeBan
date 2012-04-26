package me.Bambusstock.TimeBan;

import java.util.*;
import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.BanSet;

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
			log.info("[TimeBan] Check for unbans...");
			
			/**
			 * To prevent a java.util.ConcurrentModificationException we 'll
			 * use a workSet. The events take care of the ´real´ banlist, so
			 * we don't need to care about it.
			 */
			BanSet workSet = (BanSet) this.plugin.banSet.clone(); 		
			// go through list until unban date is in future
			Iterator<Ban> it = workSet.iterator();
			while(it.hasNext()) {
				Ban next  = it.next();
				if(next.until.before(Calendar.getInstance())) {
					TimeBanUnbanEvent event = new TimeBanUnbanEvent(next);
					this.plugin.getServer().getPluginManager().callEvent(event);
				} else {
					break;
				}
			}
			
		}
	}
}
