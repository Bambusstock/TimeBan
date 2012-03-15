package de.endlesscraft.TimeBan;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public class TimeBanEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Ban ban;
 
    public TimeBanEvent(Ban ban) {
     	this.ban = ban;
    }
 
    /**
     * Return a ban object.
     * @return Ban object
     */
    public Ban getBan() {
        return this.ban;
    }
     
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}