package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;

/**
 * Command used if there is no purpose for a real command but to ensure
 * architecture is working.
 * @author bambusstock
 */
public class NullCommand extends AbstractCommand {

    public NullCommand(TimeBan plugin) {
        super(plugin);
        setCommandType(TimeBanCommands.NULL);
    }

    @Override
    public void execute() {
    }
    
}
