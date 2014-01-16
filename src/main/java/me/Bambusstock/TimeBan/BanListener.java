package me.Bambusstock.TimeBan;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanBanEvent;
import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;


/**
 * Main listener on events like prelogin or bans unbans.
 */
public class BanListener implements Listener {

    /** 
     * Logger used by this class.
     */
    private static final Logger log = Logger.getLogger("Minecraft");
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

        // Get a ban event
        Ban ban = plugin.getController().getBan(playerName);

        // player is still banned, but he should already be unbanned
        if (ban != null && Calendar.getInstance().after(ban.getUntil())) {
            TimeBanUnbanEvent unbanEvent = new TimeBanUnbanEvent(ban);
            plugin.getServer().getPluginManager().callEvent(unbanEvent);

            // player is banned...
        } else if (ban != null && Calendar.getInstance().before(ban.getUntil())) {
            String banMessage = String.format("You're banned from this server until %s because '%s'!",
                    ban.getUntil().getTime(),
                    ban.getReason());
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
        Ban b = event.getBan();
        String player = b.getPlayer().getName();
        Date bannedUntil = b.getUntil().getTime();

        boolean successfull = plugin.getController().executeBan(b);

        if (successfull) {
            if (event.isSenderPlayer()) {
                String admin = event.getSender().getDisplayName();
                String userMessage = String.format("%sBanned `%s` until %s",
                        ChatColor.DARK_GREEN,
                        player,
                        bannedUntil);
                event.getSender().sendMessage(userMessage);

                String serverMessage = String.format("[TimeBan] Banned `%s` until %s by %s",
                        player,
                        bannedUntil,
                        admin);
                log.info(serverMessage);
            } else {
                 String serverMessage = String.format("[TimeBan] Banned `%s` until %s by %s",
                        player,
                        bannedUntil,
                        "console");
                log.info(serverMessage);
            }
        } else {
            if (event.isSenderPlayer()) {
                String userMessage = String.format("%sSomething went wrong banning `%s`...",
                        ChatColor.RED,
                        player);
                event.getSender().sendMessage(userMessage);
            }

            String serverMessage = String.format("[TimeBan] Something went wrong banning `%s`...",
                    player);
            log.info(serverMessage);
        }
    }

    @EventHandler
    public void onTimeUnbanEvent(TimeBanUnbanEvent event) {
        Ban ban = event.getBan();
        String player = ban.getPlayer().getName();
        plugin.getController().executeUnban(ban);

        if(!event.isSilent()) {
            if (event.isSenderPlayer()) {
                String userMessage = String.format("%sUnbanned `%s` from banlist.",
                        ChatColor.DARK_GREEN,
                        player);
                event.getSender().sendMessage(userMessage);

                String admin = event.getSender().getDisplayName();
                String serverMessage = String.format("[TimeBan] Unbaned and removed `%s` from banlist by %s",
                        player,
                        admin);

                log.log(Level.INFO, serverMessage);
            } else {
                log.log(Level.INFO, "[TimeBan] Unbaned and removed `{0}` from banlist.", player);
            }
        }
    }
}
