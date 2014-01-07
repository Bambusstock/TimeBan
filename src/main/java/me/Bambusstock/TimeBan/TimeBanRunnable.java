package me.Bambusstock.TimeBan;

import java.util.*;
import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;

public class TimeBanRunnable implements Runnable {

    Logger log = Logger.getLogger("Minecraft");
    private TimeBan plugin;

    public TimeBanRunnable(TimeBan instance) {
        this.plugin = instance;
    }

    public void run() {
        Map<String, Ban> bans = plugin.getController().getBans();
        synchronized (bans) {
            if (bans.isEmpty()) {
                return;
            }
            log.info("[TimeBan] Check for unbans...");
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
                    this.plugin.getServer().getPluginManager().callEvent(event);
                } else {
                    break;
                }
            }
            
            log.info("[TimeBan] Check complete.");
        }
    }
}
