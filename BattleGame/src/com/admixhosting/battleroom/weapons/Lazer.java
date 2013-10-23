package com.admixhosting.battleroom.weapons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.weapon.Gun;
import com.CodCraft.api.model.weapon.MethodType;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.Weapons;
import com.admixhosting.battleroom.BattleRoom;
import com.admixhosting.battleroom.game.BattlePlayer;

public class Lazer extends Gun {

	private BattleRoom plugin2;
	public Map<String, BukkitTask> reloads = new HashMap<String, BukkitTask>();

	public Lazer(String name, Weapons plugin, BattleRoom plugin2) {
		super(name, plugin);
		putAction(Material.IRON_BARDING, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.IRON_BARDING, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.GOLD_BARDING, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.GOLD_BARDING, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.DIAMOND_BARDING, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.DIAMOND_BARDING, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		
		putAction(Material.IRON_BARDING, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.IRON_BARDING, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.GOLD_BARDING, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.GOLD_BARDING, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.DIAMOND_BARDING, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.DIAMOND_BARDING, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		
		putAction(Material.LEVER, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.LEVER, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.DIODE, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.DIODE, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.REDSTONE_COMPARATOR, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.REDSTONE_COMPARATOR, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		
		putAction(Material.LEVER, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.LEVER, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.DIODE, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.DIODE, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.REDSTONE_COMPARATOR, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.REDSTONE_COMPARATOR, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		addGame(plugin2);
		this.plugin2 = plugin2;
	}

	@Override
	public void giveAmmo(Player p, Material mat, Action action) {}

	@Override
	public void giveWeapon(Player p, Material mat, Action action) {
		Game<?> g = plugin2.api.getModuleForClass(GameManager.class).getGameWithPlayer(p);
		if(g != null) {
			if(g.getPlugin() == plugin2) {
				BattlePlayer bp = (BattlePlayer) g.findTeamWithPlayer(p).findPlayer(p);
				if(bp.medic) {
					if(p.hasPermission("battleroom.comparator")) {
						ItemStack it = new ItemStack(Material.REDSTONE_COMPARATOR);
						ItemMeta im = it.getItemMeta();
						im.setDisplayName("Firewall");
						it.setItemMeta(im);
						p.getInventory().addItem(it);
					} else if(p.hasPermission("battleroom.repeater")) {
						ItemStack it = new ItemStack(Material.DIODE);
						ItemMeta im = it.getItemMeta();
						im.setDisplayName("Ice Breaker");
						it.setItemMeta(im);
						p.getInventory().addItem(it);
					} else {
						ItemStack it = new ItemStack(Material.LEVER);
						ItemMeta im = it.getItemMeta();
						im.setDisplayName("de-icer");
						it.setItemMeta(im);
						p.getInventory().addItem(it);
					}
				} else {
					if(p.hasPermission("battleroom.diamond")) {
						ItemStack it = new ItemStack(Material.DIAMOND_BARDING);
						ItemMeta im = it.getItemMeta();
						im.setDisplayName("Galatial Blaster");
						it.setItemMeta(im);
						p.getInventory().addItem(it);
					} else if (p.hasPermission("battleroom.gold")) {
						ItemStack it = new ItemStack(Material.GOLD_BARDING);
						ItemMeta im = it.getItemMeta();
						im.setDisplayName("Cryo Cannon");
						it.setItemMeta(im);
						p.getInventory().addItem(it);
					} else {
						ItemStack it = new ItemStack(Material.IRON_BARDING);
						ItemMeta im = it.getItemMeta();
						im.setDisplayName("Bone Chiller");
						it.setItemMeta(im);
						p.getInventory().addItem(it);
					}
				}
			}
		}
		//p.getInventory().addItem(new ItemStack(Material.WOOD_SPADE));
		
	}

	@Override
	public void onPlace(Player p, Material mat, Action action, Location loc) {}
	
	@Override
	public void useWeapon(final Player p, Material mat, Action action, Event event) {
		if(event instanceof PlayerInteractEvent) {
			
				GameManager gm = plugin2.api.getModuleForClass(GameManager.class);
				Game<?> g = gm.getGameWithPlayer(p);
				if(g != null) {
					if(g.getPlugin() == plugin2) {
						BattlePlayer bp = (BattlePlayer) g.findTeamWithPlayer(p).findPlayer(p);
						if(bp.getFrozen() || bp.isPermfrozen()) {
							return;
						}
						if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
							if(bp.getOnWall()) {
								plugin2.reset(p);
								bp.setOnWall(false);
								bp.setToMove(p.getEyeLocation().getDirection().multiply(1));
							}
						} else {
							Entity proj;
							if(!bp.medic) {
								if(!reloads.containsKey(p.getName())) {
									if(p.hasPermission("battleroom.diamond")) {
										Bukkit.getScheduler().runTaskLater(plugin2, new Runnable() {
											
											@Override
											public void run() {
												Entity proj1 = (Snowball) p.launchProjectile(Snowball.class);
												Vector vec = p.getEyeLocation().getDirection();
												plugin2.firework.put(proj1.getUniqueId(), vec.multiply(2));
												proj1.setVelocity(vec.multiply(2));
											}
										}, 6);
										p.setExp((float) .9);
										reloads.put(p.getName(), Bukkit.getScheduler().runTaskTimer(plugin2, new ReloadTimer(this, p, .4F), 0, 5));

									} else if(p.hasPermission("battleroom.gold")) {
										p.setExp((float) .9);
										reloads.put(p.getName(), Bukkit.getScheduler().runTaskTimer(plugin2, new ReloadTimer(this, p, .2F), 0, 5));
									} else {
										p.setExp((float) .9);
										reloads.put(p.getName(), Bukkit.getScheduler().runTaskTimer(plugin2, new ReloadTimer(this, p, .1F), 0, 5));
									}
									proj = (Snowball) p.launchProjectile(Snowball.class);
									Vector vec = p.getEyeLocation().getDirection();
									plugin2.firework.put(proj.getUniqueId(), vec.multiply(2));
									proj.setVelocity(vec.multiply(2));
								}

							} else {
								if(!reloads.containsKey(p.getName())) {
									if(p.hasPermission("battleroom.comparator")) {
										p.setExp((float) .9);
										reloads.put(p.getName(), Bukkit.getScheduler().runTaskTimer(plugin2, new ReloadTimer(this, p, .4F), 0, 5));
										Bukkit.getScheduler().runTaskLater(plugin2, new Runnable() {
											
											@Override
											public void run() {
												Entity proj1 = (Snowball) p.launchProjectile(Snowball.class);
												Vector vec = p.getEyeLocation().getDirection();
												plugin2.firework.put(proj1.getUniqueId(), vec.multiply(2));
												proj1.setVelocity(vec.multiply(2));
											}
										}, 6);
									} else if(p.hasPermission("battleroom.repeater")) {
										p.setExp((float) .9);
										reloads.put(p.getName(), Bukkit.getScheduler().runTaskTimer(plugin2, new ReloadTimer(this, p, .2F), 0, 5));
									} else {
										p.setExp((float) .9);
										reloads.put(p.getName(), Bukkit.getScheduler().runTaskTimer(plugin2, new ReloadTimer(this, p, .1F), 0, 5));
									}
									proj = (Snowball) p.launchProjectile(Snowball.class);
									plugin2.firework.put(proj.getUniqueId(), p.getEyeLocation().getDirection().multiply(3));
									proj.setVelocity(p.getEyeLocation().getDirection().multiply(3));
								}


							}
						}

					}
				}
				/*if(plugin2.onWall.containsKey(p.getName())){
					if(plugin2.onWall.get(p.getName())) {
						plugin2.onWall.put(p.getName(), false);
						p.setVelocity(p.getEyeLocation().getDirection().multiply(4));
						plugin2.toMove.put(p.getName(), p.getEyeLocation().getDirection().multiply(1));
					}
				} */
			

		}

	}

	@Override
	public void onDamage(Player hurt, Player hurter, Material mar, Action action, Event event) {
		
		
	}

}
