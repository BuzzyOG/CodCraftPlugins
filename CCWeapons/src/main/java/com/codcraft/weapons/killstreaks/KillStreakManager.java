package com.codcraft.weapons.killstreaks;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.event.team.TeamPlayerLostEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCModule;
import com.codcraft.codcraftplayer.CCClass;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.weapons.Weapons;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

public class KillStreakManager extends CCModule implements Listener {
	
  public Weapons plugin;
  private List<KillStreak> killstreaks = new ArrayList<>();
  private Map<String, Integer> playerstreaks = new HashMap<>();

  public KillStreakManager(Weapons plugin, CCAPI api) {
    super(api);
    Bukkit.getPluginManager().registerEvents(this, plugin);
    this.plugin = plugin;
    this.killstreaks.add(new Nuke(this));
    this.killstreaks.add(new AttackDog(this));
  }

  public void check(Player p) {
    int kills = ((Integer)this.playerstreaks.get(p.getName())).intValue();
    CCPlayerModule ccpm = (CCPlayerModule)this.api.getModuleForClass(CCPlayerModule.class);
    String streak = ccpm.getPlayer(p).getClass(ccpm.getPlayer(p).getCurrentclass().intValue()).getKillStreak();
    for (KillStreak ks : this.killstreaks) {
    	if ((ks.getName().equalsIgnoreCase(streak)) && (ks.getKills() == kills)) {
    		ks.onEarn(p);
    	}
    }
  }

  public void starting() {
  }

  public void closing() {
  }

  public boolean checkIfGameIsInstanceOfPlugin(Game<?> g)
  {
    return this.plugin.checkIfGameIsInstanceOfPlugin(g);
  }

  @EventHandler
  public void PlayerDeath(PlayerDeathEvent e) {
    GameManager gm = (GameManager)this.api.getModuleForClass(GameManager.class);
    Game<?> g = gm.getGameWithPlayer(e.getEntity());
    if ((g != null) && (checkIfGameIsInstanceOfPlugin(g))) {
    	for (KillStreak ks : this.killstreaks) {
    		ks.onDeath(e.getEntity(), g);
    	}
    	this.playerstreaks.put(e.getEntity().getName(), Integer.valueOf(0));
    	if ((e.getEntity().getKiller() instanceof Player)) {
    		Player killer = e.getEntity().getKiller();
    		if (this.playerstreaks.get(killer.getName()) == null) {
    				this.playerstreaks.put(killer.getName(), Integer.valueOf(0));
    		}
    		this.playerstreaks.put(killer.getName(), Integer.valueOf(((Integer)this.playerstreaks.get(killer.getName())).intValue() + 1));
    		check(killer);
    	}
    }
  }

  @EventHandler
  public void onIntreact(PlayerInteractEvent e) {
    GameManager gm = (GameManager)this.api.getModuleForClass(GameManager.class);
    Game<?> g = gm.getGameWithPlayer(e.getPlayer());
    if ((g != null) && (checkIfGameIsInstanceOfPlugin(g))) {
    	if (e.getAction() == Action.PHYSICAL) {
        return;
      }
      Player p = e.getPlayer();
      ItemMeta meta = p.getInventory().getItemInHand().getItemMeta();
      if ((meta != null) && (meta.getDisplayName() != null)) {
    	  for (KillStreak ks : this.killstreaks) {
    		  if (meta.getDisplayName().equalsIgnoreCase(ks.getName()))
    			  ks.onUse(p, g);
    	  	  }
      	  }
    }
  }

  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    GameManager gm = (GameManager)this.api.getModuleForClass(GameManager.class);
    Game<?> g = gm.getGameWithPlayer(e.getPlayer());
    if ((g != null) && (checkIfGameIsInstanceOfPlugin(g))) {
    	for (KillStreak ks : this.killstreaks) {
    		ks.onDeath(e.getPlayer(), g);
    	}
    	this.playerstreaks.put(e.getPlayer().getName(), Integer.valueOf(0));
    }
  }

  @EventHandler
  public void onRage(TeamPlayerLostEvent e)
  {
    GameManager gm = (GameManager)this.api.getModuleForClass(GameManager.class);
    Game<?> g = gm.getGameWithPlayer(e.getPlayer());
    if ((g != null) && (checkIfGameIsInstanceOfPlugin(g))) {
    	for (KillStreak ks : this.killstreaks) {
    		ks.onDeath(Bukkit.getPlayer(e.getPlayer().getName()), g);
    	}
    	this.playerstreaks.put(e.getPlayer().getName(), Integer.valueOf(0));
    }
  }
}