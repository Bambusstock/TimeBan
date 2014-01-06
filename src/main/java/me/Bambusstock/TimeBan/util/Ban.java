package me.Bambusstock.TimeBan.util;

import java.io.Serializable;
import java.util.Calendar;

import me.Bambusstock.TimeBan.TimeBan;

import org.bukkit.entity.Player;

public class Ban implements Serializable, Comparable<Ban> {

    private static final long serialVersionUID = -4327491657720734089L;
    private transient TimeBan plugin;

    private String stdReason = "Standard reason";
    private int stdBanDuration = 3600;

    private String player;
    private Calendar until;
    private String reason;

    /**
     * Instantiate a ban.
     *
     * @param Player Player object
     * @param until Date until user is banned
     * @param reason Reasons why the user had been banned
     */
    public Ban(TimeBan plugin, String player, Calendar until, String reason) {
        this.player = player;
        this.plugin = plugin;
        this.until = until;
        this.reason = reason;
    }

    /**
     * Compare two bans by time. 0 > if before comparator 0 < if after 0 if
     * equal
     */
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
     * Set the standard ban duration in seconds.
     *
     * @param duration Amount of seconds a user is banned, if no duration was
     * given.
     */
    public void setStandardBanDuration(int duration) {
        this.stdBanDuration = duration;
    }

    /**
     * Set the standard reason.
     *
     * @param Standard reason used if no given
     */
    public void setStandardReason(String reason) {
        this.stdReason = reason;
    }

    /**
     * Use the standard ban duration to create a standard Calendar-Object, that
     * is used to define date until user is banned.
     *
     * @return
     */
    private Calendar stdDurationToCalendar() {
        Calendar result = Calendar.getInstance();
        result.set(Calendar.SECOND, result.get(Calendar.SECOND) + this.stdBanDuration);
        return result;
    }

    /**
     *
     * @return OfflinePlayer object for this ban.
     */
    public Player getPlayer() {
        return plugin.getServer().getPlayer(player);
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
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("`").append(player).append("`").append(" until ");
        b.append(until.get(Calendar.DAY_OF_MONTH)).append(".");
        b.append(until.get(Calendar.MONTH)).append(".");
        b.append(until.get(Calendar.YEAR));
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
        hash = 11 * hash + (this.plugin != null ? this.plugin.hashCode() : 0);
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
        if (this.plugin != other.plugin && (this.plugin == null || !this.plugin.equals(other.plugin))) {
            return false;
        }
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
