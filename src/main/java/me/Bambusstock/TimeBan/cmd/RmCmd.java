package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RmCmd extends Cmd {

    public RmCmd(TimeBan plugin) {
        super(plugin);
    }

    /**
     * Search for a ban for the given players and remove him. No event is fired.
     *
     * @param sender
     * @param players
     */
    public void rm(Player sender, String[] players) {
        for (String playerName : players) {
            Ban removedBan = plugin.getController().getBans().remove(playerName);
            if (removedBan != null) {
                sender.sendMessage("Removed temporary ban for ´" + playerName + "´. He is still banned!");
            } else {
                sender.sendMessage(ChatColor.RED + "No ban for player ´" + playerName + "´ found!");
            }
        }
    }

    /**
     * Search for a ban for the given players and remove him. No event is fired.
     * Console version.
     *
     * @param players
     */
    public void rm(String[] players) {
        for (String playerName : players) {
            Ban removedBan = plugin.getController().getBans().remove(playerName);
            if (removedBan != null) {
                log.info("[TimeBan] Removed temporary ban for ´" + playerName + "´. He is still banned!");
            } else {
                log.info("[TimeBan] No ban for player ´" + playerName + "´ found!");
            }
        }
    }

    /**
     * Remove all bans. No event is fired.
     *
     * @param sender
     */
    public void rmAll(Player sender) {
        plugin.getController().getBans().clear();
        sender.sendMessage("All bans removed!");
    }

    /**
     * Remove all bans. Console version.
     */
    public void rmAll() {
        plugin.getController().getBans().clear();
        log.info("[TimeBan] All temporary bans are removed! The players are still banned!");
    }
}
