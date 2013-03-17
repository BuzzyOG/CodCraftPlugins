package com.codcraft.lobby;


import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyMaker implements Listener {
	
	private CCLobby plugin;
	
	private static Step step = Step.NAME;
	public static String name;
	public static String server;
	private Location block1;
	private Location block2;
	private Location sign1;
	private Location sign2;
	
	public LobbyMaker(CCLobby plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onIneract(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.WOOD_SWORD) {
				if(step == Step.Block1) {
					block1 = e.getClickedBlock().getLocation();
					setStep(e.getPlayer(), Step.Block2);
					e.setCancelled(true);
				} else if(step == Step.Block2) {
					block2 = e.getClickedBlock().getLocation();
					setStep(e.getPlayer(), Step.Sign1);
					e.setCancelled(true);
				} else if (step == Step.Sign1) {
					sign1 = e.getClickedBlock().getLocation();
					setStep(e.getPlayer(), Step.Sign2);
					e.setCancelled(true);
				} else if(step == Step.Sign2) {
					sign2 = e.getClickedBlock().getLocation();
					setStep(e.getPlayer(), Step.NAME);
					save(e.getPlayer());
					e.setCancelled(true);
				}
			}
		}
		
	}
	
	private void save(Player p) {
		File file = new File(plugin.getDataFolder(), "games.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection name = config.createSection("Games."+LobbyMaker.name);
		name.set("Name", LobbyMaker.name);
		name.set("Server", server);
		ConfigurationSection block1 = name.createSection("Block1");
		block1.set("x", this.block1.getX());
		block1.set("y", this.block1.getY());
		block1.set("z", this.block1.getZ());
		name.set("Block1", block1);
		ConfigurationSection block2 = name.createSection("Block2");
		block2.set("x", this.block2.getX());
		block2.set("y", this.block2.getY());
		block2.set("z", this.block2.getZ());
		name.set("Block2", block2);
		ConfigurationSection sign1 = name.createSection("sign1");
		sign1.set("x", this.sign1.getX());
		sign1.set("y", this.sign1.getY());
		sign1.set("z", this.sign1.getZ());
		name.set("sign1", sign1);
		ConfigurationSection sign2 = name.createSection("sign2");
		sign2.set("x", this.sign2.getX());
		sign2.set("y", this.sign2.getY());
		sign2.set("z", this.sign2.getZ());
		name.set("sign2", sign2);
		p.sendMessage("Save Complete");
		
		
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setStep(Player p, Step step) {
		LobbyMaker.step = step;
		if(LobbyMaker.step == Step.NAME) {
			p.sendMessage("Please Specify a name");
		}
		if(LobbyMaker.step == Step.SERVER) {
			p.sendMessage("Please Specify a server name");
		}
		if(LobbyMaker.step == Step.Block1) {
			p.sendMessage("Please Click the first block");
		}
		if(LobbyMaker.step == Step.Block2) {
			p.sendMessage("Please Click the second block");
		}
		if(LobbyMaker.step == Step.Sign1) {
			p.sendMessage("Please Click the Sign Block1");
		}
		if(LobbyMaker.step == Step.Sign2) {
			p.sendMessage("Please Click the Sign Block2");
		}
	}
	
	public static Step getStep() {
		return step;
	}
	
	
	
	
	public enum Step {
		FIRST,
		SERVER,
		NAME,
		Block1,
		Block2,
		Sign1,
		Sign2,
		
	}

}
