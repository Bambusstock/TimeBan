package me.Bambusstock.TimeBan.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Ban implements Serializable, Comparable<Ban> {

    private static final long serialVersionUID = -4327491657720734089L;
    
    // short date format used by toString()
    private final SimpleDateFormat shortFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

    // player name
    private String player;
    
    // unban date
    private Calendar until;
    
    // reason of ban
    private String reason;

    /**
     * Instantiate a ban.
     *
     * @param player Player object
     * @param until Date until user is banned
     * @param reason Reasons why the user had been banned
     */
    public Ban(String player, Calendar until, String reason) {
        this.player = player;
        this.until = until;
        this.reason = reason;
    }

    @Override
    public int compareTo(Ban ban) {
        // not exactly equal but already exists
        if (!ban.player.equalsIgnoreCase(this.player)) {
            if (this.until.after(ban)) {
                return 1;
            } else if (this.until.before(ban)) {
                return -1;
            }
            // would mean that the dates are equal, however, append it
            return -1;
        } else {
            return 0;
        }
    }

    /**
     *
     * @return OfflinePlayer object for this ban.
     */
    public OfflinePlayer getPlayer() {
        return Bukkit.getServer().getOfflinePlayer(player);
    }

    /**
     *
     * @return Calender object for this ban.
     */
    public Calendar getUntil() {
        return this.until;
    }

    /**
     * Return the banning reason
     *
     * @return Reason
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * Convert this object into a string.
     */
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("`").append(player).append("`").append(" until ");
        b.append(shortFormat.format(until.getTime()));
        b.append(" - ").append(reason);
        
        return b.toString();
    }

    /**
     * Check if this ban is empty.
     *
     * @return
     */
    public boolean isEmpty() {
        return (!this.player.isEmpty() && !this.reason.isEmpty());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (this.player != null ? this.player.hashCode() : 0);
        hash = 11 * hash + (this.until != null ? this.until.hashCode() : 0);
        hash = 11 * hash + (this.reason != null ? this.reason.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ban other = (Ban) obj;
        if ((this.player == null) ? (other.player != null) : !this.player.equals(other.player)) {
            return false;
        }
        if (this.until != other.until && (this.until == null || !this.until.equals(other.until))) {
            return false;
        }
        if ((this.reason == null) ? (other.reason != null) : !this.reason.equals(other.reason)) {
            return false;
        }
        return true;
    }
    
    
}
