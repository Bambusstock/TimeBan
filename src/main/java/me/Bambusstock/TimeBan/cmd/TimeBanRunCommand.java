package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.TimeBanRunnable;

/**
 * Command used to manually check for unbans.
 */
public class TimeBanRunCommand extends AbstractCommand {

    public TimeBanRunCommand(TimeBan plugin) {
        super(plugin);
        setCommandType(TimeBanCommands.RUN);
    }

    @Override
    public void execute() {
        new TimeBanRunnable(this.plugin).run();
    }
}
