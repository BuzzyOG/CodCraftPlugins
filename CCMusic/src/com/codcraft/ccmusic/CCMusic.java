package com.codcraft.ccmusic;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.codcraft.lobby.event.PlayerToLobbyEvent;
public class CCMusic extends JavaPlugin implements Listener {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void toLobby(PlayerToLobbyEvent e) {
		e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.RECORD_PLAY, Material.RECORD_6);
	}
}