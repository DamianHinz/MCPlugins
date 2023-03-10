package de.yami.sunshine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import de.yami.sunshine.main.Main;

public class SunshineCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			
			FileConfiguration config = Main.getPlugin().getConfig();
			if (config.getBoolean("enabled")) {
				config.set("enabled", false);
				Main.getPlugin().getServer().broadcastMessage("§aSunshine wurde §6deaktiviert.");
				Main.getPlugin().saveConfig();
			} else {
				config.set("enabled", true);
				Main.getPlugin().saveConfig();
				Main.getPlugin().getServer().broadcastMessage("§aSunshine wurde §6aktiviert.");
			}
		}
		return false;
	}
}
