package com.codcraft.CCSG;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class SurvialGames extends CCGamePlugin {
	
	public CCAPI api;
	public CCSGChest sgc;
	
	public Map<String, MAP> maps = new HashMap<String, MAP>();
	
	public void onEnable() {
		sgc = new CCSGChest();
		final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	      if(api != null || !(api instanceof CCAPI)) {  
	         this.api = (CCAPI) api;
	      } else {
	       // Disable if we cannot get the api.
	       getLogger().warning("Could not find API. Disabling...");
	       getServer().getPluginManager().disablePlugin(this);
	       return;
	    }
	    getServer().getPluginManager().registerEvents(new GameListner(this), this);
		loadconfig();
	}
	
	
	
	private void loadconfig() {
		File f = new File("./plugins/sg/config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		
		for(String map : config.getConfigurationSection("maps").getKeys(false)) {
			getLogger().info(map);
			MAP mapmap = new MAP();
			for(String chest : config.getConfigurationSection("maps."+map+".chests").getKeys(false)) {
				getLogger().info(chest);
				Location loc = new Location(Bukkit.getWorld("world"), 0, 0, 0);
				getLogger().info(config.getString("maps."+map+".chests."+chest+".x"));
				loc.setX(Integer.parseInt(config.getString("maps."+map+".chests."+chest+".x")));
				loc.setY(Integer.parseInt(config.getString("maps."+map+".chests."+chest+".y")));
				loc.setZ(Integer.parseInt(config.getString("maps."+map+".chests."+chest+".z")));
				int i = Integer.parseInt(config.getString("maps."+map+".chests."+chest+".p"));
				mapmap.chests.put(loc, i);
			}
			for(String spawn : config.getConfigurationSection("maps."+map+".spawn").getKeys(false)) {
				Location loc = new Location(Bukkit.getWorld("world"), 0, 0, 0);
				loc.setX(Integer.parseInt(config.getString("maps."+map+".spawn."+spawn+".x")));
				loc.setY(Integer.parseInt(config.getString("maps."+map+".spawn."+spawn+".y")));
				loc.setZ(Integer.parseInt(config.getString("maps."+map+".spawn."+spawn+".z")));
				mapmap.spawnblocks.put(loc, null);
			}
			maps.put(map, mapmap);
		}
		
	}



	@Override
	public String getTag() {
		return "[CCSG]";
	}

	@Override
	public void makegame(String name) {
		System.out.println(name);
		CCSGGame game = new CCSGGame(this);
		game.setName(name);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}
	
	/*public void timer() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				for(Entry<String, GameModel> map : games.entrySet()) {
					CCSGGame g = (CCSGGame) api.getModuleForClass(GameManager.class).getGameWithId(map.getKey());
					if(map.getValue().mode == GameState.LOBBY){
						if(map.getValue().timeleft >= 1) {
							games.get(map.getKey()).timeleft = map.getValue().timeleft - 1;
						}
						if(map.getValue().timeleft <= 0) {
							String s = null;
							int i = 0;
							for(Entry<String, Integer> votes : g.votes.entrySet()) {
								if(votes.getValue() >= i) {
									s = votes.getKey();
									i = votes.getValue();
								}
							}
							if(s == null) {
								map.getValue().timeleft = 30;
							} else {
								map.getValue().mode = GameState.PREGAME;
								map.getValue().timeleft = 30;
								int ii = 0;
								MAP map1 = maps.get(s);
								for(Team t : g.getTeams()) {
									for(TeamPlayer tp : t.getPlayers()) {
										Player p = Bukkit.getPlayer(tp.getName());
										p.teleport(map1.spawns.get(ii));
										ii++;
									}
								}
								for(Entry<Location, Integer> loc : map1.chests.entrySet()) {
									if(loc.getKey().getBlock() instanceof Chest) {
										Chest chest = (Chest) loc.getKey().getBlock();
										SetChestContents(chest, g, loc.getValue());
									}
								}
							}
							
						}
					} else if(map.getValue().mode == GameState.PREGAME) {
						if(map.getValue().timeleft >= 1) {
							map.getValue().timeleft = map.getValue().timeleft--;
						} else {
							map.getValue().mode = GameState.INGAME;
						}
					} 	
				}
			}
		}, 20, 20);
		
	}*/
	public void SetChestContents(Chest chest, Game<?> g, Integer integer) {
		World world = Bukkit.getWorld(g.getId());
		Location loc = new Location(world, chest.getLocation().getX(), chest.getLocation().getY(), chest.getLocation().getZ());
		if(loc.getBlock() instanceof Chest) {
			Chest c = (Chest) loc.getBlock();
			Random rnd = new Random();
			int i = 0;
			if(integer == 1) {
				while(i <= 27) {
					i = i + rnd.nextInt(5) + 2;
					Material mat = sgc.level1.get(rnd.nextInt(sgc.level1.size()-1));
					c.getBlockInventory().setItem(i, new ItemStack(mat));
				}
			} else if(integer == 2) {
				while(i <= 27) {
					i = i + rnd.nextInt(4) + 6;
					Material mat = sgc.level2.get(rnd.nextInt(sgc.level2.size()-1));
					c.getBlockInventory().setItem(i, new ItemStack(mat));
				}
			} else if(integer == 3) {
				while(i <= 27) {
					i = i + rnd.nextInt(2) + 6;
					Material mat = sgc.level3.get(rnd.nextInt(sgc.level3.size()-1));
					c.getBlockInventory().setItem(i, new ItemStack(mat));
				}
			} else if(integer == 4) {
				while(i <= 27) {
					i = i + rnd.nextInt(1) + 8;
					Material mat = sgc.level4.get(rnd.nextInt(sgc.level4.size()-1));
					c.getBlockInventory().setItem(i, new ItemStack(mat));
				}
			}
		}
		
	}

}
