package com.codcraft.codcraftplayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CCdatabase {
	private CCPlayerMain plugin;
	public CCdatabase(CCPlayerMain plugin) {
		this.plugin = plugin;
	}
	
	
	public void savep(Player p) {
		CCPlayer player = plugin.players.get(p.getName());
		if(player != null) {
			Statement statement5;
			try {
				statement5 = plugin.con.createStatement();
				Bukkit.broadcastMessage(""+player.mysqlid);
				String SQL2 = "UPDATE players SET kills = '"+player.getKills()+"', deaths = '"+player.Deaths+"', wins = '"+player.getWins()+
						"',losses = '"+player.getLosses()+"',Levels = '"+player.getLevel()+"', Points = '"+player.getPoints()+"', classes = '"+player.CaCint+"' WHERE id = "+player.mysqlid+"";
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
	
	
	public void getp(Player p) {
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
				addefaults(p);
				plasyers.setKills(0);
				plasyers.setDeaths(0);
				plasyers.setWins(0);
				plasyers.setLosses(0);
				plasyers.setLevel(0);
				plasyers.setPoints(0);
				plasyers.CaCint = 0;
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
					adddefaultclass(p);
					
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	public void updatep(Player p) {
		//Saving
			CCPlayer player = plugin.players.get(p.getName());
			if(player != null) {
				Statement statement5;
				try {
					statement5 = plugin.con.createStatement();
					String SQL2 = "UPDATE players SET kills = '"+player.getKills()+"', deaths = '"+player.Deaths+"', wins = '"+player.getWins()+
							"',losses = '"+player.getLosses()+"',Levels = '"+player.getLevel()+"', Points = '"+player.getPoints()+"', classes = '"+player.CaCint+"' WHERE playername = "+p.getName()+"";
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
			addefaults(p);
			plasyers.setKills(0);
			plasyers.setDeaths(0);
			plasyers.setWins(0);
			plasyers.setLosses(0);
			plasyers.setLevel(0);
			plasyers.setPoints(0);
			plasyers.CaCint = 0;
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
				adddefaultclass(p);
				
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
}

private void adddefaultclass(Player p) {
	PreparedStatement pst;
	String SQL = "INSERT INTO classes(username, classid, gun, attachment, Perk1, Perk2, Perk3, KillStreak) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	try {
		pst = plugin.con.prepareStatement(SQL);
		pst.setString(1, p.getName());
		pst.setInt(2, 1);
		pst.setString(3, "Test");
		pst.setString(4, "Test");
		pst.setString(5, "Test");
		pst.setString(6, "Test");
		pst.setString(7, "Test");
		pst.setString(8, "Test");
		pst.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

private void addefaults(Player p) {
	PreparedStatement pst;
	String SQL = "INSERT INTO players(playername, kills, deaths, wins, losses, Levels, Points, classes) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	try {
		pst = plugin.con.prepareStatement(SQL);
		pst.setString(1, p.getName());
		pst.setInt(2, 0);
		pst.setInt(3, 0);
		pst.setInt(4, 0);
		pst.setInt(5, 0);
		pst.setInt(6, 1);
		pst.setInt(7, 0);
		pst.setInt(8, 1);
		pst.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}



}
