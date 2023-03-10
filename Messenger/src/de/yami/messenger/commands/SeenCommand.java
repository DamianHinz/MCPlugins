package de.yami.messenger.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.yami.messenger.main.Main;

public class SeenCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration config = Main.getPlugin().getConfig();
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				if (config.getBoolean("Messenger.Players." + player.getName() + ".Show")) {
					config.set("Messenger.Players." + player.getName() + ".Show", false);
					//config.set("Messenger.Players." + player.getName() + ".*", 0);
					player.sendMessage("§aNews werden §6versteckt.");
				} else {
					config.set("Messenger.Players." + player.getName() + ".Show", true);
					player.sendMessage("§aNews werden §6angezeigt.");
				} 
			} else player.sendMessage("§cBitte benutze §6/seen §coder §6/seen unset§c.");
		Main.getPlugin().saveConfig();
		}
		return false;
	}
}
