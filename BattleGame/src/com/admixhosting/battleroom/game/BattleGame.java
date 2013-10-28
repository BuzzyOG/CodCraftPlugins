package com.admixhosting.battleroom.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.model.hook.Hook;
import com.CodCraft.api.model.hook.Rotation;
import com.CodCraft.api.model.hook.TeamHook;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.ScoreBoard;
import com.admixhosting.battleroom.BattleRoom;
import com.admixhosting.battleroom.states.InGameState;
import com.admixhosting.battleroom.states.LobbyState;

public class BattleGame extends Game<BattleRoom> {
	
	private List<String> inLobby = new ArrayList<>();
	public Map<String, Team> requestedTeams = new HashMap<String, Team>();
	/*public Map<String, Location> bluespawn = new HashMap<>();
	public Map<String, Location> redspawn = new HashMap<>();*/
	private List<Location> stars = new ArrayList<>();
	public final int xCenter = 578;
	public final int yCenter = 122;
	public final int zCenter = -369;
	private boolean freezeTag;

	public BattleGame(BattleRoom instance) {
		super(instance);
	}
	
	
	@Override
	public Team findTeamWithPlayer(Player player) {
		if(requestedTeams.containsKey(player.getName())) {
			return requestedTeams.get(player.getName());
		}
		for(Team team : teams) {
			if(team.containsPlayer(player)) {
				return team;
	        }
	    }
		return null;
	}
	
	@Override
	public Team findTeamWithPlayer(TeamPlayer player) {
		if(requestedTeams.containsKey(player.getName())) {
			return requestedTeams.get(player.getName());
		}
		for(Team team : teams) {
			if(team.containsPlayer(player)) {
				return team;
		    }
		}
		return null;
	}
	
	@Override
	public boolean containsPlayer(Player p) {
		if(requestedTeams.containsKey(p.getName())) {
			return true;
		}
	    for(Team team : teams) {
	    	if(team.containsPlayer(p)) {
	    		return true;
	        }
	    }
	    return false;
	}
	
	@Override
	 public boolean containsPlayer(TeamPlayer player) {
		if(requestedTeams.containsKey(player.getName())) {
			return true;
		}
	    for(Team team : teams) {
	    	if(team.containsPlayer(player)) {
	    		return true;
	        }
	    }
	    return false;
	}

	@Override
	public void initialize() {
		
		WorldCreator creator = new WorldCreator(getName());
		if(!isFreezeTag()) {
			setCurrentmap("Battle");
		}
		addHook(new TeamHook(this));
		if(isFreezeTag()) {
			
			addHook(new Rotation(this));
		}

		Bukkit.createWorld(creator);
		/*YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/FreezeTag/spawns.yml"));
		for(String map : config.getConfigurationSection("blue").getKeys(false)) {
			int x = Integer.parseInt(config.getString("blue."+map+".x"));
			int y = Integer.parseInt(config.getString("blue."+map+".y"));
			int z = Integer.parseInt(config.getString("blue."+map+".z"));
			bluespawn.put(map, new Location(Bukkit.getWorld(getName()), x, y, z));
		}
		for(String map : config.getConfigurationSection("red").getKeys(false)) {
			int x = Integer.parseInt(config.getString("red."+map+".x"));
			int y = Integer.parseInt(config.getString("red."+map+".y"));
			int z = Integer.parseInt(config.getString("red."+map+".z"));
			redspawn.put(map, new Location(Bukkit.getWorld(getName()), x, y, z));
		*/
		knownStates.put(new LobbyState(this).getId(), new LobbyState(this));
		knownStates.put(new InGameState(this).getId(), new InGameState(this));
		setState(new LobbyState(this));
		ScoreBoard scoreboard = plugin.api.getModuleForClass(ScoreBoard.class);
		scoreboard.createScoreBoardForGame(this);
		scoreboard.setObjective("Score", this);
		scoreboard.setStringScoreForBoard(ChatColor.DARK_RED+"Red", 0, this);
		scoreboard.setStringScoreForBoard(ChatColor.RED+"Red_Frozen", 0, this);
		scoreboard.setStringScoreForBoard(ChatColor.DARK_BLUE+"Blue", 0, this);
		scoreboard.setStringScoreForBoard(ChatColor.BLUE+"Blue_Frozen", 0, this);
		if(!isFreezeTag()) {
			genStars();
		}

	}
	
