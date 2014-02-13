package me.Bambusstock.TimeBan.cmd;

import java.util.Date;
import java.util.Map;
import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.entity.Player;

import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.TerminalUtil;

/**
 * Command to display some general status information about TimeBan.
 */
public class TimeBanInfoCommand extends AbstractCommand {

    public TimeBanInfoCommand(TimeBan plugin) {
        super(plugin);
    }

    @Override
    public void execute() {
        Map<String, Ban> bans = plugin.getController().getBans();
        Player receiver = getReceiver();

        StringBuilder builder = new StringBuilder();
        builder.append(TerminalUtil.createHeadline("TimeBan Info")).append("\n");
        builder.append("Amount of bans: " + bans.size()).append("\n");
        if (bans.size() > 0) {
            Date unbanTime = plugin.getController().getUpcomingBan().getUntil().getTime();
            builder.append("Next unban: " + unbanTime);
        }

        String output = builder.toString();
        if (receiver == null) {
            TerminalUtil.printToConsole(output);
        } else {
            TerminalUtil.printToPlayer(receiver, output);
        }
    }
}
