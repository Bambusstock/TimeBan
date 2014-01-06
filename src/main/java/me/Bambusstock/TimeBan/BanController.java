package me.Bambusstock.TimeBan;

import java.util.Map;
import java.util.logging.Logger;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.BanMapLoader;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author ferenc
 */
public class BanController {

    private static final Logger log = Logger.getLogger("Minecraft");
    private Map<String, Ban> bans;

    public void init() {
        bans = BanMapLoader.load("./plugins/TimeBan/banlist.dat");
    }

    public void close() {
        BanMapLoader.save(bans, "./plugins/TimeBan/banlist.dat");
    }

    /**
     * Executes a ban. If there's already a ban it gets overwritten.
     *
     * @param ban Ban object.
     * @return true if ban was successful.
     */
    public boolean executeBan(Ban ban) {
        Player player = ban.getPlayer();
        String reason = ban.getReason();

        player.setBanned(true);
        if (player.isOnline()) {
            player.kickPlayer(reason);
        }

        bans.put(player.getName(), ban);
        return bans.containsKey(player.getName());
    }

    /**
     * Executes a unban. If there's not ban nothing happens.
     *
     * @param ban Ban object
     * @return true
     */
    public boolean executeUnban(Ban ban) {
        Player player = ban.getPlayer();
        player.setBanned(false);

        synchronized (bans) {
            bans.remove(ban);
        }

        return true;
    }

    public Map<String, Ban> getBans() {
        return bans;
    }

    public Ban getBan(String player) {
        return bans.get(player);
    }
}
