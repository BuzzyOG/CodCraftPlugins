package com.codcraft.endersshop.shop;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.endersshop.EndersShop;

public class DefaultItem extends Item {
	
	private Material mat;
	private final String ItemName;
	private final int price;
	private String permission;
	private String boughtm;
	private int position;
	private List<String> lore;
	private EndersShop plugin;
	
	public DefaultItem(String name, String itemName, int price, Material mat, String permission, EndersShop plugin) {
		super(name);
		this.ItemName = itemName;
		this.price = price;
		this.permission = permission;
		this.mat = mat;
		this.plugin = plugin;
	}

	public String getItemName(Player p) {
		return ItemName;
	}

	public int getPrice(Player p) {
		return price;
	}

	public String getPermission(Player p) {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getBoughtMessage(Player p) {
		return boughtm;
	}

	public void setBoughtm(String boughtm) {
		this.boughtm = boughtm;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition(Player p) {
		return position;
	}

	public Material getMaterial(Player p) {
		return mat;
	}

	public void setMat(Material mat) {
		this.mat = mat;
	}

	@Override
	public String getPermision(Player p) {
		return permission;
	}
	
	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	@Override
	public List<String> getLore(Player p) {
		return lore;
	}

	@Override
	public void onBought(Player p) {
		p.sendMessage(getBoughtMessage(p));
	}

	@Override
	public void onBuy(Player p) {
		CCPlayerModule ccpm = plugin.api.getModuleForClass(CCPlayerModule.class);
		CCPlayer ccp = ccpm.getPlayer(p);
		if(!p.hasPermission(this.getPermision(p))) {
			Bukkit.broadcastMessage(""+ccp.getCredits());
			if(ccp.getCredits() >= this.getPrice(p)) {
				ccp.removeCredits(getPrice(p));
				this.onBought(p);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add " + this.getPermision(p));
			} else {
				p.sendMessage("You don't have enough credits for "  + getName());
			}
		} else {
			p.sendMessage("You already have "  + getName());
		}
		
	}

}
