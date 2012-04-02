package me.Bambusstock.TimeBan.event;


import me.Bambusstock.TimeBan.util.Ban;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public class TimeUnbanEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    protected Ban ban;
    protected Player sender;
 
    public TimeUnbanEvent(Player sender, Ban ban) {
     	this.ban = ban;
     	this.sender = sender;
    }
    
    public TimeUnbanEvent(Ban ban) {
     	this.ban = ban;
    }
 
    /**
     * Return a ban object.
     * @return Ban object
     */
    public Ban getBan() {
        return this.ban;
    }
    
    /**
     *
     * @return Return sender
     */
    public Player getSender() {
    	return this.sender;
    }
    
    /**
     * @return Return if the sender is a player.
     */
    public boolean isSenderPlayer() {
    	return (this.sender.isEmpty()) ? false : true;
    }
     
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}