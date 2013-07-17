package com.codcraft.shop;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.codcraft.shop.CCShop.Type;

public class CCShopItem {
	
	private String addition;
	
	private Type type1;
	
	private String name;
	
	private Integer price;
	
	private String boughtmessage;
	
	private String denyboughtmessage = ChatColor.DARK_BLUE+"Can not buy this item!";
	
	private Material mat;
	
	public String Sign1info1;
	public String Sign1info2;
	public String Sign1info3;
	public String Sign1info4;
	
	public String Sign2info1;
	public String Sign2info2;
	public String Sign2info3;
	public String Sign2info4;
	
	public String Sign3info1;
	public String Sign3info2;
	public String Sign3info3;
	public String Sign3info4;
	
	
	public Material getMaterial() {
		return mat;
	}

	public void setMaterial(String mat) {
		this.mat = Material.valueOf(mat);
	}



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
		return addition;
	}

	public void setPermisison(String permisison) {
		if(permisison == null) {
			throw new IllegalArgumentException("permissions can not be null!");
		}
		this.addition = permisison;
	}

	public Type getType1() {
		return type1;
	}

	public void setType1(Type type1) {
		this.type1 = type1;
	}

}
