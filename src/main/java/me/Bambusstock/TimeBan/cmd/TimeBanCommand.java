package me.Bambusstock.TimeBan.cmd;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.entity.Player;

public class TimeBanCommand {

    public enum Commands {

        BAN("ban"),
        UNBAN("unban"),
        INFO("info"),
        LIST("list"),
        RM("rm"),
        RUN("run"),
        HELP("help");

        private String cmdName;

        private Commands(String cmdName) {
            this.cmdName = cmdName;
        }

        public String getName() {
            return cmdName;
        }
    };

    protected static final Logger log = Logger.getLogger("Minecraft");
    protected TimeBan plugin;

    public TimeBanCommand(TimeBan plugin) {
        this.plugin = plugin;
    }

    /**
     * Write a message to the receiver. If reveicer == null message is writtent
     * to the console log.
     *
     * @param receiver Either a Player instance or null if message should be
     * written to console
     * @param message Message to write
     *
     */
    protected void writeMessage(Player receiver, String message) {
        if (receiver == null) {
            log.info(message);
        } else {
            receiver.sendMessage(message);
        }
    }
}
