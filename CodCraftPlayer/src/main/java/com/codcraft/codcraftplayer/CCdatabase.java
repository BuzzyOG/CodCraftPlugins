package com.codcraft.codcraftplayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

public class CCdatabase {
	private CCPlayerMain plugin;
	public CCdatabase(CCPlayerMain plugin) {
		this.plugin = plugin;
	}
	
	
	public void savep(Player p) {
		if(plugin.con == null) {
			plugin.getLogger().severe("Connection not setup. Stopping!");
			return;
		}
		CCPlayer player = plugin.players.get(p.getName());
		if(player != null) {
			Statement statement5;
			try {
				statement5 = plugin.con.createStatement();
				String SQL2 = "UPDATE players SET kills = '"+player.getKills()+"', deaths = '"+player.Deaths+"', wins = '"+player.getWins()+
						"',losses = '"+player.getLosses()+"',Levels = '"+player.getLevel()+"', Points = '"+player.getPoints()+"', classes = '"+player.CaCint+
						"', TDMKills = '"+player.getTDMKills()+"', TDMDeaths = '"+player.getTDMDeaths()+"', TDMWins = '"+player.getTDMWins()+"', TDMLosses = '"+player.getTDMLosses()+
						"', FFAKills = '"+player.getFFAKills()+"', FFADeaths = '"+player.getFFADeaths()+"', FFAWins = '"+player.getFFAWins()+"', FFALosses = '"+player.getFFALosses()+
						"', SSBKills = '"+player.getSSBKills()+"', SSBDeaths = '"+player.getSSBDeaths()+"', SSBWins = '"+player.getSSBWins()+"', SSBLosses = '"+player.getSSBLosses()+
						"', UHCKills = '"+player.getUHCKills()+"', UHCDeaths = '"+player.getUHCDeaths()+"', UHCWins = '"+player.getUHCWins()+"', UHCLosses = '"+player.getUHCLosses()+
						"' WHERE id = "+player.mysqlid+"";
				statement5.executeUpdate(SQL2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			for(Entry<Integer, CCClass> clazz : player.getMapClasses().entrySet()) {
				if(clazz.getValue().mysqlid == null) {
					PreparedStatement pst;
					String SQL = "INSERT INTO classes(username, classid, gun, attachment, Perk1, Perk2, Perk3, KillStreak, Equipment) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
					try {
						pst = plugin.con.prepareStatement(SQL);
						pst.setString(1, p.getName());
						pst.setInt(2, clazz.getKey());
						pst.setString(3, clazz.getValue().getGun());
						pst.setString(4, clazz.getValue().getAttachment());
						pst.setString(5, clazz.getValue().getPerk1());
						pst.setString(6, clazz.getValue().getPerk2());
						pst.setString(7, clazz.getValue().getPerk3());
						pst.setString(8, clazz.getValue().getKillStreak());
						pst.setString(9, clazz.getValue().getEquipment());
						pst.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
					
				
				Statement statement4;
				try {
					statement4 = plugin.con.createStatement();
					String SQL2 = "UPDATE classes SET gun = '"+clazz.getValue().getGun()+"', attachment = '"+clazz.getValue().attachment+"', Perk1 = '"+clazz.getValue().getPerk1()+
							"',Perk2 = '"+clazz.getValue().getPerk2()+"',Perk3 = '"+clazz.getValue().getPerk3()+"', Equipment = '"+clazz.getValue().getEquipment()+"', KillStreak = '"+clazz.getValue().getKillStreak()+"' WHERE ID = "+clazz.getValue().mysqlid+"";
					statement4.executeUpdate(SQL2);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}

	}
	
	
	public void getp(String p) {
		Statement statement1 = null;
		if(plugin.con == null) {
			plugin.getLogger().severe("Connection not setup. Stopping!");
			return;
		}

		try {
			statement1 = plugin.con.createStatement();
			String SQL = "SELECT * FROM `players` WHERE `playername` = '" + p + "'";
			ResultSet rs = statement1.executeQuery(SQL);
			CCPlayer plasyers = new CCPlayer();
			int id = 0;
			while(rs.next()) {
				id = rs.getInt(1);
			}
			if(id == 0) {
				addefaults(p);
				plasyers.setKills(0);
				plasyers.setDeaths(0);
				plasyers.setWins(0);
				plasyers.setLosses(0);
				plasyers.setLevel(0);
				plasyers.setPoints(0);
				plasyers.CaCint = 3;
				plasyers.setTDMKills(0);
				plasyers.setTDMDeaths(0);
				plasyers.setTDMLosses(0);
				plasyers.setTDMWins(0);
				plasyers.setFFADeaths(0);
				plasyers.setFFAKills(0);
				plasyers.setFFALosses(0);
				plasyers.setFFAWins(0);
				plasyers.setSSBDeaths(0);
				plasyers.setSSBKills(0);
				plasyers.setSSBLosses(0);
				plasyers.setSSBWins(0);
				plasyers.setUHCDeaths(0);
				plasyers.setUHCKills(0);
				plasyers.setUHCLosses(0);
				plasyers.setUHCWins(0);
				
			} else {
				Statement statement11 = plugin.con.createStatement();
				String SQL1 = "SELECT * FROM `players` WHERE `playername` = '" + p + "'";
				ResultSet rs1 = statement11.executeQuery(SQL1);
				while (rs1.next()) {
					plasyers.mysqlid = rs1.getInt(1);
					plasyers.setKills(rs1.getInt(3));
					plasyers.setDeaths(rs1.getInt(4));
					plasyers.setWins(rs1.getInt(5));
					plasyers.setLosses(rs1.getInt(6));
					plasyers.setLevel(rs1.getInt(7));
					plasyers.setPoints(rs1.getInt(8));
					plasyers.CaCint = rs1.getInt(9);
					plasyers.setTDMKills(rs1.getInt(10));
					plasyers.setTDMDeaths(rs1.getInt(11));
					plasyers.setTDMLosses(rs1.getInt(12));
					plasyers.setTDMWins(rs1.getInt(13));
					plasyers.setFFAKills(rs1.getInt(14));
					plasyers.setFFADeaths(rs1.getInt(15));
					plasyers.setFFALosses(rs1.getInt(16));
					plasyers.setFFAWins(rs1.getInt(17));
					plasyers.setSSBKills(rs1.getInt(18));
					plasyers.setSSBDeaths(rs1.getInt(19));
					plasyers.setSSBLosses(rs1.getInt(20));
					plasyers.setSSBWins(rs1.getInt(21));
					plasyers.setUHCKills(rs1.getInt(22));
					plasyers.setUHCDeaths(rs1.getInt(23));
					plasyers.setUHCLosses(rs1.getInt(24));
					plasyers.setUHCWins(rs1.getInt(25));
					
				}
			}
			if(plugin.players.containsKey(p)) {
				plugin.players.remove(p);
			}
			plugin.players.put(p, plasyers);

		} catch (SQLException error) {
			plugin.getLogger().info("sql exception");
			error.printStackTrace();
		}
		
			Statement statement = null;
			try {
				statement = plugin.con.createStatement();
				String SQL = "SELECT * FROM `classes` WHERE `username` = '" + p + "'";
				ResultSet result = null;
				result = statement.executeQuery(SQL);
				List<Integer> Classes = new ArrayList<>();
				while(result.next()) {
					Classes.add(result.getInt(1));
					
				}
				for(Integer classid : Classes) {
					Statement statement2 = plugin.con.createStatement();
					String SQL2 = "SELECT * FROM `classes` WHERE `ID` = '" + classid  + "'";
					ResultSet result2 = null;
					result2 = statement2.executeQuery(SQL2);
					CCPlayer ccplayer = null;
					while(result2.next()) {
						if(plugin.players.get(p) == null) {
							ccplayer = new CCPlayer();
						} else {
							ccplayer = plugin.players.get(p);
						}
						int classspot = result2.getInt(3);
						CCClass clazz = new CCClass();
						clazz.mysqlid = result2.getString(1);
						clazz.attachment = result2.getString(5);
						clazz.gun = result2.getString(4);
						clazz.Equipment = result2.getString(9);
						clazz.KillStreak = result2.getString(10);
						clazz.Perk1 = result2.getString(6);
						clazz.Perk2 = result2.getString(7);
						clazz.Perk3 = result2.getString(8);
						ccplayer.setClass(clazz, classspot);
					}
					plugin.players.put(p, ccplayer);
				}
				if(plugin.players.get(p).classes.size() == 0) {
					adddefaultclass(p);
					
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	public void updatep(Player p) {
		if(plugin.con == null) {
			plugin.getLogger().severe("Connection not setup. Stopping!");
			return;
		}
		
		//Saving
			CCPlayer player = plugin.players.get(p.getName());
			if(player != null) {
				Statement statement5;
				try {
					statement5 = plugin.con.createStatement();
					String SQL2 = "UPDATE players SET kills = '"+player.getKills()+"', deaths = '"+player.Deaths+"', wins = '"+player.getWins()+
							"',losses = '"+player.getLosses()+"',Levels = '"+player.getLevel()+"', Points = '"+player.getPoints()+"', classes = '"+player.CaCint+
							"', TDMKills = '"+player.getTDMKills()+"', TDMDeaths = '"+player.getTDMDeaths()+"', TDMWins = '"+player.getTDMWins()+"', TDMLosses = '"+player.getTDMLosses()+
							"', FFAKills = '"+player.getFFAKills()+"', FFADeaths = '"+player.getFFADeaths()+"', FFAWins = '"+player.getFFAWins()+"', FFALosses = '"+player.getFFALosses()+
							"', SSBKills = '"+player.getSSBKills()+"', SSBDeaths = '"+player.getSSBDeaths()+"', SSBWins = '"+player.getSSBWins()+"', SSBLosses = '"+player.getSSBLosses()+
							"', UHCKills = '"+player.getUHCKills()+"', UHCDeaths = '"+player.getUHCDeaths()+"', UHCWins = '"+player.getUHCWins()+"', UHCLosses = '"+player.getUHCLosses()+
							"' WHERE id = "+player.mysqlid+"";
					statement5.executeUpdate(SQL2);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				for(Entry<Integer, CCClass> clazz : player.getMapClasses().entrySet()) {
					if(clazz.getValue().mysqlid == null) {
						PreparedStatement pst;
						String SQL = "INSERT INTO classes(username, classid, gun, attachment, Perk1, Perk2, Perk3, KillStreak, Equipment) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
						try {
							pst = plugin.con.prepareStatement(SQL);
							pst.setString(1, p.getName());
							pst.setInt(2, clazz.getKey());
							pst.setString(3, clazz.getValue().getGun());
							pst.setString(4, clazz.getValue().getAttachment());
							pst.setString(5, clazz.getValue().getPerk1());
							pst.setString(6, clazz.getValue().getPerk2());
							pst.setString(7, clazz.getValue().getPerk3());
							pst.setString(8, clazz.getValue().getKillStreak());
							pst.setString(9, clazz.getValue().getEquipment());
							pst.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
						
					
					Statement statement4;
					try {
						statement4 = plugin.con.createStatement();
						String SQL2 = "UPDATE classes SET gun = '"+clazz.getValue().getGun()+"', attachment = '"+clazz.getValue().attachment+"', Perk1 = '"+clazz.getValue().getPerk1()+
								"',Perk2 = '"+clazz.getValue().getPerk2()+"',Perk3 = '"+clazz.getValue().getPerk3()+"', Equipment = '"+clazz.getValue().getEquipment()+"', KillStreak = '"+clazz.getValue().getKillStreak()+"' WHERE ID = "+clazz.getValue().mysqlid+"";
						statement4.executeUpdate(SQL2);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			}

	
		
	//Getsing
	Statement statement1 = null;

	try {
		statement1 = plugin.con.createStatement();
		String SQL = "SELECT * FROM `players` WHERE `playername` = '" + p.getName() + "'";
		ResultSet rs = statement1.executeQuery(SQL);
		CCPlayer plasyers = new CCPlayer();
		int id = 0;
		while(rs.next()) {
			id = rs.getInt(1);
		}
		if(id == 0) {
			addefaults(p.getName());
			plasyers.setKills(0);
			plasyers.setDeaths(0);
			plasyers.setWins(0);
			plasyers.setLosses(0);
			plasyers.setLevel(0);
			plasyers.setPoints(0);
			plasyers.CaCint = 3;
			plasyers.setTDMKills(0);
			plasyers.setTDMDeaths(0);
			plasyers.setTDMLosses(0);
			plasyers.setTDMWins(0);
			plasyers.setFFADeaths(0);
			plasyers.setFFAKills(0);
			plasyers.setFFALosses(0);
			plasyers.setFFAWins(0);
			plasyers.setSSBDeaths(0);
			plasyers.setSSBKills(0);
			plasyers.setSSBLosses(0);
			plasyers.setSSBWins(0);
			plasyers.setUHCDeaths(0);
			plasyers.setUHCKills(0);
			plasyers.setUHCLosses(0);
			plasyers.setUHCWins(0);
		} else {
			Statement statement11 = plugin.con.createStatement();
			String SQL1 = "SELECT * FROM `players` WHERE `playername` = '" + p.getName() + "'";
			ResultSet rs1 = statement11.executeQuery(SQL1);
			while (rs1.next()) {
				plasyers.mysqlid = rs1.getInt(1);
				plasyers.setKills(rs1.getInt(3));
				plasyers.setDeaths(rs1.getInt(4));
				plasyers.setWins(rs1.getInt(5));
				plasyers.setLosses(rs1.getInt(6));
				plasyers.setLevel(rs1.getInt(7));
				plasyers.setPoints(rs1.getInt(8));
				plasyers.CaCint = rs1.getInt(9);
				plasyers.setTDMKills(rs1.getInt(10));
				plasyers.setTDMDeaths(rs1.getInt(11));
				plasyers.setTDMLosses(rs1.getInt(12));
				plasyers.setTDMWins(rs1.getInt(13));
				plasyers.setFFAKills(rs1.getInt(14));
				plasyers.setFFADeaths(rs1.getInt(15));
				plasyers.setFFALosses(rs1.getInt(16));
				plasyers.setFFAWins(rs1.getInt(17));
				plasyers.setSSBKills(rs1.getInt(18));
				plasyers.setSSBDeaths(rs1.getInt(19));
				plasyers.setSSBLosses(rs1.getInt(20));
				plasyers.setSSBWins(rs1.getInt(21));
				plasyers.setUHCKills(rs1.getInt(22));
				plasyers.setUHCDeaths(rs1.getInt(23));
				plasyers.setUHCLosses(rs1.getInt(24));
				plasyers.setUHCWins(rs1.getInt(25));
				
			}
		}
		if(plugin.players.containsKey(p.getName())) {
			plugin.players.remove(p.getName());
		}
		plugin.players.put(p.getName(), plasyers);

	} catch (SQLException error) {
		plugin.getLogger().info("sql exception");
		error.printStackTrace();
	}
	
		Statement statement = null;
		try {
			statement = plugin.con.createStatement();
			String SQL = "SELECT * FROM `classes` WHERE `username` = '" + p.getName() + "'";
			ResultSet result = null;
			result = statement.executeQuery(SQL);
			List<Integer> Classes = new ArrayList<>();
			while(result.next()) {
				Classes.add(result.getInt(1));
				
			}
			for(Integer classid : Classes) {
				Statement statement2 = plugin.con.createStatement();
				String SQL2 = "SELECT * FROM `classes` WHERE `ID` = '" + classid  + "'";
				ResultSet result2 = null;
				result2 = statement2.executeQuery(SQL2);
				CCPlayer ccplayer = null;
				while(result2.next()) {
					if(plugin.players.get(p.getName()) == null) {
						ccplayer = new CCPlayer();
					} else {
						ccplayer = plugin.players.get(p.getName());
					}
					int classspot = result2.getInt(3);
					CCClass clazz = new CCClass();
					clazz.mysqlid = result2.getString(1);
					clazz.attachment = result2.getString(5);
					clazz.gun = result2.getString(4);
					clazz.Equipment = result2.getString(9);
					clazz.KillStreak = result2.getString(10);
					clazz.Perk1 = result2.getString(6);
					clazz.Perk2 = result2.getString(7);
					clazz.Perk3 = result2.getString(8);
					ccplayer.setClass(clazz, classspot);
				}
				plugin.players.put(p.getName(), ccplayer);
			}
			if(plugin.players.get(p.getName()).classes.size() == 0) {
				adddefaultclass(p.getName());
				
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
}

private void adddefaultclass(String p) {
	PreparedStatement pst;
	String SQL = "INSERT INTO classes(username, classid, gun, attachment, Perk1, Perk2, Perk3, Equipment, KillStreak) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	try {
		pst = plugin.con.prepareStatement(SQL);
		pst.setString(1, p);
		pst.setInt(2, 1);
		pst.setString(3, "Sniper");
		pst.setString(4, "none");
		pst.setString(5, "Marathon");
		pst.setString(6, "LightWeight");
		pst.setString(7, "Equip2X");
		pst.setString(8, "C4");
		pst.setString(9, "none");
		pst.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

private void addefaults(String p) {
	PreparedStatement pst;
	String SQL = "INSERT INTO players(playername, kills, deaths, wins, losses, Levels, Points, classes, TDMKills, TDMDeaths, TDMWins, TDMLosses, FFAKills, FFADeaths, FFAWins, FFALosses, SSBKills, SSBDeaths, SSBWins, SSBLosses, UHCKills, UHCDeaths, UHCWins, UHCLosses )" +
			" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	try {
		pst = plugin.con.prepareStatement(SQL);
		pst.setString(1, p);
		pst.setInt(2, 0);
		pst.setInt(3, 0);
		pst.setInt(4, 0);
		pst.setInt(5, 0);
		pst.setInt(6, 1);
		pst.setInt(7, 0);
		pst.setInt(8, 3);
		pst.setInt(9, 0);
		pst.setInt(10, 0);
		pst.setInt(11, 0);
		pst.setInt(12, 0);
		pst.setInt(13, 0);
		pst.setInt(14, 0);
		pst.setInt(15, 0);
		pst.setInt(16, 0);
		pst.setInt(17, 0);
		pst.setInt(18, 0);
		pst.setInt(19, 0);
		pst.setInt(20, 0);
		pst.setInt(21, 0);
		pst.setInt(22, 0);
		pst.setInt(23, 0);
		pst.setInt(24, 0);
		pst.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}



}
