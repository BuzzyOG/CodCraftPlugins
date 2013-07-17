package com.codcraft.cac;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.codcraft.codcraftplayer.CCClass;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;

public class CaCListener implements Listener {
	private Cac plugin;

	public CaCListener(Cac plugin) {
		this.plugin = plugin;
	}
	
	
	
	@EventHandler
	public void onLeft(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block clickedblock = e.getClickedBlock();
			if(clickedblock != null) {
				if(clickedblock.getState() instanceof Sign) {
					Location loc = new Location(Bukkit.getWorld("world"), -111, 139, 62);
					if(clickedblock.getLocation().equals(loc)) {
						p.performCommand("cac join");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		CCPlayerModule playermodule = plugin.api.getModuleForClass(CCPlayerModule.class);
		CCPlayer player = playermodule.getPlayer(p);
		CaCModule cac = plugin.api.getModuleForClass(CaCModule.class);
		if(cac.isCaCUser(p)) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block b = e.getClickedBlock();
				if(b.getType() == Material.SIGN_POST || b.getType() == Material.SIGN || b.getType() == Material.WALL_SIGN) {
					Sign s = (Sign) b.getState();
					if(s.getLine(0).equalsIgnoreCase("lobby"+cac.GetBox(p))) {
						switch (s.getLine(1)) {
						case "Class":
							System.out.println("6");
							int i = Integer.parseInt(s.getLine(3));
							if(i >= player.getCaCint()) {
								i = 1;
							} else {
								i++;
							}
							s.setLine(3, ""+i);
							s.update();
							
							CCClass clazz = player.getClass(i);
							cac.LoadSigns(p, clazz.getGun(), clazz.getPerk1(), clazz.getPerk2(), clazz.getPerk3(),
									clazz.getEquipment(), clazz.getKillStreak());
							break;
						case "Gun":
							Location signloc = plugin.locations.SignLocation.get(cac.GetBox(p)).get(0);
							Block sb = signloc.getBlock();
							if(sb.getType() == Material.SIGN_POST || sb.getType() == Material.SIGN || sb.getType() == Material.WALL_SIGN) {
								Sign ssb = (Sign) sb.getState();
								Integer ci = Integer.parseInt(ssb.getLine(3));
								int index = plugin.weapons.indexOf(s.getLine(3)) + 1;
								if(index >= plugin.weapons.size()){
								    index = 0;
								}
								String nextGun = plugin.weapons.get(index);
								s.setLine(3, nextGun);
								s.update();
								player.getClass(ci).setGun(s.getLine(3));
							}
							break;
						case "Attachment":
							Location asignloc = plugin.locations.SignLocation.get(cac.GetBox(p)).get(0);
							Block asb = asignloc.getBlock();
							
							if(asb.getType() == Material.SIGN_POST || asb.getType() == Material.SIGN || asb.getType() == Material.WALL_SIGN) {
								Sign ssb = (Sign) asb.getState();
								Integer ci = Integer.parseInt(ssb.getLine(3));
								int index = plugin.Attachement.indexOf(s.getLine(3)) + 1;
								if(index >= plugin.Attachement.size()){
								    index = 0;
								}
								String nextGun = plugin.Attachement.get(index);
								s.setLine(3, nextGun);
								s.update();
								player.getClass(ci).setAttachment(s.getLine(3));
							}
							break;
						case "Perk1":
							Location p1signloc = plugin.locations.SignLocation.get(cac.GetBox(p)).get(0);
							Block p1sb = p1signloc.getBlock();
							
							if(p1sb.getType() == Material.SIGN_POST || p1sb.getType() == Material.SIGN || p1sb.getType() == Material.WALL_SIGN) {
								Sign ssb = (Sign) p1sb.getState();
								Integer ci = Integer.parseInt(ssb.getLine(3));
								int index = plugin.Perk1.indexOf(s.getLine(3)) + 1;
								if(index >= plugin.Perk1.size()){
								    index = 0;
								}
								String nextGun = plugin.Perk1.get(index);
								s.setLine(3, nextGun);
								s.update();
								player.getClass(ci).setPerk1(s.getLine(3));
							}
							break;
						case "Perk2":
							Location p2signloc = plugin.locations.SignLocation.get(cac.GetBox(p)).get(0);
							Block p2sb = p2signloc.getBlock();
							
							if(p2sb.getType() == Material.SIGN_POST || p2sb.getType() == Material.SIGN || p2sb.getType() == Material.WALL_SIGN) {
								Sign ssb = (Sign) p2sb.getState();
								Integer ci = Integer.parseInt(ssb.getLine(3));
								int index = plugin.Perk2.indexOf(s.getLine(3)) + 1;
								if(index >= plugin.Perk2.size()){
								    index = 0;
								}
								String nextGun = plugin.Perk2.get(index);
								s.setLine(3, nextGun);
								s.update();
								player.getClass(ci).setPerk2(s.getLine(3));
							}
							break;
						case "Perk3":
							Location p3signloc = plugin.locations.SignLocation.get(cac.GetBox(p)).get(0);
							Block p3sb = p3signloc.getBlock();
							
							if(p3sb.getType() == Material.SIGN_POST || p3sb.getType() == Material.SIGN || p3sb.getType() == Material.WALL_SIGN) {
								Sign ssb = (Sign) p3sb.getState();
								Integer ci = Integer.parseInt(ssb.getLine(3));
								int index = plugin.Perk3.indexOf(s.getLine(3)) + 1;
								if(index >= plugin.Perk3.size()){
								    index = 0;
								}
								String nextGun = plugin.Perk3.get(index);
								s.setLine(3, nextGun);
								s.update();
								player.getClass(ci).setPerk3(s.getLine(3));
							}
							break;
						case "Equipment":
							Location esignloc = plugin.locations.SignLocation.get(cac.GetBox(p)).get(0);
							Block esb = esignloc.getBlock();
							
							if(esb.getType() == Material.SIGN_POST || esb.getType() == Material.SIGN || esb.getType() == Material.WALL_SIGN) {
								Sign ssb = (Sign) esb.getState();
								Integer ci = Integer.parseInt(ssb.getLine(3));
								int index = plugin.Equipment.indexOf(s.getLine(3)) + 1;
								if(index >= plugin.Equipment.size()){
								    index = 0;
								}
								String nextGun = plugin.Equipment.get(index);
								s.setLine(3, nextGun);
								s.update();
								player.getClass(ci).setEquipment(s.getLine(3));
							}
							break;
						case "KillStreak":
							Location ksignloc = plugin.locations.SignLocation.get(cac.GetBox(p)).get(0);
							Block ksb = ksignloc.getBlock();
							
							if(ksb.getType() == Material.SIGN_POST || ksb.getType() == Material.SIGN || ksb.getType() == Material.WALL_SIGN) {
								Sign ssb = (Sign) ksb.getState();
								Integer ci = Integer.parseInt(ssb.getLine(3));
								int index = plugin.KillStreak.indexOf(s.getLine(3)) + 1;
								if(index >= plugin.KillStreak.size()){
								    index = 0;
								}
								String nextGun = plugin.KillStreak.get(index);
								s.setLine(3, nextGun);
								s.update();
								player.getClass(ci).setKillStreak(s.getLine(3));
							}
							break;

						default:
							break;
						}
					}
				}
			}
		}
		
		
	}
	
}
