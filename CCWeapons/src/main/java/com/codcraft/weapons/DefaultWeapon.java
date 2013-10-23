package com.codcraft.weapons;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.weapon.Gun;
import com.CodCraft.api.model.weapon.Mag;
import com.CodCraft.api.model.weapon.MethodType;
import com.CodCraft.api.model.weapon.ShotType;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.api.services.CCGamePlugin;

public class DefaultWeapon extends Gun {

	private double damage;

	public DefaultWeapon(String name, Weapons plugin, Material mat, long rpm, int speed, int damage, Mag mag, Long blife, List<CCGamePlugin> games, Integer reloadtime, Integer reloads) {
		super(name, plugin);
		setsType(ShotType.HOLD);
		setRps(rpm);
		setMat(mat);
		this.setSpeed(speed);
		this.damage = damage;
		setMag(mag);
		setButtetLife(blife);
		setReloadtime(reloadtime);
		setReloads(reloads);
		for(CCGamePlugin game : games) {
			addGame(game);
		}
		putAction(mat, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(mat, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		
	}

	@Override
	public void giveAmmo(Player p, Material mat, Action action) {
		ItemStack is = new ItemStack(getMag().getItem(), getMag().getAmount());
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Mag");
		is.setItemMeta(im);
		p.getInventory().addItem(is);
		p.getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));
		
	}

	@Override
	public void giveWeapon(Player p, Material mat, Action action) {
		ItemStack is = new ItemStack(getMat(), 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(getName());
		is.setItemMeta(im);
		p.getInventory().addItem(is);
	}

	@Override
	public void onPlace(Player p, Material mat, Action action, Location loc) {}

	@Override
	public void onDamage(Player hurt, Player hurter, Material mar, Action action, Event event) {
		((EntityDamageByEntityEvent)event).setDamage(damage);
		
	}

}
