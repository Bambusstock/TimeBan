package me.Bambusstock.TimeBan.cmd.configurator;

import me.Bambusstock.TimeBan.cmd.AbstractCommand;
import me.Bambusstock.TimeBan.cmd.TimeBanHelpCommand;

/**
 *
 * @author bambusstock
 */
public class HelpConfigurator extends AbstractConfigurator {

    @Override
    public void configure(AbstractCommand command, String[] args) {
        TimeBanHelpCommand helpCommand = (TimeBanHelpCommand) command;

        if (args.length >= 1) {
            helpCommand.setManPage(args[0]);
        } else {
            helpCommand.setManPage("help");
        }
    }

}
