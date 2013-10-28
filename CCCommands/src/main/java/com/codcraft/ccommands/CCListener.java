package com.codcraft.ccommands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.util.Vector;
import org.kitteh.tag.PlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

import com.CodCraft.api.event.RequestJoinGameEvent;
import com.CodCraft.api.event.team.TeamPlayerGainedEvent;
import com.CodCraft.api.event.team.TeamPlayerLostEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.codcraftplayer.PlayerStatUpdate;

public class CCListener implements Listener {
	
	private CCCommands plugin;
	private int x;
	private int y;
	private int z;
	private int x1;
	private int y1;
	private int z1;
	
	/*private int x2;
	private int y2;
	private int z2;
	private int x3;
	private int y3;
	private int z3;*/
	
	public CCListener(CCCommands plugin) {
		this.plugin = plugin;
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/CCCommands/ads.yml"));
		this.x = config.getInt("x1");
		this.y = config.getInt("y1");
		this.z = config.getInt("z1");
		this.x1 = config.getInt("x2");
		this.y1 = config.getInt("y2");
		this.z1 = config.getInt("z2");
		
		/*this.x2 = config.getInt("x3");
		this.y2 = config.getInt("y3");
		this.z2 = config.getInt("z3");
		this.x3 = config.getInt("x4");
		this.y3 = config.getInt("y4");
		this.z3 = config.getInt("z4");*/
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(isInside(e.getPlayer(), x, y, z, x1, y1, z1)/* || isInside(e.getPlayer(), x2, y2, z2, x3, y3, z3)*/) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
		    DataOutputStream out = new DataOutputStream(b);
		    try {
		    	out.writeUTF("Connect");
		        out.writeUTF("lobby");
		    } catch (IOException localIOException) {
		    	//plugin.getLogger().info("Error: " + module.server);
		    }
		    e.getPlayer().sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
		}
	}
	@EventHandler
	public void onUpdaye(PlayerReceiveNameTagEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getNamedPlayer());
		if(g == null) {
			String prefix = plugin.chat.getGroupPrefix(Bukkit.getWorld("world"), plugin.permi.getPrimaryGroup(e.getNamedPlayer()));
			prefix = prefix.substring(0, 2);
			prefix = ChatColor.translateAlternateColorCodes('&', prefix);
			e.setTag(prefix + e.getNamedPlayer().getName()); 
		}

	}
	
	
	
	@EventHandler
	public void onclick(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block clickedblock = e.getClickedBlock();
			if(clickedblock != null) {
				if(clickedblock.getState() instanceof Sign) {
					Location loc = new Location(Bukkit.getWorld("world"), -111, 139, 58);
					if(clickedblock.getLocation().equals(loc)) {
						ByteArrayOutputStream b = new ByteArrayOutputStream();
					    DataOutputStream out = new DataOutputStream(b);
					    try {
					    	out.writeUTF("Connect");
					        out.writeUTF("lobby");
					    } catch (IOException localIOException) {
					    	//plugin.getLogger().info("Error: " + module.server);
					    }
					    e.getPlayer().sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null) {
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onInt(PlayerInteractEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(e.getPlayer());
		if(g == null) {
			if(e.getClickedBlock() != null) {
				if(e.getClickedBlock().getType() == Material.BEACON || e.getClickedBlock().getType() == Material.CHEST) {
					e.setCancelled(true);
				}
			}
			
			if (e.getAction() == Action.PHYSICAL && e.getPlayer().getLocation().getBlock().getType().equals(Material.STONE_PLATE)){
			    Vector vec = e.getPlayer().getEyeLocation().getDirection();
			    e.getPlayer().setVelocity(vec.multiply(4));
			    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.GHAST_FIREBALL, 3.0F, 0.5F);
			}
		}

	}
	
	@EventHandler
	public void onPlayerStat(final PlayerStatUpdate e) {
		if(e.getStat().equalsIgnoreCase("credits")) {
			final Player p = Bukkit.getPlayer(e.getPlayer().getName());
			if(p != null) {
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						p.setLevel(e.getNewscore());
						
					}
				}, 1);

			}
		}
	}
	
	@EventHandler
	public void onFade(BlockFadeEvent e) {
		if(e.getBlock().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	public void onEnd(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			Game<?> g = gm.getGameWithPlayer(p);
			if(g == null) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onrepsawn(final PlayerRespawnEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null) {
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					Bukkit.dispatchCommand(e.getPlayer(), "spawn");
					
				}
			}, 1);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(!e.getPlayer().hasPermission("CodCraft.build")) {
			if(gm.getGameWithPlayer(e.getPlayer()) == null) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEnityRegain(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			GameManager gm = plugin.api.getModuleForClass(GameManager.class);
			if(gm.getGameWithPlayer(p) == null) {
				e.setFoodLevel(20);
			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				guiUpdate();
				
			}
		}, 1);

		e.setQuitMessage(null);
	}
	

	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null) {
			Bukkit.dispatchCommand(e.getPlayer(), "lobby");
		}
		e.getPlayer().getInventory().setHelmet(null);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				guiUpdate();
			}
		}, 1);
	}
	
	
	@EventHandler
	public void onChange(WeatherChangeEvent e) {
	    GameManager gm = plugin.api.getModuleForClass(GameManager.class);
	    boolean cancel = true;
	    for (Game<?> g : gm.getAllGames()) {
	    	if ((g.getName().equalsIgnoreCase(e.getWorld().getName())) && (e.toWeatherState())) {
	    		cancel = false;
	    	}
	    }
	    e.setCancelled(cancel);

	  }
		
	@EventHandler
	public void onSpawn(final PlayerDoSpawnEvent e) {
		if(e.getPlayer().hasPermission("codcraft.fly")) {
			e.getPlayer().setAllowFlight(true);
			e.getPlayer().setFlying(true);
		} else {
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setFlying(false);
		}
		guiUpdate();


	}
	
	private void guiUpdate() {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		GUI gui = plugin.api.getModuleForClass(GUI.class);
		TreeMap<String, String> sorted = new TreeMap<>();
		int i = 0;
		for(Player p : Bukkit.getOnlinePlayers()) {
			i++;
			if(gm.getGameWithPlayer(p) == null) {
				String prefix = plugin.chat.getGroupPrefix(Bukkit.getWorld("world"), plugin.permi.getPrimaryGroup(p));
				prefix = prefix.substring(0, 2);
				prefix = ChatColor.translateAlternateColorCodes('&', prefix);
				sorted.put(""+i, prefix+p.getName());
			}
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(gm.getGameWithPlayer(p) == null) {
				//TagAPI.refreshPlayer(p);
				gui.updateplayerlist(p, sorted);
			}

		}
		
	}
	
	@EventHandler
	public void on(TeamPlayerGainedEvent e) {
		guiUpdate();
	}
	@EventHandler
	public void of(TeamPlayerLostEvent e) {
		guiUpdate();
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onRequest(RequestJoinGameEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		if(p != null) {
			p.setAllowFlight(false);
			p.setFlying(false);
		}
	}
	
	@EventHandler
	public void commandhappen(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().equalsIgnoreCase("/stop")) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
		      DataOutputStream out = new DataOutputStream(b);
		      try {
		        out.writeUTF("Connect");
		        out.writeUTF("lobby");
		      } catch (IOException localIOException) {
		      }
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
			}
			try {
				Thread.sleep(1000);
				System.out.println("worked");
			} catch (InterruptedException e1) {
				System.out.println("not-worked");
			}
		}
		
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
