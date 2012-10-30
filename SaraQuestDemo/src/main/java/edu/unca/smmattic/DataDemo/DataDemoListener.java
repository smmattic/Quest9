package edu.unca.smmattic.DataDemo;

import java.text.MessageFormat;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

/*
 * This is a sample event listener
 */
public class DataDemoListener implements Listener {
	private final DataDemo plugin;

	/*
	 * This listener needs to know about the plugin which it came from
	 */
	public DataDemoListener(DataDemo plugin) {
		// Register the listener
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		this.plugin = plugin;
	}

	/*
	 * Send the sample message to all players that join
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.setMetadata(event.getPlayer(), "bless", false, plugin);
		event.getPlayer().sendMessage(
				this.plugin.getConfig().getString("sample.message"));

	}

	/*
	 * Another example of a event handler. This one will give you the name of
	 * the entity you interact with, if it is a Creature it will give you the
	 * creature Id.
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		final EntityType entityType = event.getRightClicked().getType();
		event.getPlayer().sendMessage(
				MessageFormat.format(
						"You interacted with a {0} it has an id of {1}",
						entityType.getName(), entityType.getTypeId()));
	}

	//This will give the player a 'blessing' if 'bless' is typed and then a block is clicked
	//Items, food and a flower will appear, as will the ability to fly
	@EventHandler(priority = EventPriority.HIGH)
	public void demoEvent(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if ((Boolean) plugin
					.getMetadata(event.getPlayer(), "bless", plugin)) {
				Block b = event.getClickedBlock();
				if (b != null) {
					Location loc = b.getLocation();
					loc.getWorld().dropItem(loc, new ItemStack(Material.BREAD, 10));
					loc.getWorld().dropItem(loc, new ItemStack(Material.RED_ROSE));
					loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND_SWORD, 1));
					loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND_BOOTS, 1));
					loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND_HELMET, 1));
					loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND_LEGGINGS, 1));
					loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
					loc.getWorld().getBlockAt(loc).setType(Material.DIAMOND_BLOCK);
					event.getPlayer().setAllowFlight(true);
					event.getPlayer().sendMessage("You have been blessed by the gods!");
					plugin.logger.info(event.getPlayer()+ " has been blessed by the gods!");

				}

			}

		}
	}
}
