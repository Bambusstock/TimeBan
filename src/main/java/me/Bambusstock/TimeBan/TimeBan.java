package me.Bambusstock.TimeBan;

import me.Bambusstock.TimeBan.cmd.TimeBanExecutor;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class TimeBan extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private BanController controller;

    public void onEnable() {
        // set up configuration
        this.configureMe();

        // init ban model...
        controller = new BanController();
        controller.init();

        // register listener
        scheduleBanChecker();
        this.getServer().getPluginManager().registerEvents(new BanListener(this), this);

        // register commands
        this.getCommand("timeban").setExecutor(new TimeBanExecutor(this));
    }

    public void onDisable() {
        controller.close();
        
    }

    public void configureMe() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
    
    protected void scheduleBanChecker() {
        boolean isSilent = getConfig().getBoolean("runSilent", false);
        long runDelay = getConfig().getLong("runDelay", 1);
        
        TimeBanRunnable r = new TimeBanRunnable(this, isSilent);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, r, 60L, runDelay * 20 * 60);
    }

    public BanController getController() {
        return controller;
    }
}