	@SuppressWarnings("deprecation")
	public void updateGUI() {
		GUI gui = getPlugin().api.getModuleForClass(GUI.class);
		TreeMap<String, String> sorted = new TreeMap<>();
		//ArrayList<String> tab = new ArrayList<>();
		int i = 0;
		for(Entry<String, Team> en : requestedTeams.entrySet()) {
			i++;
			Player p = Bukkit.getPlayer(en.getKey());
			if(p != null) {
				String prefix = getPlugin().chat.getGroupPrefix(Bukkit.getWorld(getName()), getPlugin().permi.getPrimaryGroup(p));
				prefix = ChatColor.translateAlternateColorCodes('&', prefix);
				String full = prefix+en.getValue().getColor()+p.getName();
				if(full.length() >= 17) {
					sorted.put(""+i, full.substring(0, 15));
				} else {
					sorted.put(""+i, full);
				}

			}
		}
		i = 0;
		for(Team t : getTeams()) {
			for(TeamPlayer tp : t.getPlayers()) {
				i++;
				Player p = Bukkit.getPlayer(tp.getName());
				if(p != null) {
					String prefix = getPlugin().chat.getGroupPrefix(Bukkit.getWorld(getName()), getPlugin().permi.getPrimaryGroup(p));
					prefix = ChatColor.translateAlternateColorCodes('&', prefix);
					String full = prefix+t.getColor()+tp.getName();
					if(full.length() >= 17) {
						sorted.put(""+i, full.substring(0, 15));
					} else {
						sorted.put(""+i, full);
					}

				}

			}
		}
		for(Team t : getTeams()) {
			for(TeamPlayer tp : t.getPlayers()) {
				Player p = Bukkit.getPlayer(tp.getName());
				if(p != null) {
					gui.updateplayerlist(p, sorted);
				}

			}
		}
		for(Entry<String, Team> en : requestedTeams.entrySet()) {
			Player p = Bukkit.getPlayer(en.getKey());
			if(p != null) {
				gui.updateplayerlist(p, sorted);
			}
		}
	}
	
	public List<String> getInLobby() {
		return inLobby;
	}
	
	public void addInPlayer(Player p) {
		inLobby.add(p.getName());
	}
	
	public void removeInLobby(Player p) {
		inLobby.remove(p.getName());
	}

	public Location getRedSpawn() {
		return getTeams().get(1).getSpawn();
	}

	public Location getBlueSpawn() {
		return getTeams().get(0).getSpawn();
	}

