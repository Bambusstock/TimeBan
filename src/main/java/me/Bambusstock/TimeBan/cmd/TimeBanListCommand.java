package me.Bambusstock.TimeBan.cmd;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.MessagesUtil;
import me.Bambusstock.TimeBan.util.TerminalUtil;

import org.bukkit.entity.Player;

/**
 * Command to list bans currently watched by TimeBan.
 */
public class TimeBanListCommand extends AbstractCommand {

    private static final SimpleDateFormat shortFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

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
        setCommandType(TimeBanCommands.LIST);
    }

    @Override
    public void execute() {
        Player receiver = getReceiver();

        StringBuilder builder = new StringBuilder();
        builder.append(TerminalUtil.createHeadline("TimeBan list")).append("\n");

        List<Ban> result = plugin.getController().searchBans(search, reverse);
        if (!result.isEmpty()) {
            HashMap<String, String> values = new HashMap<String, String>(3);
            if (simple == true) {
                for (Ban b : result) {
                    values.put("{user}", b.getPlayer().getName());
                    values.put("{until}", shortFormat.format(b.getUntil().getTime()));
                    values.put("{reason}", b.getReason());

                    String message = MessagesUtil.formatMessage("list_short_result", values);
                    builder.append(message).append("\n");
                }
            } else {
                for (Ban b : result) {
                    values.put("{user}", b.getPlayer().getName());
                    values.put("{until}", b.getUntil().getTime().toString());
                    values.put("{reason}", b.getReason());

                    String message = MessagesUtil.formatMessage("list_result", values);
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
