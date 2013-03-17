package com.CodCraft.ffa.GM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;


import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.MYSQL;
import com.CodCraft.api.modules.Perks;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.api.services.CCGamePlugin;
import com.CodCraft.api.services.GameState;
import com.CodCraft.ffa.CodCraft;
import com.CodCraft.ffa.MapLoader;
import com.CodCraft.ffa.Users;
import com.CodCraft.ffa.listener.PlayerListener;

public class Round extends Game<CodCraft> {

	public Map<String, ArrayList<Location>> locations = new HashMap<>();
	
	public  String  world1;
	public  String  world2;
	public  ArrayList<World> worlds = new ArrayList<World>();
	public  Random rnd = new Random();
	public  HashMap<Player, Location> PreGameLocation = new HashMap<Player, Location>();
	public  Integer PreGame = 0;
	public  HashMap<String, Integer> PlayerKills = new HashMap<String, Integer>();
	public  HashMap<String, Integer> PlayerDeaths = new HashMap<String, Integer>();
	public  HashMap<String, Integer> PlayerWins = new HashMap<String, Integer>();
	public  HashMap<String, Integer> PlayerLoses = new HashMap<String, Integer>();
	private CodCraft API;
	private GameManager gm;
	private Weapons weap;
	private Perks perk;
	public Game<CodCraft> game;
	private Integer world1votes;
	private Integer world2votes;
	private Integer WinScore;
	
	public World currentmap;


	private List<String> voters = new ArrayList<>(); 
	
	public Round(CodCraft plugin) {
		super(plugin);
		game.addListener(new PlayerListener(plugin));
		
		API = plugin;
		gm = API.api.getModuleForClass(GameManager.class);
		weap = API.api.getModuleForClass(Weapons.class);
		perk = API.api.getModuleForClass(Perks.class);
		setWinScore(75);
	}
	
	
	
	public Integer getWinScore() {
		return WinScore;
	}
	public void setWinScore(Integer winScore) {
		WinScore = winScore;
	}
	public void Lobby() {
		game.initialize();
		for(World w : MapLoader.Maps) {
        	if(!(w.getName().equals("lobby"))) {
        		worlds.add(w);
        	} else {
        	}	
        }

		setWorld1votes(0);
		setWorld2votes(0);
	    world1 = "";
	    world2 = "";	    	    	
	    world1 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    API.getLogger().info(world1);
	    world2 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    API.getLogger().info(world2);
	    while(world1.equals(world2)) {
	    	world2 = worlds.get(rnd.nextInt(worlds.size())).getName();
	    	 API.getLogger().info(world2);
	    	}
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE + "Please vote for the next map:");
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    world1);
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    "or");
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE +    world2);
	    Bukkit.broadcastMessage(ChatColor.DARK_RED +"[CodCraft]" + ChatColor.WHITE + "using /vote <mapName>");
    			
		}
	public  void PreGame() {
		for(String s : player.Whoplaying()) {
			Player p = Bukkit.getPlayer(s);
			player.ClearInventory(p);
			PreGameLocation.put(p, p.getLocation());
	    	weap.GiveKnife(p);
	    	weap.GiveWeapons(p, Users.PlayerGun.get(p), Users.Playerequipment.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p));
		}
		PreGame = 3;
	}
	public  void Games() {
		if(gm.getVotes(world2) > gm.getVotes(world1)) {
			gm.SetCurrentWorld(world2);	
		} else {
			gm.SetCurrentWorld(world1);	
		}
		gm.setGameTimer(600);
		perk.StartMaratonTimer();
		perk.StartLightWightTimer();
		API.api.getModuleForClass(Teleport.class).RespawnAll(gm.GetCurrentWorld());
		
	}
	public  void MainTimer() {
		@SuppressWarnings({ "unused", "deprecation" })
		int TaskID = API.getServer().getScheduler().scheduleAsyncRepeatingTask(API, new Runnable() {		
			@Override
			public void run() {
				//Bukkit.broadcastMessage(CCGameManger.getGameTime() + " " + CCGameManger.getLobbyTime());
				if(gm.getLobbyTime() >= 1) {
					for (String s : player.Whoplaying()) {
						Player p = Bukkit.getPlayer(s);
						p.setLevel(gm.getLobbyTime());
					}
					if(gm.getLobbyTime() == 40) {
						DisplayMessage();
					}
					if(gm.getLobbyTime() == 20) {
						DisplayMessage();
					}
					if(gm.getLobbyTime() == 1) {
						PreGame();
					}
					int i = gm.getLobbyTime();
					i--;
					gm.setLobbyTime(i);
				}
				if(PreGame >= 1) {
					for(String s : player.Whoplaying()) {
						Player p = Bukkit.getPlayer(s);
						p.teleport(PreGameLocation.get(p));
					}
					for (String s : player.Whoplaying()) {
						Player p = Bukkit.getPlayer(s);
						p.setLevel(PreGame);
					}
					if(PreGame == 1) {
						Games();
					}
					PreGame--;

				}
				if(gm.getGameTime() >= 1) {
					gm.setGameTimer(gm.getGameTime() - 1);
					
					for (String s : player.Whoplaying()) {
						Player p = Bukkit.getPlayer(s);
						p.setLevel(gm.getGameTime());
					}
					if(gm.getGameTime() == 1) {
						gm.TimeDetectWin();
					}
				}
			}

			
		}, 20L, 20L);
	}

public void Savedata() {
		
		for(String s : player.Whoplaying()) {
			Player p = Bukkit.getPlayer(s);
			
			Integer i = PlayerKills.get(p.getName())+player.getKills(p);
			Integer o = PlayerDeaths.get(p.getName())+player.getDeaths(p);
			
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
        		perk.addMarathon(p);
        	} else {
        		perk.removeMarathon(p);
        	}
    		if(Users.PlayerPerk2.get(p).equalsIgnoreCase("LightWeight")) {
        		perk.AddLightuser(p);
        	} else {
        		perk.RemoveLightuser(p);
        	}
		}
	}
	protected  void DisplayMessage() {
		Bukkit.broadcastMessage(gm.DisplayVotes(world1));
		Bukkit.broadcastMessage(gm.DisplayVotes(world2));
	}

	public Integer getWorld1votes() {
		return world1votes;
	}

	public void setWorld1votes(Integer world1votes) {
		this.world1votes = world1votes;
	}
	public Integer getWorld2votes() {
		return world2votes;
	}

	public void setWorld2votes(Integer world2votes) {
		this.world2votes = world2votes;
	}
	public void addvoter(Player p) {
		voters.add(p.getName());
	}
	public void remotevoter(Player p) {
		voters.remove(p.getName());
	}
	
	public boolean hasvoted(Player p) {
		if(voters.contains(p.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public void preStateSwitch(GameState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState mode) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void preStateSwitch(GameState<CodCraft> state) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void postStateSwitch(GameState<CodCraft> state) {
		// TODO Auto-generated method stub
		
	}
}
