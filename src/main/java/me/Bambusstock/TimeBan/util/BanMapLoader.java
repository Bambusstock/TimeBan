package me.Bambusstock.TimeBan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides you with easy to use methods to load and save a Map of
 * bans.
 *
 * @author Bambusstock
 */
public class BanMapLoader {

    private static final Logger log = Logger.getLogger("Minecraft");
    
    /**
     * Create a new file and write the serialization of an empty Map to it.
     *
     * @param path Path to the file to touch.
     */
    protected static void touchBanList(String path) {
        Map<String, Ban> emptyMap = Collections.emptyMap();
        save(emptyMap, path);
    }

    /**
     * Save the ban list.
     * 
     * @param bans a list of bans.
     * @param path the path to the file where the map should be saved.
     */
    public static void save(Map<String, Ban> bans, String path) {
        File f = new File(path);
        
        try {
            FileOutputStream fileStream = new FileOutputStream(f);
            ObjectOutputStream output = new ObjectOutputStream(fileStream);
            output.writeObject(bans);
            output.close();
        } catch (IOException e) {
            log.warning("[TimeBan] Error while writing to stream! Try a server reload...");
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Load the ban list from file path.
     * 
     * @param path Path leading to the file loading the ban list.
     * 
     * @return Map with ban(empty if no file found).
     */
    public static Map<String, Ban> load(String path) {
        Map<String, Ban> result = Collections.emptyMap();
        File f = new File(path);

        if (f.exists() == false) {
            touchBanList(path);
        } else {
            try {
                FileInputStream fileStream = new FileInputStream(f);
                ObjectInputStream input = new ObjectInputStream(fileStream);
                result = (Map<String, Ban>) input.readObject();
                input.close();
            } catch (IOException e) {
                log.warning("[TimeBan] Error while reading from stream! Try a server reload...");
                log.info(e.getMessage());
            } catch (ClassNotFoundException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        
        return result;
    }
}
