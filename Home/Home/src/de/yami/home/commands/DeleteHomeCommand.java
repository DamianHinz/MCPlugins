package de.yami.home.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.yami.home.main.Main;

public class DeleteHomeCommand implements CommandExecutor{

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			FileConfiguration config = Main.getPlugin().getConfig();
			if (args.length == 0) {
				player.sendMessage("§cBitte gebe einen §6Home §cein das du löschen möchtest.");
				return false;
			} else if (args.length == 1) {
				if (args[0].length() == 1) { //Argument mit nur einem Zeichen
					int number = (int) args[0].charAt(0) - 48; // -48 weil der Ascii Code von '1' genommen wird.
					if (number >= 1 && number <= Main.getMaxHomes()) {
						int portId = Integer.parseInt(args[0]) - 1;
						int slotNumber = portId + 1;
						String slotName = "Slot 0" + slotNumber;
						deleteHomeID(config, player, portId, slotName);
						return false;
					}
				}
				int portId = getNameID(config, player, args[0]);
				if (portId == -1) { //Name wurde nicht gefunden
					player.sendMessage("§cDein Home §6" + args[0] +" §ckonnte nicht gefunden werden.");
					return false;
				} else if (portId >=0 && portId <= Main.getMaxHomes() - 1) {
					deleteHomeID(config, player, portId, args[0]);
					return false;
				}
			} else player.sendMessage("§cBitte benutze §6/deletehome <home name>");
		}
		return false;
	}
	
	//Löscht ein Home Punkt über die ID.
	private void deleteHomeID(FileConfiguration config, Player player, int id, String homeName) {
		String name = player.getName();
		if (config.getBoolean("Home."+ name + ".home" + Integer.toString(id) +".Empty") == true || !config.isString("Home." + name + ".home" + Integer.toString(id) + ".Name")) {
			player.sendMessage("§cDer Slot §6" + homeName + " §cist bereits leer.");
			return;
		}
		config.set("Home." + name + ".home" + Integer.toString(id) + ".Name", "");
		config.set("Home."+ name + ".home" + Integer.toString(id) +".X", 0); 
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Y", 0);
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Z", 0);
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Yaw", 0); 
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Pitch", 0);
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Empty", true);
		Main.getPlugin().saveConfig();
		player.sendMessage("§cDein Home §6" + homeName + " §c wurde gelöscht.");
	}
	
	private int getNameID(FileConfiguration config, Player player, String homeName) {
		for(int i = 0;i < Main.getMaxHomes();i++) {
			if (config.getString("Home."+ player.getName() + ".home" + Integer.toString(i) +".Name").equals(homeName)) {
				if (!config.getBoolean("Home."+ player.getName() + ".home" + Integer.toString(i) +".Empty")) return i;
			}
		}
		return -1;
	}
}
