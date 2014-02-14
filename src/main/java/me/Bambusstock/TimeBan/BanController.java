package me.Bambusstock.TimeBan;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import me.Bambusstock.TimeBan.util.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
/**
 *
 * @author Bambusstock
 */
public class BanController {

    // list mapping usernames to according bans
    private Map<String, Ban> bans;

    /**
     * Initiate the ban controller. Load the serialized map from disk.
     */
    public void init() {
        Map<String, Ban> rawMap = BanMapLoader.load("./plugins/TimeBan/banlist.dat");
        bans = Collections.synchronizedMap(rawMap);
        
        if(bans == null || bans.isEmpty()) {
            bans = Collections.synchronizedMap(new TreeMap<String, Ban>());
        }
    }

    /**
     * Save a serialized ban list to the disk.
     */
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
        OfflinePlayer player = ban.getPlayer();
        String reason = ban.getReason();

        player.setBanned(true);
        if (player.isOnline()) {
            Bukkit.getPlayer(player.getName()).kickPlayer(reason);
        }

        bans.put(player.getName(), ban);
        return bans.containsKey(player.getName());
    }

    /**
     * Executes a unban.
     *
     * @param ban Ban object
     * @return true if ban found false if no ban could be found.
     */
    public boolean executeUnban(Ban ban) {
        OfflinePlayer player = ban.getPlayer();
        player.setBanned(false);
        
        Ban banObject = bans.remove(ban.getPlayer().getName());

        return banObject != null;
    }

    public Map<String, Ban> getBans() {
        return bans;
    }

    public Ban getBan(String player) {
        return bans.get(player);
    }

    public Ban getUpcomingBan() {
        List<Ban> sorted = new ArrayList<Ban>(bans.values());
        Collections.sort(sorted, new BanComparator(false));
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

        if (search == null || search.isEmpty()) {
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
