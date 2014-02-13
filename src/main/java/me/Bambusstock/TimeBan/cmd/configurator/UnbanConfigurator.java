package me.Bambusstock.TimeBan.cmd.configurator;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Bambusstock.TimeBan.cmd.AbstractCommand;
import me.Bambusstock.TimeBan.cmd.TimeBanUnbanCommand;
import me.Bambusstock.TimeBan.util.CommandLineParser;

/**
 *
 * @author bambusstock
 */
public class UnbanConfigurator extends AbstractConfigurator {
    
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void configure(AbstractCommand command, String[] args) {
        TimeBanUnbanCommand unbanCommand = (TimeBanUnbanCommand) command;
        
        if (CommandLineParser.isOptionPresent(args[0], 'a')) {
            unbanCommand.setUnbanAll(true);
        } else if (args[0] != null && !args[0].isEmpty()) {
            List<String> players = CommandLineParser.getListOfString(args[0]);
            unbanCommand.setPlayers(players);
        } else {
            log.log(Level.SEVERE, "Error. Wrong syntax!");
        }
    }
    
}
