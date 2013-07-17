package com.codcraft.CCSG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.ImmutableMap;

public class ChestManager {
	
	private SurvialGames plugin;
	private Map<Material, Integer> Level1Items = new HashMap<>();
	private Map<Material, Integer> Level2Items = new HashMap<>();
	private Map<Material, Integer> Level3Items = new HashMap<>();
	private Map<Material, Integer> Level4Items = new HashMap<>();

	public ChestManager(SurvialGames plugin) {
		this.plugin = plugin;
	}
	
	public void fillChest(Chest c, String s) {
		switch (s.toLowerCase()) {
		case "1":
			fillChest(c, Level1Items);
			break;
		case "2":
			fillChest(c, Level2Items);
			break;
		case "3":
			fillChest(c, Level3Items);
			break;
		case "4":
			fillChest(c, Level4Items);
			break;
		default:
			break;
		}
	}
	
	public void fillChest(Chest chest, Map<Material, Integer> list) {
		Inventory chestInventory = chest.getBlockInventory();
		Random rand = new Random();

		for(int i = 0; i < 27; i++) {
			List<Material> keys = new ArrayList<Material>(list.keySet());
			Material randomKey = keys.get(rand.nextInt(keys.size()));
			Integer value = list.get(randomKey);
			ItemStack itemtoadd = new ItemStack(randomKey, rand.nextInt(value - 1) + 1);
			int ii = rand.nextInt(8);
			if(i + ii > 27) {
				return;
			} else {
				i = i + ii;
				chestInventory.setItem(i, itemtoadd);
			}
		}
	}
	
	
	
	public Map<Material, Integer> getLevel4Items() {
		return ImmutableMap.copyOf(Level4Items);
	}
	
	public void addLevel4Item(Material mat) {
		Level4Items.put(mat, 1);
	}
	
	public void addLevel4Item(Material mat, Integer max) {
		Level4Items.put(mat, max);
	}
	
	public void removeLevel4Item(Material mat) {
		Level4Items.remove(mat);
	}
	
	
	public Map<Material, Integer> getLevel3Items() {
		return ImmutableMap.copyOf(Level3Items);
	}
	
	public void addLevel3Item(Material mat) {
		Level3Items.put(mat, 1);
	}
	
	public void addLevel3Item(Material mat, Integer max) {
		Level3Items.put(mat, max);
	}
	
	public void removeLevel3Item(Material mat) {
		Level3Items.remove(mat);
	}
	
	public Map<Material, Integer> getLevel2Items() {
		return ImmutableMap.copyOf(Level2Items);
	}
	
	public void addLevel2Item(Material mat) {
		Level2Items.put(mat, 1);
	}
	
	public void addLevel2Item(Material mat, Integer max) {
		Level2Items.put(mat, max);
	}
	
	public void removeLevel2Item(Material mat) {
		Level2Items.remove(mat);
	}
	
	public Map<Material, Integer> getLevel1Items() {
		return ImmutableMap.copyOf(Level1Items);
	}
	
	public void addLevel1Item(Material mat) {
		Level1Items.put(mat, 1);
	}
	
	public void addLevel1Item(Material mat, Integer max) {
		Level1Items.put(mat, max);
	}
	
	public void removeLevel1Item(Material mat) {
		Level1Items.remove(mat);
	}
	
	

}
