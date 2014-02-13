package me.Bambusstock.TimeBan.cmd;

import java.util.List;
import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.TerminalUtil;

import org.bukkit.entity.Player;

/**
 * Command to remove a ban from the TimeBan watchlist.
 */
public class TimeBanRmCommand extends AbstractCommand {

    public TimeBanRmCommand(TimeBan plugin) {
        super(plugin);
    }

    // list of players to remove from watchlist
    private List<String> players;

    // option to remove all players
    private boolean rmAll;

    @Override
    public void execute() {
        Player receiver = getReceiver();

        StringBuilder builder = new StringBuilder();
        if (!players.isEmpty()) {
            builder.append(TerminalUtil.createHeadline("TimeBan rm")).append("\n");
            for (String playerName : players) {
                Ban removedBan = plugin.getController().getBans().remove(playerName);
                if (removedBan != null) {
                    builder.append("Removed temporary ban for ´").append(playerName).append("´. Player is still banned!").append("\n");
                } else {
                    builder.append("No ban for player ´").append(playerName).append("´ found!").append("\n");
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
            builder.append("All bans removed! Players still banned.");
            
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
