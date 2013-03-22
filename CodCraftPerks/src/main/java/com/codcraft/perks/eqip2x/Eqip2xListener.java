package com.codcraft.perks.eqip2x;


import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.codcraft.cac.CaCModule;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;
import com.codcraft.perks.Perks;

public class Eqip2xListener implements Listener {
	private Perks plugin;
	
	public Eqip2xListener(Perks plugin2) {
		this.plugin = plugin2;
		plugin.api.getModuleForClass(CaCModule.class).addweapon("Eqip2X", "Perk3");
	}
	@EventHandler (priority = EventPriority.MONITOR)
	public void ongetClass(PlayerGetClassEvent e) {
		final CCPlayer player = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(e.getPlayer());
		if(player.getClass(player.getCurrentclass()).getPerk3().equalsIgnoreCase("Eqip2X") ) {
			for(ItemStack in : e.getItems()) {
				if(in.getType() == Material.LEVER) {
					e.addItem(new ItemStack(Material.LEVER, 1));
					return;
				}
			}
		}

	}


}
