package me.Bambusstock.TimeBan.cmd;

import java.util.HashMap;
import java.util.List;
import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.MessagesUtil;
import me.Bambusstock.TimeBan.util.TerminalUtil;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

/**
 * Command to remove a ban from the TimeBan watchlist.
 */
public class TimeBanRmCommand extends AbstractCommand {

    public TimeBanRmCommand(TimeBan plugin) {
        super(plugin);
        setCommandType(TimeBanCommands.RM);
    }

    // list of players to remove from watchlist
    private List<String> players;

    // option to remove all players
    private boolean rmAll;

    @Override
    public void execute() {
        Player receiver = getReceiver();

        StringBuilder builder = new StringBuilder();
        if (players != null && !players.isEmpty()) {
            HashMap<String, String> values = new HashMap<String, String>(1);
            builder.append(TerminalUtil.createHeadline("TimeBan rm")).append("\n");

            for (String playerName : players) {
                Ban removedBan = plugin.getController().getBans().remove(playerName);
                values.put("{user}", playerName);
                
                if (removedBan != null) {
                    String message = MessagesUtil.formatMessage("rm_result", values);
                    builder.append(message).append("\n");
                } else {
                    String message = MessagesUtil.formatMessage("rm_no_result", values);
                    builder.append(message).append("\n");
                }
            }
            
            String result = builder.toString();
            if(receiver == null) {
                TerminalUtil.printToConsole(result);
            } else {
                TerminalUtil.printToPlayer(receiver, result);
            }

            // stop here in case that rmAll is also true
            return;
        }

        builder = new StringBuilder();
        if (rmAll) {
            plugin.getController().getBans().clear();
            builder.append(TerminalUtil.createHeadline("TimeBan rm")).append("\n");
            builder.append(MessagesUtil.formatMessage("rm_result_all", null));
            
            String result = builder.toString();
            if(receiver == null) {
                TerminalUtil.printToConsole(result);
            } else {
                TerminalUtil.printToPlayer(receiver, result);
            }
        }
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public boolean isRmAll() {
        return rmAll;
    }

    public void setRmAll(boolean rmAll) {
        this.rmAll = rmAll;
    }
    
    
}
