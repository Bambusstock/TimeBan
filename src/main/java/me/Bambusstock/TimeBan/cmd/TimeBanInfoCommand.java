package me.Bambusstock.TimeBan.cmd;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.entity.Player;

import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.MessagesUtil;
import me.Bambusstock.TimeBan.util.TerminalUtil;

/**
 * Command to display some general status information about TimeBan.
 */
public class TimeBanInfoCommand extends AbstractCommand {

    public TimeBanInfoCommand(TimeBan plugin) {
        super(plugin);
        setCommandType(TimeBanCommands.INFO);
    }

    @Override
    public void execute() {
        Map<String, Ban> bans = plugin.getController().getBans();
        Player receiver = getReceiver();

        // prepare values
        HashMap<String, String> values = new HashMap<String, String>(2);
        values.put("{amount}", String.valueOf(bans.size()));

        // put output together
        StringBuilder builder = new StringBuilder();
        builder.append(TerminalUtil.createHeadline("TimeBan info")).append("\n");
        builder.append(MessagesUtil.formatMessage("info_ban_amount", values)).append("\n");
        if (bans.size() > 0) {
            Date unbanTime = plugin.getController().getUpcomingBan().getUntil().getTime();
            values.put("{unban}", unbanTime.toString());
            
            builder.append(MessagesUtil.formatMessage("info_next_unban", values));
        }

        String output = builder.toString();
        if (receiver == null) {
            TerminalUtil.printToConsole(output);
        } else {
            TerminalUtil.printToPlayer(receiver, output);
        }
    }
}
