package com.codcraft.ccuhc;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.server.v1_6_R2.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_6_R2.CraftServer;
import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.codcraft.ccuhc.states.InGameStates;
import com.codcraft.ccuhc.states.LobbyState;


public class UHCGame extends Game<UHC> {
	
	private int radius = 1000;
	
	private String LobbyLeader = "";
	
	public List<GameType> types;

	public Map<String, List<String>> invited = new HashMap<String, List<String>>();
	
	public UHCGame(UHC instance) {
		super(instance);
		addListener(new RecipeHandler(instance));
		knownStates.put(new LobbyState(this).getId(), new LobbyState(this));
		knownStates.put(new InGameStates(this).getId(), new InGameStates(this));
		setState(new LobbyState(this));
	}

	@Override
	public void initialize() {
		Long longint = new Random().nextLong();
		Team team = new Team("main");
		team.setMaxPlayers(24);
		addTeam(team);
		types = new ArrayList<>();
		WorldCreator world = new WorldCreator(getId());
		world.seed(longint);
		world.generateStructures(true);
		Bukkit.createWorld(world);
		setState(new LobbyState(this));
	}

	public void BuildBox(World world) {
		System.out.println("Starting");
		System.out.println("Starting X+");
		for(int x = radius * -1; x < radius; x++) {
			for(int y = 0; y < 256; y++) {
				world.getBlockAt(x, y, radius).setType(Material.BEDROCK);
			}
		}
		System.out.println("Starting X-");
		for(int x = radius * -1; x < radius; x++) {
			for(int y = 0; y < 256; y++) {
				world.getBlockAt(x, y, radius* -1).setType(Material.BEDROCK);
			}
		}

		System.out.println("Starting Z+");
		for(int z = radius * -1; z < radius; z++) {
			for(int y = 0; y < 256; y++) {
				world.getBlockAt(radius, y, z).setType(Material.BEDROCK);
			}
		}
		System.out.println("Starting Z-");
		for(int z = radius * -1; z < radius; z++) {
			for(int y = 0; y < 256; y++) {
				world.getBlockAt(radius * -1, y, z).setType(Material.BEDROCK);
			}
		}

		System.out.println("Finsihed");
	}

	@Override
	public void preStateSwitch(GameState<UHC> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<UHC> state) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deinitialize() {
		System.out.println("trying to unload");
		World w = Bukkit.getWorld(getId());
		Bukkit.broadcastMessage(w.getName());
		for(Player p : w.getPlayers()) {
			Bukkit.broadcastMessage(p.getName());
			p.teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
		}
		for(Chunk c : w.getLoadedChunks()) {
			c.unload(true, false);
		}
        forceUnloadWorld(w);
        if(Bukkit.getWorld(getId()) != null) {
        	System.out.println("did not unload!");
        }
        //deleteWorld(w.getName());
        regenerateID();
        teams.clear();

	}
	
	
	private void forceUnloadWorld(World world)
	{
		world.setAutoSave(false);
		for ( Player player : world.getPlayers() )
			player.kickPlayer("World is being deleted... and you were in it!");

		// formerly used server.unloadWorld at this point. But it was sometimes failing, even when I force-cleared the player list
		CraftServer server = (CraftServer)plugin.getServer();
		CraftWorld craftWorld = (CraftWorld)world;

		try
		{
			Field f = server.getClass().getDeclaredField("worlds");
			f.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, World> worlds = (Map<String, World>)f.get(server);
			worlds.remove(world.getName().toLowerCase());
			f.setAccessible(false);
		}
		catch ( IllegalAccessException ex )
		{
			ex.printStackTrace();
		}
		catch  ( NoSuchFieldException ex )
		{
			ex.printStackTrace();
		}

		MinecraftServer ms = getMinecraftServer();
		ms.worlds.remove(ms.worlds.indexOf(craftWorld.getHandle()));
	}

	/*private boolean deleteWorld(String worldName)
	{
		boolean allGood = true;

		File folder = new File(plugin.getServer().getWorldContainer() + File.separator + worldName);
		try
		{
			if ( folder.exists() && !delete(folder) )
				allGood = false;
		}
		catch ( Exception e )
		{
			System.out.println("An error occurred when deleting the " + worldName + " world: " + e.getMessage());
		}

		return allGood;
	}*/
	
	/*private boolean delete(File folder)
	{
		if ( !folder.exists() )
			return true;
		boolean retVal = true;
		if (folder.isDirectory())
			for (File f : folder.listFiles())
				if (!delete(f))
				{
					retVal = false;
					//plugin.log.warning("Failed to delete file: " + f.getName());
				}
		return folder.delete() && retVal;
	}*/
	
	protected MinecraftServer getMinecraftServer()
	{
		CraftServer server = (CraftServer)plugin.getServer();
		return server.getServer();
	}

	public int getRadius() {
		return radius;
	}

	public boolean setRadius(int radius) {
		if(radius < 100 || radius > 2500) {
			return false;
		}
		this.radius = radius;
		return true;
	}

	public String getLobbyLeader() {
		return LobbyLeader;
	}

	public void setLobbyLeader(String lobbyLeader) {
		LobbyLeader = lobbyLeader;
	}
	
	/*private String build() {
	    HollowCylinderBrush hcb = new HollowCylinderBrush(256);
	    try {
	      hcb.build(new EditSession(new BukkitWorld(Bukkit.getWorld(getName()+plugin.i)), 2147483647), 
	        new Vector(500, 0, 500), 
	        new SingleBlockPattern(new BaseBlock(7, 0)), 50 + 5);
	      return "Border complete!"; 
	      } catch (MaxChangedBlocksException e) {
	    }
	    return "Border generation failed: WorldEdit max edit blocks reached";
	  }*/
	

}
