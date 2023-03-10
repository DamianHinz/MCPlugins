package de.yami.messenger.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.yami.messenger.commands.SetNewsCommand;
import de.yami.messenger.commands.SeenCommand;
import de.yami.messenger.listeners.JoinListener;

public class Main extends JavaPlugin{
	private static Main plugin;
	
	public void onEnable() {
		plugin = this;
		
		getCommand("setnews").setExecutor(new SetNewsCommand());
		getCommand("seen").setExecutor(new SeenCommand());
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new JoinListener(), this);
	}

	public static Main getPlugin() { return plugin; }
}
