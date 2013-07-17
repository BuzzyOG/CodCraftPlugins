package com.codcraft.weapons;

import org.bukkit.Material;
import org.bukkit.event.block.Action;

public class Weapon {
	
	private final String name;
	private int ammo;
	private int damage;
	private Material mat;
	private Action action;
	private int rpm = 0;
	private int reloads;
	private int reloadTime;

	public Weapon(String name) {
		this.name = name;
	}
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Material getMat() {
		return mat;
	}
	public void setMat(Material mat) {
		this.mat = mat;
	}
	public int getAmmo() {
		return ammo;
	}
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	public String getName() {
		return name;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		if(damage <= -1 || damage >= 21) {
			return;
		}
		this.damage = damage;
	}
	public int getRpm() {
		return rpm;
	}
	public void setRpm(int rpm) {
		this.rpm = rpm;
	}
	public int getReloads() {
		return reloads;
	}
	public void setReloads(int reloads) {
		this.reloads = reloads;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}
}
