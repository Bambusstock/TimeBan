package me.Bambusstock.TimeBan.cmd;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.entity.Player;

/**
 * Basic class to extend if you wanna create a new TimeBan command.
 */
public abstract class AbstractCommand {
    
    private Player receiver;

    // "Global" logger used by all commands.
    protected static final Logger log = Logger.getLogger("Minecraft");
    
    // Plugin reference used by all commands.
    protected TimeBan plugin;
    
    private TimeBanCommands commandType;

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
    
    public boolean executionAllowed() {
        return (receiver == null || receiver.hasPermission(getCommandType().getPermission()));
    }

    public TimeBanCommands getCommandType() {
        return commandType;
    }

    public void setCommandType(TimeBanCommands commandType) {
        this.commandType = commandType;
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
