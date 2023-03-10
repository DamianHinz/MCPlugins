package de.yami.home.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.yami.home.main.Main;


public class ShowHomeCommand implements CommandExecutor{

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			FileConfiguration config = Main.getPlugin().getConfig();
			if (args.length == 0) { //Zeigt alle Homes an falls nichts angegeben wird.
				for(int i = 0;i < Main.getMaxHomes();i++) {
					showHomeID(config, player, i);
				} return false;
			} else if(args.length == 1) { //Anzahl der Argumente
				if (args[0].length() == 1) { //Länge des ersten Arguments
					int number = (int) args[0].charAt(0) - 48; // -48 weil der Ascii Code von '1' genommen wird.
					if (number >= 1 && number <= Main.getMaxHomes()) {
						int showId = Integer.parseInt(args[0]) - 1;
						showHomeID(config, player, showId);
						return false;
					} 
				} 
				int showId = showHomeName(config, player, args[0]);
				if (showId == -1) { //Falls der Name nicht gefunden wurde.
					player.sendMessage("§cKein Home mit dem namen §6" + args[0] + " §cgefunden.");
					return false;
				}
				showHomeID(config, player, showId);
				return false;
				
			} else player.sendMessage("§cBitte benutze §6/showhome§c.");
		} 
		return false;
	}
	
	//Printet den Homepunkt mit einer bestimmten ID.
	public void showHomeID(FileConfiguration config, Player player, int id) {
		String name = player.getName();
		if (config.getBoolean("Home."+ name + ".home" + Integer.toString(id) +".Empty") || !config.isString("Home."+ name + ".home" + Integer.toString(id) +".Name")) {
			id++;
			player.sendMessage("§aHomepunkt Slot §60" + id +"§a ist leer.");
			return;
		}
		double x = Math.round(config.getDouble("Home."+ name + ".home" + Integer.toString(id) +".X"));
		double y = Math.round(config.getDouble("Home."+ name + ".home" + Integer.toString(id) +".Y"));
		double z = Math.round(config.getDouble("Home."+ name + ".home" + Integer.toString(id) +".Z"));
		player.sendMessage("§aDein Homepunkt §6" + config.getString("Home."+ name + ".home" + Integer.toString(id) + ".Name") + " §aist §6(" + x + ", " + y + ", " + z + ")");
	}
	
	//Sucht nach dem Namen eines Home Punkts und gibt seine ID wieder.
	public int showHomeName(FileConfiguration config, Player player, String homeName) {
		for(int i = 0;i < Main.getMaxHomes();i++) {
			if (config.getString("Home."+ player.getName() + ".home" + Integer.toString(i) +".Name").equals(homeName)) {
				if (!config.getBoolean("Home."+ player.getName() + ".home" + Integer.toString(i) +".Empty")) return i;
			}
		}
		return -1;
	}

}