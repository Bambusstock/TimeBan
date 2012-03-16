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
	 * Load a ban list.
	 * @param handle File object pointing to the file where to save the data.
	 * @param plugin Plugin instance.
	 */
	public void load(File handle, TimeBan plugin) {
		IOHelper helper = new IOHelper(handle, plugin);
		helper.load();
	}
	
	/**
	 * Save ban list.
	 * @param handle File object pointing to the file where to save the data.
	 * @param plugin Plugin instance.
	 */
	public void save(File handle, TimeBan plugin) {
		IOHelper helper = new IOHelper(handle, plugin);
		helper.save();
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
				log.warning("[TimeBan] Error while writing to stream.");
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
				log.warning("[TimeBan] Error while writing to stream.");
				log.info(e.toString());
			}
			catch (ClassNotFoundException e) {
				log.warning("[TimeBan] Class not found.");
				log.info(e.toString());
			}
		}
	}
}
