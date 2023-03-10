package de.yami.messenger.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import de.yami.messenger.main.Main;

public class JoinListener implements Listener{
	FileConfiguration config = Main.getPlugin().getConfig();
	private String news = config.getString("Messenger.News");
	
	public String getNews() { return news; }
	
	public void setNews(String news_) { news = news_; }
	
	@EventHandler
	public void handlePlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		FileConfiguration config = Main.getPlugin().getConfig();
		boolean show;
		if (config.isSet("Messenger.Players." + player.getName() + ".Show")) { //Nimmt boolean aus Config wenn vorhanden.
			show = config.getBoolean("Messenger.Players." + player.getName() + ".Show");
		} else { 
			config.set("Messenger.Players." + player.getName() + ".Show", true); //Falls Spieler nicht in Config erstellen und show auf true.
			show = true;
		} Main.getPlugin().saveConfig();
		
		event.setJoinMessage("§aDer Spieler §6" + event.getPlayer().getName() + "§a hat den Server betreten.");
		//Damian
		if (event.getPlayer().getName().equals("Yami001")) {
			event.getPlayer().sendMessage("§aDer Erbauer von Welten ist unter uns.");
		} 
		//Felix
		else if(event.getPlayer().getName().equals("FtheG")) {
			event.getPlayer().sendMessage("§aGott hat sich unter die Sterblichen begeben.");
		}
		//Kathi
		else if(event.getPlayer().getName().equals("kathoelgato")) {
			event.getPlayer().sendMessage("§aDer Kapitalismus wird aufblühen.");
		}
		//Janet
		else if(event.getPlayer().getName().equals("Jetti26")) {
			event.getPlayer().sendMessage("§aKathis Hassliebe wird wieder aufblühen.");
		}
		//Darius
		else if(event.getPlayer().getName().equals("DasKellerKind")) {
			event.getPlayer().sendMessage("§aUnterjocher der Großen.");
		}
		//Hendrik
		else if(event.getPlayer().getName().equals("Hen96")) {
			event.getPlayer().sendMessage("§aMan kann nie sicher sein wer grade an der Tastertur sitzt.");
		}
		//Thorben
		else if(event.getPlayer().getName().equals("com13")) {
			event.getPlayer().sendMessage("§aDie Kolonien werden dich feiern. Ob sie wollen oder nicht!");
		}
		//Sylvie
		else if(event.getPlayer().getName().equals("HappyCat96")) {
			event.getPlayer().sendMessage("§aSeid Gegrüßt Erdlinge. Der Comicbuchverkäufer ist da.");
		}
		//Christian
		else if(event.getPlayer().getName().equals("Pankator")) {
			event.getPlayer().sendMessage("§aMittelerde ist wieder sicher.");
		} 
		//Stephan
		else if(event.getPlayer().getName().equals("Midnightcrash")) {
			event.getPlayer().sendMessage("§aDer Tavernenwirt des vertrauens ist hier.");
		}
		if (show) event.getPlayer().sendMessage("§cNeuerungen: " + "§6" + news);
		else if (!show) return;
		else player.sendMessage("§cSollte nicht erreicht werden.");
		
	}
}
