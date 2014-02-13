package me.Bambusstock.TimeBan.cmd;

import java.util.List;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.TerminalUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;
import org.bukkit.util.ChatPaginator.ChatPage;

/**
 * Command to list bans currently watched by TimeBan.
 */
public class TimeBanListCommand extends AbstractCommand {

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
        b.append("`%s`");

        userMessage = b.toString();
    }

    /**
     * Console message.
     */
    private static final String consoleMessage = "[TimeBan] `%s` until %s because `%s`";

    // page to display
    private int page;

    // search string
    private String search;

    // reverse option
    private boolean reverse;

    // simple option
    private boolean simple;

    public TimeBanListCommand(TimeBan plugin) {
        super(plugin);
    }

    @Override
    public void execute() {
        Player receiver = getReceiver();

        StringBuilder builder = new StringBuilder();
        builder.append(TerminalUtil.createHeadline("TimeBan list")).append("\n");

        List<Ban> result = plugin.getController().searchBans(search, reverse);
        if (!result.isEmpty()) {
            if (simple == true) {
                for (Ban b : result) {
                    builder.append(b.toString()).append("\n");
                }
            } else {
                for (Ban b : result) {
                    String player = b.getPlayer().getName();
                    String until = b.getUntil().getTime().toString();
                    String reason = b.getReason();

                    String message = createColoredMessage(receiver == null, player, until, reason);
                    builder.append(message).append("\n");
                }
            }
        } else {
            builder.append("No results.");
        }

        String message = builder.toString();
        if (receiver == null) {
            TerminalUtil.printToConsole(message);
        } else {
            TerminalUtil.printToPlayer(receiver, message, page);
        }
    }

    /**
     * Write a plain message to the console or add a formatted message to the
     * local StringBuffer to use the whole output with a ChatPaginator.
     *
     * @param colored true if should be colored
     * @param args args to fill the message
     *
     * @return message
     */
    protected String createColoredMessage(boolean colored, String... args) {
        if (!colored) {
            return String.format(consoleMessage, args);
        } else {
            return String.format(userMessage, args);
        }
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean isSimple() {
        return simple;
    }

    public void setSimple(boolean simple) {
        this.simple = simple;
    }

}
