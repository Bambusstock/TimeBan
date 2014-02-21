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

    // global configuration
    private ConfigurationSection globalConfig;

    public BanConfigurator(ConfigurationSection globalConfig) {
        stdBanDuration = globalConfig.getString("defaultBanDuration", "1h");
        stdBanReason = globalConfig.getString("defaultBanReason", "No reason given.");

        this.globalConfig = globalConfig;
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
                until = UntilStringParser.parse(args[1]);
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
        ConfigurationSection shortcuts = globalConfig.getConfigurationSection("shortcuts");

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

}
