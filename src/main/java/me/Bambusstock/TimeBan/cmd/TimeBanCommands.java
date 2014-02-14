package me.Bambusstock.TimeBan.cmd;

/**
 *
 * @author bambusstock
 */
public enum TimeBanCommands {
    NULL("", ""),
    BAN("ban", "timeban.ban"),
    HELP("help", "timeban.help"),
    INFO("info", "timeban.info"),
    LIST("list", "timeban.list"),
    RM("rm", "timeban.rm"),
    RUN("run", "timeban.run"),
    UNBAN("unban", "timeban.unban");

    private final String cmdName;
    
    private final String permission;

    private TimeBanCommands(String cmdName, String permission) {
        this.cmdName = cmdName;
        this.permission = permission;
    }

    /**
     * Get the "name" of the command.
     */
    public String getName() {
        return cmdName;
    }
    
    /**
     * Get the permission string.
     */
    public String getPermission() {
        return permission;
    }
}
