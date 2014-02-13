package me.Bambusstock.TimeBan.cmd.configurator;

import me.Bambusstock.TimeBan.cmd.AbstractCommand;
import me.Bambusstock.TimeBan.cmd.TimeBanListCommand;
import me.Bambusstock.TimeBan.util.CommandLineParser;

/**
 *
 * @author bambusstock
 */
public class ListConfigurator extends AbstractConfigurator {

    @Override
    public void configure(AbstractCommand command, String[] args) {
        int page = 0;
        String search = null;
        boolean listReverse = false;
        boolean listSimple = false;

        if (args.length == 3) {
            page = Integer.parseInt(args[0]);
            search = args[1];
            listReverse = CommandLineParser.isOptionPresent(args[2], 'r');
            listSimple = CommandLineParser.isOptionPresent(args[2], 's');
        } else if (args.length == 2) {
            if (CommandLineParser.isInteger(args[0])) {
                page = Integer.parseInt(args[0]);

                listReverse = CommandLineParser.isOptionPresent(args[1], 'r');
                listSimple = CommandLineParser.isOptionPresent(args[1], 's');

                if (!listReverse && !listSimple) {
                    search = args[1];
                }
            } else {
                search = args[0];
                listReverse = CommandLineParser.isOptionPresent(args[1], 'r');
                listSimple = CommandLineParser.isOptionPresent(args[1], 's');
            }
        } else if (args.length == 1) {
            if (CommandLineParser.isInteger(args[0])) {
                page = Integer.parseInt(args[0]);
            } else {
                listReverse = CommandLineParser.isOptionPresent(args[0], 'r');
                listSimple = CommandLineParser.isOptionPresent(args[0], 's');

                if (listReverse || listSimple) {
                    search = null;
                } else {
                    search = args[0];
                }
            }
        }

        
        TimeBanListCommand listCommand = (TimeBanListCommand) command;
        listCommand.setPage(page);
        listCommand.setReverse(listReverse);
        listCommand.setSearch(search);
        listCommand.setSimple(listSimple);
    }

}
