package me.Bambusstock.TimeBan.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author bambusstock
 */
public class MessagesUtil {
    
    private static final Logger log = Logger.getLogger("Minecraft");

    static {
        reload("en");
    }
    
    // path to messages.yml
    private static final String messagesPathRaw = "plugins/TimeBan/lang/%s/messages.yml";
    
    // mapping key to messages
    private static Map<String, String> messages;

    private MessagesUtil() {
    }

    /**
     * Reload a messages.yml for the given lang.
     *
     * @param lang lang directory. default is en.
     */
    public static void reload(String lang) {
        if (lang == null || lang.isEmpty()) {
            lang = "en";
        }
        
        messages = new HashMap<String, String>();
        String messagesPath = String.format(messagesPathRaw, lang);
        File f = new File(messagesPath);
        YamlConfiguration messagesConfiguration = YamlConfiguration.loadConfiguration(f);
        for(String key : messagesConfiguration.getKeys(false)) {
            messages.put(key, messagesConfiguration.getString(key));
        }
    }

    /**
     * Get a string from the messages.yml and format it.
     *
     * @param messageId a messageId like no_permission
     * @param values a set of values to be replaced
     * 
     * @return formatted message
     */
    public static String formatMessage(String messageId, Map<String, String> values) {
        String message = messages.get(messageId);
        if(message == null) {
            log.log(Level.WARNING, "No message found for id {0}", messageId);
            return "";
        }
        
        if(values == null || values.isEmpty()) {
            return message;
        } else {
            for(Map.Entry<String, String> value : values.entrySet()) {
                message = message.replace(value.getKey(), value.getValue());
            }
            return message;
        }
    }
}
