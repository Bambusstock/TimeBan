package me.Bambusstock.TimeBan.cmd;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.TimeBan;
import org.bukkit.entity.Player;

/**
 * Basic class to extend if you wanna create a new TimeBan command.
 */
public abstract class AbstractCommand {
    
    // "Global" logger used by all commands.
    protected static final Logger log = Logger.getLogger("Minecraft");
    
    // receiver of repsonses
    private Player receiver;
    
    // Plugin reference used by all commands.
    protected TimeBan plugin;
    
    private TimeBanCommands commandType;

    public AbstractCommand(TimeBan plugin) {
        this.plugin = plugin;
    }
    
    /**
     * This method is called to execute the specific command.
     */
    public abstract void execute();
    
    public void setReceiver(Player p) {
        receiver = p;
    }
    
    public Player getReceiver() {
        return receiver;
    }
    
    /**
     * Check if this command is allowed to be executed.
     * 
     * @return true If receiver is a user and has the permission to execute the command.
     * If the receiver is not a user the command is automatically allowed to be executed.
     */
    public boolean executionAllowed() {
        return (receiver == null || receiver.hasPermission(getCommandType().getPermission()));
    }

    public TimeBanCommands getCommandType() {
        return commandType;
    }

    public void setCommandType(TimeBanCommands commandType) {
        this.commandType = commandType;
    }
}
