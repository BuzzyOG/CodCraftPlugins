package com.codcraft.shop;

import org.bukkit.ChatColor;

public class CCShopItem {
	
	private String permisison;
	
	private String name;
	
	private Integer price;
	
	private String boughtmessage;
	
	private String denyboughtmessage = ChatColor.DARK_BLUE+"Can not buy this item!";
	
	
	

	public String getDenyboughtmessage() {
		return denyboughtmessage;
	}

	public void setDenyboughtmessage(String denyboughtmessage) {
		this.denyboughtmessage = denyboughtmessage;
	}

	public String getBoughtmessage() {
		return boughtmessage;
	}

	public void setBoughtmessage(String boughtmessage) {
		this.boughtmessage = boughtmessage;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermisison() {
		return permisison;
	}

	public void setPermisison(String permisison) {
		if(permisison == null) {
			throw new IllegalArgumentException("permissions can not be null!");
		}
		this.permisison = permisison;
	}

}
