package de.yami.home.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import de.yami.home.main.Main;

public class HomeCommand implements CommandExecutor{

	@Override
	public boolean onCommand( CommandSender sender,  Command command,  String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			FileConfiguration config = Main.getPlugin().getConfig();
			if (args.length == 0) { //Ohne zusätzliche Parameter.
				int portId = searchFirstHome(config, player);
				if (portId == -1) { //Falls kein Home gefunden wurde.
					player.sendMessage("§cKein §6Home §cgefunden. Bitte setze zuerst ein §6Home§c!");
					return false;
				}
				portToHomeID(config, player, portId);
				return false;
				
				//Ein zusätzlicher Parameter.
			} else if(args.length == 1) {
				if (args[0].equals("?")) {
					player.sendMessage("§6/sethome <home name>: §aSetze einen §6Home §aPunkt.");
					player.sendMessage("§6/home: §aTeleportiere dich zu dem §6ersten belegten §ahome Slot.");
					player.sendMessage("§6/home <home name>: §aTeleportiere dich zu §6<home name>§a.");
					player.sendMessage("§6/home <slot Nr.>: §aTeleport zu home Slot §6<slot Nr.>§a.");
					player.sendMessage("§6/showhome: §aZeigt dir die Position deiner §6Home §aPunkte.");
					player.sendMessage("§6/showhome <home name>: §aZeigt die Position von §6<home name>");
					player.sendMessage("§6/showhome <slot Nr.>: §aZeigt die Position von Slot §6<slot Nr.>");
					player.sendMessage("§6/deletehome <home name>: §aLöscht den home Punkt §6<home name>§a.");
					player.sendMessage("§6/deletehome <slot Nr.>: §aLöscht den home Punkt in Slot §6<slot Nr.>§a.");
					return false;
				}  
				else if (args[0].length() == 1) { //Argument mit nur einem Zeichen
					int number = (int) args[0].charAt(0) - 48; // -48 weil der Ascii Code von '1' genommen wird.
					if (number >= 1 && number <= Main.getMaxHomes()) {
						int portId = Integer.parseInt(args[0]) - 1;
						portToHomeID(config, player, portId);
						return false;
					}
				}
				int portId = searchHomeName(config, player, args[0]);
				if (portId == -1) { //Name wurde nicht gefunden
					player.sendMessage("§cDein Home §6" + args[0] +" §ckonnte nicht gefunden werden.");
					return false;
				} else if (portId >=0 && portId <= Main.getMaxHomes() - 1) {
					portToHomeID(config, player, portId);
					return false;
				}
			} else player.sendMessage("§cBitte benutze §6/home <home name>§c oder §6/home ? §cführ mehr Informationen.");
		} 
		return false;
	}
	
	public void portToHomeID(FileConfiguration config, Player player, int id) {
		String name = player.getName();
		if (id == -1 ) {
			player.sendMessage("§cFehlercode 6");
			return;
		}
		//Falls home leer ist, oder noch nicht generiert wurde.
		if (config.getBoolean("Home."+ name + ".home" + Integer.toString(id) +".Empty") == true || !config.isString("Home." + player.getName() + ".home" + Integer.toString(id) +".Name")) { //Falls slot leer ist.
			player.sendMessage("§cGesuchte §6Home §cnicht gefunden.");
			return;
		} 
		World world = Bukkit.getWorld(config.getString("Home."+ name + ".home" + Integer.toString(id) +".World"));
		double x = config.getDouble("Home."+ name + ".home" + Integer.toString(id) +".X");
		double y = config.getDouble("Home."+ name + ".home" + Integer.toString(id) +".Y");
		double z = config.getDouble("Home."+ name + ".home" + Integer.toString(id) +".Z");
		float yaw = (float) config.getDouble("Home."+ name + ".home" + Integer.toString(id) +".Yaw");
		float pitch = (float) config.getDouble("Home."+ name + ".home" + Integer.toString(id) +".Pitch");
		Location location = new Location(world, x, y, z, yaw, pitch);
		player.teleport(location);
		player.sendMessage("§aWilkommen bei §6" + config.getString("Home."+ name + ".home" + Integer.toString(id) +".Name"));
	}
	
	//Sucht nach dem ersten Home Slot in dem ein Home gespeichert ist.
	public int searchFirstHome(FileConfiguration config, Player player) {
		for (int i = 0; i < Main.getMaxHomes() ;i++) {
			if (config.isDouble("Home." + player.getName() + ".home" + Integer.toString(i) +".X") && !config.getBoolean("Home." + player.getName() + ".home" + Integer.toString(i) +".Empty")) return i;
		}
		return -1;
	}

	//Sucht und returnt eine ID zu einem Home Namen
	public int searchHomeName(FileConfiguration config, Player player, String homeName) {
		for(int i = 0;i < Main.getMaxHomes(); i++) {
			if (config.isString("Home." + player.getName() + ".home" + Integer.toString(i) +".Name")) {
				if (config.getString("Home." + player.getName() + ".home" + Integer.toString(i) +".Name").equals(homeName)) return i;
			}
		} 
		return -1;
	}

}