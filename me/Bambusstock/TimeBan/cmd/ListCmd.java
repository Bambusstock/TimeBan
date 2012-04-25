package me.Bambusstock.TimeBan.cmd;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.Ban;
import me.Bambusstock.TimeBan.util.BanSet;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ListCmd extends Cmd
{
	public ListCmd(TimeBan plugin) {
		super(plugin);
	}
	
	public void list(Player sender, String search, boolean reverse, boolean simple) {
		BanSet resultSet = this.getResultSet(search);
		if(!resultSet.isEmpty()) {
			Iterator<Ban> iterator = resultSet.descendingIterator();
			if(reverse == true) iterator = resultSet.iterator(); 
			if(simple == true) {
				while(iterator.hasNext()) sender.sendMessage(iterator.next().toString());
			}
			else {
				while(iterator.hasNext()) {
					Ban ban = iterator.next();
					sender.sendMessage("`" + ChatColor.RED + ban.getPlayer().getName() + ChatColor.WHITE 
							+ "` until " + ChatColor.AQUA + ban.getUntil().getTime() + ChatColor.WHITE
							+ " because `" + ChatColor.GOLD + ban.getReason() + ChatColor.WHITE + "`");
				}
			}
		}
	}
	
	/**
	 * List all bans. Console version
	 * @param search Regex used for a search
	 * @param reverse True, then all bans are sorted reverse (descending time)
	 * @param simple True, then the ban information are compressed
	 */
	public void list(String search, boolean reverse, boolean simple) {
		BanSet resultSet = this.getResultSet(search);
		if(!resultSet.isEmpty()) {
			Iterator<Ban> iterator = resultSet.iterator();
			if(reverse == true) iterator = resultSet.descendingIterator(); 
			if(simple == true) {
				while(iterator.hasNext()) log.info(iterator.next().toString());
			}
			else {
				while(iterator.hasNext()) {
					Ban ban = iterator.next();
					log.info("`" + ban.getPlayer().getName() + "` until " + ban.getUntil().getTime() + " because `" + ban.getReason() + "`");
				}
			}
		}
	}
	
	/**
	 * Return a result set.
	 * @param search
	 * @return
	 */
	protected BanSet getResultSet(String search) {
		BanSet resultSet = new BanSet();
		if(search.isEmpty()) {
			resultSet = (BanSet)this.plugin.banSet.clone();
		}
		else {
			Pattern searchPattern = Pattern.compile(search);
			for(Ban ban : this.plugin.banSet) {
				Matcher m = searchPattern.matcher(ban.getPlayer().getName());
				if(m.matches()) resultSet.add(ban);
			}
		}
		return resultSet;
	}
}
