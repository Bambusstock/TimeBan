package me.Bambusstock.TimeBan.cmd;

import java.util.Calendar;
import java.util.List;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.event.TimeBanBanEvent;
import me.Bambusstock.TimeBan.util.Ban;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

/**
 * Command used to ban players.
 */
public class TimeBanBanCommand extends TimeBanCommand {

    public TimeBanBanCommand(TimeBan plugin) {
        super(plugin);
    }
    
    /**
     * Create a ban and fire a ban event.
     *
     * @param sender
     * @param players Array of player names
     * @param until Calendar until unban time
     * @param reason Reason
     */
    public void ban(Player sender, List<String> players, Calendar until, String reason) {
        for (String playerName : players) {
            Ban ban = new Ban(this.plugin, playerName, until, reason);
            
            TimeBanBanEvent event;
            if(sender != null) {
                event = new TimeBanBanEvent(sender, ban);
            } else {
                event = new TimeBanBanEvent(ban);
            }
            
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    /**
     * Create a ban and fire a ban event. Console version.
     *
     * @param players Array of player names
     * @param until Calendar until unban time
     * @param reason Reason
     */
    public void ban(List<String> players, Calendar until, String reason) {
        ban(null, players, until, reason);
    }
}
