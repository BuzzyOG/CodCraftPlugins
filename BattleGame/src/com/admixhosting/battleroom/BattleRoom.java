package com.admixhosting.battleroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.api.services.CCGamePlugin;
import com.admixhosting.battleroom.command.TeamCommand;
import com.admixhosting.battleroom.game.BattleGame;
import com.admixhosting.battleroom.game.BattlePlayer;
import com.admixhosting.battleroom.listener.GameListener;
import com.admixhosting.battleroom.lobby.BGUpdate;
import com.admixhosting.battleroom.weapons.CallMedic;
import com.admixhosting.battleroom.weapons.DragonsBreath;
import com.admixhosting.battleroom.weapons.Hook;
import com.admixhosting.battleroom.weapons.Lazer;
import com.admixhosting.battleroom.weapons.PermaFrost;
import com.admixhosting.battleroom.weapons.SaveYourSkin;
import com.codcraft.lobby.Lobby;
import com.codcraft.lobby.LobbyModule;

public class BattleRoom extends CCGamePlugin {
	
	public CCAPI api;
	public Map<UUID, Vector> firework = new HashMap<UUID, Vector>();
	private Map<String, Integer> i = new HashMap<>();


	public void onEnable() {
		final CCAPI api = (CCAPI) getServer().getPluginManager().getPlugin("CodCraftAPI");
		if(api != null || !(api instanceof CCAPI)) {
			this.api = (CCAPI) api;
		} else {
			getLogger().warning("Could not find API. Disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		getCommand("team").setExecutor(new TeamCommand(this));
		getServer().getPluginManager().registerEvents(new GameListener(this), this);

		setupLobbyUpdate();
		timer();
		api.getModuleForClass(Weapons.class).addWeapon(new Lazer("Lazer", api.getModuleForClass(Weapons.class), this));
		api.getModuleForClass(Weapons.class).addWeapon(new PermaFrost("PermaFrost", this));
		api.getModuleForClass(Weapons.class).addWeapon(new DragonsBreath("DragonsBreath", this));
		api.getModuleForClass(Weapons.class).addWeapon(new Hook("Hook", this));
		api.getModuleForClass(Weapons.class).addWeapon(new SaveYourSkin("SaveYourSkin", this));
		api.getModuleForClass(Weapons.class).addWeapon(new CallMedic("CallMedic", this));
		alwaysDay();

		makegame("FT1", true);
	}
	
	private void alwaysDay() {
		final BattleRoom ffa = this;
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
		        List<Game<?>> games = api.getModuleForClass(GameManager.class).getGamesForPlugin(ffa);
		        for (Game<?> g : games) {
		          World world = Bukkit.getWorld(g.getName());
		          if (world != null)
		            world.setTime(14000);
		        }
			}
		}
		, 0L, 200L);
	}
	
	public void reset(Player p) {
		i.put(p.getName(), 0);
	}
	


	private void timer() {
		final Plugin pl = this;
		final Map<String, BukkitTask> tasks = new HashMap<>();
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			
			
			

			@Override
			public void run() {
				List<TeamPlayer> names = new ArrayList<TeamPlayer>();
				for(Game<?> g : api.getModuleForClass(GameManager.class).getAllGames()) {
					if(g.getPlugin() == pl) {
						BattleGame game = (BattleGame) g;
						if(!game.isFreezeTag()) {
							for(Team t : game.getTeams()) {
								for(TeamPlayer tp : t.getPlayers()) {
									final BattlePlayer bp = (BattlePlayer) tp;
									final Player p = Bukkit.getPlayer(tp.getName());
									if(p == null) {
										names.add(tp);
										game.findTeamWithPlayer(tp).removePlayer(tp);
										break;
									}
									if(!i.containsKey(p.getName())) {
										i.put(p.getName(), 0);
									}
									i.put(p.getName(), i.get(p.getName()) + 1);
									if(bp.getToMove() != null) {
										if(bp.getOldLoc() == null) {
											bp.setOldLoc(p.getLocation());
										}
									}
									if(bp.getOldLoc() == null) {
										bp.setOldLoc(p.getLocation());
									}
									if(bp.getFrozen() == null) {
										bp.setFrozen(true);
									}
									if(checkBlueFreeze(p) || checkRedFreeze(p)) {
										bp.setInBase(true);
										bp.setToMove(null);
										bp.setOnWall(false);
									} else {
										if(bp.getToMove() == null) {
											bp.setToMove(p.getLocation().getDirection());
											tasks.put(p.getName(), Bukkit.getScheduler().runTaskLater(pl, new Runnable() {	
												
												@Override
												public void run() {
													tasks.remove(p.getName());
												}
												
											}, 10));
										} else {
											if(!tasks.containsKey(p.getName())) {
												checkOnWall(p.getLocation(), bp);
												checkOnWall(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 1, p.getLocation().getZ()), bp);
											}
										}

									}
									if(!bp.getOnWall()) {
										bp.setOldLoc(p.getLocation());
									}
									/*if(bp.isPermfrozen()) {
										p.setFlying(true);
										Location loc2 = bp.getOldLoc();
										if(!checkLoc(loc2, p.getLocation())) {
											p.teleport(new Location(loc2.getWorld(), loc2.getX(), loc2.getY(), loc2.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
										}
									}*/
									//Bukkit.broadcastMessage(bp.getName() + ": " + "Frozen: "+ bp.getFrozen() + " PermFrozen: " + bp.isPermfrozen());
									if(bp.getFrozen() || bp.isPermfrozen()) {
										p.setAllowFlight(true);
										p.setFlying(true);
										Location loc2 = bp.getFrozenpoint();
										//Bukkit.broadcastMessage(bp.getName() + " is frozen");
										if(!checkLoc(loc2, p.getLocation())) {
											p.teleport(new Location(loc2.getWorld(), loc2.getX(), loc2.getY(), loc2.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
										}
									} else {
										if(bp.getOnWall() != null) {
											if(bp.getOnWall()) {
												p.setAllowFlight(true);
												p.setFlying(true);
												Location loc2 = bp.getOldLoc();
												//Bukkit.broadcastMessage("Old Loc: " +loc2 + "Current: " + p.getLocation());
												if(!checkLoc(loc2, p.getLocation())) {
													//if(i.get(p.getName()) % 20 == 0) {
														p.teleport(new Location(loc2.getWorld(), loc2.getX(), loc2.getY(), loc2.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
													//}
												}
												
											} else {
												if(bp.getToMove() != null){
													p.setVelocity(bp.getToMove());
												}
											}
										} else {
											bp.setOnWall(false);
										}		
									}
								}
							}
						} else {
							for(Team t : game.getTeams()) {
								for(TeamPlayer tp : t.getPlayers()) {
									final BattlePlayer bp = (BattlePlayer) tp;
									final Player p = Bukkit.getPlayer(tp.getName());
									if(p != null) {
										if(bp.getFrozen() || bp.isPermfrozen()) {
											p.setAllowFlight(true);
											p.setFlying(true);
											Location loc2 = bp.getFrozenpoint();
											//Bukkit.broadcastMessage(bp.getName() + " is frozen");
											if(!checkLoc(loc2, p.getLocation())) {
												p.teleport(new Location(loc2.getWorld(), loc2.getX(), loc2.getY(), loc2.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
											}
										}
									}
								}
							}
						}

						List<Entity> toRemove = new ArrayList<>();
						for(Entity entity : Bukkit.getWorld(game.getName()).getEntities()) {
							if(entity instanceof Snowball || entity instanceof Firework) {

								if(firework.get(entity.getUniqueId()) == null) {
									break;
								}
								entity.setVelocity(firework.get(entity.getUniqueId()));
								if(checkRed(entity) || checkBlue(entity)) {
									toRemove.add(entity);
								}
								if(entity.getTicksLived() <= 8) {
									break;
								}
								if(entity.getLocation().getBlock() != null) {
									if(entity.getLocation().getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR){
										toRemove.add(entity);
									}
									if(entity.getLocation().getBlock().getRelative(BlockFace.WEST).getType() != Material.AIR){
										toRemove.add(entity);
									}
									if(entity.getLocation().getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR){
										toRemove.add(entity);
									}
									if(entity.getLocation().getBlock().getRelative(BlockFace.SOUTH).getType() != Material.AIR){
										toRemove.add(entity);
									}
									if(entity.getLocation().getBlock().getRelative(BlockFace.UP).getType() != Material.AIR) {
										toRemove.add(entity);
									}
									if(entity.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR){
										toRemove.add(entity);
									}
								} else {
								}
								
							}
						}
						Bukkit.getWorld(game.getName()).getEntities().removeAll(toRemove);
						for(Team t : game.getTeams()) {
							for(TeamPlayer tp : names) {
								t.removePlayer(tp);
							}
						}

					}
				}
				
			}


		}, 0, 1);
		
	}
	private void checkOnWall(Location loc, BattlePlayer bp) {
		if(loc.getBlock().getRelative(BlockFace.EAST).getType() != Material.AIR){
			bp.setOnWall(true);
		}
		if(loc.getBlock().getRelative(BlockFace.WEST).getType() != Material.AIR){
			bp.setOnWall(true);
		}
		if(loc.getBlock().getRelative(BlockFace.NORTH).getType() != Material.AIR){
			bp.setOnWall(true);
		}
		if(loc.getBlock().getRelative(BlockFace.SOUTH).getType() != Material.AIR){
			bp.setOnWall(true);
		}
		if(loc.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR) {
			bp.setOnWall(true);
		}
		if(loc.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR){
			bp.setOnWall(true);
		}
	}
	
	public boolean checkBlueFreeze(Entity p) {
		if(p == null) {
			return true;
		}
		//return isInside(p, -340, 70, -414, -416, 41, -388);
		Location loc1 = new Location(p.getWorld(), 561, 116, -317);
		Location loc2 = new Location(p.getWorld(), 594, 138, -283);
		return isInside(p, loc1, loc2);
	}

	public boolean checkRedFreeze(Entity p) {
		if(p == null) {
			return false;
		}

		//return isInside(p, -372, 66, -288, -343, 53, -262);
		Location loc1 = new Location(p.getWorld(), 557, 119, -419);
		Location loc2 = new Location(p.getWorld(), 595, 138, -454);
		return isInside(p, loc1, loc2);
	}
	
	
	public boolean checkBlue(Entity p) {
		return isInside(p, -372, 140, 547, -414, 117, 511);
	}



	public boolean checkRed(Entity p) {
		return isInside(p, -378, 140, 409, -410, 117, 374);
		
	}



	public boolean checkLoc(Location loc, Location loc2) {
		if(loc.getBlockX() == loc2.getBlockX()) {
			if(loc.getBlockY() == loc2.getBlockY()) {
				if(loc.getBlockZ() == loc2.getBlockZ()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isInside(Entity p, double maxX, double maxY, double maxZ, double minX, double minY, double minZ) {
		Location loc = p.getLocation();
		if(loc.getX() < minX || loc.getX() > maxX)
			return false;
		if(loc.getZ() < minZ || loc.getZ() > maxZ)
			return false;
		if(loc.getY() < minY || loc.getY() > maxY)
			return false;
		return true;
	}
	
	private boolean isInside(Entity p, Location loc1, Location loc2) {
		Location loc = p.getLocation();
		double minX;
		double maxX;
		double maxZ;
		double minZ;
		double maxY;
		double minY;
		if(loc1.getX() > loc2.getX()) {
			maxX = loc1.getX();
			minX = loc2.getX();
		} else {
			maxX = loc2.getX();
			minX = loc1.getX();
		}
		if(loc1.getY() > loc2.getY()) {
			maxY = loc1.getY();
			minY = loc2.getY();
		} else {
			maxY = loc2.getY();
			minY = loc1.getY();
		}
		if(loc1.getZ() > loc2.getZ()) {
			maxZ = loc1.getZ();
			minZ = loc2.getZ();
		} else {
			maxZ = loc2.getZ();
			minZ = loc1.getZ();
		}
		
		if(loc.getX() < minX || loc.getX() > maxX)
			   return false;  
		if(loc.getZ() < minZ || loc.getZ() > maxZ)
			   return false;
		if(loc.getY() < minY || loc.getY() > maxY)
			   return false;   
		return true;		
	}

	@Override
	public String getTag() {
		return "BattleRoom";
	}

	@Override
	public void makegame(String[] args) {
		BattleGame game = new BattleGame(this);
		game.setName(args[0]);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}
	
	public void makegame(String name, Boolean freezetag) {
		BattleGame game = new BattleGame(this);
		game.setName(name);
		game.setFreezeTag(freezetag);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}
	
	private void setupLobbyUpdate() {
		final BattleRoom plugin = this;
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			
			public void run() {
				
				GameManager gm = (GameManager)api.getModuleForClass(GameManager.class);
		        List<Game<?>> games = gm.getGamesForPlugin(plugin);
		        LobbyModule lm = api.getModuleForClass(LobbyModule.class);
		       
		        for(Entry<Integer, Lobby> s : lm.getLobbys().entrySet()) {
		        	for(Game<?> g : games) {
		        		if(s.getValue().getName().equalsIgnoreCase(g.getName())) {
		        			lm.setUpdateCode(s.getKey(), new BGUpdate(plugin, s.getKey()));
		        		}
		        	}
		        	
		        }

		      }
		    }
		    , 80L);
	}
	

}
