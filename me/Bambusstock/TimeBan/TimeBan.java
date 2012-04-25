package me.Bambusstock.TimeBan;

import java.io.File;
import java.util.logging.Logger;


import me.Bambusstock.TimeBan.util.BanSet;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @TODO:
 * 	- add permission system
 *  - add commands
 *  	- make it available from console and ingame
 * @author Bambusstock
 *
 */
public class TimeBan extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");
	public BanSet banSet = new BanSet();
	public Long runDelay;
	/**
	 * Load a ban list, initialize Listener of TimeBan event and a synchronous scheduler. 
	 */
	public void onEnable() {
		this.configureMe();
		this.banSet.load(new File("./plugins/TimeBan/banlist.dat"), this);		
		this.getServer().getPluginManager().registerEvents(new BanListener(this), this);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimeBanRunnable(this), 60L, this.runDelay * 20 * 60);
		this.getCommand("timeban").setExecutor(new TimeBanExecutor(this));
	}
	
	public void configureMe() {
		this.getConfig().options().copyDefaults(false);
		this.runDelay = this.getConfig().getLong("runDelay");
	}
	
	/**
	 * Save a ban list.
	 */
	public void onDisable() {
		this.banSet.save(new File("./plugins/TimeBan/banlist.dat"), this);
	}		

}