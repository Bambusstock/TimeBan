package me.Bambusstock.TimeBan;

import java.io.Serializable;
import java.util.Calendar;

import org.bukkit.OfflinePlayer;

/**
 * Class to handle a ban.
 * @author Bambusstock
 *
 */
public class Ban
implements Serializable, Comparable<Ban>
{
	private static final long	serialVersionUID	= -4327491657720734089L;
	private transient TimeBan plugin;
	
	public static String stdReason = "Standard reason";
	public static int stdBanDuration = 3600;
	
	protected String player;
	protected Calendar until;
	protected String reason;
	
	/**
	 * Instantiate a empty ban.
	 */
	public Ban() {
		
	}
	
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
		this.reason = Ban.stdReason;
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
		this.reason = Ban.stdReason;
	}
	
	/**
	 * Compare two bans by time.
	 */
	public int compareTo(Ban ban) {
		if(this.until.after(ban)) {
			return 1;
		} else if(this.until.before(ban)) {
			return -1;
		} else {
			return 0;
		}
	}
	
	/**
	 * Set the standard ban duration in seconds.
	 * @param duration Amount of seconds a user is banned, if no duration was given.
	 */
	public static void setStandardBanDuration(int duration) {
		Ban.stdBanDuration= duration;
	}
	
	/**
	 * Set the standard reasons.
	 * @param Standard reason used if no given
	 */
	public static void setStandardReasons(String reason) {
		Ban.stdReason = reason;
	}
	
	/**
	 * Use the standard ban duration to create a standard Calendar-Object, 
	 * that is used to define date until user is banned.
	 * @return 
	 */
	private Calendar stdDurationToCalendar() {
		Calendar result = Calendar.getInstance();
		result.set(Calendar.SECOND, result.get(Calendar.SECOND) + Ban.stdBanDuration);
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
    	if(!this.reason.isEmpty()) {
    		return this.reason;
    	}
    	else {
    		return Ban.stdReason;
    	}
    }
	
	/**
	 * Convert this object into a string.
	 */
	public String toString() {
		return "`" +  this.player + "` until " + this.until.getTime().toString() + " --> "  + this.reason;
	}
	
	/**
	 * Check if this ban is empty.
	 * @return
	 */
	public boolean isEmpty() {
		if(!this.player.isEmpty() && !this.reason.isEmpty()) {
		 return true;
		}
		return false;
	}
}
