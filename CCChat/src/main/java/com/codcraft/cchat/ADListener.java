package com.codcraft.cchat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


/**
 *
 * @author Joby
 */
public class ADListener implements Listener {

    private final AntiAd plugin;
    private Map<String, Boolean> chatbad = new HashMap<String, Boolean>(); 

    public ADListener(AntiAd plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(plugin.getAdfinder().check(e.getPlayer(), e.getMessage(), 1,true)); 
        
        final Player p = e.getPlayer();
        if(!p.hasPermission("ccchat.filter")) {
        	if(!chatbad.containsKey(p.getName())) {
        		chatbad.put(p.getName(), false);
        	}
        	if(chatbad.get(p.getName())) {
        		p.sendMessage("Please wait 3 seconds between chat!");
            	e.setCancelled(true);
        		return;
        	}  else {
        		chatbad.put(p.getName(), true);
        		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
					
					@Override
					public void run() {
						chatbad.put(p.getName(), false);
						
					}
				}, 60);
        	}
        } else {
        	
        }
        if (p instanceof Player) {
        	/*if (p.isOp()) {
        		String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[OP] "+ p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE   + m);
        	} else*/
        	if (p.hasPermission("CodCraft.Owner")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.DARK_RED + "[Owner] "+ p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE   + m);
        	/*} else if (p.hasPermission("CodCraft.SrAdmin")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[Sr_A] "+ p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE   + m);*/
			} else if (p.hasPermission("CodCraft.Admin")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.DARK_RED + "[Admin] "+ p.getName() +": "+ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE  + m);
			}  else if (p.hasPermission("CodCraft.Mod")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.DARK_GREEN + "[Mod] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);
			}  else if (p.hasPermission("CodCraft.Apprentice")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.GREEN + "[Apprentice] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);	
			}  else if (p.hasPermission("CodCraft.MCF")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.GOLD + "[MCF] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);	
			}  else if (p.hasPermission("CodCraft.Mojang")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[Mojang] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);	
			}  else if (p.hasPermission("CodCraft.YT")) {
				String m = e.getMessage();
				e.setFormat("["+ChatColor.RED+"Y"+ChatColor.WHITE+"T] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);
			}  else if (p.hasPermission("CodCraft.Commander")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.BLUE + "[Commander] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);
			}  else if (p.hasPermission("CodCraft.Captain")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.DARK_AQUA + "[Captain] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);
			}  else if (p.hasPermission("CodCraft.Soldier")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.AQUA + "[Soldier] " + p.getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);
			}  else if (p.hasPermission("CodCraft.Recruit")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.GRAY + "[Recruit] " + p.getName()+": "+ ChatColor.GRAY  + m);
				e.setMessage(ChatColor.WHITE +  m);
			} else {
				String m = e.getMessage();
				e.setFormat(ChatColor.GRAY + "[Launchie] " + p.getName()+": "+ ChatColor.GRAY  + m);
				e.setMessage(ChatColor.GRAY  + m);
			}
        	e.setCancelled(true);
        	//plugin.utils.sendMessageToPlayers(e.getFormat(), e.getPlayer().getName());
        	 plugin.sendGlobalMessage(e.getPlayer().getName(), String.format(e.getFormat(), new Object[] { e.getPlayer().getName(), e.getMessage() }));
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    	e.setJoinMessage(null);
    	//plugin.sendGlobalMessage(" ", ChatColor.GRAY + e.getPlayer().getName() + " has joined " + plugin.getConfig().getString("Name"));
    	plugin.players.put(e.getPlayer().getName(), ChatType.SERVER);
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
    	plugin.players.remove(e.getPlayer().getName());
    }
   

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignCreation(SignChangeEvent sign) {

        for (int i = 0; i < sign.getLines().length; i++) {

            if (plugin.getAdfinder().check(sign.getPlayer(), sign.getLine(i), 3, false)) {
                i = sign.getLines().length;
                sign.setCancelled(true);
                sign.getBlock().breakNaturally();
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCommandSent(PlayerCommandPreprocessEvent chat) {


        String CL = chat.getMessage().split("\\s+")[0];
        List<String> Commands = plugin.getConfig().getStringList("Detected-Commands");
        if (Commands.contains(CL)) {
            chat.setCancelled(plugin.getAdfinder().check(chat.getPlayer(), chat.getMessage(), 2,true));
        }
    }
    
	 @EventHandler
		public void onLeftClick(PlayerInteractEvent e) {
			if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(e.getPlayer().getItemInHand().getType() == Material.WRITTEN_BOOK) {
					//GameManager gm = plugin.api.getModuleForClass(GameManager.class);
					//if(gm.getGameWithPlayer(e.getPlayer()) == null) {
						Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER);
						
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
						inv.addItem(Buddies);
						inv.addItem(NONE);
						e.getPlayer().openInventory(inv);
					//}
				}
			}
		}
		
		@EventHandler
		public void invclick(InventoryClickEvent e) {
			if(e.getWhoClicked() instanceof Player) {
				Player p = (Player) e.getWhoClicked();
				//GameManager gm = plugin.api.getModuleForClass(GameManager.class);
				//if(gm.getGameWithPlayer(p) == null){
					if(e.getCurrentItem() != null) {
						if(e.getCurrentItem().getType() == Material.BEACON) {
							//ItemMeta im = e.getCurrentItem().getItemMeta();
							/*if(im != null) {
								if("ChatType: All".equalsIgnoreCase(im.getDisplayName())) {
									plugin.players.put(p.getName(), ChatType.ALL);
									p.closeInventory();
								}
							}*/
						} else if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
							ItemMeta im = e.getCurrentItem().getItemMeta();
							if(im != null) {
								if("ChatType: Buddies".equalsIgnoreCase(im.getDisplayName())) {
									plugin.players.put(p.getName(), ChatType.BUDDIES);
									p.closeInventory();
								}
							}
						} else if (e.getCurrentItem().getType() == Material.TNT) {
							ItemMeta im = e.getCurrentItem().getItemMeta();
							if(im != null) {
								if("ChatType: NONE".equalsIgnoreCase(im.getDisplayName())) {
									plugin.players.put(p.getName(), ChatType.NONE);
									p.closeInventory();
								}
							}
						} else if (e.getCurrentItem().getType() == Material.MINECART) {
							ItemMeta im = e.getCurrentItem().getItemMeta();
							if(im != null) {
								if("ChatType: SERVER".equalsIgnoreCase(im.getDisplayName())) {
									plugin.players.put(p.getName(), ChatType.SERVER);
									p.closeInventory();
								}
							}
						}
					}
				//}
			}
		}
}
