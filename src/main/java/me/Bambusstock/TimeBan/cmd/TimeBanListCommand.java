package me.Bambusstock.TimeBan.cmd;

import java.util.List;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;
import org.bukkit.util.ChatPaginator.ChatPage;

/**
 * Command to list bans currently watched by TimeBan.
 */
public class TimeBanListCommand extends TimeBanCommand {

    /**
     * Colored user message. Constructed using a static initializer.
     */
    private static final String userMessage;

    static {
        StringBuilder b = new StringBuilder();
        b.append(ChatColor.RED);
        b.append("`%s`").append(ChatColor.WHITE);
        b.append(" until ").append(ChatColor.AQUA);
        b.append("%s").append(ChatColor.WHITE);
        b.append(" because ").append(ChatColor.GOLD);
        b.append("`%s`").append("\n");

        userMessage = b.toString();
    }

    /**
     * Console message.
     */
    private static final String consoleMessage = "[TimeBan] `%s` until %s because `%s`";

    protected StringBuilder userOutput = new StringBuilder();

    public TimeBanListCommand(TimeBan plugin) {
        super(plugin);
    }

    /**
     * List all bans on the game screen.
     *
     * @param sender Receiver of the message.
     * @param page Page to display. 0 will display everything.
     * @param search Search string to filter banned users.
     * @param reverse Sort of the output(sorted by date).
     * @param simple Output style
     */
    public void list(Player sender, int page, String search, boolean reverse, boolean simple) {
        log.info("Page: " + page);
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

                    appendPrettyMessage(sender, player, until, reason);
                }
            }
        } else {
            writeMessage(sender, "No bans found.");
        }
        writeMessage(sender, "======================");

        // send message
        if (sender != null && sender instanceof Player) {
            if (page > 0) {
                ChatPage output = ChatPaginator.paginate(userOutput.toString(), page);
                sender.sendMessage(output.getLines());
            } else {
                sender.sendMessage(userOutput.toString());
            }
        }
    }

    /**
     * List all bans. Console version
     *
     * @param page Pager to display. 0 to display everything.
     * @param search Regex used for a search
     * @param reverse True, then all bans are sorted reverse (descending time)
     * @param simple True, then the ban information are compressed
     */
    public void list(int page, String search, boolean reverse, boolean simple) {
        list(null, page, search, reverse, simple);
    }

    /**
     * Write a plain message to the console or add a formatted message to the
     * local StringBuffer to use the whole output with a ChatPaginator.
     *
     * @param receiver Player to receive this message, null if console is output
     * @param args Arguments to be inserted into message.
     *
     */
    protected void appendPrettyMessage(Player receiver, String... args) {
        if (receiver == null) {
            log.info(String.format(consoleMessage, args));
        } else {
            userOutput.append(String.format(userMessage, args));
        }
    }

}
