package me.Bambusstock.TimeBan.cmd.configurator;

import me.Bambusstock.TimeBan.cmd.AbstractCommand;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author bambusstock
 */
public class ConfiguratorFactory {
    
    private ConfiguratorFactory() {
    }
    
    public static AbstractConfigurator getConfigurator(ConfigurationSection globalConfig, AbstractCommand command) {
        AbstractConfigurator result = new NullConfigurator();
        
        switch (command.getCommandType()) {
            case BAN:
                result = new BanConfigurator(globalConfig);
                break;

            case UNBAN:
                result = new UnbanConfigurator();
                break;

            case LIST:
                result = new ListConfigurator();
                break;

            case RM:
                result = new RmConfigurator();
                break;

            case HELP:
                result = new HelpConfigurator();
                break;
        }
        
        return result;
    }
}
