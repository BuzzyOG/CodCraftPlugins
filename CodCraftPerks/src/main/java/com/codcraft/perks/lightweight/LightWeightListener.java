package com.codcraft.perks.lightweight;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.cac.CaCModule;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.perks.Perks;

public class LightWeightListener implements Listener {
	private Perks plugin;
	private GameManager gm;
	public LightWeightListener(Perks plugin) {
		this.plugin = plugin;
		plugin.api.getModuleForClass(CaCModule.class).addweapon("LightWeight", "Perk2", null);
		gm = plugin.api.getModuleForClass(GameManager.class);
	}
	


	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		final Player p = e.getPlayer();
		Game<?> g = gm.getGameWithPlayer(p);
		if(g != null) {
			if(plugin.checkIfGameIsInstanceOfPlugin(g)) {
				LightWeightCheck(p);
			}
		}
		
	}
	
	
	public void LightWeightCheck(final Player p) {
		final CCPlayer player = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(p);
		if(player.getClass(player.getCurrentclass()).getPerk2().equalsIgnoreCase("LightWeight") ) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1));
				}
			}, 5);
		}
	}

}
