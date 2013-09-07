package com.codcraft.weapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.weapon.MethodType;
import com.CodCraft.api.model.weapon.Weapon;
import com.CodCraft.api.modules.GameManager;

public class C4Test extends Weapon {
	
	private Map<Location, String> C4spots = new HashMap<Location, String>();
	private Weapons plugin; 

	public C4Test(String name, Weapons plugin) {
		super(name);
		this.plugin = plugin;
		addGame(plugin.ffa);
		addGame(plugin.TDM);
		addGame(plugin.Infected);
		putAction(Material.STICK, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.STICK, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.STICK, Action.RIGHT_CLICK_BLOCK, MethodType.ON_PLACE);
	}

	@Override
	public void giveAmmo(Player p, Material mat, Action action) {
		p.getInventory().addItem(new ItemStack(Material.LEVER, 1));
	}

	@Override
	public void giveWeapon(Player p, Material mat, Action action) {}

	@Override
	public void useWeapon(Player p, Material mat, Action action) {
		if(mat == Material.STICK) {
			for(Entry<Location, String> en : C4spots.entrySet()) {
				if(en.getValue().equalsIgnoreCase(p.getName())) {
					for(Entity ent : getNearbyEntities(en.getKey(), 3)) {
						if(ent instanceof Player) {
							expotiondetect(p, (Player) ent);
						}
					}
				}
			}
		}
	}

	@Override
	public void onPlace(Player p, Material mat, Action action, Location loc) {
		if(mat == Material.LEVER) {
			C4spots.put(loc, p.getName());
		}
	}
	
	public List<Entity> getNearbyEntities(Location where, int range) {
		List<Entity> found = new ArrayList<Entity>();
		for(Entity entity : where.getWorld().getEntities()) {
			if(isInBorder(where, entity.getLocation(), range)) {
	            found.add(entity);
	         }
	    }
	    return found;
	}

	public boolean isInBorder(Location center, Location notCenter, int range) {
		int x = center.getBlockX(), z = center.getBlockZ();
	    int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();

	    if(x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
	    	return false;
	    }
	    return true;
	}
	   
	   
	public boolean expotiondetect(Player p, Player k) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> game = gm.getGameWithPlayer(p);
		if(game == null) {
			return false;
		}
		Team p1 = game.findTeamWithPlayer(p);
		Team k1 = game.findTeamWithPlayer(k);
		if (p == k){
			p.damage(20D);
			return true;
		} else if(p1.getName().equalsIgnoreCase(k1.getName()))  {
			return false;
		} else {
			k.damage(20D, p);
			return true;
		}
	}
}
