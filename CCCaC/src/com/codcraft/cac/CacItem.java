package com.codcraft.cac;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.model.inventory.Item;
import com.codcraft.codcraftplayer.CCPlayerModule;

public class CacItem extends Item {
	
	private String name;
	private ItemStack itemstack;
	private int position;
	private List<String> lore;
	private Cac plugin;

	public CacItem(String name, int position, ItemStack itemstack, List<String> lore, Cac plugin) {
		this.name = name;
		this.position = position;
		this.itemstack = itemstack;
		this.lore = lore;
		this.plugin = plugin;
	}

	@Override
	public ItemStack getItem(Player p) {
		return itemstack;
	}

	@Override
	public int getPosition(Player p) {
		return position;
	}

	@Override
	public List<String> getLore(Player p) {
		return lore;
	}

	@Override
	public void onClick(Player p) {
		CCPlayerModule ccpm = plugin.api.getModuleForClass(CCPlayerModule.class);
		
	}

}
