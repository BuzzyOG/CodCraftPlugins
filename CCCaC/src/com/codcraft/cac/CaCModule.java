package com.codcraft.cac;

import java.util.ArrayList;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCModule;
import com.codcraft.codcraftplayer.CCClass;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;

public class CaCModule extends CCModule {

	private Cac plugin;
	public CaCModule(Cac plugin, CCAPI api) {
		super(api);
		this.plugin = plugin;
	}
	
	public void forceAddCaCUser(final Player p, int box) {
		GameManager gm = api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		
	      CaCEvent event = new CaCEvent(p);
	      Bukkit.getPluginManager().callEvent(event);
	      if(event.isCancelled()) {
	         return;
	      }
	      if(!isCaCUser(p)) {
	         plugin.locations.CaCBox.put(p.getName(), box);
	      }
	      p.sendMessage("Telporting in 5 secs");
	      Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(g != null) {
					g.findTeamWithPlayer(p).removePlayer(p);
				}
				p.teleport(plugin.locations.LobbySpawn.get(GetBox(p)));
			}
		}, 100);
	}
	
	public void addCaCUser(final Player p) {
		GameManager gm = api.getModuleForClass(GameManager.class);
		final Game<?> g = gm.getGameWithPlayer(p);
		
	      CaCEvent event = new CaCEvent(p);
	      Bukkit.getPluginManager().callEvent(event);
	      if(event.isCancelled()) {
	         return;
	      }
	      if(!isCaCUser(p)) {
	         plugin.locations.CaCBox.put(p.getName(), GetBoxtoput());
	      }
	      if(g != null) {
	    	  p.sendMessage("Telporting in 5 secs");
				CCPlayerModule playermodule = api.getModuleForClass(CCPlayerModule.class);
				CCPlayer player = playermodule.getPlayer(p);
				final CCClass clazz = player.getClass(1);

		      Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
				
				@Override
				public void run() {
					if(g != null) {
						g.findTeamWithPlayer(p).removePlayer(p);
					}
					p.getInventory().clear();
					ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
					BookMeta bm = (BookMeta) book.getItemMeta();
					bm.addPage("Welcome to Create A Class! Please go up to signs and right click to configure your classes!");
					book.setItemMeta(bm);
					p.getInventory().addItem(book);
					p.teleport(plugin.locations.LobbySpawn.get(GetBox(p)));
					LoadSigns(p, clazz.getGun(), clazz.getPerk1(), clazz.getPerk2(), clazz.getPerk3(),
							clazz.getEquipment(), clazz.getKillStreak());
				}
			}, 100);
		      
	      } else {
	    	  p.sendMessage("Telporting...");
			CCPlayerModule playermodule = api.getModuleForClass(CCPlayerModule.class);
			CCPlayer player = playermodule.getPlayer(p);
			final CCClass clazz = player.getClass(1);
			p.getInventory().clear();
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
			BookMeta bm = (BookMeta) book.getItemMeta();
			bm.addPage("Welcome to Create A Class! Please go up to signs and right click to configure your classes!");
			book.setItemMeta(bm);
			p.getInventory().addItem(book);
			p.teleport(plugin.locations.LobbySpawn.get(GetBox(p)));
			LoadSigns(p, clazz.getGun(), clazz.getPerk1(), clazz.getPerk2(), clazz.getPerk3(), clazz.getEquipment(), clazz.getKillStreak());
	      }
	       
	      
	}
	public int GetBox(Player p) {
		if(!plugin.locations.CaCBox.containsKey(p.getName())) {
			return (Integer) null;
		}
		return plugin.locations.CaCBox.get(p.getName());
	}
	
	   public boolean Leave(Player p) {
		      // Grab modules

		      if(isCaCUser(p)) {
		         plugin.locations.CaCBox.remove(p.getName());
		         //TODO fix
		         Bukkit.dispatchCommand(p, "spawn");
		         return true;
		      } else {
		    	  p.sendMessage(ChatColor.BLUE+"You or not in the create a class.");
		         return false;
		      }
	   }
	
	private Integer GetBoxtoput() {
		ArrayList<Integer> UsedBoxes = new ArrayList<Integer>();
		ArrayList<Integer> UnusedBoxes = new ArrayList<Integer>();
		for(Entry<String, Integer> e : plugin.locations.CaCBox.entrySet()) {
			UsedBoxes.add(e.getValue());
		}
		int temp = 0;
		while(temp < 20) {
			if(!UsedBoxes.contains(temp)) {
				UnusedBoxes.add(temp);
		    }
		    temp++;
		}
		Random rnd = new Random();
		return UnusedBoxes.get(rnd.nextInt(UnusedBoxes.size()));
	}
	
	
	public boolean isCaCUser(Player p) {
		return plugin.locations.CaCBox.containsKey(p.getName());
	}
	
	public boolean isCaCUser(TeamPlayer p) {
		return plugin.locations.CaCBox.containsKey(p.getName());
	}
	   public void LoadSigns(Player p, String Gun, String Perk1, String Perk2, String Perk3, String Equimpent, String KillStreak) {
		      int box = plugin.locations.CaCBox.get(p.getName());
		      Sign gun = (Sign) plugin.locations.SignLocation.get(box).get(1).getBlock().getState();
		      gun.setLine(3, Gun);
		      Sign perk1 = (Sign) plugin.locations.SignLocation.get(box).get(3).getBlock().getState();
		      perk1.setLine(3, Perk1);
		      Sign perk2 = (Sign) plugin.locations.SignLocation.get(box).get(4).getBlock().getState();
		      perk2.setLine(3, Perk2);
		      Sign perk3 = (Sign) plugin.locations.SignLocation.get(box).get(5).getBlock().getState();
		      perk3.setLine(3, Perk3);
		      Sign equimpent = (Sign) plugin.locations.SignLocation.get(box).get(6).getBlock().getState();
		      equimpent.setLine(3, Equimpent);
		      Sign killStreak = (Sign) plugin.locations.SignLocation.get(box).get(7).getBlock().getState();
		      killStreak.setLine(3, KillStreak);
		      gun.update();
		      perk1.update();
		      perk2.update();
		      perk3.update();
		      equimpent.update();
		      killStreak.update();
		   }
	public void addweapon(String name, String list, String permission) {
		switch (list.toLowerCase()) {
		case "weapons":
			plugin.weapons.put(name, permission);
			break;
		case "attachement":
			plugin.Attachement.add(name);
			break;
		case "perk1":
			Bukkit.broadcastMessage("added: "+ name);
			plugin.Perk1.add(name);
			break;
		case "perk2":
			plugin.Perk2.add(name);
			break;
		case "perk3":
			plugin.Perk3.add(name);
			break;
		case "equipment":
			plugin.Equipment.add(name);
			break;
		case "killstreak":
			plugin.KillStreak.add(name);
			break;


		default:
			break;
		}
	}
	

	@Override
	public void starting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closing() {
		// TODO Auto-generated method stub
		
	}

}
