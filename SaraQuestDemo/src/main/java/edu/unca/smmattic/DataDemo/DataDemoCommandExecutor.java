package edu.unca.smmattic.DataDemo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

/*
 * This is a sample CommandExectuor
 */
public class DataDemoCommandExecutor implements CommandExecutor {
	private final DataDemo plugin;

	/*
	 * This command executor needs to know about its plugin from which it came
	 * from
	 */
	public DataDemoCommandExecutor(DataDemo plugin) {
		this.plugin = plugin;
	}

	/*
	 * On command set the sample message
	 * 
	 * The following commands will register a 'key' to the player and store it. 
	 * This key will allow for the plugin to remember what player has what ability and will
	 * create a temporary file that will hold this information. As it stands now, 
	 * this information will be deleted at the termination of the program.
	 * 
	 * If the metadata registers as true, the player will receive a 'blessing'
	 * but if it is false, the player will not receive a blessing.
	 * 
	 * I have noticed that the program tends to spaz a little. If not 'blessed' 
	 * but jumping in rapid succession, the character might be able to fly. 
	 * I think this might have something to do with built-in OP commands. 
	 */
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + command.getUsage());
			return false;
		} else if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "you must be logged on to use these commands");
			return false;
		} else if (args[0].equalsIgnoreCase("bless")
				&& sender.hasPermission("blessing.bless")) {
			Player fred = (Player) sender;
			plugin.setMetadata(fred, "bless", true, plugin);
			sender.sendMessage(ChatColor.RED + fred.getName()
					+ " you have been blessed by the gods!");
			plugin.logger.info(fred.getName() + " has been blessed by the gods!");
			return true;
		} else if (args[0].equalsIgnoreCase("none")
				&& sender.hasPermission("blessing.none")) {
			Player fred = (Player) sender;
			plugin.setMetadata(fred, "bless", false, plugin);
			sender.sendMessage(ChatColor.RED + fred.getName()
					+ " is no longer blessed by the gods");
			plugin.logger.info(fred.getName() + " is no longer blessed by the gods.");
			return true;
		} else if (args[0].equalsIgnoreCase("message")
				&& sender.hasPermission("blessing.message")) {
			this.plugin.getConfig().set("sample.message",
					Joiner.on(' ').join(args));
			return true;

		} else {
			return false;
		}
	}

}
