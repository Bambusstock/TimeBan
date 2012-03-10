package de.endlesscraft.TimeBan;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @TODO:
 * 	- complete methods to load/save ban list to/from json
 *  - make Ban comparable
 * 	- add permission system
 *  - add commands
 * @author Bambusstock
 *
 */
public class TimeBan extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new BanListener(this), this);
		log.info("Hey, this is TimeBan 0.1");
	}
		
	public void onDisable() {
		log.info("Bye!");
	}

}