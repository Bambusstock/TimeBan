package me.Bambusstock.TimeBan.cmd;

import java.util.Map;
import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.entity.Player;

import me.Bambusstock.TimeBan.util.Ban;

public class TimeBanInfoCommand extends TimeBanCommand {

    public TimeBanInfoCommand(TimeBan plugin) {
        super(plugin);
    }
    
    public void info(Player sender) {
        Map<String, Ban> bans = plugin.getController().getBans();
        sender.sendMessage("TimeBan - Info:");
        sender.sendMessage("===============");
        sender.sendMessage("Amount of bans: " + bans.size());
        if (bans.size() > 0) {
            sender.sendMessage("Next unban at: " + plugin.getController().getUpcomingBan().getUntil().getTime());
        }
    }

    public void info() {
        Map<String, Ban> bans = plugin.getController().getBans();
        log.info("TimeBan - Info:");
        log.info("===============");
        log.info("Amount of bans: " + bans.size());
        if (bans.size() > 0) {
            log.info("Next unban at: " + plugin.getController().getUpcomingBan().getUntil().getTime());
        }
    }
}
