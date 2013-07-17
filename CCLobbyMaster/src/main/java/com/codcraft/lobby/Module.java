package com.codcraft.lobby;

import org.bukkit.Location;

public class Module {
	
	public String server;
	
	public Location Block1;
	
	public Location Block2;
	
	public Location SignBlock1;
	
	public Location SignBlock2;
	
	public String name;
	
	public String IP;
	
	public int port;

	public Location getSignBlock2() {
		return SignBlock2;
	}

	public void setSignBlock2(Location signBlock2) {
		SignBlock2 = signBlock2;
	}

	public Location getSignBlock() {
		return SignBlock1;
	}

	public void setSignBlock1(Location signBlock) {
		SignBlock1 = signBlock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getBlock2() {
		return Block2;
	}

	public void setBlock2(Location block2) {
		Block2 = block2;
	}

	public Location getBlock1() {
		return Block1;
	}

	public void setBlock1(Location block1) {
		Block1 = block1;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
	
	
	
	
	
	
	

}
