package me.Bambusstock.TimeBan.util;

import java.io.Serializable;
import java.util.Calendar;

import me.Bambusstock.TimeBan.TimeBan;

import org.bukkit.OfflinePlayer;

public class Ban
implements Serializable, Comparable<Ban>
{
	private static final long	serialVersionUID	= -4327491657720734089L;
	private transient TimeBan plugin;
	
	public String stdReason = "Standard reason";
	public int stdBanDuration = 3600;
	
	public String player;
	public Calendar until;
	public String reason;
	
	/**
	 * Instantiate a ban.
	 * @param Player Player object
	 * @param until Date until user is banned
	 * @param reason Reasons why the user had been banned
	 */
	public Ban(TimeBan plugin, String player, Calendar until, String reason) {
		this.player = player;
		this.plugin = plugin;
		this.until  = until;
		this.reason = reason;
	}
	
	/**
	 * Instantiate a ban using the default reason.
	 * @param Player Player object
	 * @param until Date until the user is banned
	 */
	public Ban(TimeBan plugin, String player, Calendar until) {
		this.player = player;
		this.plugin = plugin;
		this.until = until;
		this.reason = this.stdReason;
	}
	
	/**
	 * Instantiate a ban using the default duration.
	 * @param Player object
	 * @param Reason why the player was banned
	 */
	public Ban(TimeBan plugin, String player, String reason) {
		this.player= player;
		this.plugin = plugin;
		this.until = this.stdDurationToCalendar();
		this.reason = reason;
	}
	
	/**
	 * Instantiate a ban using the default duration and default reasons.
	 * @param Player object
	 */
	public Ban(TimeBan plugin, String player) {
		this.player = player;
		this.plugin = plugin;
		this.until = this.stdDurationToCalendar();
		this.reason = this.stdReason;
	}
	
	/**
	 * Compare two bans by time.
	 *  0 > if before comparator
	 *  0 < if after
	 *  0 if equal
	 */
	public int compareTo(Ban ban) {
		// not exactly equal but already exists
		if(!ban.player.equalsIgnoreCase(this.player)) {
			if(this.until.after(ban)) {
				return 1;
			} 
			else if(this.until.before(ban)) {
				return -1;
			}
			// would mean that the dates are equal, however, append it
			return -1;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Set the standard ban duration in seconds.
	 * @param duration Amount of seconds a user is banned, if no duration was given.
	 */
	public void setStandardBanDuration(int duration) {
		this.stdBanDuration= duration;
	}
	
	/**
	 * Set the standard reason.
	 * @param Standard reason used if no given
	 */
	public void setStandardReason(String reason) {
		this.stdReason = reason;
	}
	
	/**
	 * Use the standard ban duration to create a standard Calendar-Object, 
	 * that is used to define date until user is banned.
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
	public OfflinePlayer getPlayer() {
		return this.plugin.getServer().getOfflinePlayer(this.player);
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
     * @return Reason
     */
    public String getReason() {
   		return this.reason;
    }
	
	/**
	 * Convert this object into a string.
	 */
	public String toString() {
		String until = this.until.get(Calendar.DAY_OF_MONTH) + "." + this.until.get(Calendar.MONTH) + "." + this.until.get(Calendar.YEAR);
		return "`" +  this.player + "` until " + until + " - "  + this.reason;
	}
	
	/**
	 * Check if this ban is empty.
	 * @return
	 */
	public boolean isEmpty() {
		if(!this.player.isEmpty() && !this.reason.isEmpty()) return false;
		return true;
	}
}
