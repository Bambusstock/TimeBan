package de.endlesscraft.TimeBan;

import java.util.Calendar;

/**
 * Class to handle a ban.
 * @author Bambusstock
 *
 */
public class Ban
{
	public static String[] stdReasons = {"Standard reasons"};
	public static int stdBanDuration = 3600;
	
	protected String user;
	protected Calendar until;
	protected String[] reasons;
	
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
	 * @param user The standard username
	 * @param until Date until user is banned
	 * @param reasons Array of reasons why the user had been banned
	 */
	public Ban(String user, Calendar until, String[] reasons) {
		this.user = user;
		this.until = until;
		this.reasons = reasons;
	}
	
	/**
	 * Instantiate a ban using the default reason.
	 * @param user The standard username
	 * @param until Date until the user is banned
	 */
	public Ban(String user, Calendar until) {
		this.user = user;
		this.until = until;
		this.reasons = Ban.stdReasons;
	}
	
	/**
	 * Instantiate a ban using the default duration.
	 * @param user The standard username
	 * @param reasons Array of reasons why the user had been banned
	 */
	public Ban(String user, String[] reasons) {
		this.user = user;
		this.until = this.stdDurationToCalendar();
		this.reasons = reasons;
	}
	
	/**
	 * Instantiate a ban using the default duration and default reasons.
	 * @param user The standard username
	 */
	public Ban(String user) {
		this.user = user;
		this.until = this.stdDurationToCalendar();
		this.reasons = Ban.stdReasons;
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
	 * @param reasons Array of reasons use if no reasons were given.
	 */
	public static void setStandardReasons(String[] reasons) {
		Ban.stdReasons = reasons;
	}
	
	
	/**
	 * Convert this object into a string.
	 */
	public String toString() {
		return "User `" +  this.user + "` banned until the " + this.until.toString() + "because of "  + this.reasons.toString();
	}
}
