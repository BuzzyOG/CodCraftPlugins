package com.codcraft.weapons;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import com.CodCraft.api.event.PlayerDamgedByWeaponEvent;
import com.CodCraft.api.event.PlayerDamgedByWeaponEvent.DamageCause;
import com.CodCraft.api.event.team.TeamPlayerLostEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.codcraftplayer.PlayerGetClassEvent;

public class WeaponsListener implements Listener {
	
	private Weapons plugin;
	WeaponModule weap;

	public WeaponsListener(Weapons plugin) {
		this.plugin = plugin;
		weap = plugin.api.getModuleForClass(WeaponModule.class);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getEntity());
		if(g != null) {
			if(plugin.checkIfGameIsInstanceOfPlugin(g)) {
				if(plugin.firing.containsKey(e.getEntity().getName())) {
					plugin.firing.get(e.getEntity().getName()).cancel();
					plugin.firing.remove(e.getEntity().getName());
				}
			}
		}
	}
	
	
	@EventHandler
	public void onLeave(TeamPlayerLostEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(plugin.checkIfGameIsInstanceOfPlugin(g)) {
				if(plugin.firing.containsKey(e.getPlayer().getName())) {
					plugin.firing.get(e.getPlayer().getName()).cancel();
					plugin.firing.remove(e.getPlayer().getName());
				}
			}
		}
	}
	
	@EventHandler
	public void onInvChange(PlayerItemHeldEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g != null) {
			if(plugin.checkIfGameIsInstanceOfPlugin(g)) {
				if(plugin.firing.containsKey(e.getPlayer().getName())) {
					plugin.firing.get(e.getPlayer().getName()).cancel();
					plugin.firing.remove(e.getPlayer().getName());
				}
			}
		}
	}
	
	@EventHandler
	public void onRequest(PlayerGetClassEvent e) {
		if(plugin.checkIfGameIsInstanceOfPlugin(e.getGame())) {
			Weapon weapon = weap.getWeaponForPlayer(e.getPlayer());
			if(weapon != null) {
				ItemStack item = new ItemStack(weapon.getMat(), 1);
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(weapon.getName());
				item.setItemMeta(im);
				e.getPlayer().getInventory().addItem(item);
				e.getPlayer().getInventory().addItem(new ItemStack(Material.SNOW_BALL, weapon.getAmmo()));
				e.getPlayer().getInventory().addItem(new ItemStack(Material.BLAZE_ROD, weapon.getReloads()));
			}
		}
	}
	
	@EventHandler
	public void onLeftClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			for(Weapon weap : plugin.weapons) {
				if(weap.getMat() == e.getPlayer().getItemInHand().getType()) {
					ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
					if(meta != null) {
						if(weap.getName().equalsIgnoreCase(meta.getDisplayName())) {
							if(e.getPlayer().getInventory().contains(Material.BLAZE_ROD)) {
								if(plugin.reloders.containsKey(e.getPlayer().getName())) {
									plugin.reloders.get(e.getPlayer().getName()).cancel();
									plugin.reloders.remove(e.getPlayer().getName());
								}
								e.getPlayer().getInventory().removeItem(new ItemStack(Material.BLAZE_ROD, 1));
								e.getPlayer().getInventory().removeItem(new ItemStack(Material.SNOW_BALL, weap.getAmmo()));
								e.getPlayer().setExp(1);
								plugin.reloders.put(e.getPlayer().getName(), Bukkit.getScheduler().runTaskTimer(plugin, new ReloadTimer(plugin, e.getPlayer().getName(),
										weap.getReloadTime(), weap.getAmmo()), 0, weap.getReloadTime()));
							}
						}
					}
				}
			}
		}
	}
	
	
	@EventHandler
	public void onDamage(PlayerDamgedByWeaponEvent e) {
		if(plugin.checkIfGameIsInstanceOfPlugin(e.getGame())) {
			if(e.getCause() == DamageCause.EQUIPMENT) {
				Weapon weape = weap.getWeaponForPlayer(e.getDamager());
				if(weape != null) {
					if(weape.getMat() == e.getDamager().getItemInHand().getType()) {
						if(e.getDamager().getItemInHand().getItemMeta() == null) {
							return;
						}
						if(weape.getName().equalsIgnoreCase(e.getDamager().getItemInHand().getItemMeta().getDisplayName())) {
							e.setDamage(weape.getDamage());
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(plugin.firing.containsKey(p.getName())) {
			plugin.firing.get(p.getName()).cancel();
			plugin.firing.remove(p.getName());
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		for(Weapon weap : plugin.weapons) {
			if(e.getAction() == weap.getAction()) {
				if(e.getPlayer().getItemInHand() != null) {
					if(weap.getMat() == e.getPlayer().getItemInHand().getType()) {
						ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
						if(meta != null) {
							if(weap.getName().equalsIgnoreCase(meta.getDisplayName())) {
								if(e.getPlayer().getInventory().contains(Material.SNOW_BALL)) {
									if(!plugin.firing.containsKey(e.getPlayer().getName())) {
										plugin.firing.put(e.getPlayer().getName(), Bukkit.getScheduler().runTaskTimer(plugin, new FiringRunable(e.getPlayer().getName()), 0, weap.getRpm()));
										e.getPlayer().getInventory().removeItem(new ItemStack(Material.SNOW_BALL, 1));
									} else {
										plugin.firing.get(e.getPlayer().getName()).cancel();
										plugin.firing.remove(e.getPlayer().getName());
									}

								}	
							}
						}
					}

				}
			}
		}
	}
	
	//@EventHandler
	public void onInnteract(PlayerInteractEvent e) {
		for(Weapon weap : plugin.weapons) {
			if(e.getAction() == weap.getAction()) {
				if(e.getPlayer().getItemInHand() != null) {
					if(weap.getMat() == e.getPlayer().getItemInHand().getType()) {
						ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();
						if(meta != null) {
							if(weap.getName().equalsIgnoreCase(meta.getDisplayName())) {
								if(e.getPlayer().getInventory().contains(Material.SNOW_BALL)) {
									e.getPlayer().launchProjectile(Snowball.class);
									if(weap.getRpm() > 0) {
									}
									e.getPlayer().getInventory().removeItem(new ItemStack(Material.SNOW_BALL, 1));
								}	
							}
						}
					}

				}
			}
		}
	}
}
