package me.Bambusstock.TimeBan;

import me.Bambusstock.TimeBan.event.BanListener;
import me.Bambusstock.TimeBan.cmd.TimeBanExecutor;
import java.util.logging.Logger;
import me.Bambusstock.TimeBan.util.MessagesUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class TimeBan extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private BanController controller;

    public void onEnable() {
        // set up configuration
        this.configureMe();
        
        // load resources with locale
        reloadResource();

        // init ban model...
        controller = new BanController();
        controller.init();

        // register listener
        scheduleBanChecker();
        this.getServer().getPluginManager().registerEvents(new BanListener(this), this);

        // register commands
        this.getCommand("timeban").setExecutor(new TimeBanExecutor(this));
    }

    @Override
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
    
    /**
     * Reload resources like messages or man pages.
     */
    protected void reloadResource() {
        String locale = getConfig().getString("locale");
        MessagesUtil.reload(locale);
    }

    public BanController getController() {
        return controller;
    }
}
