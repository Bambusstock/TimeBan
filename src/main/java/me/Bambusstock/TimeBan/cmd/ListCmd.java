package me.Bambusstock.TimeBan.cmd;

import java.util.List;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ListCmd extends Cmd {

    private static final String userMessage;
    private static final String consoleMessage = "[TimeBan] `%s` until %s because `%s`";

    static {
        StringBuilder b = new StringBuilder();
        b.append(ChatColor.RED);
        b.append("`%s`").append(ChatColor.WHITE);
        b.append(" until ").append(ChatColor.AQUA);
        b.append("%s").append(ChatColor.WHITE);
        b.append(" because ").append(ChatColor.GOLD);
        b.append("`%s`");

        userMessage = b.toString();
    }
    

    public ListCmd(TimeBan plugin) {
        super(plugin);
    }

    /**
     * List all bans on the game screen.
     * 
     * @param sender Receiver of the message.
     * @param search Search string to filter banned users.
     * @param reverse Sort of the output(sorted by date).
     * @param simple Output style
     */
    public void list(Player sender, String search, boolean reverse, boolean simple) {
        sender.sendMessage("=== TimeBan - Listing ===");
        List<Ban> result = plugin.getController().searchBans(search, reverse);
        if (!result.isEmpty()) {
            if (simple == true) {
                for (Ban b : result) {
                    sender.sendMessage(b.toString());
                }
            } else {
                for (Ban b : result) {
                    String player = b.getPlayer().getName();
                    String until = b.getUntil().getTime().toString();
                    String reason = b.getReason();

                    sender.sendMessage(String.format(userMessage, player, until, reason));
                }
            }
        } else {
            sender.sendMessage(ChatColor.GREEN + "No bans found.");
        }
        sender.sendMessage("======================");
    }

    /**
     * List all bans. Console version
     *
     * @param search Regex used for a search
     * @param reverse True, then all bans are sorted reverse (descending time)
     * @param simple True, then the ban information are compressed
     */
    public void list(String search, boolean reverse, boolean simple) {
        List<Ban> bans = plugin.getController().searchBans(search, reverse);
        if (!bans.isEmpty()) {
            if (simple == true) {
                for(Ban b : bans) {
                    log.info(b.toString());
                }
            } else {
                for(Ban b : bans) {
                    String player = b.getPlayer().getName();
                    String until = b.getUntil().getTime().toString();
                    String reason = b.getReason();
                    
                    log.info(String.format(consoleMessage, player, until, reason));
                }
            }
        } else {
            log.info("No bans found.");
        }
    }
}
