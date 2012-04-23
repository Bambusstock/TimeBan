package me.Bambusstock.TimeBan.cmd;

import org.bukkit.entity.Player;

import me.Bambusstock.TimeBan.TimeBan;

public class InfoCmd extends Cmd
{
	public InfoCmd(TimeBan plugin) {
		super(plugin);
	}
	
	public void info(Player sender) {
		sender.sendMessage("TimeBan - Info:");
		sender.sendMessage("===============");
		sender.sendMessage("Amount of bans: " + this.plugin.banSet.size());
		if(this.plugin.banSet.size() > 0) {
			sender.sendMessage("Next unban at: " + this.plugin.banSet.last().getUntil().getTime());
		}
	}
	
	public void info() {
		log.info("TimeBan - Info:");
		log.info("===============");
		log.info("Amount of bans: " + this.plugin.banSet.size());
		if(this.plugin.banSet.size() > 0) {
			log.info("Next unban at: " + this.plugin.banSet.last().getUntil().getTime());
		}
	}
}
