package com.codcraft.weapons.killstreaks;

import com.CodCraft.api.model.Game;
import org.bukkit.entity.Player;

public abstract class KillStreak {
  private int kills;
  private String name;
  public KillStreakManager km;

  public KillStreak(KillStreakManager km) {
    this.km = km;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getKills() {
    return this.kills;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }

  public abstract void onEarn(Player paramPlayer);

  public abstract void onUse(Player paramPlayer, Game<?> paramGame);

  public abstract void onDeath(Player paramPlayer, Game<?> paramGame);
}