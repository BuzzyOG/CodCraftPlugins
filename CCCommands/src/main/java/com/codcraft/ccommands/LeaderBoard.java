package com.codcraft.ccommands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;



public class LeaderBoard implements Runnable {
	
	private CCCommands plugin;

	public LeaderBoard(CCCommands plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		try {
			Statement statement = plugin.con.createStatement();
			String sql = "SELECT * FROM  `players` ORDER BY  `players`.`FFAKills` DESC LIMIT 0 , 40";
			ResultSet rs = statement.executeQuery(sql);
			int ii = -1;
			while (rs.next()) {
				ii++;
				int sign = ii /4;
				sign = Math.round(sign);
				Location signloc = null;
				if(sign == 0) {
					signloc = new Location(Bukkit.getWorld("world"), -95, 139, 58);
				} else if (sign == 1) {
					signloc = new Location(Bukkit.getWorld("world"), -95, 138, 58);
				} else if (sign == 2) {
					signloc = new Location(Bukkit.getWorld("world"), -95, 139, 59);
				} else if (sign == 3) {
					signloc = new Location(Bukkit.getWorld("world"), -95, 138, 59);
				} else if (sign == 4) {
					signloc = new Location(Bukkit.getWorld("world"), -95, 139, 60);
				} else if (sign == 5) {
					signloc = new Location(Bukkit.getWorld("world"), -95, 138, 60);
				} else if (sign == 6) {
					signloc = new Location(Bukkit.getWorld("world"), -95, 139, 61);
				} else if (sign == 7) {
					signloc = new Location(Bukkit.getWorld("world"), -95, 138, 61);
				} 
				if(signloc != null) {
					if(signloc.getBlock().getState() instanceof Sign) {
						Sign signblock = (Sign) signloc.getBlock().getState();
						int line;
						int i = ii+1;
						if        (i == 1 || i == 5 || i == 9  || i == 13 || i == 17 || i == 21 || i == 25 || i == 29 || i == 33 || i == 37) {
							//Bukkit.broadcastMessage(rs.getString(2)+": 1");
							line = 0;
						} else if (i == 2 || i == 6 || i == 10 || i == 14 || i == 18 || i == 22 || i == 26 || i == 30 || i == 34 || i == 38 ) {
							//Bukkit.broadcastMessage(rs.getString(2)+": 2");
							line = 1;
						} else if (i == 3 || i == 7 || i == 11 || i == 15 || i == 19 || i == 23 || i == 27 || i == 31 || i == 35 || i == 39 ) {
							//Bukkit.broadcastMessage(rs.getString(2)+": 3");
							line = 2;
						} else {
							//Bukkit.broadcastMessage(rs.getString(2)+": 4");
							line = 3;
						}
						
						signblock.setLine(line, ""+i+"."+rs.getString(2));
						signblock.update();
					} else {
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
