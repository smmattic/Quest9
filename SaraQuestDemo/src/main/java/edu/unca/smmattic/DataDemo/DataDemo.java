package edu.unca.smmattic.DataDemo;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * This is the main class of the sample plug-in
 */
public class DataDemo extends JavaPlugin {
	/*
	 * This is called when your plug-in is enabled
	 */
	DataDemoLogger logger;

	@Override
	public void onEnable() {
		// save the configuration file
		saveDefaultConfig();

		// Create logger
		logger = new DataDemoLogger(this);

		// Create the SampleListener
		new DataDemoListener(this);

		// set the command executor for sample
		this.getCommand("blessing").setExecutor(new DataDemoCommandExecutor(this));
	}

	/*
	 * This is called when your plug-in shuts down
	 */
	@Override
	public void onDisable() {

	}
/* This sets up the meta data, fixing a value to a player, with 
 * the "key" being a string, all in regards to the pugin being used
 * 
 */
	public void setMetadata(Player player, String key, Object value,
			DataDemo plugin) {
		player.setMetadata(key, new FixedMetadataValue(plugin, value));
	}
	
	/*This checks to see if the player has a metadata value assigned to them.
	 *If they do, the boolean value will return true or false, depending on
	 *what the program default is. 
	 */

	public Object getMetadata(Player player, String key, DataDemo plugin) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName()
					.equals(plugin.getDescription().getName())) {
				return (value.asBoolean());
			}
		}
		return null;
	}
}
