package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;

/**
 *
 * @author bambusstock
 */
public class CommandBuilder {

    public static AbstractCommand createCommand(TimeBan plugin, String cmd, String[] args) {
        AbstractCommand result = new NullCommand(plugin);

        TimeBanCommands command;
        try {
            command = TimeBanCommands.valueOf(cmd);
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