	@Override
	public void preStateSwitch(GameState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState state) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkState(GameState state) {
		if(getCurrentState().getId().equalsIgnoreCase(state.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public void deinitialize() {
		for(Hook hook : getHooks()) {
			hook.deinitialize();
			
		}
		getHooks().clear();
		if(!isFreezeTag()) {
			/*new Location(Bukkit.getWorld(this.getName()), -392, 125, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -392, 126, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -392, 127, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -392, 128, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -393, 126, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -393, 127, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -394, 125, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -394, 126, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -394, 127, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -394, 128, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -395, 126, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -395, 127, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -396, 125, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -396, 126, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -396, 127, 511).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -396, 128, 511).getBlock().setType(Material.GLASS);
			
			new Location(Bukkit.getWorld(this.getName()), -392, 125, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -392, 126, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -392, 127, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -392, 128, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -393, 126, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -393, 127, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -394, 125, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -394, 126, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -394, 127, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -394, 128, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -395, 126, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -395, 127, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -396, 125, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -396, 126, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -396, 127, 409).getBlock().setType(Material.GLASS);
			new Location(Bukkit.getWorld(this.getName()), -396, 128, 409).getBlock().setType(Material.GLASS);*/
			clearStars();
		}
		
		teams.clear();

	}

	private void clearStars() {
		for(Location loc : stars) {
			new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ()).getBlock().setType(Material.AIR);
			new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ() - 1).getBlock().setType(Material.AIR);
			new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ() + 1).getBlock().setType(Material.AIR);
			new Location(loc.getWorld(), loc.getX() - 2, loc.getY(), loc.getZ()).getBlock().setType(Material.AIR);
			
			new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ()).getBlock().setType(Material.AIR);
			new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ() - 1).getBlock().setType(Material.AIR);
			new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ() + 1).getBlock().setType(Material.AIR);
			new Location(loc.getWorld(), loc.getX() + 2, loc.getY(), loc.getZ()).getBlock().setType(Material.AIR);
			
			new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ()).getBlock().setType(Material.AIR);
			new Location(loc.getWorld(), loc.getX(), loc.getY() -1, loc.getZ()).getBlock().setType(Material.AIR);
			loc.getBlock().setType(Material.AIR);
		}
		stars.clear();
		
	}
	
	private Location randomSpherePoint(int x, int y, int z, int radios) {
		double u = Math.random();
		double v = Math.random();
		double theta = 2 * Math.PI * u;
		double phi = Math.acos(2 * v -1);
		double x1 = x + (radios * Math.sin(phi) * Math.cos(theta));
		double y1 = y + (radios * Math.sin(phi) * Math.sin(theta));
		double z1 = z + (radios * Math.cos(phi));
		//System.out.println("X " +  x1 + " Y " + y1 + " Z " + z1);
		return new Location(Bukkit.getWorld(getName()), x1, y1, z1);
	}
	
	
	@SuppressWarnings("deprecation")
	private void genStars() {
		Random rnd = new Random();
		int i = rnd.nextInt(40);
		int finalint = i + 10;
		for(int ii = 0; ii < finalint; ii++) {
			//System.out.println("Generating Start" +  ii);
			Location center = randomSpherePoint(xCenter, yCenter, zCenter, rnd.nextInt(39) + 5);
			Location glassleft1 = new Location(center.getWorld(), center.getX() - 1, center.getY(), center.getZ());
			Location glassleft2 = new Location(center.getWorld(), center.getX() - 1, center.getY(), center.getZ() - 1);
			Location glassleft3 = new Location(center.getWorld(), center.getX() - 1, center.getY(), center.getZ() + 1);
			Location glassleft4 = new Location(center.getWorld(), center.getX() - 2, center.getY(), center.getZ());
			
			Location glassright1 = new Location(center.getWorld(), center.getX() + 1, center.getY(), center.getZ());
			Location glassright2 = new Location(center.getWorld(), center.getX() + 1, center.getY(), center.getZ() - 1);
			Location glassright3 = new Location(center.getWorld(), center.getX() + 1, center.getY(), center.getZ() + 1);
			Location glassright4 = new Location(center.getWorld(), center.getX() + 2, center.getY(), center.getZ());
			
			Location slabUP1 = new Location(center.getWorld(), center.getX(), center.getY() + 1, center.getZ());
			Location slabDOWN1 = new Location(center.getWorld(), center.getX(), center.getY() -1, center.getZ());
			
			stars.add(center);
			center.getBlock().setType(Material.BEACON);
			glassleft1.getBlock().setType(Material.GLASS);
			glassleft2.getBlock().setType(Material.GLASS);
			glassleft3.getBlock().setType(Material.GLASS);
			glassleft4.getBlock().setType(Material.GLASS);
			
			glassright1.getBlock().setType(Material.GLASS);
			glassright2.getBlock().setType(Material.GLASS);
			glassright3.getBlock().setType(Material.GLASS);
			glassright4.getBlock().setType(Material.GLASS);
			
			slabUP1.getBlock().setType(Material.STEP);
			slabUP1.getBlock().setData((byte) 7);
			slabDOWN1.getBlock().setType(Material.STEP);
			slabDOWN1.getBlock().setData((byte) 15);
			
			
		}
	}

	public boolean isFreezeTag() {
		return freezeTag;
	}

	public void setFreezeTag(boolean freezeTag) {
		this.freezeTag = freezeTag;
	}


}
