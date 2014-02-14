package me.Bambusstock.TimeBan.cmd.configurator;

import java.util.List;
import me.Bambusstock.TimeBan.cmd.AbstractCommand;
import me.Bambusstock.TimeBan.cmd.TimeBanRmCommand;
import me.Bambusstock.TimeBan.util.CommandLineParser;

/**
 *
 * @author bambusstock
 */
public class RmConfigurator extends AbstractConfigurator {

    @Override
    public void configure(AbstractCommand command, String[] args) {
        TimeBanRmCommand rmCommand = (TimeBanRmCommand) command;
        if (CommandLineParser.isOptionPresent(args[0], 'a')) {
            rmCommand.setRmAll(true);
        } else {
            List<String> players = CommandLineParser.getListOfString(args[0]);
            rmCommand.setPlayers(players);
        }
    }

}
