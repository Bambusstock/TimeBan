package me.Bambusstock.TimeBan.cmd;

import me.Bambusstock.TimeBan.TimeBan;
import me.Bambusstock.TimeBan.util.ManPageUtil;
import me.Bambusstock.TimeBan.util.ManPageUtil.ManPage;
import me.Bambusstock.TimeBan.util.TerminalUtil;
import org.bukkit.entity.Player;

/**
 * Command to display help for all TimeBan commands.
 */
public class TimeBanHelpCommand extends AbstractCommand {

    // serves as a parameter to indicate which man page to show.
    private ManPage manPage;

    public TimeBanHelpCommand(TimeBan plugin) {
        super(plugin);
        setCommandType(TimeBanCommands.HELP);
    }

    @Override
    public void execute() {
        ManPageUtil util = new ManPageUtil(plugin.getConfig().getString("locale", "en"));
        Player receiver = getReceiver();

        // display the help to the console
        if (receiver == null) {
            String manPageText = util.getMan(getManPage());
            TerminalUtil.printToConsole(manPageText);

            // display the help to the player
        } else {
            String manPageText = util.getMan(getManPage(), true);
            TerminalUtil.printToPlayer(receiver, manPageText);
        }
    }

    /**
     * Set the name of the manual page to display.
     *
     * @param manPage name of the man page. If man page not found standard man
     * page for help is displayed.
     */
    public void setManPage(String manPage) {
        ManPage page = ManPage.HELP;
        if (manPage != null && !manPage.isEmpty()) {
            try {
                page = ManPage.valueOf(manPage.toUpperCase());
            } catch (IllegalArgumentException e) {
                page = ManPage.HELP;
            }
        }

        this.manPage = page;
    }

    public ManPage getManPage() {
        return manPage;
    }
}
