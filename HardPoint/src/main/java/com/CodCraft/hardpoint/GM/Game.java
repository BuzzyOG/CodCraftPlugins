package com.CodCraft.hardpoint.GM;

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
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.MYSQL;
import com.CodCraft.api.modules.Perks;
import com.CodCraft.api.modules.TeamPlayer;
import com.CodCraft.api.modules.Teleport;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.hardpoint.CodCraft;
import com.CodCraft.hardpoint.MapLoader;
import com.CodCraft.hardpoint.Users;

public class Game {
   private CodCraft plugin;
   private Hardpoint hardpoint;
   public String world1;
   public String world2;
   public List<World> worlds = new ArrayList<>();
   private static final Random RND = new Random();
   public Map<String, Location> PreGameLocation = new HashMap<>();
   public Integer PreGame = 0;
   public Integer Hardpointtime = 0;
   private final Map<String, Integer> playerKills = new HashMap<>();
   private final Map<String, Integer> playerDeaths = new HashMap<>();
   private final Map<String, Integer> playerWins = new HashMap<>();
   private final Map<String, Integer> playerLosses = new HashMap<>();
   private GameManager gm;
   private Weapons weap;
   private TeamPlayer player;
   private Perks perk;
   public Game(Plugin plugin) {
	  gm = this.plugin.api.getModuleForClass(GameManager.class);
	   weap = this.plugin.api.getModuleForClass(Weapons.class);
	   player = this.plugin.api.getModuleForClass(TeamPlayer.class);
	   perk = this.plugin.api.getModuleForClass(Perks.class);
   }


   public void setHardpoint(Hardpoint hardpoint) {
      this.hardpoint = hardpoint;
   }

   public void setPlugin(CodCraft plugin) {
      this.plugin = plugin;
   }

   public void Lobby() {
	   gm.setLobbyTime(60);
      for(World w : MapLoader.Maps) {
         if(!(w.getName().equals("lobby"))) {
            worlds.add(w);
         } else {
         }
      }
      gm.RemoveVotes();
      gm.RemoveVoters();
      world1 = "";
      world2 = "";
      world1 = worlds.get(RND.nextInt(worlds.size())).getName();
      while(world1.equalsIgnoreCase("CreateAClass")) {

         world1 = worlds.get(RND.nextInt(worlds.size())).getName();
      }
      world2 = worlds.get(RND.nextInt(worlds.size())).getName();
      while(world2.equalsIgnoreCase("CreateAClass")) {
         world2 = worlds.get(RND.nextInt(worlds.size())).getName();
      }
      if(world1.equalsIgnoreCase("CreateAClass")) {
         while(world1.equalsIgnoreCase("CreateAClass")) {

            world1 = worlds.get(RND.nextInt(worlds.size())).getName();
         }
      }
      if(world2.equalsIgnoreCase("CreateAClass")) {
         while(world2.equalsIgnoreCase("CreateAClass")) {
            world2 = worlds.get(RND.nextInt(worlds.size())).getName();
         }
      }
      while(world1.equals(world2)) {
         world2 = worlds.get(RND.nextInt(worlds.size())).getName();
      }
      Bukkit.broadcastMessage(ChatColor.DARK_RED + "[CodCraft]" + ChatColor.WHITE + "Please vote for the next map:");
      Bukkit.broadcastMessage(ChatColor.DARK_RED + "[CodCraft]" + ChatColor.WHITE + world1);
      Bukkit.broadcastMessage(ChatColor.DARK_RED + "[CodCraft]" + ChatColor.WHITE + "or");
      Bukkit.broadcastMessage(ChatColor.DARK_RED + "[CodCraft]" + ChatColor.WHITE + world2);
      Bukkit.broadcastMessage(ChatColor.DARK_RED + "[CodCraft]" + ChatColor.WHITE + "using /vote <mapName>");

      gm.setVotes(0, world1);
      gm.setVotes(0, world2);

   }

