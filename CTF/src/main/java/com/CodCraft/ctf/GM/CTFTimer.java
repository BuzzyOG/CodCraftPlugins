package com.CodCraft.ctf.GM;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.Teams;
import com.CodCraft.api.modules.Teleport;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.ctf.CodCraft;
import com.CodCraft.ctf.MapLoader;

public class CTFTimer implements Runnable {
   private CodCraft plugin;
   private long delay;

   public CTFTimer(CodCraft plugin, long l) {
      this.plugin = plugin;
      this.delay = l;
   }

   @Override
   public void run() {
	   Weapons weap = plugin.getApi().getModuleForClass(Weapons.class);
	   GameManager gm = plugin.getApi().getModuleForClass(GameManager.class);
	   Teams t = plugin.getApi().getModuleForClass(Teams.class);
      if(CTF.WhoHasFlag.get("Spawn1")) {
         for(Entity e : weap
               .getNearbyEntities(MapLoader.ctflocations.get(gm.GetCurrentWorld()).get(0), 2)) {
            if(e instanceof Player) {
               Player p = (Player) e;
               if(t.getTeam(p) == 1) {
                  if(!CTF.WhoHasFlag.containsKey(p.getName())) {
                     CTF.WhoHasFlag.put(p.getName(), false);
                  }
                  if(CTF.WhoHasFlag.get(p.getName())) {
                     FlagCap(p);
                  }
               }
               if(t.getTeam(p) == 2) {
                  if(CTF.WhoHasFlag.get("Spawn1")) {
                     CTF.WhoHasFlag.put(p.getName(), true);
                     CTF.WhoHasFlag.put("Spawn1", false);
                  }
               }
            }

         }

      }
      if(CTF.WhoHasFlag.get("Spawn2")) {
         for(Entity e : weap
               .getNearbyEntities(MapLoader.ctflocations.get(gm.GetCurrentWorld()).get(1), 2)) {
            if(e instanceof Player) {
               Player p = (Player) e;
               if(t.getTeam(p) == 2) {
                  if(CTF.WhoHasFlag.get(p.getName())) {
                     FlagCap(p);
                  }
               }
               if(t.getTeam(p) == 1) {
                  if(CTF.WhoHasFlag.get("Spawn2")) {
                     CTF.WhoHasFlag.put(p.getName(), true);
                     CTF.WhoHasFlag.put("Spawn2", false);
                  }
               }
            }
         }
      }
      for(Entry<String, Boolean> e : CTF.WhoHasFlag.entrySet()) {
         if(e.getKey().equalsIgnoreCase("Spawn1")) {
            Location loc = MapLoader.ctflocations.get(gm.GetCurrentWorld()).get(0);
            Location loc2 = new Location(gm.GetCurrentWorld(), loc.getX(), loc.getY() + 2, loc.getZ());
            Item i = gm.GetCurrentWorld().dropItem(loc2, new ItemStack(Material.WOOL, 1, (short) 14));
            CTF.pitem.put(i, 3);
            break;
         } else if(e.getKey().equalsIgnoreCase("Spawn2")) {
            Location loc = MapLoader.ctflocations.get(gm.GetCurrentWorld()).get(1);
            Location loc2 = new Location(gm.GetCurrentWorld(), loc.getX(), loc.getY() + 2, loc.getZ());
            Item i = gm.GetCurrentWorld().dropItem(loc2, new ItemStack(Material.WOOL, 1, (short) 11));
            CTF.pitem.put(i, 3);
            break;
         }
         Player p = Bukkit.getPlayer(e.getKey());
         ItemStack item = getItem(t.getTeam(p));
         Location loc = new Location(gm.GetCurrentWorld(), p.getLocation().getX(), p.getLocation().getY() + 2, p
               .getLocation().getZ());
         Item i = gm.GetCurrentWorld().dropItem(loc, item);
         CTF.pitem.put(i, 3);

      }

      @SuppressWarnings("unchecked")
      HashMap<Item, Integer> en = (HashMap<Item, Integer>) CTF.pitem.clone();
      for(Entry<Item, Integer> e : en.entrySet()) {
         int i = CTF.pitem.get(e.getKey());
         i--;
         CTF.pitem.put(e.getKey(), i);
         if(i == 0) {

            CTF.pitem.remove(e.getKey());
            e.getKey().remove();
         }
      }

      Bukkit.getScheduler().runTaskLater(plugin, new CTFTimer(plugin, delay), delay);
   }

   private void FlagCap(Player p) {
	   Teleport tel = plugin.getApi().getModuleForClass(Teleport.class);
	   GameManager gm = plugin.getApi().getModuleForClass(GameManager.class);
	   Teams t = plugin.getApi().getModuleForClass(Teams.class);
      Bukkit.broadcastMessage(p.getName() + " has caped the flag.");
      if(CTF.Flagcaps.get(t.getTeam(p)) == null) {
         CTF.Flagcaps.put(t.getTeam(p), 0);
      }
      CTF.Flagcaps.put(t.getTeam(p), CTF.Flagcaps.get(t.getTeam(p)) + 1);
      if(CTF.Flagcaps.get(t.getTeam(p)) == 3) {
         gm.Win(t.getTeam(p));
         CTF.Flagcaps.clear();
      } else {
         tel.RespawnAll(gm.GetCurrentWorld());
      }
      CTF.WhoHasFlag.clear();
      CTF.WhoHasFlag.put("Spawn1", true);
      CTF.WhoHasFlag.put("Spawn2", true);
      plugin.getApi().getModuleForClass(GUI.class).updatelist();
   }

   private ItemStack getItem(Integer team) {
      ItemStack i;
      if(team == 1) {
         i = new ItemStack(Material.WOOL, 1, (short) 14);
      } else {
         i = new ItemStack(Material.WOOL, 1, (short) 11);
      }
      return i;
   }

}
