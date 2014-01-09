package me.Bambusstock.TimeBan.cmd;

import java.util.Map;
import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.entity.Player;

import me.Bambusstock.TimeBan.util.Ban;

/**
 * Command to display some general status information about TimeBan.
 */
public class TimeBanInfoCommand extends TimeBanCommand {

    public TimeBanInfoCommand(TimeBan plugin) {
        super(plugin);
    }
   
    /**
     * Player version of the command.
     * 
     * @param receiver Receiver of the info.
     */
    public void info(Player receiver) {
        Map<String, Ban> bans = plugin.getController().getBans();
        writeMessage(receiver, "TimeBan - Info:");
        writeMessage(receiver, "===============");
        writeMessage(receiver, "Amount of bans: " + bans.size());
        if (bans.size() > 0) {
            writeMessage(receiver, "Next unban at: " + plugin.getController().getUpcomingBan().getUntil().getTime());
        }
    }

    /**
     * Console version of the command.
     */
    public void info() {
        info(null);
    }
}
