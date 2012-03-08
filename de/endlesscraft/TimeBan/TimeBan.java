package de.endlesscraft.TimeBan;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class TimeBan extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		log.info("Hey, this is TimeBan 0.1");
	}
	
	public void onDisable() {
		log.info("Bye!");
	}

}