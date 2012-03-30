package me.Bambusstock.TimeBan;

/**
 * Check if there is a player to unban.
 * @author Bambusstock
 *
 */
public class TimeBanRunnable implements Runnable
{
	private TimeBan plugin;
	public TimeBanRunnable(TimeBan instance) {
		this.plugin = instance;
	}
	
	public void run() {
		this.plugin.getServer();
	}
}
