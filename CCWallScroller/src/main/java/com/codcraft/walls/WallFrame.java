package com.codcraft.walls;



import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCModule;

public class WallFrame extends CCModule {

	private WS plugin;
	private int x;
	private int y;

	public WallFrame(CCAPI api, WS plugin, int x, int y) {
		super(api);
		this.plugin = plugin;
		this.x = x;
		this.y = y;
	}
	
	public boolean setPixels(MaterialType[][] pixels, Board board) {
		for(int i = 0; i < x; i++) {
			for(int ii = 0; ii < y; ii++) {
				MaterialType b = pixels[i][ii];
				if(b == null) {
					b = new MaterialType(Material.WOOL, (byte) 15);
					pixels[i][ii] = b;
				}
			}
		}
		return board.renderBoard(pixels);
	}

	@Override
	public void starting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closing() {
		// TODO Auto-generated method stub
		
	}

}
