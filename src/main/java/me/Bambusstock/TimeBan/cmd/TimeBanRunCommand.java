package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.TimeBanRunnable;

public class TimeBanRunCommand extends TimeBanCommand {

    public TimeBanRunCommand(TimeBan plugin) {
        super(plugin);
    }

    public void run() {
        new TimeBanRunnable(this.plugin).run();
    }
}
