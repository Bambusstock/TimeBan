package me.Bambusstock.TimeBan.event;


import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public class TimeBanEvent extends Event {
    
    // list of handlers
    private static final HandlerList handlers = new HandlerList();
    
    // ban transported
    protected Ban ban;
    
    // sender of command
    protected Player sender;
    
    // whether this command should be silent or not
    private boolean silent;
 
    public TimeBanEvent(Player sender, Ban ban) {
     	this.ban = ban;
     	this.sender = sender;
    }
    
    public TimeBanEvent(Ban ban) {
     	this.ban = ban;
     	this.sender = null;
    }
 
    /**
     * Return a ban object.
     * @return Ban object
     */
    public Ban getBan() {
        return this.ban;
    }
    
    /**
     * @return Return sender
     */
    public Player getSender() {
    	return this.sender;
    }
    
    /**
     * @return Return true if the sender is a player.
     */
    public boolean isSenderPlayer() {
   		if(sender != null && sender instanceof Player) return true;
    	return false;
    }
     
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }
    
}