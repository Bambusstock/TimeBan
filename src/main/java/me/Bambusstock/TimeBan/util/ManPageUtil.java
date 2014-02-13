package me.Bambusstock.TimeBan.util;

import java.io.File;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * <p>Class helping you displaying man pages. A man page is a file in YAML format
 * consisting of the following elements:</p>
 * <ul>
 * <li>manTitle - Title to use</li>
 * <li>manDescription - A description (list of lines)</li>
 * <li>manParameters - A list of parameters (list of lines)</li>
 * <li>manExamples - A list of examples (list of lines)</li>
 * </ul>
 * 
 */
public class ManPageUtil {

    public enum ManPage {

        BAN("ban.yml"),
        HELP("help.yml"),
        INFO("info.yml"),
        LIST("list.yml"),
        RM("rm.yml"),
        RUN("run.yml"),
        UNBAN("unban.yml");

        private final String name;
    

        private ManPage(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    // determines which language to use
    private final String lang;
    
    // template to resolve the path
    private static final String manPathRaw = "plugins/TimeBan/lang/%s/man/%s";
    
    /**
     * Instantiate a new util with the given language
     */
    public ManPageUtil(String lang) {
        this.lang = lang;
    }
    
    /**
     * Get a man page as string.
     *
     * @param page - man page to fetch
     */
    public String getMan(ManPage page) {
        return getMan(page, false);
    }
    
    public String getMan(ManPage page, boolean colored) {
        StringBuilder result = new StringBuilder();
        
        // load man page
        String manPath = String.format(manPathRaw, lang, page.getName());
        File f = new File(manPath);
        YamlConfiguration manPage = YamlConfiguration.loadConfiguration(f);
        
        // Construct output
        result.append(TerminalUtil.createHeadline(manPage.getString("manTitle"))).append("\n");
        if(colored) result.append(ChatColor.GRAY);
        result.append(getContentString(manPage, "manDescription"));
        
        String parameters = getContentString(manPage, "manParameters");
        if(parameters != null && !parameters.isEmpty()) {
            result.append("\n");
            if(colored) result.append(ChatColor.WHITE);
            result.append("Parameters:").append("\n");
            if(colored) result.append(ChatColor.GRAY);
            result.append(parameters);
        }
        
        String examples = getContentString(manPage, "manExamples");
        if(examples != null && !examples.isEmpty()) {
            result.append("\n");
            if(colored) result.append(ChatColor.WHITE);
            result.append("Examples:").append("\n");
            if(colored) result.append(ChatColor.GRAY);
            result.append(examples);
        }
        
        return result.toString();
    }
    
    /**
     * Converts a list of strings into a string with new lines.
     * @param langFile
     * @param key
     * @return 
     */
    private String getContentString(YamlConfiguration langFile, String key) {
        StringBuilder result = new StringBuilder();
        
        List<String> lines = langFile.getStringList(key);
        for(String line : lines) {
            result.append(line).append("\n");
        }
        
        return result.toString();
    }
   
}
