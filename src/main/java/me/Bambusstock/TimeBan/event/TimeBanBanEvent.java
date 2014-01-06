package me.Bambusstock.TimeBan.event;

import me.Bambusstock.TimeBan.util.Ban;
import org.bukkit.entity.Player;
 
public class TimeBanBanEvent extends TimeBanEvent {
    public TimeBanBanEvent(Player sender, Ban ban) {
    	super(sender, ban);
    }
    
    public TimeBanBanEvent(Ban ban) {
    	super(ban);
    }
}