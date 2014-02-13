package me.Bambusstock.TimeBan.cmd;

import java.util.logging.Logger;
import javax.sound.midi.Receiver;

import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.entity.Player;

/**
 * Basic class to extend if you wanna create a new TimeBan command. This class
 * provides you with a logger an enum and some methods to write messages.
 */
public abstract class AbstractCommand {
    
    private Player receiver;

    /**
     * Enum containing available commands and their "names".
     */
    public enum Commands {

        BAN("ban"),
        HELP("help"),
        INFO("info"),
        LIST("list"),
        RM("rm"),
        RUN("run"),
        UNBAN("unban");

        private String cmdName;

        private Commands(String cmdName) {
            this.cmdName = cmdName;
        }

        /**
         * Get the "name" of the command.
         */
        public String getName() {
            return cmdName;
        }
    };

    /**
     * "Global" logger used by all commands.
     */
    protected static final Logger log = Logger.getLogger("Minecraft");
    
    /**
     * Plugin reference used by all commands.
     */
    protected TimeBan plugin;

    public AbstractCommand(TimeBan plugin) {
        this.plugin = plugin;
    }
    
    public abstract void execute();
    
    public void setReceiver(Player p) {
        receiver = p;
    }
    
    public Player getReceiver() {
        return receiver;
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
