package com.CodCraft.infected.GM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.MYSQL;
import com.CodCraft.infected.CodCraft;
import com.CodCraft.infected.MapLoader;
import com.CodCraft.infected.Users;
import com.CodCraft.infected.api.GameState.Gamemodes;

public class Game {
	private CodCraft plugin;
	public static  String  world1;
	public static String  world2;
	public  ArrayList<World> worlds = new ArrayList<World>();
	public  Random rnd = new Random();
	public  HashMap<Player, Location> PreGameLocation = new HashMap<Player, Location>();
	public  Integer PreGame = 0;
	public  HashMap<String, Integer> PlayerKills = new HashMap<String, Integer>();
	public  HashMap<String, Integer> PlayerDeaths = new HashMap<String, Integer>();
	public  HashMap<String, Integer> PlayerWins = new HashMap<String, Integer>();
	public  HashMap<String, Integer> PlayerLoses = new HashMap<String, Integer>();
	
	public Game(CodCraft plugin) {
		this.plugin = plugin;
	}

	public  void Lobby() {
		plugin.getApi().getGameManager().setLobbyTime(60);
		for(World w : MapLoader.Maps) {
        	if(!(w.getName().equals("lobby"))) {
        		worlds.add(w);
        	} else {
        	}	
        }
		plugin.getApi().getGameManager().RemoveVotes();
	    plugin.getApi().getGameManager().RemoveVoters();
	    world1 = "";
	    world2 = "";	    	    	
	    world1 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    while (world1.equalsIgnoreCase("CreateAClass")) {
	    	
	    	world1 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    }
	    world2 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    while (world2.equalsIgnoreCase("CreateAClass")) {
	    	world2 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    	}
	    if(world1.equalsIgnoreCase("CreateAClass")) {
	    	while (world1.equalsIgnoreCase("CreateAClass")) {
	    		
	    		world1 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    		}
	    	}
	    if(world2.equalsIgnoreCase("CreateAClass")) {
	    	while (world2.equalsIgnoreCase("CreateAClass")) {
	    		world2 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    		}
	    	}
	    while(world1.equals(world2)) {
	    	world2 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    	}
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE + "Please vote for the next map:");
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    world1);
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    "or");
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    world2);
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE + "using /vote <mapName>");

	    plugin.getApi().getGameManager().setVotes(0, world1);
	    plugin.getApi().getGameManager().setVotes(0, world2);
    			
		}
	public  void PreGame() {
		for(String s : plugin.getApi().getPlayers().Whoplaying()) {
			Player p = Bukkit.getPlayer(s);
			plugin.getApi().getPlayers().ClearInventory(p);
			PreGameLocation.put(p, p.getLocation());
	    	plugin.getApi().getWeapons().GiveKnife(p);
	    	plugin.getApi().getWeapons().GiveWeapons(p, Users.PlayerGun.get(p), Users.Playerequipment.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p));
		}
		PreGame = 3;
	}
	public  void Games() {
		if(plugin.getApi().getGameManager().getVotes(world2) > plugin.getApi().getGameManager().getVotes(world1)) {
			plugin.getApi().getGameManager().SetCurrentWorld(world2);	
		} else {
			plugin.getApi().getGameManager().SetCurrentWorld(world1);	
		}
		plugin.getApi().getGameManager().setGameTimer(600);
		plugin.getApi().getGamemode().setGameMode(Gamemodes.INGAME);
		plugin.getApi().getPerks().StartMaratonTimer();
		plugin.getApi().getPerks().StartLightWightTimer();
		plugin.getApi().getTelport().RespawnAll(plugin.getApi().getGameManager().GetCurrentWorld());
		
	}
	public  void MainTimer() {
		@SuppressWarnings({ "unused", "deprecation" })
		int TaskID = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {		
			@Override
			public void run() {
				//Bukkit.broadcastMessage(plugin.getApi().getGameManager().getGameTime() + " " + plugin.getApi().getGameManager().getLobbyTime());
				if(plugin.getApi().getGameManager().getLobbyTime() >= 1) {
					for (String s : plugin.getApi().getPlayers().Whoplaying()) {
						Player p = Bukkit.getPlayer(s);
						p.setLevel(plugin.getApi().getGameManager().getLobbyTime());
					}
					if(plugin.getApi().getGameManager().getLobbyTime() == 40) {
						DisplayMessage();
					}
					if(plugin.getApi().getGameManager().getLobbyTime() == 20) {
						DisplayMessage();
					}
					if(plugin.getApi().getGameManager().getLobbyTime() == 1) {
						PreGame();
					}
					int i = plugin.getApi().getGameManager().getLobbyTime();
					i--;
					plugin.getApi().getGameManager().setLobbyTime(i);
				}
				if(PreGame >= 1) {
					for(String s : plugin.getApi().getPlayers().Whoplaying()) {
						Player p = Bukkit.getPlayer(s);
						p.teleport(PreGameLocation.get(p));
					}
					for (String s : plugin.getApi().getPlayers().Whoplaying()) {
						Player p = Bukkit.getPlayer(s);
						p.setLevel(PreGame);
					}
					if(PreGame == 1) {
						Games();
					}
					PreGame--;

				}
				if(plugin.getApi().getGameManager().getGameTime() >= 1) {
					plugin.getApi().getGameManager().setGameTimer(plugin.getApi().getGameManager().getGameTime() - 1);
					for (String s : plugin.getApi().getPlayers().Whoplaying()) {
						Player p = Bukkit.getPlayer(s);
						p.setLevel(plugin.getApi().getGameManager().getGameTime());
					}
					if(plugin.getApi().getGameManager().getGameTime() == 1) {
						plugin.getApi().getGameManager().TimeDetectWin();
					}
				}
			}

			
		}, 20L, 20L);
	}
	/*private  void TimeLimitDetectwin() {
		if(GlobalKills.get(1) == null) {
			GlobalKills.put(1, 0);
		}
		if(GlobalKills.get(2) == null) {
			GlobalKills.put(2, 0);
		}
		if(GlobalKills.get(1) > GlobalKills.get(2)) {
			for(String s : CCTeams.getTeams(1)) {
				int i = PlayerWins.get(s);
				i++;
				PlayerWins.put(s, i);
			}
			for(String s : CCTeams.getTeams(2)) {
				int i = PlayerLoses.get(s);
				i++;
				PlayerLoses.put(s, i);
			}
			plugin.getApi().getGameManager().endRound();
			Savedata();

		} else {
			for(String s : CCTeams.getTeams(2)) {
				int i = PlayerWins.get(s);
				i++;
				PlayerWins.put(s, i);
			}
			for(String s : CCTeams.getTeams(1)) {
				int i = PlayerLoses.get(s);
				i++;
				PlayerLoses.put(s, i);
			}
			plugin.getApi().getGameManager().endRound();
			Savedata();
		}		
	}*/
	public  void Savedata() {
		
		for(String s : plugin.getApi().getPlayers().Whoplaying()) {
			Player p = Bukkit.getPlayer(s);
			
			Integer i = PlayerKills.get(p.getName())+plugin.getApi().getPlayers().getKills(p);
			Integer o = PlayerDeaths.get(p.getName())+plugin.getApi().getPlayers().getDeaths(p);
			
			MYSQL.SaveEveryOneData(p, i, o, PlayerWins.get(p.getName()), PlayerLoses.get(p.getName()), Users.PlayerGun.get(p), "null", Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p), Users.Playerequipment.get(p));		
    		@SuppressWarnings("unchecked")
			HashMap<String, String> hm = (HashMap<String, String>) MYSQL.SinglePlayerALLData(p).clone();
    		CodCraft.PlayerKills.put(p, Integer.parseInt(hm.get("PlayerKills")));
    		CodCraft.PlayerDeaths.put(p, Integer.parseInt(hm.get("PlayerDeaths")));
    		PlayerWins.put(p.getName(), Integer.parseInt(hm.get("PlayerWins")));
    		PlayerLoses.put(p.getName(), Integer.parseInt(hm.get("Playerlosses")));
    		Users.PlayerGun.put(p, hm.get("PlayerGun"));
    		Users.PlayerAttactment.put(p, hm.get("PlayerAttactment"));
    		Users.PlayerPerk1.put(p, hm.get("PlayerPerk1"));
    		Users.PlayerPerk2.put(p, hm.get("PlayerPerk2"));
    		Users.PlayerPerk3.put(p, hm.get("PlayerPerk3"));
    		Users.PlayerKIllStreaks.put(p, hm.get("PlayerKIllStreaks"));
    		Users.Playerequipment.put(p, hm.get("Playerequipment"));
    		if(Users.PlayerPerk1.get(p).equalsIgnoreCase("Marathon")) {
    			plugin.getApi().getPerks().addMarathon(p);
        	} else {
        		plugin.getApi().getPerks().removeMarathon(p);
        	}
    		if(Users.PlayerPerk2.get(p).equalsIgnoreCase("LightWeight")) {
        		plugin.getApi().getPerks().AddLightuser(p);
        	} else {
        		plugin.getApi().getPerks().RemoveLightuser(p);
        	}
		}
	}
	protected  void DisplayMessage() {
		Bukkit.broadcastMessage(plugin.getApi().getGameManager().DisplayVotes(world1));
		Bukkit.broadcastMessage(plugin.getApi().getGameManager().DisplayVotes(world2));
	}

}
