package me.Bambusstock.TimeBan.cmd.configurator;

import me.Bambusstock.TimeBan.cmd.AbstractCommand;

/**
 *
 */
public abstract class AbstractConfigurator {
    public abstract void configure(AbstractCommand command, String[] args);
}
