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

public class SaveYourSkin extends Weapon {

	private BattleRoom plugin;

	public SaveYourSkin(String name, BattleRoom plugin) {
		super(name);
		putAction(Material.BUCKET, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.BUCKET, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		addGame(plugin);
		this.plugin = plugin;
	}

	@Override
	public void giveAmmo(Player p, Material mat, Action action) {}

	@Override
	public void giveWeapon(Player p, Material mat, Action action) {
		if(p.hasPermission("battleroom.SaveYourSkin")) {
			ItemStack it = new ItemStack(Material.BUCKET, 1);
			ItemMeta im = it.getItemMeta();
			im.setDisplayName("Save Your Skin");
			it.setItemMeta(im);
			p.getInventory().addItem(it);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void useWeapon(Player p, Material mat, Action action, Event event) {
		if(event instanceof PlayerInteractEvent) {

			Game<?> g = plugin.api.getModuleForClass(GameManager.class).getGameWithPlayer(p);
			if(g != null) {
				if(g.getPlugin() == plugin) {
					BattlePlayer bp = (BattlePlayer) g.findTeamWithPlayer(p).findPlayer(p);
					if(bp.getFrozen() && !bp.isPermfrozen()) {
						p.getInventory().remove(Material.BUCKET);
						bp.setFrozen(false);
						for(Team t1 : g.getTeams()) {
							for(TeamPlayer tp1 : t1.getPlayers()) {
								Player p1 = Bukkit.getPlayer(tp1.getName());
								if(p1 != null) {
									p1.sendMessage(g.findTeamWithPlayer(p).getColor() + p.getName() + ChatColor.WHITE +" has unfrozen " + g.findTeamWithPlayer(p).getColor() + bp.getName());
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
	public void onDamage(Player hurt, Player hurter, Material mar, Action action, Event event) {}

}
