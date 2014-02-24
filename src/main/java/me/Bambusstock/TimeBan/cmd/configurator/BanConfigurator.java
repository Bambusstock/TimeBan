package me.Bambusstock.TimeBan.cmd.configurator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    // parse a date or use simple format
    private final boolean banUntilDate;

    // date format provided by config
    private final SimpleDateFormat banDateParser;

    // global configuration
    private final ConfigurationSection config;

    public BanConfigurator(ConfigurationSection config) {
        stdBanDuration = config.getString("defaultBanDuration", "1h");
        stdBanReason = config.getString("defaultBanReason", "No reason given.");
        banUntilDate = config.getBoolean("banUntilDate", false);
        banDateParser = new SimpleDateFormat(config.getString("dateFormat", "dd.MM.yyyy-HH:mm"));

        this.config = config;
    }

    @Override
    public void configure(AbstractCommand command, String[] args) {
        TimeBanBanCommand banCommand = (TimeBanBanCommand) command;

        List<String> players = CommandLineParser.getListOfString(args[0]);
        String reason = stdBanReason;
        Calendar until = UntilStringParser.parse(stdBanDuration);

        if (args.length == 2) {
            // What did we get? reason or duration...
            // got a reason
            if (args[1].contains("\"")) {
                reason = CommandLineParser.getPrettyString(args[1]);
                until = UntilStringParser.parse(stdBanDuration);
            } else if (args[1].startsWith("&")) {
                String shortcutName = args[1].substring(1); // remove &
                setAttributesByShortcut(shortcutName, banCommand);
                banCommand.setPlayers(players);
                return;
            } else {
                reason = stdBanReason;
                until = getCalendarFromArg(args[1]);
            }
        } else if (args.length == 3) {
            // every parameter given
            reason = CommandLineParser.getPrettyString(args[2]);
            until = UntilStringParser.parse(args[1]);
        } else if (args.length > 3) {
            log.log(Level.SEVERE, "Error. Wrong syntax!");
        }

        banCommand.setPlayers(players);
        banCommand.setReason(reason);
        banCommand.setUntil(until);
    }

    /**
     * Set the attributes reason and until using a shortcut from the
     * configuration.
     *
     * @param shortcut name of the shortcut in section shortcuts
     * @param command command object to configure
     *
     */
    protected void setAttributesByShortcut(String shortcut, TimeBanBanCommand command) {
        ConfigurationSection shortcuts = config.getConfigurationSection("shortcuts");

        // configure reason
        String reason = shortcuts.getConfigurationSection(shortcut).getString("reason");
        if (reason == null || reason.isEmpty()) {
            command.setReason(stdBanReason);
        } else {
            command.setReason(reason);
        }

        // configure duration
        String durationRaw = shortcuts.getConfigurationSection(shortcut).getString("duration");
        if (durationRaw == null || durationRaw.isEmpty()) {
            command.setUntil(UntilStringParser.parse(stdBanDuration));
        } else {
            command.setUntil(UntilStringParser.parse(durationRaw));
        }
    }

    /**
     * Get a calendar object from the given string.
     * 
     * @param dateString date as string according to the format given in the config.
     */
    protected Calendar getCalendarFromArg(String dateString) {
        Calendar result = null;
        if (banUntilDate) {
            try {
                result = Calendar.getInstance();
                result.setTime(banDateParser.parse(dateString));
            } catch (ParseException e) {
                log.log(Level.SEVERE, "Error parsing given date({0})!", dateString);
            }
        } else {
            result = UntilStringParser.parse(dateString);
        }
        
        return result;
    }

}
