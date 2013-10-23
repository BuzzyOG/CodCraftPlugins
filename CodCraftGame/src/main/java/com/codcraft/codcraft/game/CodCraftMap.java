package com.codcraft.codcraft.game;

public class CodCraftMap {
	
	private final String name;
	private Integer time = null;
	private boolean weather;
	private boolean blockDamage;
	private boolean creatureSpawn;
	

	public CodCraftMap(String name) {
		this.name = name;
	}
	
	public CodCraftMap(String name, int time, boolean weather, boolean blockDamage, boolean creatureSpawn) {
		this.name = name;
		this.time = time;
		this.weather = weather;
		this.blockDamage = blockDamage;
		this.creatureSpawn = creatureSpawn;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public boolean isWeather() {
		return weather;
	}

	public void setWeather(boolean weather) {
		this.weather = weather;
	}

	public boolean isBlockDamage() {
		return blockDamage;
	}

	public void setBlockDamage(boolean blockDamage) {
		this.blockDamage = blockDamage;
	}

	public boolean isCreatureSpawn() {
		return creatureSpawn;
	}

	public void setCreatureSpawn(boolean creatureSpawn) {
		this.creatureSpawn = creatureSpawn;
	}
	
	
	
	
	

}
