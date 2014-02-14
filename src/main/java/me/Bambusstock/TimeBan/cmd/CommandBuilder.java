package me.Bambusstock.TimeBan.cmd;

import java.util.logging.Logger;
import me.Bambusstock.TimeBan.TimeBan;

/**
 *
 * @author bambusstock
 */
public class CommandBuilder {

    private static final Logger log = Logger.getLogger("Minecraft");
    
    public static AbstractCommand createCommand(TimeBan plugin, String cmd) {
        AbstractCommand result = new NullCommand(plugin);

        TimeBanCommands command;
        try {
            command = TimeBanCommands.valueOf(cmd.toUpperCase());
        } catch (IllegalArgumentException e) {
            return result;
        } catch (NullPointerException e) {
            return result;
        }
        
        switch (command) {
            case BAN:
                result = new TimeBanBanCommand(plugin);
                break;

            case UNBAN:
                result = new TimeBanUnbanCommand(plugin);
                break;

            case LIST:
                result = new TimeBanListCommand(plugin);
                break;

            case INFO:
                result = new TimeBanInfoCommand(plugin);
                break;

            case RM:
                result = new TimeBanRmCommand(plugin);
                break;

            case HELP:
                result = new TimeBanHelpCommand(plugin);
                break;

            case RUN:
                result = new TimeBanRunCommand(plugin);
                break;
        }
        
        return result;
    }
}
