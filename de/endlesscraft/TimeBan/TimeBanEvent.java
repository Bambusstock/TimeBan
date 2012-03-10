package de.endlesscraft.TimeBan;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public class TimeBanEvent extends Event {
	Logger log = Logger.getLogger("Minecraft");
    private static final HandlerList handlers = new HandlerList();
    private Ban ban;
 
    public TimeBanEvent(Ban ban) {
     	this.ban = ban;
    	log.info("TimeBanEvent fired!");
    }
 
    /**
     * This method return a ban.
     * @return Ban object
     */
    public Ban getBan() {
        return this.ban;
    }
    
    /**
     * This method return a player.
     * @return Player object
     */
    public Player getPlayer() {
    	return this.ban.player;
    }
    
    /**
     * Return the banning reason
     * @return Reason
     */
    public String getReason() {
    	return this.ban.reason;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}