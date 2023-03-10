package de.yami.home.main;

import org.bukkit.plugin.java.JavaPlugin;

import de.yami.home.commands.DeleteHomeCommand;
import de.yami.home.commands.HomeCommand;
import de.yami.home.commands.SetHomeCommand;
import de.yami.home.commands.ShowHomeCommand;

public class Main extends JavaPlugin{
	
	final static int MAXHOMES = 5;
	private static Main plugin;

	public void onEnable() {
		plugin = this;
		
		getCommand("sethome").setExecutor(new SetHomeCommand());
		getCommand("home").setExecutor(new HomeCommand());
		getCommand("showhome").setExecutor(new ShowHomeCommand());
		getCommand("deletehome").setExecutor(new DeleteHomeCommand());
	}
	
	public static Main getPlugin() { return plugin; }
	
	public static int getMaxHomes() { return MAXHOMES; }
	
}
