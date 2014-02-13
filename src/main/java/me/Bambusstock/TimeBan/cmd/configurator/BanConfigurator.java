package me.Bambusstock.TimeBan.cmd.configurator;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Bambusstock.TimeBan.cmd.AbstractCommand;
import me.Bambusstock.TimeBan.cmd.TimeBanBanCommand;
import me.Bambusstock.TimeBan.util.CommandLineParser;
import me.Bambusstock.TimeBan.util.UntilStringParser;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 */
public class BanConfigurator extends AbstractConfigurator {
    
    private static final Logger log = Logger.getLogger("Minecraft");
    
    // default ban reason
    private final String stdBanReason;
    
    // default ban duration
    private final String stdBanDuration;
    
    public BanConfigurator(ConfigurationSection globalConfig) {
        stdBanDuration = globalConfig.getString("defaultBanDuration", "1h");
        stdBanReason = globalConfig.getString("defaultBanReason", "No reason given.");
    }

    @Override
    public void configure(AbstractCommand command, String[] args) {
        TimeBanBanCommand banCommand = (TimeBanBanCommand) command;
        
        List<String> players = CommandLineParser.getListOfString(args[0]);
        String reason = stdBanReason;
        Calendar until = UntilStringParser.parse(stdBanDuration);

        // only username was given, all to standard
        if (args.length == 2) {
            // What did we get? reason or duration...

            // got a reason
            if (args[1].contains("\"")) {
                reason = CommandLineParser.getPrettyString(args[1]);
                until = UntilStringParser.parse(stdBanDuration);
            } else {
                reason = stdBanReason;
                until = UntilStringParser.parse(args[1]);
            }
        } else if (args.length == 3) {
            // every parameter given

            reason = CommandLineParser.getPrettyString(args[2]);
            until = UntilStringParser.parse(args[1]);
        } else {
            log.log(Level.SEVERE, "Error. Wrong syntax!");
        }

        banCommand.setPlayers(players);
        banCommand.setReason(reason);
        banCommand.setUntil(until);
    }
    
}
