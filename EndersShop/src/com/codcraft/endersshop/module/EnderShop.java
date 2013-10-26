package com.codcraft.endersshop.module;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCModule;
import com.codcraft.endersshop.EndersShop;
import com.codcraft.endersshop.shop.DefaultItem;
import com.codcraft.endersshop.shop.Item;

public class EnderShop extends CCModule {
	
	private List<Item> items = new ArrayList<>();
	public List<Material> mats = new ArrayList<Material>();
	public List<String> lors = new ArrayList<String>();
	
	private EndersShop plugin;

	public EnderShop(CCAPI api, EndersShop plugin) {
		super(api);
		this.plugin = plugin;
	}
	
	public void teleportSetup(){
		mats.add(Material.BOW); // to hub
		mats.add(Material.WOOL); // to codcraft
		mats.add(Material.FEATHER); // to battleroom
		mats.add(Material.ICE); // to freezetag
		
		lors.add("Teleport to Hub");
		lors.add("Teleport to CodCraft");
		lors.add("Teleport to BattleRoom");
		lors.add("Teleport to FreezeTag");
		
		lors.add("§aHUB");
		lors.add("§aCODCRAFT");
		lors.add("§aBATTLEROOM");
		lors.add("§aFREEZETAG");
	}
	
	public Inventory teleportInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "Quick Teleport");
		for(int x = 0; x < 4; x++) {
			List<String> newLore = new ArrayList<String>();
			newLore.add(lors.get(x));
			ItemStack is = new ItemStack(mats.get(x), 1);
			ItemMeta im = is.getItemMeta();
			im.setLore(newLore);
			im.setDisplayName(lors.get(x+4));
			is.setItemMeta(im);
			inv.setItem(x, is);
		}
		return inv;
	}
	
	public Inventory requestInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "BattleShop");
		for(Item item : items) {
			ItemStack is = new ItemStack(item.getMaterial(p), 1);
			ItemMeta im = is.getItemMeta();
			im.setLore(item.getLore(p));
			im.setDisplayName(item.getItemName(p));
			is.setItemMeta(im);
			inv.setItem(item.getPosition(p), is);
		}
		return inv;
		
	}
	
	public Item getItemFromMaterial(Material mat, Player p) {
		for(Item item : items) {
			if(item.getMaterial(p) == mat) {
				return item;
			}
		}
		return null;
	}
	
	public boolean addItem(Item item) {
		for(Item i : items) {
			if(i.getName().equalsIgnoreCase(item.getName())) {
				return false;
			}
			
		}
		items.add(item);
		return true;
	}
	
	public void removeItem(DefaultItem item) {
		items.remove(item);
	}
	
	public boolean makeItem(String name, String itemName, int price, String permisison, Material mat, String boughtmessage, int position, List<String> lore) {
		DefaultItem item = new DefaultItem(name, itemName, price, mat , permisison, plugin, lore);
		item.setBoughtm(boughtmessage);
		item.setPosition(position);
		return addItem(item);
		 
	}

	@Override
	public void starting() {

	}

	@Override
	public void closing() {

	}

}
