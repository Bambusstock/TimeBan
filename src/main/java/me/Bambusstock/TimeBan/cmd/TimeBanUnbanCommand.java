package me.Bambusstock.TimeBan.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.TerminalUtil;

/**
 * Command to unban a player watched by TimeBan.
 */
public class TimeBanUnbanCommand extends AbstractCommand {

    // list of all players to unban
    private List<String> players;
    
    // option to unban all banned players
    private boolean unbanAll;

    public TimeBanUnbanCommand(TimeBan plugin) {
        super(plugin);
    }

    @Override
    public void execute() {
        Player receiver = getReceiver();

        if (!players.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append(TerminalUtil.createHeadline("TimeBan unban")).append("\n");
            for (String playerName : players) {

                Ban ban = plugin.getController().getBan(playerName);
                if (ban != null) {

                    TimeBanUnbanEvent event;
                    if (receiver != null) {
                        event = new TimeBanUnbanEvent(receiver, ban);
                    } else {
                        event = new TimeBanUnbanEvent(ban);
                    }

                    this.plugin.getServer().getPluginManager().callEvent(event);
                } else {
                    builder.append("No ban for player ´").append(playerName).append("´ found!");
                }
            }

            String result = builder.toString();
            if (receiver == null) {
                TerminalUtil.printToConsole(result);
            } else {
                TerminalUtil.printToPlayer(receiver, result);
            }

            return;
        }

        if (unbanAll) {
            Map<String, Ban> bans = plugin.getController().getBans();
            List<Ban> workSet = new ArrayList<Ban>(bans.values());

            if (receiver == null) {
                for (Ban ban : workSet) {
                    TimeBanUnbanEvent event = new TimeBanUnbanEvent(ban);
                    this.plugin.getServer().getPluginManager().callEvent(event);
                }
            } else {
                for (Ban ban : workSet) {
                    TimeBanUnbanEvent event = new TimeBanUnbanEvent(receiver, ban);
                    this.plugin.getServer().getPluginManager().callEvent(event);
                }
            }
        }
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public boolean isUnbanAll() {
        return unbanAll;
    }

    public void setUnbanAll(boolean unbanAll) {
        this.unbanAll = unbanAll;
    }

}
