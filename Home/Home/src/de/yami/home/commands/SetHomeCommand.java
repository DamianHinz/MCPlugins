package de.yami.home.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import de.yami.home.main.Main;

public class SetHomeCommand implements CommandExecutor {

	
	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 1) {
				FileConfiguration config = Main.getPlugin().getConfig();
				int isFree = checkEmptyID(player, config);
				if (isFree == -1) {
					player.sendMessage("§cAlle Home Punkt sind belegt!");
					return false;
				} else {
					setHomeSlot(isFree, player, config, args[0]);
				}
			} else player.sendMessage("§cBitte benutze §6/sethome <home name>§c!");
		}
		return false;
	}
	
	private void setHomeSlot(int id, Player player, FileConfiguration config, String homeName) {
		String name = player.getName();
		config.set("Home." + name + ".home" + Integer.toString(id) + ".Name", homeName);
		config.set("Home."+ name + ".home" + Integer.toString(id) +".World", player.getWorld().getName()); 
		config.set("Home."+ name + ".home" + Integer.toString(id) +".X", player.getLocation().getX()); 
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Y", player.getLocation().getY());
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Z", player.getLocation().getZ());
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Yaw", player.getLocation().getYaw()); 
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Pitch", player.getLocation().getPitch());
		config.set("Home."+ name + ".home" + Integer.toString(id) +".Empty", false);
		Main.getPlugin().saveConfig();
		player.sendMessage("§aDu hast dein Home §6"+ homeName + " §agesetzt.");
	}
	
	private int checkEmptyID(Player player, FileConfiguration config ) {
		for(int i = 0;i < Main.getMaxHomes(); i++) {
			//if (!config.isInt("Home." + player.getName() + ".ID")) return i; //Falls noch keine Daten vorhanden sind.
			if (!config.isDouble("Home." + player.getName() + ".home" + Integer.toString(i) +".X")) {
				return i;
			}
			if (config.getBoolean("Home." + player.getName() + ".home" + Integer.toString(i) +".Epmty")) return i; //Falls ID vorhanden, aber Daten leer sind.
			if (i == Main.getMaxHomes()) player.sendMessage("§cFehlercode 5. Bitte Admin bescheidgeben.");
		} 
		return -1;
	}
}