package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.TimeBanRunnable;

public class RunCmd extends Cmd
{
	public RunCmd(TimeBan plugin) {
		super(plugin);
	}
	
	public void run() {
		new TimeBanRunnable(this.plugin).run();
	}
}
