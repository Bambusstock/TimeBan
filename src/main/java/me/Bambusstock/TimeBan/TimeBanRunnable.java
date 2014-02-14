package me.Bambusstock.TimeBan;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.BanComparator;
import me.Bambusstock.TimeBan.util.MessagesUtil;
import org.bukkit.Bukkit;

/**
 * Class handling automatic unban checking.
 */
public class TimeBanRunnable implements Runnable {

    // class' logger
    private static final Logger log = Logger.getLogger("Minecraft");
    
    // plugin instance to get server instance
    private TimeBan plugin;
    
    // silten mode
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
     * @param silent true if no output should be produced.
     */
    public TimeBanRunnable(TimeBan instance, boolean silent) {
        this.plugin = instance;
        this.silent = silent;
    }

    @Override
    public void run() {
        Map<String, Ban> bans = plugin.getController().getBans();

        // empty ban list print info and stop
        if (bans.isEmpty()) {
            if (!silent) {
                String message = MessagesUtil.formatMessage("run_no_bans", null);
                log.log(Level.INFO, "[TimeBan] {0}", message);
            }
            return;
        }

        if (!silent) {
            String message = MessagesUtil.formatMessage("run_check", null);
            log.log(Level.INFO, "[TimeBan] {0}", message);
        }

        // go through list until unban date is in future
        List<Ban> sortedBans = new ArrayList<Ban>(bans.values());
        Collections.sort(sortedBans, new BanComparator(false));
        for (Ban b : sortedBans) {
            if (b.getUntil().before(Calendar.getInstance())) {
                TimeBanUnbanEvent event = new TimeBanUnbanEvent(b);
                event.setSilent(silent);
                Bukkit.getServer().getPluginManager().callEvent(event);
            } else {
                break;
            }
        }

        if (!silent) {
            String message = MessagesUtil.formatMessage("run_check_complete", null);
            log.log(Level.INFO, "[TimeBan] {0}", message);
        }
    }
}
