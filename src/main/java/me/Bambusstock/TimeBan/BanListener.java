package me.Bambusstock.TimeBan;

import java.util.Calendar;
import java.util.logging.Logger;

import me.Bambusstock.TimeBan.event.TimeBanBanEvent;
import me.Bambusstock.TimeBan.event.TimeBanUnbanEvent;
import me.Bambusstock.TimeBan.util.Ban;
//import me.Bambusstock.TimeBan.cmd.*;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;

public class BanListener implements Listener
{
	Logger log = Logger.getLogger("Minecraft");
	private TimeBan 		plugin;

	public BanListener(TimeBan instance) {
		this.plugin = instance;
	}
	
	@EventHandler
	public void onPlayerPreLoginEvent(PlayerPreLoginEvent event) {
		Ban ban = this.plugin.banSet.getBanByPlayerName(event.getName());
		// player is still banned, but he should already be unbanned
		if(ban != null && Calendar.getInstance().after(ban.getUntil())) {
			log.info("bu");
			TimeBanUnbanEvent unbanEvent = new TimeBanUnbanEvent(ban);
			this.plugin.getServer().getPluginManager().callEvent(unbanEvent);
		}
		else if(ban != null && Calendar.getInstance().before(ban.getUntil())) {
			event.disallow(PlayerPreLoginEvent.Result.KICK_BANNED, "Youre banned from this server until " + ban.getUntil().getTime() + " because '" + ban.getReason() + "'!");
			log.info("Muhm");
		}
	}
	
	@EventHandler
	public void onTimeBanEvent(TimeBanBanEvent event) {
		Ban ban = event.getBan();
		OfflinePlayer player = ban.getPlayer();
		
		player.setBanned(true);
		if(player.isOnline()) ((Player) player).kickPlayer(ban.getReason());
		
		boolean banResult = this.plugin.banSet.add(ban);

		if(event.isSenderPlayer()) {
			if(banResult) {
				event.getSender().sendMessage(ChatColor.DARK_GREEN + "Banned '" + player.getName() + "' until " + ban.until.getTime());
				log.info("[TimeBan] Banned `"+ player.getName() + "` until " + ban.until.getTime() + " by " + event.getSender().getDisplayName());
			}
			else {
				event.getSender().sendMessage(ChatColor.RED + "Seems that '" + player.getName() + "' is already banned...");
			}
		}
		else {
			if(banResult) {
				log.info("[TimeBan] Banned '"+ player.getName() + "' until " + ban.until.getTime());
			}
			else {
				log.info("[TimeBan] Seems that `" + player.getName() + "` is already banned...");
			}
		}
	}
	
	@EventHandler
	public void onTimeUnbanEvent(TimeBanUnbanEvent event) {
		OfflinePlayer player = event.getBan().getPlayer();
		player.setBanned(false);
		
		synchronized (this.plugin.banSet) {
			this.plugin.banSet.remove(event.getBan());	
		}
		
		if(event.isSenderPlayer()) {
			event.getSender().sendMessage(ChatColor.DARK_GREEN + "Unbaned and removed `" + player.getName() + "` from banlist.");
			log.info("[TimeBan] Unbaned and removed `" + player.getName() + "` from ban list by " + event.getSender().getDisplayName());
		} 
		else {
			log.info("[TimeBan] Unbaned and removed `" + player.getName() + "` from banlist.");
		}
	}
}
