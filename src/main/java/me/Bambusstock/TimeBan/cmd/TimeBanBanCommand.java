package me.Bambusstock.TimeBan.cmd;

import java.util.Calendar;
import java.util.List;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.event.TimeBanBanEvent;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.entity.Player;

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
            TimeBanBanEvent event = new TimeBanBanEvent(sender, ban);
            this.plugin.getServer().getPluginManager().callEvent(event);
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
        for (String playerName : players) {
            Ban ban = new Ban(this.plugin, playerName, until, reason);
            TimeBanBanEvent event = new TimeBanBanEvent(ban);
            this.plugin.getServer().getPluginManager().callEvent(event);
        }
    }
}