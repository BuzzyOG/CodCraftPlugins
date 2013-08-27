package com.codcraft.weapons.killstreaks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.Game;
import com.codcraft.cac.CaCModule;

public class AttackDog extends KillStreak
{
  private Map<String, Wolf> dogs = new HashMap<>();

  public AttackDog(KillStreakManager km) {
    super(km);
    setKills(5);
    setName("AttackDog");
    ((CaCModule)km.plugin.api.getModuleForClass(CaCModule.class)).addweapon(getName(), "killStreak", "");
  }

  public void onEarn(Player p)
  {
    ItemStack is = new ItemStack(Material.GHAST_TEAR);
    ItemMeta im = is.getItemMeta();
    im.setDisplayName(getName());
    is.setItemMeta(im);
    p.getInventory().addItem(new ItemStack[] { is });
  }

  public void onUse(Player p, Game<?> g)
  {
    Location loc = p.getLocation();
    Wolf dog = (Wolf)loc.getWorld().spawn(loc, Wolf.class);
    dog.setOwner(p);
    this.dogs.put(p.getName(), dog);
    p.getInventory().remove(Material.GHAST_TEAR);
  }

  public void onDeath(Player p, Game<?> g)
  {
    if (this.dogs.containsKey(p.getName())) {
      ((Wolf)this.dogs.get(p.getName())).remove();
      this.dogs.remove(p.getName());
    }
  }
}