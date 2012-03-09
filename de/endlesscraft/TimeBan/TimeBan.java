package de.endlesscraft.TimeBan;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @TODO:
 * 	- use a sorted set
 * 	- provide method to load/save the ban list
 * 	- add event system
 * 	- add permission system
 * @author Bambusstock
 *
 */
public class TimeBan extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		Set<Object> BanSet = new HashSet<Object>();
		Object test = new Ban("test");
		BanSet.add(test);
		
		TimeBanRunnable t = new TimeBanRunnable();
		TimeBanRunnable.setSet(BanSet);
		getServer().getScheduler().scheduleAsyncRepeatingTask(this, t, 60L, 200L); // Synchronous, using main thread every 10 sec
		
		log.info("Hey, this is TimeBan 0.1");
	}
		
	public void onDisable() {
		// Save Ban list ....
		log.info("Bye!");
	}

}