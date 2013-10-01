package com.codcraft.endersshop.shop;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Item {
	
	private String name;
	
	public Item(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract String getItemName(Player p);
	public abstract Material getMaterial(Player p);
	public abstract int getPrice(Player p);
	public abstract int getPosition(Player p);
	public abstract String getPermision(Player p);
	public abstract List<String> getLore(Player p);
	public abstract String getBoughtMessage(Player p);
	public abstract void onBought(Player p);

	public abstract void onBuy(Player p);

}
