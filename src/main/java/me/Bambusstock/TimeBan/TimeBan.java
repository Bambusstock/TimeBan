package me.Bambusstock.TimeBan;

import me.Bambusstock.TimeBan.cmd.TimeBanExecutor;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class TimeBan extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private BanController controller;
    private long runDelay;

    public void onEnable() {
        // set up configuration
        this.configureMe();

        // init ban model...
        controller = new BanController();
        controller.init();

        // register listener
        this.getServer().getPluginManager().registerEvents(new BanListener(this), this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimeBanRunnable(this), 60L, this.runDelay * 20 * 60);

        // register commands
        this.getCommand("timeban").setExecutor(new TimeBanExecutor(this));
    }

    public void onDisable() {
        controller.close();
        
    }

    public void configureMe() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.runDelay = this.getConfig().getLong("runDelay");
    }

    public BanController getController() {
        return controller;
    }
}
