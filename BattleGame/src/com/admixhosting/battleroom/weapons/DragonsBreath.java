package com.admixhosting.battleroom.weapons;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.model.weapon.MethodType;
import com.CodCraft.api.model.weapon.Weapon;
import com.CodCraft.api.modules.GameManager;
import com.admixhosting.battleroom.BattleRoom;
import com.admixhosting.battleroom.game.BattlePlayer;

public class DragonsBreath extends Weapon {

	private BattleRoom plugin;

	public DragonsBreath(String name, BattleRoom plugin) {
		super(name);
		putAction(Material.FIRE, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.FIRE, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		
		
		putAction(Material.FIRE, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.FIRE, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		addGame(plugin);
		this.plugin = plugin;
	}

	@Override
	public void giveAmmo(Player p, Material mat, Action action) {}

	@Override
	public void giveWeapon(Player p, Material mat, Action action) {
		if(p.hasPermission("battleroom.DragonsBreath")) {
			ItemStack it = new ItemStack(Material.FIRE, 1);
			ItemMeta im = it.getItemMeta();
			im.setDisplayName("Dragons' Breath");
			it.setItemMeta(im);
			p.getInventory().addItem(it);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void useWeapon(Player p, Material mat, Action action, Event event) {
		if(event instanceof PlayerInteractEvent) {
			if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				Game<?> g = gm.getGameWithPlayer(p);
				if(g != null) {
					if(g.getPlugin() == plugin) {
						BattlePlayer bp = (BattlePlayer) g.findTeamWithPlayer(p).findPlayer(p);
						if(bp.getOnWall()) {
							plugin.reset(p);
							bp.setOnWall(false);
							bp.setToMove(p.getEyeLocation().getDirection().multiply(1));
						}
					}
				}

			} else {

				p.getInventory().remove(Material.FIRE);
				Game<?> g = plugin.api.getModuleForClass(GameManager.class).getGameWithPlayer(p);
				if(g != null) {
					if(g.getPlugin() == plugin) {
						for(Team t : g.getTeams()) {
							for(TeamPlayer tp : t.getPlayers()) {
								Player p1 = Bukkit.getPlayer(tp.getName());
								if(p1 != null) {
									p1.sendMessage(g.findTeamWithPlayer(p).getColor() + p.getName() + ChatColor.WHITE +" has activated Dragons' Breath");
								}
							}
						}
						Team t = g.findTeamWithPlayer(p);
						for(TeamPlayer tp : t.getPlayers()) {
							BattlePlayer bp = (BattlePlayer) tp;
							if(bp.getFrozen()) {
								bp.setFrozen(false);
								for(Team t1 : g.getTeams()) {
									for(TeamPlayer tp1 : t1.getPlayers()) {
										Player p1 = Bukkit.getPlayer(tp1.getName());
										if(p1 != null) {
											p1.sendMessage(t.getColor() + p.getName() + ChatColor.WHITE +" has unfrozen " + t.getColor() + tp.getName());
										}
									}
								}
							}
						}
					}
				}
			}
		}

	}

	@Override
	public void onPlace(Player p, Material mat, Action action, Location loc) {}

	@Override
	public void onDamage(Player p, Material mar, Action action, Event event) {}

}
