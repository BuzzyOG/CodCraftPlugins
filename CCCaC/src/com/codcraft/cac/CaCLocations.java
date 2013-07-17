package com.codcraft.cac;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CaCLocations {
	public HashMap<String, Integer> CaCBox = new HashMap<String, Integer>();
	public HashMap<Integer, ArrayList<Location>> SignLocation = new HashMap<Integer, ArrayList<Location>>();
	public HashMap<Integer, Location> LobbySpawn = new HashMap<Integer, Location>();
	public CaCLocations() {
		SignLocation.put(0, new ArrayList<Location>());
	      SignLocation.put(1, new ArrayList<Location>());
	      SignLocation.put(2, new ArrayList<Location>());
	      SignLocation.put(3, new ArrayList<Location>());
	      SignLocation.put(4, new ArrayList<Location>());
	      SignLocation.put(5, new ArrayList<Location>());
	      SignLocation.put(6, new ArrayList<Location>());
	      SignLocation.put(7, new ArrayList<Location>());
	      SignLocation.put(8, new ArrayList<Location>());
	      SignLocation.put(9, new ArrayList<Location>());
	      SignLocation.put(10, new ArrayList<Location>());
	      SignLocation.put(11, new ArrayList<Location>());
	      SignLocation.put(12, new ArrayList<Location>());
	      SignLocation.put(13, new ArrayList<Location>());
	      SignLocation.put(14, new ArrayList<Location>());
	      SignLocation.put(15, new ArrayList<Location>());
	      SignLocation.put(16, new ArrayList<Location>());
	      SignLocation.put(17, new ArrayList<Location>());
	      SignLocation.put(18, new ArrayList<Location>());
	      SignLocation.put(19, new ArrayList<Location>());
	      // Sign Locations for Lobby 0.3
	      SignLocation.get(0).add(new Location(Bukkit.getWorld("CreateAClass"), -1.6, 7, -0.9));
	      SignLocation.get(0).add(new Location(Bukkit.getWorld("CreateAClass"), -2.6, 7, -0.9));
	      SignLocation.get(0).add(new Location(Bukkit.getWorld("CreateAClass"), -3.6, 7, -0.9));
	      SignLocation.get(0).add(new Location(Bukkit.getWorld("CreateAClass"), -4.6, 7, -0.9));
	      SignLocation.get(0).add(new Location(Bukkit.getWorld("CreateAClass"), -5.6, 7, -0.9));
	      SignLocation.get(0).add(new Location(Bukkit.getWorld("CreateAClass"), -6.6, 7, -0.9));
	      SignLocation.get(0).add(new Location(Bukkit.getWorld("CreateAClass"), -7.6, 7, -0.9));
	      SignLocation.get(0).add(new Location(Bukkit.getWorld("CreateAClass"), -8.6, 7, -0.9));
	      // Sign Location for Lobby 1
	      SignLocation.get(1).add(new Location(Bukkit.getWorld("CreateAClass"), -13.6, 7, -0.9));
	      SignLocation.get(1).add(new Location(Bukkit.getWorld("CreateAClass"), -14.6, 7, -0.9));
	      SignLocation.get(1).add(new Location(Bukkit.getWorld("CreateAClass"), -15.6, 7, -0.9));
	      SignLocation.get(1).add(new Location(Bukkit.getWorld("CreateAClass"), -16.6, 7, -0.9));
	      SignLocation.get(1).add(new Location(Bukkit.getWorld("CreateAClass"), -17.6, 7, -0.9));
	      SignLocation.get(1).add(new Location(Bukkit.getWorld("CreateAClass"), -18.6, 7, -0.9));
	      SignLocation.get(1).add(new Location(Bukkit.getWorld("CreateAClass"), -19.6, 7, -0.9));
	      SignLocation.get(1).add(new Location(Bukkit.getWorld("CreateAClass"), -20.6, 7, -0.9));
	      // Sign Location for Lobby 2
	      SignLocation.get(2).add(new Location(Bukkit.getWorld("CreateAClass"), -25.6, 7, -0.9));
	      SignLocation.get(2).add(new Location(Bukkit.getWorld("CreateAClass"), -26.6, 7, -0.9));
	      SignLocation.get(2).add(new Location(Bukkit.getWorld("CreateAClass"), -27.6, 7, -0.9));
	      SignLocation.get(2).add(new Location(Bukkit.getWorld("CreateAClass"), -28.6, 7, -0.9));
	      SignLocation.get(2).add(new Location(Bukkit.getWorld("CreateAClass"), -29.6, 7, -0.9));
	      SignLocation.get(2).add(new Location(Bukkit.getWorld("CreateAClass"), -30.6, 7, -0.9));
	      SignLocation.get(2).add(new Location(Bukkit.getWorld("CreateAClass"), -31.6, 7, -0.9));
	      SignLocation.get(2).add(new Location(Bukkit.getWorld("CreateAClass"), -32.6, 7, -0.9));
	      // Sign Location for Lobby 3
	      SignLocation.get(3).add(new Location(Bukkit.getWorld("CreateAClass"), -37.6, 7, -0.9));
	      SignLocation.get(3).add(new Location(Bukkit.getWorld("CreateAClass"), -38.6, 7, -0.9));
	      SignLocation.get(3).add(new Location(Bukkit.getWorld("CreateAClass"), -39.6, 7, -0.9));
	      SignLocation.get(3).add(new Location(Bukkit.getWorld("CreateAClass"), -40.6, 7, -0.9));
	      SignLocation.get(3).add(new Location(Bukkit.getWorld("CreateAClass"), -41.6, 7, -0.9));
	      SignLocation.get(3).add(new Location(Bukkit.getWorld("CreateAClass"), -42.6, 7, -0.9));
	      SignLocation.get(3).add(new Location(Bukkit.getWorld("CreateAClass"), -43.6, 7, -0.9));
	      SignLocation.get(3).add(new Location(Bukkit.getWorld("CreateAClass"), -44.6, 7, -0.9));
	      // Sign Location for Lobby 4
	      SignLocation.get(4).add(new Location(Bukkit.getWorld("CreateAClass"), -49.6, 7, -0.9));
	      SignLocation.get(4).add(new Location(Bukkit.getWorld("CreateAClass"), -50.6, 7, -0.9));
	      SignLocation.get(4).add(new Location(Bukkit.getWorld("CreateAClass"), -51.6, 7, -0.9));
	      SignLocation.get(4).add(new Location(Bukkit.getWorld("CreateAClass"), -52.6, 7, -0.9));
	      SignLocation.get(4).add(new Location(Bukkit.getWorld("CreateAClass"), -53.6, 7, -0.9));
	      SignLocation.get(4).add(new Location(Bukkit.getWorld("CreateAClass"), -54.6, 7, -0.9));
	      SignLocation.get(4).add(new Location(Bukkit.getWorld("CreateAClass"), -55.6, 7, -0.9));
	      SignLocation.get(4).add(new Location(Bukkit.getWorld("CreateAClass"), -56.6, 7, -0.9));
	      // Sign Location for Lobby 5
	      SignLocation.get(5).add(new Location(Bukkit.getWorld("CreateAClass"), -61.6, 7, -0.9));
	      SignLocation.get(5).add(new Location(Bukkit.getWorld("CreateAClass"), -62.6, 7, -0.9));
	      SignLocation.get(5).add(new Location(Bukkit.getWorld("CreateAClass"), -63.6, 7, -0.9));
	      SignLocation.get(5).add(new Location(Bukkit.getWorld("CreateAClass"), -64.6, 7, -0.9));
	      SignLocation.get(5).add(new Location(Bukkit.getWorld("CreateAClass"), -65.6, 7, -0.9));
	      SignLocation.get(5).add(new Location(Bukkit.getWorld("CreateAClass"), -66.6, 7, -0.9));
	      SignLocation.get(5).add(new Location(Bukkit.getWorld("CreateAClass"), -67.6, 7, -0.9));
	      SignLocation.get(5).add(new Location(Bukkit.getWorld("CreateAClass"), -68.6, 7, -0.9));
	      // Sign Location for Lobby 6
	      SignLocation.get(6).add(new Location(Bukkit.getWorld("CreateAClass"), -73.6, 7, -0.9));
	      SignLocation.get(6).add(new Location(Bukkit.getWorld("CreateAClass"), -74.6, 7, -0.9));
	      SignLocation.get(6).add(new Location(Bukkit.getWorld("CreateAClass"), -75.6, 7, -0.9));
	      SignLocation.get(6).add(new Location(Bukkit.getWorld("CreateAClass"), -76.6, 7, -0.9));
	      SignLocation.get(6).add(new Location(Bukkit.getWorld("CreateAClass"), -77.6, 7, -0.9));
	      SignLocation.get(6).add(new Location(Bukkit.getWorld("CreateAClass"), -78.6, 7, -0.9));
	      SignLocation.get(6).add(new Location(Bukkit.getWorld("CreateAClass"), -79.6, 7, -0.9));
	      SignLocation.get(6).add(new Location(Bukkit.getWorld("CreateAClass"), -80.6, 7, -0.9));
	      // Sign Location for Lobby 7
	      SignLocation.get(7).add(new Location(Bukkit.getWorld("CreateAClass"), -86, 7, -1));
	      SignLocation.get(7).add(new Location(Bukkit.getWorld("CreateAClass"), -87, 7, -1));
	      SignLocation.get(7).add(new Location(Bukkit.getWorld("CreateAClass"), -88, 7, -1));
	      SignLocation.get(7).add(new Location(Bukkit.getWorld("CreateAClass"), -89, 7, -1));
	      SignLocation.get(7).add(new Location(Bukkit.getWorld("CreateAClass"), -90, 7, -1));
	      SignLocation.get(7).add(new Location(Bukkit.getWorld("CreateAClass"), -91, 7, -1));
	      SignLocation.get(7).add(new Location(Bukkit.getWorld("CreateAClass"), -92, 7, -1));
	      SignLocation.get(7).add(new Location(Bukkit.getWorld("CreateAClass"), -93, 7, -1));
	      // Sign Location for Lobby 8
	      SignLocation.get(8).add(new Location(Bukkit.getWorld("CreateAClass"), -97.6, 7, -0.9));
	      SignLocation.get(8).add(new Location(Bukkit.getWorld("CreateAClass"), -98.6, 7, -0.9));
	      SignLocation.get(8).add(new Location(Bukkit.getWorld("CreateAClass"), -99.6, 7, -0.9));
	      SignLocation.get(8).add(new Location(Bukkit.getWorld("CreateAClass"), -100.6, 7, -0.9));
	      SignLocation.get(8).add(new Location(Bukkit.getWorld("CreateAClass"), -101.6, 7, -0.9));
	      SignLocation.get(8).add(new Location(Bukkit.getWorld("CreateAClass"), -102.6, 7, -0.9));
	      SignLocation.get(8).add(new Location(Bukkit.getWorld("CreateAClass"), -103.6, 7, -0.9));
	      SignLocation.get(8).add(new Location(Bukkit.getWorld("CreateAClass"), -104.6, 7, -0.9));
	      // Sign Location for Lobby 9
	      SignLocation.get(9).add(new Location(Bukkit.getWorld("CreateAClass"), -109.6, 7, -0.9));
	      SignLocation.get(9).add(new Location(Bukkit.getWorld("CreateAClass"), -110.6, 7, -0.9));
	      SignLocation.get(9).add(new Location(Bukkit.getWorld("CreateAClass"), -111.6, 7, -0.9));
	      SignLocation.get(9).add(new Location(Bukkit.getWorld("CreateAClass"), -112.6, 7, -0.9));
	      SignLocation.get(9).add(new Location(Bukkit.getWorld("CreateAClass"), -113.6, 7, -0.9));
	      SignLocation.get(9).add(new Location(Bukkit.getWorld("CreateAClass"), -114.6, 7, -0.9));
	      SignLocation.get(9).add(new Location(Bukkit.getWorld("CreateAClass"), -115.6, 7, -0.9));
	      SignLocation.get(9).add(new Location(Bukkit.getWorld("CreateAClass"), -116.6, 7, -0.9));
	      // Sign Location for Lobby 10
	      SignLocation.get(10).add(new Location(Bukkit.getWorld("CreateAClass"), -109.6, 7, 11.9));
	      SignLocation.get(10).add(new Location(Bukkit.getWorld("CreateAClass"), -110.6, 7, 11.9));
	      SignLocation.get(10).add(new Location(Bukkit.getWorld("CreateAClass"), -111.6, 7, 11.9));
	      SignLocation.get(10).add(new Location(Bukkit.getWorld("CreateAClass"), -112.6, 7, 11.9));
	      SignLocation.get(10).add(new Location(Bukkit.getWorld("CreateAClass"), -113.6, 7, 11.9));
	      SignLocation.get(10).add(new Location(Bukkit.getWorld("CreateAClass"), -114.6, 7, 11.9));
	      SignLocation.get(10).add(new Location(Bukkit.getWorld("CreateAClass"), -115.6, 7, 11.9));
	      SignLocation.get(10).add(new Location(Bukkit.getWorld("CreateAClass"), -116.6, 7, 11.9));
	      // Sign Location for Lobby11.3
	      SignLocation.get(11).add(new Location(Bukkit.getWorld("CreateAClass"), -97.6, 7, 11.9));
	      SignLocation.get(11).add(new Location(Bukkit.getWorld("CreateAClass"), -98.6, 7, 11.9));
	      SignLocation.get(11).add(new Location(Bukkit.getWorld("CreateAClass"), -99.6, 7, 11.9));
	      SignLocation.get(11).add(new Location(Bukkit.getWorld("CreateAClass"), -100.6, 7, 11.9));
	      SignLocation.get(11).add(new Location(Bukkit.getWorld("CreateAClass"), -101.6, 7, 11.9));
	      SignLocation.get(11).add(new Location(Bukkit.getWorld("CreateAClass"), -102.6, 7, 11.9));
	      SignLocation.get(11).add(new Location(Bukkit.getWorld("CreateAClass"), -103.6, 7, 11.9));
	      SignLocation.get(11).add(new Location(Bukkit.getWorld("CreateAClass"), -104.6, 7, 11.9));
	      // Sign Location for Lobby 12
	      SignLocation.get(12).add(new Location(Bukkit.getWorld("CreateAClass"), -85.6, 7, 11.9));
	      SignLocation.get(12).add(new Location(Bukkit.getWorld("CreateAClass"), -86.6, 7, 11.9));
	      SignLocation.get(12).add(new Location(Bukkit.getWorld("CreateAClass"), -87.6, 7, 11.9));
	      SignLocation.get(12).add(new Location(Bukkit.getWorld("CreateAClass"), -88.6, 7, 11.9));
	      SignLocation.get(12).add(new Location(Bukkit.getWorld("CreateAClass"), -89.6, 7, 11.9));
	      SignLocation.get(12).add(new Location(Bukkit.getWorld("CreateAClass"), -90.6, 7, 11.9));
	      SignLocation.get(12).add(new Location(Bukkit.getWorld("CreateAClass"), -91.6, 7, 11.9));
	      SignLocation.get(12).add(new Location(Bukkit.getWorld("CreateAClass"), -92.6, 7, 11.9));
	      // Sign Location for Lobby 13
	      SignLocation.get(13).add(new Location(Bukkit.getWorld("CreateAClass"), -73.6, 7, 11.9));
	      SignLocation.get(13).add(new Location(Bukkit.getWorld("CreateAClass"), -74.6, 7, 11.9));
	      SignLocation.get(13).add(new Location(Bukkit.getWorld("CreateAClass"), -75.6, 7, 11.9));
	      SignLocation.get(13).add(new Location(Bukkit.getWorld("CreateAClass"), -76.6, 7, 11.9));
	      SignLocation.get(13).add(new Location(Bukkit.getWorld("CreateAClass"), -77.6, 7, 11.9));
	      SignLocation.get(13).add(new Location(Bukkit.getWorld("CreateAClass"), -78.6, 7, 11.9));
	      SignLocation.get(13).add(new Location(Bukkit.getWorld("CreateAClass"), -79.6, 7, 11.9));
	      SignLocation.get(13).add(new Location(Bukkit.getWorld("CreateAClass"), -80.6, 7, 11.9));
	      // Sign Location for Lobby 14
	      SignLocation.get(14).add(new Location(Bukkit.getWorld("CreateAClass"), -61.6, 7, 11.9));
	      SignLocation.get(14).add(new Location(Bukkit.getWorld("CreateAClass"), -62.6, 7, 11.9));
	      SignLocation.get(14).add(new Location(Bukkit.getWorld("CreateAClass"), -63.6, 7, 11.9));
	      SignLocation.get(14).add(new Location(Bukkit.getWorld("CreateAClass"), -64.6, 7, 11.9));
	      SignLocation.get(14).add(new Location(Bukkit.getWorld("CreateAClass"), -65.6, 7, 11.9));
	      SignLocation.get(14).add(new Location(Bukkit.getWorld("CreateAClass"), -66.6, 7, 11.9));
	      SignLocation.get(14).add(new Location(Bukkit.getWorld("CreateAClass"), -67.6, 7, 11.9));
	      SignLocation.get(14).add(new Location(Bukkit.getWorld("CreateAClass"), -68.6, 7, 11.9));
	      // Sign Location for Lobby 15
	      SignLocation.get(15).add(new Location(Bukkit.getWorld("CreateAClass"), -49.6, 7, 11.9));
	      SignLocation.get(15).add(new Location(Bukkit.getWorld("CreateAClass"), -50.6, 7, 11.9));
	      SignLocation.get(15).add(new Location(Bukkit.getWorld("CreateAClass"), -51.6, 7, 11.9));
	      SignLocation.get(15).add(new Location(Bukkit.getWorld("CreateAClass"), -52.6, 7, 11.9));
	      SignLocation.get(15).add(new Location(Bukkit.getWorld("CreateAClass"), -53.6, 7, 11.9));
	      SignLocation.get(15).add(new Location(Bukkit.getWorld("CreateAClass"), -54.6, 7, 11.9));
	      SignLocation.get(15).add(new Location(Bukkit.getWorld("CreateAClass"), -55.6, 7, 11.9));
	      SignLocation.get(15).add(new Location(Bukkit.getWorld("CreateAClass"), -56.6, 7, 11.9));
	      // Sign Location for Lobby 16
	      SignLocation.get(16).add(new Location(Bukkit.getWorld("CreateAClass"), -37.6, 7, 11.9));
	      SignLocation.get(16).add(new Location(Bukkit.getWorld("CreateAClass"), -38.6, 7, 11.9));
	      SignLocation.get(16).add(new Location(Bukkit.getWorld("CreateAClass"), -39.6, 7, 11.9));
	      SignLocation.get(16).add(new Location(Bukkit.getWorld("CreateAClass"), -40.6, 7, 11.9));
	      SignLocation.get(16).add(new Location(Bukkit.getWorld("CreateAClass"), -41.6, 7, 11.9));
	      SignLocation.get(16).add(new Location(Bukkit.getWorld("CreateAClass"), -42.6, 7, 11.9));
	      SignLocation.get(16).add(new Location(Bukkit.getWorld("CreateAClass"), -43.6, 7, 11.9));
	      SignLocation.get(16).add(new Location(Bukkit.getWorld("CreateAClass"), -44.6, 7, 11.9));
	      // Sign Location for Lobby 17
	      SignLocation.get(17).add(new Location(Bukkit.getWorld("CreateAClass"), -25.6, 7, 11.9));
	      SignLocation.get(17).add(new Location(Bukkit.getWorld("CreateAClass"), -26.6, 7, 11.9));
	      SignLocation.get(17).add(new Location(Bukkit.getWorld("CreateAClass"), -27.6, 7, 11.9));
	      SignLocation.get(17).add(new Location(Bukkit.getWorld("CreateAClass"), -28.6, 7, 11.9));
	      SignLocation.get(17).add(new Location(Bukkit.getWorld("CreateAClass"), -29.6, 7, 11.9));
	      SignLocation.get(17).add(new Location(Bukkit.getWorld("CreateAClass"), -30.6, 7, 11.9));
	      SignLocation.get(17).add(new Location(Bukkit.getWorld("CreateAClass"), -31.6, 7, 11.9));
	      SignLocation.get(17).add(new Location(Bukkit.getWorld("CreateAClass"), -32.6, 7, 11.9));
	      // Sign Location for Lobby 18
	      SignLocation.get(18).add(new Location(Bukkit.getWorld("CreateAClass"), -13.6, 7, 11.9));
	      SignLocation.get(18).add(new Location(Bukkit.getWorld("CreateAClass"), -14.6, 7, 11.9));
	      SignLocation.get(18).add(new Location(Bukkit.getWorld("CreateAClass"), -15.6, 7, 11.9));
	      SignLocation.get(18).add(new Location(Bukkit.getWorld("CreateAClass"), -16.6, 7, 11.9));
	      SignLocation.get(18).add(new Location(Bukkit.getWorld("CreateAClass"), -17.6, 7, 11.9));
	      SignLocation.get(18).add(new Location(Bukkit.getWorld("CreateAClass"), -18.6, 7, 11.9));
	      SignLocation.get(18).add(new Location(Bukkit.getWorld("CreateAClass"), -19.6, 7, 11.9));
	      SignLocation.get(18).add(new Location(Bukkit.getWorld("CreateAClass"), -20.6, 7, 11.9));
	      // Sign Location for Lobby 19
	      SignLocation.get(19).add(new Location(Bukkit.getWorld("CreateAClass"), -1.6, 7, 11.9));
	      SignLocation.get(19).add(new Location(Bukkit.getWorld("CreateAClass"), -2.6, 7, 11.9));
	      SignLocation.get(19).add(new Location(Bukkit.getWorld("CreateAClass"), -3.6, 7, 11.9));
	      SignLocation.get(19).add(new Location(Bukkit.getWorld("CreateAClass"), -4.6, 7, 11.9));
	      SignLocation.get(19).add(new Location(Bukkit.getWorld("CreateAClass"), -5.6, 7, 11.9));
	      SignLocation.get(19).add(new Location(Bukkit.getWorld("CreateAClass"), -6.6, 7, 11.9));
	      SignLocation.get(19).add(new Location(Bukkit.getWorld("CreateAClass"), -7.6, 7, 11.9));
	      SignLocation.get(19).add(new Location(Bukkit.getWorld("CreateAClass"), -8.6, 7, 11.9));

	      LobbySpawn.put(0, new Location(Bukkit.getWorld("CreateAClass"), -4, 5, -9));
	      LobbySpawn.put(1, new Location(Bukkit.getWorld("CreateAClass"), -16, 5, -9));
	      LobbySpawn.put(2, new Location(Bukkit.getWorld("CreateAClass"), -28, 5, -9));
	      LobbySpawn.put(3, new Location(Bukkit.getWorld("CreateAClass"), -40, 5, -9));
	      LobbySpawn.put(4, new Location(Bukkit.getWorld("CreateAClass"), -52, 5, -9));
	      LobbySpawn.put(5, new Location(Bukkit.getWorld("CreateAClass"), -64, 5, -9));
	      LobbySpawn.put(6, new Location(Bukkit.getWorld("CreateAClass"), -76, 5, -9));
	      LobbySpawn.put(7, new Location(Bukkit.getWorld("CreateAClass"), -88, 5, -9));
	      LobbySpawn.put(8, new Location(Bukkit.getWorld("CreateAClass"), -100, 5, -9));
	      LobbySpawn.put(9, new Location(Bukkit.getWorld("CreateAClass"), -112, 5, -9));
	      LobbySpawn.put(10, new Location(Bukkit.getWorld("CreateAClass"), -113, 5, 3));
	      LobbySpawn.put(11, new Location(Bukkit.getWorld("CreateAClass"), -100, 5, 3));
	      LobbySpawn.put(12, new Location(Bukkit.getWorld("CreateAClass"), -88, 5, 3));
	      LobbySpawn.put(13, new Location(Bukkit.getWorld("CreateAClass"), -76, 5, 3));
	      LobbySpawn.put(14, new Location(Bukkit.getWorld("CreateAClass"), -66, 5, 3));
	      LobbySpawn.put(15, new Location(Bukkit.getWorld("CreateAClass"), -53, 5, 3));
	      LobbySpawn.put(16, new Location(Bukkit.getWorld("CreateAClass"), -40, 5, 3));
	      LobbySpawn.put(17, new Location(Bukkit.getWorld("CreateAClass"), -29, 5, 3));
	      LobbySpawn.put(18, new Location(Bukkit.getWorld("CreateAClass"), -16, 5, 3));
	      LobbySpawn.put(19, new Location(Bukkit.getWorld("CreateAClass"), -5, 5, 3));
	}

}
