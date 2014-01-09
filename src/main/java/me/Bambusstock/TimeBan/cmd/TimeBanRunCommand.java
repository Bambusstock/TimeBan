package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.TimeBanRunnable;

/**
 * Command used to manually check for unbans.
 */
public class TimeBanRunCommand extends TimeBanCommand {

    public TimeBanRunCommand(TimeBan plugin) {
        super(plugin);
    }

    public void run() {
        new TimeBanRunnable(this.plugin).run();
    }
}
