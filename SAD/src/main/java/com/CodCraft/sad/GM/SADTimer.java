package com.CodCraft.sad.GM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.Teams;
import com.CodCraft.sad.CodCraft;
import com.CodCraft.sad.MapLoader;

public class SADTimer implements Runnable {
	public SADTimer(CodCraft plguin, int delay) {
		this.plugin = plguin;
		this.delay = delay;
		this.gm = plugin.getApi().getModuleForClass(GameManager.class);
		this.t = plugin.getApi().getModuleForClass(Teams.class);
	}
	private CodCraft plugin;
	private Integer delay;
	private GameManager gm; 
	   
	   private Teams t; 


	@Override
	public void run() {
		if(SAD.bombholder.get(gm.GetCurrentWorld()).equalsIgnoreCase("none")) {
			if(Bukkit.getOnlinePlayers().length > 0) {
				for (Player p : getNearbyEntities(SAD.bomb.get(gm.GetCurrentWorld()), 2)){
					if(SAD.bombholder.get(gm.GetCurrentWorld()).equalsIgnoreCase("none")) {
						SAD.bombholder.remove(gm.GetCurrentWorld());
						SAD.bombholder.put(gm.GetCurrentWorld(), p.getName());
						SAD.bomb.get(gm.GetCurrentWorld()).getBlock().setType(Material.AIR);
					}

				}
			}
		}
		if(SAD.bombholder.get(gm.GetCurrentWorld()).equalsIgnoreCase("droped")) {
			if(Bukkit.getOnlinePlayers().length > 0) {
				for (Player p : getNearbyEntities(SAD.bombdroped, 2)){
					if(SAD.bombholder.get(gm.GetCurrentWorld()).equalsIgnoreCase("droped")) {
						SAD.bombholder.remove(gm.GetCurrentWorld());
						SAD.bombholder.put(gm.GetCurrentWorld(), p.getName());
						SAD.bomb.get(gm.GetCurrentWorld()).getBlock().setType(Material.AIR);
					}

				}
			}
		}
		plugin.getApi().getModuleForClass(GUI.class).newupdatePlayerList();
		for (Entry<World, ArrayList<Location>> w : MapLoader.Blocations.entrySet()) {
			Location loc1 = w.getValue().get(0);
			Location loc2 = w.getValue().get(1);
			for (Player p : getNearbyEntities(loc1, 2)) {
				if(SAD.bombholder.get(gm.GetCurrentWorld()).equalsIgnoreCase(p.getName())&&t.getTeam(p) == plugin.getGame().O&& !(SAD.Bomb1)) {
					if(SAD.Planters.get(1).size() >= 1) {
						break;
					} 
					if(!SAD.Planters.get(1).containsKey(p.getName())) {
						SAD.Planters.get(1).put(p.getName(), 5);
					}
				}
				if(!(t.getTeam(p) == plugin.getGame().O)&& (SAD.Bomb1)) {
					if(SAD.Defuseers.get(1).size() >= 1) {
						break;
					} 
					if(!SAD.Defuseers.get(1).containsKey(p.getName())) {
						SAD.Defuseers.get(1).put(p.getName(), 5);
					}
				}
				

			}
			for (Player p : getNearbyEntities(loc2, 2)) {
				if(SAD.bombholder.get(gm.GetCurrentWorld()).equalsIgnoreCase(p.getName())&&t.getTeam(p) == plugin.getGame().O && !(SAD.Bomb2)) {
					if(SAD.Planters.get(2).size() >= 1) {
						break;
					} 
					if(!SAD.Planters.get(2).containsKey(p.getName())) {
						SAD.Planters.get(2).put(p.getName(), 5);
					}
				}
				if(!(t.getTeam(p) == plugin.getGame().O)&& (SAD.Bomb2)) {
					if(SAD.Defuseers.get(2).size() >= 1) {
						break;
					} 
					if(!SAD.Defuseers.get(2).containsKey(p.getName())) {
						SAD.Defuseers.get(2).put(p.getName(), 5);
					}
				}
			}
			
		}
		
		for (Entry<String, Integer> s : SAD.Planters.get(1).entrySet()) {
			SAD.Planters.get(1).put(s.getKey(), s.getValue() -1);
			Player p = Bukkit.getPlayer(s.getKey());
			Boolean b = false;
			for (Entry<World, ArrayList<Location>> w : MapLoader.Blocations.entrySet()) {
				Location loc1 = w.getValue().get(0);
				Location loc2 = w.getValue().get(1);
				for(Player p1 : getNearbyEntities(loc1, 2)) {
					if(p == p1) {
						b= true;
					}
				}
				for(Player p1 : getNearbyEntities(loc2, 2)) {
					if(p == p1) {
						b= true;
					}
				}
			}
			if(!b) {
				SAD.Planters.get(1).remove(s.getKey());
			}
		
			if(s.getValue() == 0) {
				SAD.Planters.get(1).remove(s.getKey());
				SAD.Bomb1 =true;
				SAD.Bomb1Timer = 60;
			}
		}
		for (Entry<String, Integer> s : SAD.Planters.get(2).entrySet()) {
			SAD.Planters.get(2).put(s.getKey(), s.getValue() -1);
			Player p = Bukkit.getPlayer(s.getKey());
			Boolean b = false;
			for (Entry<World, ArrayList<Location>> w : MapLoader.Blocations.entrySet()) {
				Location loc1 = w.getValue().get(0);
				Location loc2 = w.getValue().get(1);
				for(Player p1 : getNearbyEntities(loc1, 2)) {
					if(p == p1) {
						b= true;
					}
				}
				for(Player p1 : getNearbyEntities(loc2, 2)) {
					if(p == p1) {
						b= true;
					}
				}
			}
			if(!b) {
				SAD.Planters.get(2).remove(s.getKey());
			}
		
			if(s.getValue() == 0) {
				SAD.Planters.get(2).remove(s.getKey());
				SAD.Bomb2 =true;
				SAD.Bomb2Timer = 60;
			}
		}
		for (Entry<String, Integer> s : SAD.Defuseers.get(1).entrySet()) {
			SAD.Defuseers.get(1).put(s.getKey(), s.getValue() -1);
			Player p = Bukkit.getPlayer(s.getKey());
			Boolean b = false;
			for (Entry<World, ArrayList<Location>> w : MapLoader.Blocations.entrySet()) {
				Location loc1 = w.getValue().get(0);
				Location loc2 = w.getValue().get(1);
				for(Player p1 : getNearbyEntities(loc1, 2)) {
					if(p == p1) {
						b= true;
					}
				}
				for(Player p1 : getNearbyEntities(loc2, 2)) {
					if(p == p1) {
						b= true;
					}
				}
			}
			if(!b) {
				SAD.Defuseers.get(1).remove(s.getKey());
			}
			if(s.getValue() == 0) {
				Game.RoundWinReason = "Time";
				gm.Win(0);
				SAD.Bomb1 =false;
				SAD.Defuseers.get(1).remove(s.getKey());
				SAD.Bomb1Timer = 0;
			}
		}
		for (Entry<String, Integer> s : SAD.Defuseers.get(2).entrySet()) {
			SAD.Defuseers.get(2).put(s.getKey(), s.getValue() -1);
			Player p = Bukkit.getPlayer(s.getKey());
			Boolean b = false;
			for (Entry<World, ArrayList<Location>> w : MapLoader.Blocations.entrySet()) {
				Location loc1 = w.getValue().get(0);
				Location loc2 = w.getValue().get(1);
				for(Player p1 : getNearbyEntities(loc1, 2)) {
					if(p == p1) {
						b= true;
					}
				}
				for(Player p1 : getNearbyEntities(loc2, 2)) {
					if(p == p1) {
						b= true;
					}
				}
			}
			if(!b) {
				SAD.Defuseers.get(2).remove(s.getKey());
			}
			if(s.getValue() == 0) {
				Game.RoundWinReason = "Time";
				gm.Win(0);
				SAD.Bomb2 =false;
				SAD.Defuseers.get(2).remove(s.getKey());
				SAD.Bomb2Timer = 0;
			}
		}
		Bukkit.getScheduler().runTaskLater(plugin, new SADTimer(plugin, delay), delay);
		
	}
	public List<Player> getNearbyEntities(Location where, int range) {
		List<Player> found = new ArrayList<Player>();
		if(where.getWorld().getPlayers().size() > 0) {
			for(Player entity : where.getWorld().getPlayers()) {
				if(isInBorder(where, entity.getLocation(), range)) {
					found.add(entity);
				}
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

}
