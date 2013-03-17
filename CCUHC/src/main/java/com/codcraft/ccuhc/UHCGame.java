package com.codcraft.ccuhc;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;

import net.minecraft.server.v1_5_R1.MinecraftServer;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_5_R1.CraftServer;
import org.bukkit.craftbukkit.v1_5_R1.CraftWorld;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
public class UHCGame extends Game<UHC> {

	
	
	public UHCGame(UHC instance) {
		super(instance);
		Team team = new Team("main");
		team.setMaxPlayers(24);
		addTeam(team);
		addListener(new UHClistener(instance));
		addListener(new RecipeHandler(instance));
		
		
	}

	@Override
	public void initialize() {
		Long longint = new Random().nextLong();
		plugin.i = plugin.i+1;
		WorldCreator world = new WorldCreator(getName()+plugin.i);
		world.seed(longint);
		world.generateStructures(true);
		Bukkit.createWorld(world);
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
		World w = Bukkit.getWorld(getName()+plugin.i);
		Bukkit.broadcastMessage(w.getName());
		for(Player p : w.getPlayers()) {
			Bukkit.broadcastMessage(p.getName());
			p.teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
		}
		for(Chunk c : w.getLoadedChunks()) {
			c.unload(true, false);
		}
        forceUnloadWorld(w);
        if(Bukkit.getWorld(getName()) != null) {
        	System.out.println("did not unload!");
        }
        //deleteWorld(w.getName());

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
