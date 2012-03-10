package de.endlesscraft.TimeBan;

import java.util.Calendar;

import org.bukkit.entity.Player;

/**
 * Class to handle a ban.
 * @author Bambusstock
 *
 */
public class Ban
{
	public static String stdReason = "Standard reasons";
	public static int stdBanDuration = 3600;
	
	protected Player player;
	protected Calendar until;
	protected String reason;
	
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
	 * Instantiate a ban.
	 * @param Player Player object
	 * @param until Date until user is banned
	 * @param reason Reasons why the user had been banned
	 */
	public Ban(Player player, Calendar until, String reason) {
		this.player= player;
		this.until = until;
		this.reason = reason;
	}
	
	/**
	 * Instantiate a ban using the default reason.
	 * @param Player Player object
	 * @param until Date until the user is banned
	 */
	public Ban(Player player, Calendar until) {
		this.player = player;
		this.until = until;
		this.reason = Ban.stdReason;
	}
	
	/**
	 * Instantiate a ban using the default duration.
	 * @param Player object
	 * @param Reason why the player was banned
	 */
	public Ban(Player player, String reason) {
		this.player= player;
		this.until = this.stdDurationToCalendar();
		this.reason = reason;
	}
	
	/**
	 * Instantiate a ban using the default duration and default reasons.
	 * @param Player object
	 */
	public Ban(Player player) {
		this.player = player;
		this.until = this.stdDurationToCalendar();
		this.reason = Ban.stdReason;
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
	 * Convert this object into a string.
	 */
	public String toString() {
		return "User `" +  this.player + "` banned until " + this.until.getTime().toString() + " because of "  + this.reason;
	}
}
