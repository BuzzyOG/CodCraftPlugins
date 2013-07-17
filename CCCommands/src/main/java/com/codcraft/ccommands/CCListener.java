package com.codcraft.ccommands;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.cchat.ChatType;



public class CCListener implements Listener {
	private CCCommands plugin;
	public CCListener(CCCommands plugin) {
		this.plugin = plugin;
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
		if(!e.getPlayer().hasPermission("CodCraft.Admin")) {
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
	public void onJoin(PlayerJoinEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null) {
			Bukkit.dispatchCommand(e.getPlayer(), "spawn");
		}
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta book1 = (BookMeta) book.getItemMeta();
		book1.addPage("Welcome to CodCraft please follow these"+ChatColor.BOLD+" rules."+ChatColor.BLACK+"" +
				"             Respect all Staff marked in "+ChatColor.DARK_RED+" red!"+ ChatColor.BLACK + "           No Hacking!" +
						"               No Advertising." +
						"           Use Common Sense!");
		
		book.setItemMeta(book1);
		e.getPlayer().getInventory().addItem(book);
	}
	
	 @EventHandler
		public void onLeftClick(PlayerInteractEvent e) {
			if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(e.getPlayer().getItemInHand().getType() == Material.WRITTEN_BOOK) {
					GameManager gm = plugin.api.getModuleForClass(GameManager.class);
					if(gm.getGameWithPlayer(e.getPlayer()) == null) {
						Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
						ItemStack ALL = new ItemStack(Material.BEACON, 1);
						ItemMeta ALLMeta = ALL.getItemMeta();
						if(ALLMeta != null) {
							ALLMeta.setDisplayName("ChatType: All");
						}
						ALL.setItemMeta(ALLMeta);
						
						ItemStack Buddies = new ItemStack(Material.ENDER_PEARL, 1);
						ItemMeta BuddiesMeta = Buddies.getItemMeta();
						if(BuddiesMeta != null) {
							BuddiesMeta.setDisplayName("ChatType: Buddies");
						}
						Buddies.setItemMeta(BuddiesMeta);
						
						ItemStack NONE = new ItemStack(Material.TNT, 1);
						ItemMeta NONEMeta = NONE.getItemMeta();
						if(NONEMeta != null) {
							NONEMeta.setDisplayName("ChatType: NONE");
						}
						NONE.setItemMeta(NONEMeta);
						ItemStack SERVER = new ItemStack(Material.MINECART, 1);
						ItemMeta SERVERMeta = SERVER.getItemMeta();
						if(SERVERMeta != null) {
							SERVERMeta.setDisplayName("ChatType: SERVER");
						}
						SERVER.setItemMeta(SERVERMeta);
						inv.addItem(SERVER);
						inv.addItem(ALL);
						inv.addItem(Buddies);
						inv.addItem(NONE);
						e.getPlayer().openInventory(inv);
					}
				}
			}
		}
		
		@EventHandler
		public void invclick(InventoryClickEvent e) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				if(gm.getGameWithPlayer(p) == null){
					if(e.getCurrentItem() != null) {
						if(e.getCurrentItem().getType() == Material.BEACON) {
							ItemMeta im = e.getCurrentItem().getItemMeta();
							if(im != null) {
								if("ChatType: All".equalsIgnoreCase(im.getDisplayName())) {
									plugin.cchat.players.put(p.getName(), ChatType.ALL);
									p.closeInventory();
								}
							}
						} else if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
							ItemMeta im = e.getCurrentItem().getItemMeta();
							if(im != null) {
								if("ChatType: Buddies".equalsIgnoreCase(im.getDisplayName())) {
									plugin.cchat.players.put(p.getName(), ChatType.BUDDIES);
									p.closeInventory();
								}
							}
						} else if (e.getCurrentItem().getType() == Material.TNT) {
							ItemMeta im = e.getCurrentItem().getItemMeta();
							if(im != null) {
								if("ChatType: NONE".equalsIgnoreCase(im.getDisplayName())) {
									plugin.cchat.players.put(p.getName(), ChatType.NONE);
									p.closeInventory();
								}
							}
						} else if (e.getCurrentItem().getType() == Material.MINECART) {
							ItemMeta im = e.getCurrentItem().getItemMeta();
							if(im != null) {
								if("ChatType: SERVER".equalsIgnoreCase(im.getDisplayName())) {
									plugin.cchat.players.put(p.getName(), ChatType.SERVER);
									p.closeInventory();
								}
							}
						}
					}
				}
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
	
	
}
