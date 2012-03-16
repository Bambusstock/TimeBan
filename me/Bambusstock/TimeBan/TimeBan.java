package me.Bambusstock.TimeBan;

import java.io.File;
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
	public BanSet banSet = new BanSet();
	
	/**
	 * Load a ban list, initialize Listener of TimeBan event and a synchronous scheduler. 
	 */
	public void onEnable() {
		this.banSet.load(new File("plugins/TimeBan/banlist.dat"), this);
		
		this.getServer().getPluginManager().registerEvents(new BanListener(this), this);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimeBanRunnable(this), 60L, 200L); // all 10 secs
				
	}
	
	/**
	 * Save a ban list.
	 */
	public void onDisable() {
		this.banSet.save(new File("plugins/TimeBan/banlist.dat"), this);
	}		

}