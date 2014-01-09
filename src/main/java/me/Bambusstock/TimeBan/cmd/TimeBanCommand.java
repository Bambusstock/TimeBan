package me.Bambusstock.TimeBan.cmd;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.TimeBan;

public class TimeBanCommand {

    public enum Commands {
        
        BAN("ban"),
        UNBAN("unban"),
        LIST("list");
        
        private String cmdName;
        private Commands(String cmdName) {
            this.cmdName = cmdName;
        }
        
        public String getName() {
            return cmdName;
        }
    };
    
    Logger log = Logger.getLogger("Minecraft");
    protected TimeBan plugin;

    public TimeBanCommand(TimeBan plugin) {
        this.plugin = plugin;
    }
}
