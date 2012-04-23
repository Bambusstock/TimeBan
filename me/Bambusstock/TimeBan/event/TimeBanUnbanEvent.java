package me.Bambusstock.TimeBan.event;

import me.Bambusstock.TimeBan.util.Ban;
import org.bukkit.entity.Player;
 
public class TimeBanUnbanEvent extends TimeBanEvent {
    public TimeBanUnbanEvent(Player sender, Ban ban) {
    	super(sender, ban);
    }
    
    public TimeBanUnbanEvent(Ban ban) {
    	super(ban);
    }
}