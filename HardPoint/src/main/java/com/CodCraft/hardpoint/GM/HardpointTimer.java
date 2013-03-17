package com.CodCraft.hardpoint.GM;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.TeamPlayer;
import com.CodCraft.api.modules.Teams;
import com.CodCraft.hardpoint.CodCraft;

public class HardpointTimer implements Runnable {

   private CodCraft plugin;

   public HardpointTimer(CodCraft plugin) {
      this.plugin = plugin;
   }

   @Override
   public void run() {
      System.out.println(plugin.getHardpoint().currentspot);
      if(plugin.getHardpoint().currentlocation == null) {
         // go back to start with delay
      } else {
         Double maxX = (Double) null;
         Double maxY = (Double) null;
         Double maxZ = (Double) null;
         Double minX = (Double) null;
         Double minY = (Double) null;
         Double minZ = (Double) null;
         for(Location loc : plugin.getHardpoint().currentlocation) {
            if(maxX == (Double) null) {
               maxX = loc.getX();
            }
            if(maxY == (Double) null) {
               maxY = loc.getX();
            }
            if(maxZ == (Double) null) {
               maxZ = loc.getX();
            }
            if(minX == (Double) null) {
               minX = loc.getX();
            }
            if(minY == (Double) null) {
               minY = loc.getX();
            }
            if(minZ == (Double) null) {
               minZ = loc.getX();
            }
            if(loc.getX() > minX) {
               minX = maxX;
               maxX = loc.getX();
            }

            if(loc.getY() > maxY) {
               minY = maxY;
               maxY = loc.getY();
            }
            if(loc.getZ() > maxZ) {
               minZ = maxZ;
               maxZ = loc.getZ();
            }
            if(maxX > loc.getX()) {
               minX = loc.getX();
            }
            if(maxY > loc.getY()) {
               minY = loc.getY();
            }
            if(maxZ > loc.getZ()) {
               minZ = loc.getZ();
            }
            System.out.println(minX);
            System.out.println(minY);
            System.out.println(minZ);
            System.out.println(maxX);
            System.out.println(maxY);
            System.out.println(maxZ);
            int t1 = 0;
            int t2 = 0;
            for(String s : plugin.api.getModuleForClass(TeamPlayer.class).Whoplaying()) {
               Player p = Bukkit.getPlayer(s);
               System.out.println(p);
               if(isInside(p, maxX, maxY, maxZ, minX, minY, minZ)) {
                  System.out.println("14");
                  if(plugin.api.getModuleForClass(Teams.class).getTeam(p) == 1) {
                     t1++;
                  } else {
                     t2++;
                  }
               }
            }
            if(t1 >= 1 && t2 >= 1) {
               contested();
            } else if(t1 >= 1 && !(t2 >= 1)) {
               scoreup(t1);
            } else if(!(t1 >= 1) && t2 >= 1) {
               scoreup(t2);
            } else if(!(t1 >= 1) && !(t2 >= 1)) {
               plugin.getHardpoint().contested = false;
               plugin.getHardpoint().team1 = false;
               plugin.getHardpoint().team2 = false;

            }
         }
      }
      plugin.api.getModuleForClass(GUI.class).updatelist();
      // TODO you need the plugin to be aware of this task and kill it if it
      // already exists.
      Bukkit.getScheduler().runTaskLater(plugin, new HardpointTimer(plugin), 10L);
   }

   private void scoreup(int team) {
      plugin.getHardpoint().contested = true;
      if(team == 1) {
         plugin.getHardpoint().team2 = false;
         plugin.getHardpoint().team1 = true;
      } else if(team == 2) {
         plugin.getHardpoint().team2 = true;
         plugin.getHardpoint().team1 = false;
      }
      plugin.api.getModuleForClass(GameManager.class).setTeamscore(team, plugin.api.getModuleForClass(GameManager.class).getTeamScore(team) + 1);
   }

   private void contested() {
      plugin.getHardpoint().contested = true;
      plugin.getHardpoint().team1 = false;
      plugin.getHardpoint().team2 = false;
      plugin.api.getModuleForClass(GUI.class).updatelist();
   }

   private boolean isInside(Player p, double maxX, double maxY, double maxZ, double minX, double minY, double minZ) {
      Location loc = p.getLocation();
      if(loc.getX() < minX || loc.getX() > maxX)
         return false;
      if(loc.getZ() < minZ || loc.getZ() > maxZ)
         return false;
      if(loc.getY() < minY || loc.getY() > maxY)
         return false;
      return true;
   }

}
