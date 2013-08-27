package com.codcraft.weapons.killstreaks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.codcraft.cac.CaCModule;

public class Nuke extends KillStreak {
	
  public Nuke(KillStreakManager km) {
    super(km);
    setKills(25);
    setName("Nuke");
    km.plugin.api.getModuleForClass(CaCModule.class).addweapon(getName(), "killStreak", "");
  }

  public void onEarn(Player p) {
    ItemStack is = new ItemStack(Material.GHAST_TEAR);
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(getName());
    is.setItemMeta(im);
    p.getInventory().addItem(new ItemStack[] { is });
  }

  public void onUse(Player p, Game<?> g) {
    for (Team t : g.getTeams()) {
      if (!g.findTeamWithPlayer(p).getName().equalsIgnoreCase(t.getName())) {
        for (TeamPlayer tp : t.getPlayers()) {
          Player killed = Bukkit.getPlayer(tp.getName());
          if (killed != null) {
            killed.damage(20.0D, p);
          }
        }
      }
    }
    p.damage(40.0D);
  }

  public void onDeath(Player p, Game<?> g) {
  }
}