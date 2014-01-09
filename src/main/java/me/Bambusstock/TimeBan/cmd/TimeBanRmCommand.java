package me.Bambusstock.TimeBan.cmd;

import java.util.List;
import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.entity.Player;

public class TimeBanRmCommand extends TimeBanCommand {

    public TimeBanRmCommand(TimeBan plugin) {
        super(plugin);
    }

    /**
     * Search for a ban for the given players and remove him. No event is fired.
     *
     * @param sender
     * @param players
     */
    public void rm(Player sender, List<String> players) {
        for (String playerName : players) {
            Ban removedBan = plugin.getController().getBans().remove(playerName);
            if (removedBan != null) {
                writeMessage(sender, "Removed temporary ban for ´" + playerName + "´. Player is still banned!");
            } else {
                writeMessage(sender, "No ban for player ´" + playerName + "´ found!");
            }
        }
    }

    /**
     * Search for a ban for the given players and remove him. No event is fired.
     * Console version.
     *
     * @param players
     */
    public void rm(List<String> players) {
        rm(null, players);
    }

    /**
     * Remove all bans. No event is fired.
     *
     * @param sender
     */
    public void rmAll(Player sender) {
        plugin.getController().getBans().clear();
        writeMessage(sender, "All bans removed! Players still banned.");
    }

    /**
     * Remove all bans. Console version.
     */
    public void rmAll() {
        rmAll(null);
    }
}
