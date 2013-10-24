package com.admixhosting.battleroom.weapons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.weapon.MethodType;
import com.CodCraft.api.model.weapon.Weapon;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.Weapons;
import com.admixhosting.battleroom.BattleRoom;
import com.admixhosting.battleroom.game.BattlePlayer;

public class PermaFrost extends Weapon {
	
	public Map<Entity, String> shots = new HashMap<>();
	private BattleRoom plugin;

	public PermaFrost(String name, BattleRoom plugin) {
		super(name);
		putAction(Material.ICE, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.ICE, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.ICE, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.ICE, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		addGame(plugin);
		this.plugin = plugin;
	}

	@Override
	public void giveAmmo(Player p, Material mat, Action action) {}

	@Override
	public void giveWeapon(Player p, Material mat, Action action) {
		if(p.hasPermission("battleroom.permafrost")) {
			ItemStack it = new ItemStack(Material.ICE, 1);
			ItemMeta im = it.getItemMeta();
			im.setDisplayName("PermaFrost");
			it.setItemMeta(im);
			p.getInventory().addItem(it);
		}
		
	}

	@Override
	public void onPlace(Player p, Material mat, Action action, Location loc) {}
	@Override
	public void onDamage(Player hurt, Player hurter, Material mar, Action action, Event event) {}

	@Override
	public void useWeapon(Player p, Material mat, Action action, Event event) {
		if(event instanceof PlayerInteractEvent) {
			Game<?> g = plugin.api.getModuleForClass(GameManager.class).getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					BattlePlayer bp = (BattlePlayer) g.findTeamWithPlayer(p).findPlayer(p);
					if(bp.isPermfrozen() || bp.getFrozen()) {
						return;
					}
					if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
						if(bp.getOnWall()) {
							plugin.reset(p);
							bp.setOnWall(false);
							bp.setToMove(p.getEyeLocation().getDirection().multiply(1));
						}
					} else {
						if(!((Lazer)plugin.api.getModuleForClass(Weapons.class).getWeapon("lazer")).reloads.containsKey(p.getName())) {
							p.setExp(.9F);
							((Lazer)plugin.api.getModuleForClass(Weapons.class).getWeapon("lazer")).reloads.put(p.getName(), Bukkit.getScheduler().runTaskTimer(plugin, new ReloadTimer(((Lazer)plugin.api.getModuleForClass(Weapons.class).getWeapon("lazer")), p, .4F), 0, 10));
							Snowball proj = (Snowball) p.launchProjectile(Snowball.class);
							Vector vec = p.getEyeLocation().getDirection();
							plugin.firework.put(proj.getUniqueId(), vec.multiply(2));
							proj.setVelocity(vec.multiply(4));
							shots.put(proj, p.getName());
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 0.5F);
						}
					}
				}
			}
		}
	}

}
