package de.yami.sunshine.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import de.yami.sunshine.commands.SunshineCommand;
import java.io.File;

public class Main extends JavaPlugin{
	private static Main plugin;
	FileConfiguration config;
	File cfile;
	
	public void onEnable() {
		plugin = this;
		
		
		getCommand("sunshine").setExecutor(new SunshineCommand());
		
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		cfile = new File(getDataFolder(), "config.yml");
		
			Runnable checkTime = new Runnable() {
				public void run() {
					
					config = YamlConfiguration.loadConfiguration(cfile);
					
					double time = Bukkit.getWorld("world").getTime();
					if(config.getBoolean("enabled")) {
						if(time > 12000) {
							Bukkit.getWorld("world").setTime(1000);
							Main.getPlugin().getServer().broadcastMessage("§6Ein neuer Tag beginnt.");
						}
					}
				}
			};
			
			
		
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, checkTime, 0, 200);
		
	}
	
	public static Main getPlugin() { return plugin; }
}
