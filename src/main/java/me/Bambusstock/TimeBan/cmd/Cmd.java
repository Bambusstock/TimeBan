package me.Bambusstock.TimeBan.cmd;

import java.util.logging.Logger;

import me.Bambusstock.TimeBan.TimeBan;
public class Cmd
{
	Logger log = Logger.getLogger("Minecraft");
	protected TimeBan plugin;
	public Cmd(TimeBan plugin) {
		this.plugin = plugin;
	}
}
