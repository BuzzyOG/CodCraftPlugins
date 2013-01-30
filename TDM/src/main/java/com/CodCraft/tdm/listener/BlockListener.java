package com.CodCraft.tdm.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.modules.Weapons;
import com.CodCraft.tdm.CodCraft;

public class BlockListener implements Listener {
	private CodCraft plugin;
	public BlockListener(CodCraft plugin) {
		this.plugin = plugin;
	}
    @EventHandler
    public void onExsplotion(EntityExplodeEvent  e) {
    	e.blockList().clear();
    }
    @EventHandler
    public void OnBreak(BlockBreakEvent event) {
    	Player p = event.getPlayer();
    	if (p.getGameMode() == GameMode.CREATIVE) {	
    	} else {
    		if(event.getBlock().getType() == Material.THIN_GLASS) {
    			//BlockManger.addGlassPlace(event.getBlock().getLocation());
    		} else {
        		event.setCancelled(true);
    		}
    	}
    }
    @SuppressWarnings("deprecation")
	@EventHandler
    public void SwitchPlaceE(BlockPlaceEvent e) {
    	if(e.getBlockPlaced().getType() == Material.LEVER) {
    		plugin.getApi().getModuleForClass(Weapons.class).setC4Spot(e.getPlayer(), e.getBlockPlaced().getLocation());
    		e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.STICK));
    		e.getPlayer().updateInventory();
    	}
    }
}
