package me.Bambusstock.TimeBan;

import java.util.*;
import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;

/**
 * Class handling automatic unban checking.
 */
public class TimeBanRunnable implements Runnable {

    private static final Logger log = Logger.getLogger("Minecraft");
    private TimeBan plugin;
    private boolean silent;

    /**
     * New object. Silent mode off.
     */
    public TimeBanRunnable(TimeBan instance) {
        this(instance, false);
    }
    
    /**
     * New object.
     * 
     * @param true if no output should be produced.
     */
    public TimeBanRunnable(TimeBan instance, boolean silent) {
        this.plugin = instance;
        this.silent = silent;
    }

    public void run() {
        Map<String, Ban> bans = plugin.getController().getBans();
        synchronized (bans) {
            if (bans.isEmpty()) {
                if(!silent) {
                    log.info("[TimeBan] Banlist empty.");
                }
                return;
            }
            
            if(!silent) {
                log.info("[TimeBan] Check for unbans...");
            }
            
            /**
             * To prevent a java.util.ConcurrentModificationException we 'll use
             * a workSet. The events take care of the ´real´ banlist, so we
             * don't need to care about it.
             */
            List<Ban> workSet = new ArrayList<Ban>();
            workSet.addAll(bans.values());
            
            // go through list until unban date is in future
            for(Ban b : workSet) {
                if (b.getUntil().before(Calendar.getInstance())) {
                    TimeBanUnbanEvent event = new TimeBanUnbanEvent(b);
                    event.setSilent(silent);
                    this.plugin.getServer().getPluginManager().callEvent(event);
                } else {
                    break;
                }
            }
            
            if(!silent) {
                log.info("[TimeBan] Check complete.");
            }
        }
    }
}
