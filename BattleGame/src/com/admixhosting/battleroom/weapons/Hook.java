package com.admixhosting.battleroom.weapons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.weapon.MethodType;
import com.CodCraft.api.model.weapon.Weapon;
import com.CodCraft.api.modules.GameManager;
import com.admixhosting.battleroom.BattleRoom;
import com.admixhosting.battleroom.game.BattlePlayer;

public class Hook extends Weapon {

	private BattleRoom plugin;

	public Hook(String name, BattleRoom plugin) {
		super(name);
		putAction(Material.NAME_TAG, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.NAME_TAG, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		this.plugin = plugin;
	}

	@Override
	public void giveAmmo(Player p, Material mat, Action action) {}

	@Override
	public void giveWeapon(Player p, Material mat, Action action) {
		ItemStack it = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName("Hook");
		it.setItemMeta(im);
		p.getInventory().addItem(it);
		
	}

	@Override
	public void useWeapon(Player p, Material mat, Action action, Event event) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(p);
		if(g != null) {
			if(g.getPlugin() == plugin) {
				BattlePlayer bp = (BattlePlayer) g.findTeamWithPlayer(p).findPlayer(p);
				if(bp.getOnWall()) {
					plugin.reset(p);
					bp.setOnWall(false);
					bp.setToMove(p.getEyeLocation().getDirection().multiply(1));
					p.playSound(p.getLocation(), Sound.STEP_GRASS, 3.0F, 0.5F);
				}
			}
		}

	}

	@Override
	public void onPlace(Player p, Material mat, Action action, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDamage(Player hurt, Player hurter, Material mar, Action action, Event event) {
		// TODO Auto-generated method stub

	}

}
