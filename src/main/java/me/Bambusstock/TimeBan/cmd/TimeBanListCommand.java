package me.Bambusstock.TimeBan.cmd;

import java.util.List;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TimeBanListCommand extends TimeBanCommand {

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
    

    public TimeBanListCommand(TimeBan plugin) {
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
        writeMessage(sender, "=== TimeBan search results ===");
        List<Ban> result = plugin.getController().searchBans(search, reverse);
        if (!result.isEmpty()) {
            if (simple == true) {
                for (Ban b : result) {
                    writeMessage(sender, b.toString());
                }
            } else {
                for (Ban b : result) {
                    String player = b.getPlayer().getName();
                    String until = b.getUntil().getTime().toString();
                    String reason = b.getReason();

                   writePrettyMessage(sender, player, until, reason);
                }
            }
        } else {
            writeMessage(sender, "No bans found.");
        }
        writeMessage(sender, "======================");
    }
    
    protected void writeMessage(Player sender, String message) {
        if(sender == null) {
            log.info(message);
        } else {
            sender.sendMessage(message);
        }
    }
    
    protected void writePrettyMessage(Player sender, String... args) {
        if(sender == null) {
            log.info(String.format(consoleMessage, args));
        } else {
            sender.sendMessage(String.format(userMessage, args));
        }
    }

    /**
     * List all bans. Console version
     *
     * @param search Regex used for a search
     * @param reverse True, then all bans are sorted reverse (descending time)
     * @param simple True, then the ban information are compressed
     */
    public void list(String search, boolean reverse, boolean simple) {
        list(null, search, reverse, simple);
    }
}
