package com.codcraft.SuperAmazingJobyGameModeProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;

public class Timers {
	
	private SAJGMP plugin;
	
	public Timers(SAJGMP plugin) {
		this.plugin = plugin;
	}

	
	
	
	
	public void GunTimer() {
		
		final List<ItemStack> weapons = new ArrayList<>();
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowmeta = bow.getItemMeta();
		bowmeta.setDisplayName("Bow");
		bow.setItemMeta(bowmeta);
		weapons.add(bow);
		
		ItemStack crossbow = new ItemStack(Material.BOW);
		ItemMeta crossbowmeta = crossbow.getItemMeta();
		crossbowmeta.setDisplayName("CrossBow");
		crossbow.setItemMeta(crossbowmeta);
		weapons.add(crossbow);
		
		ItemStack FireCarge = new ItemStack(Material.FIREBALL);
		ItemMeta FireCargemeta = FireCarge.getItemMeta();
		FireCargemeta.setDisplayName("FireCarge");
		FireCarge.setItemMeta(FireCargemeta);
		weapons.add(FireCarge);
		
		ItemStack SnowBall = new ItemStack(Material.SNOW_BALL);
		ItemMeta SnowBallmeta = SnowBall.getItemMeta();
		
		SnowBallmeta.setDisplayName("SnowBall");
		SnowBall.setItemMeta(SnowBallmeta);
		weapons.add(SnowBall);
		
		Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(Entry<String, GameModel> s : plugin.games.entrySet()) {
					String currentmap = s.getValue().map;
					Location locold = plugin.mainpoint.get(currentmap);
					Location locnew = new Location(Bukkit.getWorld(s.getValue().getName()), locold.getX(), locold.getY(), locold.getZ());
					Random rnd = new Random();
					int x = rnd.nextInt(50);
					int y = rnd.nextInt(50);
					int z = rnd.nextInt(50);
					int x2 = rnd.nextInt(2);
					int y2 = rnd.nextInt(2);
					int z2 = rnd.nextInt(2);
					if (x2 == 1) {
						x = x * -1;
					}
					if(y2 == 1) {
						y = y * -1;
					}
					if (z2 == 1) {
						z = z * -1;
					}
					x = (int) (locnew.getX() + x);
					z = (int) (locnew.getZ() + z);
					y = (int) (locnew.getY() + y);
					Location loc2 = new Location(locnew.getWorld(), x, y, z);
					Location finalloc = new Location(loc2.getWorld(), x, getY(loc2), z);
					ItemStack i = weapons.get(rnd.nextInt(weapons.size()));
					List<TeamPlayer> teamPlayer = new ArrayList<>();
					for(Team t : plugin.api.getModuleForClass(GameManager.class).getGameWithId(s.getValue().getId()).getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							teamPlayer.add(tp);
						}
					}
					if(teamPlayer.size() == 0) {
						
					} else {
						final Item f = finalloc.getWorld().dropItem(finalloc, i);
						plugin.getLogger().info(""+f.getEntityId()+" has created!");
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							
							@Override
							public void run() {
								if(!f.getLocation().getChunk().isLoaded()) {
									f.getLocation().getChunk().load();
									f.remove();
									plugin.getLogger().info(""+f.getEntityId()+" has tryed to be removed!");
									f.getLocation().getChunk().unload();
								} else {
									f.remove();
									plugin.getLogger().info(""+f.getEntityId()+" has tryed to be removed!");
								}
								
							}
						}, 500);
					}
					
					
				}
			}
		}, 1, 20);
	}
	
	
	public int getY(Location loc) {
		int y = 0;
		while (loc.getBlock().getType() != Material.AIR) {
			y = (int) loc.getY();
			y++;
			loc.setY(y);
		}
		return y;
		
	}
	
}
