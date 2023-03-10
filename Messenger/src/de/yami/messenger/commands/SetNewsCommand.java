package de.yami.messenger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.yami.messenger.main.Main;

public class SetNewsCommand implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label, String[] args) {
			FileConfiguration config = Main.getPlugin().getConfig();
			String newsText = "";
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("messenger.setnews")) {
					for (int i = 0; i < args.length; i++) {
						newsText += args[i] + " ";
					} config.set("Messenger.News", newsText);
					config.set("Messenger.Players", null);
					player.sendMessage("§aNews wurden gesetzt.");
					Main.getPlugin().saveConfig();
				} else player.sendMessage("§cDazu hast du keine Rechte!");
			} 
		return false;
	}

}
