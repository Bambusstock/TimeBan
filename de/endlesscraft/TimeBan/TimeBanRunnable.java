package de.endlesscraft.TimeBan;

import java.util.Set;

/**
 * Check x seconds if there is a upcoming unban.
 * @author Bambusstock
 *
 */
public class TimeBanRunnable implements Runnable
{
	private Set<Ban> set;
	
	/**
	 * Set a set of bans to watch for unban.
	 * @param set
	 */
	public void setSet(Set<Ban> set) {
		this.set = set;
	}
	
	/**
	 * Return the set of bans to watch for unban.
	 * @return Set
	 */
	public Set<Ban> getSet() {
		return this.set;
	}
	
	public void run() {
		// check for upcoming unbans...
	}
}
