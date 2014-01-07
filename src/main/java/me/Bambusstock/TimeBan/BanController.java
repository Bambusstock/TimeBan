package me.Bambusstock.TimeBan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.BanComparator;
import me.Bambusstock.TimeBan.util.BanMapLoader;
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

    public Ban getUpcomingBan() {
        List<Ban> sorted = new ArrayList<Ban>(bans.values());
        Collections.sort(sorted, new BanComparator());
        return sorted.get(0);
    }

    /**
     * Search for a username in the ban list.
     * 
     * @param search String to search for.
     * @param reverse true if result should be returned reverse.
     * 
     * @return A ordered list of bans matching the search pattern.
     */
    public List<Ban> searchBans(String search, boolean reverse) {
        List<Ban> result = new ArrayList<Ban>();

        if (search.isEmpty()) {
            result.addAll(bans.values());
        } else {

            // compile regex to search...
            Pattern searchPattern = Pattern.compile(search);
            for (String n : bans.keySet()) {
                if (searchPattern.matcher(n).matches()) {
                    result.add(bans.get(n));
                }
            }
        }

        Collections.sort(result, new BanComparator(reverse));

        return result;
    }
}
