package me.Bambusstock.TimeBan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.Logger;


/**
 * TODO:
 * 	- add security for file management
 * @author Bambusstock
 *
 */
public class BanSet extends TreeSet<Ban>
implements Serializable
{
	transient Logger log = Logger.getLogger("Minecraft");

	private static final long	serialVersionUID	= -5972004136609506533L;
	
	/**
	 * Get a Ban object by a players name.
	 * @param name
	 * @return Ban object for the player. If not found return empty Ban!
	 */
	public Ban getBanByPlayerName(String name) {
		Ban result = new Ban();
		for(Ban ban : this) {
			if(ban.getPlayer().getName().equals(name)) {
				result = ban;
			}
		}
		return result;
	}
	
	/**
	 * Load a ban list.
	 * @param handle File object pointing to the file where to save the data.
	 * @param plugin Plugin instance.
	 */
	public void load(File handle, TimeBan plugin) {
		IOHelper helper = new IOHelper(handle, plugin);
		helper.load();
		log.info("[TimeBan] Loaded ´" + this.size() + "´ bans."); 
	}
	
	/**
	 * Save ban list.
	 * @param handle File object pointing to the file where to save the data.
	 * @param plugin Plugin instance.
	 */
	public void save(File handle, TimeBan plugin) {
		IOHelper helper = new IOHelper(handle, plugin);
		helper.save();
		log.info("[TimeBan] Saved ´" + this.size() + "´ bans.");
	}
	
	/**
	 * Helper class to serialize and deserialize a ban list.
	 * @author Bambusstock
	 *
	 */
	class IOHelper {
		private File handle;
		private TimeBan plugin;
		
		/**
		 * Initialize a IOHelper.
		 * @param handle File object pointing to the file where to save data.
		 * @param plugin Plugin instance.
		 */
		private IOHelper(File handle, TimeBan plugin) {
			this.handle = handle;
			this.plugin = plugin;
			this.checkFileExists();
		}
		
		/**
		 * Check if a file to save the banlist exists. If not it will be created if
		 * defined in the configuration.
		 */
		private void checkFileExists() {
			if(!this.handle.exists()) {
				try {
					log.info("[TimeBan IOHelper] Create file to load and save the banlist...");
					this.handle.createNewFile();
					log.info("[TimeBan IOHelper] Done.");
				}
				catch (IOException e) {
					log.warning("[TimeBan IOHelper] Error while creating new file to save!");
					log.info(e.toString());
				}
			}
		}
		
		/**
		 * Save the ban list.
		 */
		private void save() {
			try {
				FileOutputStream fileStream = new FileOutputStream(this.handle);
				ObjectOutputStream output = new ObjectOutputStream(fileStream);
				output.writeObject(BanSet.this);
				output.close();
			}
			catch (IOException e) {
				log.warning("[TimeBan] Error while writing to stream!");
				log.info(e.toString());
			}
		}
		
		/**
		 * Load the ban list.
		 */
		private void load() {
			BanSet set;
			try {
				FileInputStream fileStream = new FileInputStream(this.handle);
				ObjectInputStream input = new ObjectInputStream(fileStream);
				set = (BanSet)input.readObject();
				for(Iterator<Ban> it = set.iterator(); it.hasNext(); ) {
					Ban tmp = it.next();
					BanSet.this.add(new Ban(this.plugin, tmp.player, tmp.until, tmp.reason));
				}
				input.close();
			}
			catch (IOException e) {
				log.warning("[TimeBan IOHelper] Error while writing to stream!");
				log.info(e.toString());
			}
			catch (ClassNotFoundException e) {
				log.warning("[TimeBan IOHelper] Class not found!");
				log.info(e.toString());
			}
		}
	}
}
