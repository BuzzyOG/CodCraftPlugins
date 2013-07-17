package com.codcraft.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.shop.CCShop.Type;
import com.codcraft.shop.event.PlayerBuyItemEvent;

public class CCShopListener implements Listener {

	private CCShop plugin;
	
	public CCShopListener(CCShop plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void CommandShopEvent(ShopCommandEvent e) {
		if(e.getItem().getName().equalsIgnoreCase("Add CaC")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "a setClasses" + e.getPlayer().getName() + plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(e.getPlayer()).getCaCint() + 1);
		}
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			CCPlayerModule ccpm = plugin.api.getModuleForClass(CCPlayerModule.class);
			CCPlayer ccp = ccpm.getPlayer(e.getPlayer());
				if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST){
					Sign sign = (Sign) e.getClickedBlock().getState();
					if(sign.getLine(0).equalsIgnoreCase("[CCShop]")) {
						for(CCShopItem item : plugin.items) {
							if(item.getName().equalsIgnoreCase(sign.getLine(1))) {
								if(!e.getPlayer().hasPermission(item.getPermisison())) {
									if(ccp.getPoints() >= item.getPrice()) {
										PlayerBuyItemEvent event = new PlayerBuyItemEvent(e.getPlayer(), item);
										if(!event.isCancelled()) {
											ccp = ccpm.getPlayer(event.getPlayer());
											ccp.setPoints(ccp.getPoints() - event.getItem().getPrice());
											plugin.getLogger().info(event.getPlayer().getName() +" has bought " + event.getItem().getName());
											event.getPlayer().sendMessage(event.getItem().getBoughtmessage());
											if(item.getType1() == Type.PERMISSION) {
												try {
													plugin.perms.playerAdd(e.getPlayer(), item.getPermisison());
												} catch (Exception e2) {
													event.getPlayer().sendMessage("Error in buying!");
												}
											} else {
												ShopCommandEvent shopevent = new ShopCommandEvent(e.getPlayer(), item);
												Bukkit.getPluginManager().callEvent(shopevent);
												if(shopevent.isCancelled()) {
													return;
												}
											}

										}else {
											event.getPlayer().sendMessage(event.getItem().getDenyboughtmessage());
										}
									} else {
										e.getPlayer().sendMessage("You dont have enough points for this!");
									}
								} else {
									e.getPlayer().sendMessage("You already have this!");
								}
							}
						}
					}
			}
		}
	}
}
