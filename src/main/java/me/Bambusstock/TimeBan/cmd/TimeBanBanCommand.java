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
public class TimeBanBanCommand extends AbstractCommand {

    public TimeBanBanCommand(TimeBan plugin) {
        super(plugin);
        setCommandType(TimeBanCommands.BAN);
    }

    // list of player names to ban
    private List<String> players;
    
    // calendar object indicating ban duration
    private Calendar until;
    
    // reason for ban
    private String reason;

    @Override
    public void execute() {
        Player receiver = getReceiver();
        for (String playerName : players) {
            Ban ban = new Ban(this.plugin, playerName, until, reason);

            TimeBanBanEvent event;
            if (receiver != null) {
                event = new TimeBanBanEvent(receiver, ban);
            } else {
                event = new TimeBanBanEvent(ban);
            }

            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public Calendar getUntil() {
        return until;
    }

    public void setUntil(Calendar until) {
        this.until = until;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