   public void PreGame() {
      for(String s : player.Whoplaying()) {
         Player p = Bukkit.getPlayer(s);
         player.ClearInventory(p);
         PreGameLocation.put(p.getName(), p.getLocation());
         weap.GiveKnife(p);
         weap.GiveWeapons(p, Users.PlayerGun.get(p), Users.Playerequipment.get(p), Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p),
               Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p));
      }
      PreGame = 3;
   }

   public void Games() {
      if(gm.getVotes(world2) > gm.getVotes(world1)) {
         gm.SetCurrentWorld(world2);
      } else {
         gm.SetCurrentWorld(world1);
      }
      gm.setGameTimer(600);
      hardpoint.firstLocation();
      Hardpointtime = 75;
      Random rnd = new Random();
      int i = rnd.nextInt(hardpoint.hardpointspots.get(gm.GetCurrentWorld()).size());
      ArrayList<Location> ii = hardpoint.hardpointspots.get(gm.GetCurrentWorld()).get(i);
      hardpoint.currentlocation = ii;
      perk.StartMaratonTimer();
      perk.StartLightWightTimer();
      plugin.api.getModuleForClass(Teleport.class).RespawnAll(gm.GetCurrentWorld());

   }

   public void MainTimer() {
      @SuppressWarnings({ "unused", "deprecation" })
      int TaskID = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
         @Override
         public void run() {
            // Bukkit.broadcastMessage(plugin.api.getGm().getGameTime() + " " +
            // plugin.api.getGm().getLobbyTime());
            if(gm.getLobbyTime() >= 1) {
               for(String s : player.Whoplaying()) {
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
               for(String s : player.Whoplaying()) {
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
               for(String s : player.Whoplaying()) {
                  Player p = Bukkit.getPlayer(s);
                  p.setLevel(gm.getGameTime());
               }
               if(gm.getGameTime() == 1) {
                  gm.TimeDetectWin();

               }
               // Hardpoint code!
               if(Hardpointtime >= 1) {
                  float fl = (float) Hardpointtime / 75;
                  for(String s : player.Whoplaying()) {
                     Player p = Bukkit.getPlayer(s);
                     p.setExp(fl);
                  }
                  if(Hardpointtime == 1) {
                     hardpoint.nextLocation();
                     Hardpointtime = 75;
                  }
                  Hardpointtime--;
               }
               // Hardpoint end!
            }
         }

      }, 20L, 20L);
   }

   /*
    * private static void TimeLimitDetectwin() { if(GlobalKills.get(1) == null)
    * { GlobalKills.put(1, 0); } if(GlobalKills.get(2) == null) {
    * GlobalKills.put(2, 0); } if(GlobalKills.get(1) > GlobalKills.get(2)) {
    * for(String s : CCTeams.getTeams(1)) { int i = PlayerWins.get(s); i++;
    * PlayerWins.put(s, i); } for(String s : CCTeams.getTeams(2)) { int i =
    * PlayerLoses.get(s); i++; PlayerLoses.put(s, i); } plugin.api.getGm().endRound();
    * Savedata();
    * 
    * } else { for(String s : CCTeams.getTeams(2)) { int i = PlayerWins.get(s);
    * i++; PlayerWins.put(s, i); } for(String s : CCTeams.getTeams(1)) { int i =
    * PlayerLoses.get(s); i++; PlayerLoses.put(s, i); } plugin.api.getGm().endRound();
    * Savedata(); } }
    */
   public void Savedata() {

      for(String s : player.Whoplaying()) {
         Player p = Bukkit.getPlayer(s);

         Integer i = playerKills.get(p.getName()) + player.getKills(p);
         Integer o = playerDeaths.get(p.getName()) + player.getDeaths(p);

         MYSQL.SaveEveryOneData(p, i, o, playerWins.get(p.getName()), playerLosses.get(p.getName()), Users.PlayerGun.get(p), "null",
               Users.PlayerPerk1.get(p), Users.PlayerPerk2.get(p), Users.PlayerPerk3.get(p), Users.PlayerKIllStreaks.get(p),
               Users.Playerequipment.get(p));
         @SuppressWarnings("unchecked")
         HashMap<String, String> hm = (HashMap<String, String>) MYSQL.SinglePlayerALLData(p).clone();
         playerKills.put(p.getName(), Integer.parseInt(hm.get("PlayerKills")));
         playerDeaths.put(p.getName(), Integer.parseInt(hm.get("PlayerDeaths")));
         playerWins.put(p.getName(), Integer.parseInt(hm.get("PlayerWins")));
         playerLosses.put(p.getName(), Integer.parseInt(hm.get("Playerlosses")));
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

   protected void DisplayMessage() {
      Bukkit.broadcastMessage(gm.DisplayVotes(world1));
      Bukkit.broadcastMessage(gm.DisplayVotes(world2));
   }

   public Map<String, Integer> getPlayerKills() {
      return playerKills;
   }

   public Map<String, Integer> getPlayerDeaths() {
      return playerDeaths;
   }

   public Map<String, Integer> getPlayerWins() {
      return playerWins;
   }

   public Map<String, Integer> getPlayerLosses() {
      return playerLosses;
   }

}
