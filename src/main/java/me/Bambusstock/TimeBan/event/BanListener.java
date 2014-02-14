package me.Bambusstock.TimeBan.event;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Bambusstock.TimeBan.TimeBan;

import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.MessagesUtil;
import org.bukkit.Bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Main listener on events like prelogin or bans unbans.
 */
public class BanListener implements Listener {

    // Logger used by this class.
    private static final Logger log = Logger.getLogger("Minecraft");

    // instance of plugin used to get the controller and server instances
    private TimeBan plugin;

    public BanListener(TimeBan instance) {
        this.plugin = instance;
    }

    /**
     * This method is called when someone tries to login. We use this one to
     * check if the player is banned by TimeBan.
     *
     * @param event Event send when player logs in
     */
    @EventHandler
    public void onPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        String playerName = event.getName();

        // check if there is a ban...
        Ban ban = plugin.getController().getBan(playerName);

        // player is still banned, but he should already be unbanned
        if (ban != null && Calendar.getInstance().after(ban.getUntil())) {
            TimeBanUnbanEvent unbanEvent = new TimeBanUnbanEvent(ban);
            Bukkit.getServer().getPluginManager().callEvent(unbanEvent);

            // player is banned...
        } else if (ban != null && Calendar.getInstance().before(ban.getUntil())) {
            HashMap<String, String> values = new HashMap<String, String>(2);
            values.put("{until}", ban.getUntil().getTime().toString());
            values.put("{reason}", ban.getReason());

            String banMessage = MessagesUtil.formatMessage("banned_player_login", values);
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, banMessage);
        }
    }

    /**
     * Someone is going to be banned...
     *
     * @param event ban event
     */
    @EventHandler
    public void onTimeBanEvent(TimeBanBanEvent event) {
        String admin;
        if (event.getSender() != null && event.isSenderPlayer()) {
            admin = event.getSender().getDisplayName();
        } else {
            admin = "console";
        }

        // get ban
        Ban b = event.getBan();
        String player = b.getPlayer().getName();
        Date bannedUntil = b.getUntil().getTime();

        // prepare map with substitutions
        HashMap<String, String> values = new HashMap<String, String>(3);
        values.put("{user}", player);
        values.put("{until}", bannedUntil.toString());
        values.put("{admin}", admin);

        // ban player
        boolean successfull = plugin.getController().executeBan(b);
        if (successfull) {
            String serverMessage = MessagesUtil.formatMessage("ban_result_console", values);
            log.log(Level.INFO, "[TimeBan] {0}", serverMessage);

            if (event.isSenderPlayer()) {
                String message = MessagesUtil.formatMessage("ban_result", values);
                event.getSender().sendMessage(message);
            }
        } else {
            String message = MessagesUtil.formatMessage("ban_error", values);
            log.log(Level.INFO, "[TimeBan] {0}", message);

            if (event.isSenderPlayer()) {
                event.getSender().sendMessage(message);
            }
        }
    }

    @EventHandler
    public void onTimeUnbanEvent(TimeBanUnbanEvent event) {
        String admin;
        if (event.getSender() != null && event.isSenderPlayer()) {
            admin = event.getSender().getDisplayName();
        } else {
            admin = "console";
        }

        // get ban
        Ban ban = event.getBan();
        String player = ban.getPlayer().getName();

        // prepare map with substitutions
        HashMap<String, String> values = new HashMap<String, String>(2);
        values.put("{user}", player);
        values.put("{admin}", admin);

        // unban player
        boolean successfull = plugin.getController().executeUnban(ban);
        if (successfull) {
            if (!event.isSilent()) {
                String message = MessagesUtil.formatMessage("unban_result", values);
                log.log(Level.INFO, "[TimeBan] {0}", message);

                if (event.isSenderPlayer()) {
                    event.getSender().sendMessage(message);
                }
            }
        } else {
            String message = MessagesUtil.formatMessage("unban_no_result", values);
            log.log(Level.INFO, "[TimeBan] {0}", message);

            if (event.isSenderPlayer()) {
                event.getSender().sendMessage(message);
            }
        }

    }
}
