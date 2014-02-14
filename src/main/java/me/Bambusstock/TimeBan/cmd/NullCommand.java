package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;

/**
 *
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
