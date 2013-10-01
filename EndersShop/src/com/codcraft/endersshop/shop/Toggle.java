package com.codcraft.endersshop.shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codcraft.endersshop.EndersShop;

public class Toggle extends Item {

	private EndersShop plugin;

	public Toggle(String name, EndersShop plugin) {
		super(name);
		this.plugin = plugin;
	}

	@Override
	public String getItemName(Player p) {
		return null;
	}

	@Override
	public Material getMaterial(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPrice(Player p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPosition(Player p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPermision(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLore(Player p) {
		List<String> strings = new ArrayList<>();
		strings.add("Weather you would like to be a medic!");
		return strings;
	}

	@Override
	public String getBoughtMessage(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onBought(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBuy(Player p) {
		// TODO Auto-generated method stub
		
	}

}
