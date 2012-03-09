package de.endlesscraft.TimeBan;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Check for x seconds if there is a upcoming unban.
 * @author Bambusstock
 *
 */
public class TimeBanRunnable implements Runnable
{
	Logger log = Logger.getLogger("Minecraft");
	
	static Set<Object> set;
	
	public static void setSet(Set<Object> set) {
		TimeBanRunnable.set = set;
	}
	
	public void run() {
		log.info("Hi I'm you're scheduler.");
	}
}
