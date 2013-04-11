package com.codcraft.PlayerDataViewer;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codcraft.PlayerDataViewer.letters.Letter;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.lobby.event.PlayerToLobbyEvent;

public class NewListener implements Listener {
	private Main plugin;

	public NewListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void toLobby(PlayerToLobbyEvent e) {
		final Player p = e.getPlayer();
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				loaddata(p, p.getName());
				
			}
		}, 60L);
		
	}		
	public void loaddata(final Player p, final String name) {
		
				
				loadname(p, name);
				String kills = getScore(plugin.game, "kills", name);
			    ArrayList<Integer> killsa = new ArrayList<>();  
			     for(int i = 0; i< kills.length(); i++) {
					 char cha = kills.charAt(i);
					 Integer ints = Character.getNumericValue(cha);
					 killsa.add(ints);
				 }
				 if(killsa.size() <= 3) {
					 try {
						 int i = 3;
						 while (i != 0) {
							 Segment seg = plugin.kills.get(i);
							 int ii = -1;
							 Letter let = null;
							 if(i == 3) {
								 ii = 1;
							} else if(i == 2) {
								ii = 2;
							} else if(i == 1) {
								ii = 3;
							}
							 let = plugin.letters.get(""+killsa.get(ii - 1));
							 updatesegment(p, seg, let);
							 i--;
						 }
					 } catch (IndexOutOfBoundsException e) {
					 }
					 Segment seg2 = plugin.kills.get(0);
					 Letter let = new Letter() {};
					 updatesegment(p, seg2, let);
				 } else if (killsa.size() <= 6 && killsa.size() >= 4){
					 Integer ints = Integer.parseInt(kills);
					 ints = ints / 1000;
					 ints = Math.round(ints);
					 String kills2 = ""+ints;
					 ArrayList<Integer> kills23 = new ArrayList<>();  
					 for(int i = 0; i< kills2.length(); i++) {
						 char cha = kills2.charAt(i);
						 Integer intss = Character.getNumericValue(cha);
						 kills23.add(intss);
					 }
					 try {
						 int i = 3;
						 while (i != 0) {
							 Segment seg = plugin.kills.get(i);
							 int ii = -1;
							 Letter let = null;
							 if(i == 3) {
								 ii = 1;
							} else if(i == 2) {
								ii = 2;
							} else if(i == 1) {
								ii = 3;
							}
							 let = plugin.letters.get(""+kills23.get(ii - 1));
							 updatesegment(p, seg, let);
							 i--;
						 }
					 } catch (IndexOutOfBoundsException e) {
					 }
					 Segment seg2 = plugin.kills.get(0);
					 updatesegment(p, seg2, new Letter() {
					});
				 }
				 String deaths = getScore(plugin.game, "deaths", name);
				    ArrayList<Integer> deathsa = new ArrayList<>();  
				     for(int i = 0; i< deaths.length(); i++) {
						 char cha = deaths.charAt(i);
						 Integer ints = Character.getNumericValue(cha);
						 deathsa.add(ints);
					 }
					 if(deathsa.size() <= 3) {
						 try {
							 int i = 3;
							 while (i != 0) {
								 Segment seg = plugin.deaths.get(i);
								 int ii = -1;
								 Letter let = null;
								 if(i == 3) {
									 ii = 1;
								} else if(i == 2) {
									ii = 2;
								} else if(i == 1) {
									ii = 3;
								}
								 let = plugin.letters.get(""+deathsa.get(ii - 1));
								 updatesegment(p, seg, let);
								 i--;
							 }
						 } catch (IndexOutOfBoundsException e) {
						 }
						 Segment seg2 = plugin.deaths.get(0);
						 Letter let = new Letter() {};
						 updatesegment(p, seg2, let);
					 } else if (deathsa.size() <= 6 && deathsa.size() >= 4){
						 Integer ints = Integer.parseInt(deaths);
						 ints = ints / 1000;
						 ints = Math.round(ints);
						 String deaths2 = ""+ints;
						 ArrayList<Integer> deaths23 = new ArrayList<>();  
						 for(int i = 0; i< deaths2.length(); i++) {
							 char cha = deaths2.charAt(i);
							 Integer intss = Character.getNumericValue(cha);
							 deaths23.add(intss);
						 }
						 try {
							 int i = 3;
							 while (i != 0) {
								 Segment seg = plugin.deaths.get(i);
								 int ii = -1;
								 Letter let = null;
								 if(i == 3) {
									 ii = 1;
								} else if(i == 2) {
									ii = 2;
								} else if(i == 1) {
									ii = 3;
								}
								 let = plugin.letters.get(""+deaths23.get(ii - 1));
								 updatesegment(p, seg, let);
								 i--;
							 }
						 } catch (IndexOutOfBoundsException e) {
						 }
						 Segment seg2 = plugin.deaths.get(0);
						 updatesegment(p, seg2, new Letter() {
						});
					 }
					 String wins = getScore(plugin.game, "wins", name);
					    ArrayList<Integer> winsa = new ArrayList<>();  
					     for(int i = 0; i< wins.length(); i++) {
							 char cha = wins.charAt(i);
							 Integer ints = Character.getNumericValue(cha);
							 winsa.add(ints);
						 }
						 if(winsa.size() <= 3) {
							 try {
								 int i = 3;
								 while (i != 0) {
									 Segment seg = plugin.wins.get(i);
									 int ii = -1;
									 Letter let = null;
									 if(i == 3) {
										 ii = 1;
									} else if(i == 2) {
										ii = 2;
									} else if(i == 1) {
										ii = 3;
									}
									 let = plugin.letters.get(""+winsa.get(ii - 1));
									 updatesegment(p, seg, let);
									 i--;
								 }
							 } catch (IndexOutOfBoundsException e) {
							 }
							 Segment seg2 = plugin.wins.get(0);
							 Letter let = new Letter() {};
							 updatesegment(p, seg2, let);
						 } else if (winsa.size() <= 6 && winsa.size() >= 4){
							 Integer ints = Integer.parseInt(wins);
							 ints = ints / 1000;
							 ints = Math.round(ints);
							 String wins2 = ""+ints;
							 ArrayList<Integer> wins21 = new ArrayList<>();  
							 for(int i = 0; i< wins2.length(); i++) {
								 char cha = wins2.charAt(i);
								 Integer intss = Character.getNumericValue(cha);
								 wins21.add(intss);
							 }
							 try {
								 int i = 3;
								 while (i != 0) {
									 Segment seg = plugin.wins.get(i);
									 int ii = -1;
									 Letter let = null;
									 if(i == 3) {
										 ii = 1;
									} else if(i == 2) {
										ii = 2;
									} else if(i == 1) {
										ii = 3;
									}
									 let = plugin.letters.get(""+wins21.get(ii - 1));
									 updatesegment(p, seg, let);
									 i--;
								 }
							 } catch (IndexOutOfBoundsException e) {
							 }
							 Segment seg2 = plugin.wins.get(0);
							 updatesegment(p, seg2, new Letter() {
							});
						 }
						 String losses = getScore(plugin.game, "losses", name);
						    ArrayList<Integer> lossesa = new ArrayList<>();  
						     for(int i = 0; i< losses.length(); i++) {
								 char cha = losses.charAt(i);
								 Integer ints = Character.getNumericValue(cha);
								 lossesa.add(ints);
							 }
							 if(lossesa.size() <= 3) {
								 try {
									 int i = 3;
									 while (i != 0) {
										 Segment seg = plugin.losses.get(i);
										 int ii = -1;
										 Letter let = null;
										 if(i == 3) {
											 ii = 1;
										} else if(i == 2) {
											ii = 2;
										} else if(i == 1) {
											ii = 3;
										}
										 let = plugin.letters.get(""+lossesa.get(ii - 1));
										 updatesegment(p, seg, let);
										 i--;
									 }
								 } catch (IndexOutOfBoundsException e) {
								 }
								 Segment seg2 = plugin.losses.get(0);
								 Letter let = new Letter() {};
								 updatesegment(p, seg2, let);
							 } else if (lossesa.size() <= 6 && lossesa.size() >= 4){
								 Integer ints = Integer.parseInt(losses);
								 ints = ints / 1000;
								 ints = Math.round(ints);
								 String losses2 = ""+ints;
								 ArrayList<Integer> losses21 = new ArrayList<>();  
								 for(int i = 0; i< losses2.length(); i++) {
									 char cha = losses2.charAt(i);
									 Integer intss = Character.getNumericValue(cha);
									 losses21.add(intss);
								 }
								 try {
									 int i = 3;
									 while (i != 0) {
										 Segment seg = plugin.losses.get(i);
										 int ii = -1;
										 Letter let = null;
										 if(i == 3) {
											 ii = 1;
										} else if(i == 2) {
											ii = 2;
										} else if(i == 1) {
											ii = 3;
										}
										 let = plugin.letters.get(""+losses21.get(ii - 1));
										 updatesegment(p, seg, let);
										 i--;
									 }
								 } catch (IndexOutOfBoundsException e) {
								 }
								 Segment seg2 = plugin.losses.get(0);
								 updatesegment(p, seg2, new Letter() {
								});
							 }
							 String level = getScore(plugin.game, "level", name);
							    ArrayList<Integer> levela = new ArrayList<>();  
							     for(int i = 0; i< level.length(); i++) {
									 char cha = level.charAt(i);
									 Integer ints = Character.getNumericValue(cha);
									 levela.add(ints);
								 }
								 if(levela.size() <= 3) {
									 try {
										 int i = 3;
										 while (i != 0) {
											 Segment seg = plugin.level.get(i);
											 int ii = -1;
											 Letter let = null;
											 if(i == 3) {
												 ii = 1;
											} else if(i == 2) {
												ii = 2;
											} else if(i == 1) {
												ii = 3;
											}
											 let = plugin.letters.get(""+levela.get(ii - 1));
											 updatesegment(p, seg, let);
											 i--;
										 }
									 } catch (IndexOutOfBoundsException e) {
									 }
									 Segment seg2 = plugin.level.get(0);
									 Letter let = new Letter() {};
									 updatesegment(p, seg2, let);
								 } else if (levela.size() <= 6 && levela.size() >= 4){
									 Integer ints = Integer.parseInt(level);
									 ints = ints / 1000;
									 ints = Math.round(ints);
									 String level2 = ""+ints;
									 ArrayList<Integer> level21 = new ArrayList<>();  
									 for(int i = 0; i< level2.length(); i++) {
										 char cha = level2.charAt(i);
										 Integer intss = Character.getNumericValue(cha);
										 level21.add(intss);
									 }
									 try {
										 int i = 3;
										 while (i != 0) {
											 Segment seg = plugin.level.get(i);
											 int ii = -1;
											 Letter let = null;
											 if(i == 3) {
												 ii = 1;
											} else if(i == 2) {
												ii = 2;
											} else if(i == 1) {
												ii = 3;
											}
											 let = plugin.letters.get(""+level21.get(ii - 1));
											 updatesegment(p, seg, let);
											 i--;
										 }
									 } catch (IndexOutOfBoundsException e) {
									 }
									 Segment seg2 = plugin.level.get(0);
									 updatesegment(p, seg2, new Letter() {
									});
								 }
								 String points = getScore(plugin.game, "point", name);
								    ArrayList<Integer> pointsa = new ArrayList<>();  
								     for(int i = 0; i< points.length(); i++) {
										 char cha = points.charAt(i);
										 Integer ints = Character.getNumericValue(cha);
										 pointsa.add(ints);
									 }
									 if(pointsa.size() <= 3) {
										 try {
											 int i = 3;
											 while (i != 0) {
												 Segment seg = plugin.points.get(i);
												 int ii = -1;
												 Letter let = null;
												 if(i == 3) {
													 ii = 1;
												} else if(i == 2) {
													ii = 2;
												} else if(i == 1) {
													ii = 3;
												}
												 let = plugin.letters.get(""+pointsa.get(ii - 1));
												 updatesegment(p, seg, let);
												 i--;
											 }
										 } catch (IndexOutOfBoundsException e) {
										 }
										 Segment seg2 = plugin.points.get(0);
										 Letter let = new Letter() {};
										 updatesegment(p, seg2, let);
									 } else if (pointsa.size() <= 6 && pointsa.size() >= 4){
										 Integer ints = Integer.parseInt(points);
										 ints = ints / 1000;
										 ints = Math.round(ints);
										 String level2 = ""+ints;
										 ArrayList<Integer> level21 = new ArrayList<>();  
										 for(int i = 0; i< level2.length(); i++) {
											 char cha = level2.charAt(i);
											 Integer intss = Character.getNumericValue(cha);
											 level21.add(intss);
										 }
										 try {
											 int i = 3;
											 while (i != 0) {
												 Segment seg = plugin.points.get(i);
												 int ii = -1;
												 Letter let = null;
												 if(i == 3) {
													 ii = 1;
												} else if(i == 2) {
													ii = 2;
												} else if(i == 1) {
													ii = 3;
												}
												 let = plugin.letters.get(""+level21.get(ii - 1));
												 updatesegment(p, seg, let);
												 i--;
											 }
										 } catch (IndexOutOfBoundsException e) {
										 }
										 Segment seg2 = plugin.points.get(0);
										 updatesegment(p, seg2, new Letter() {
										});
									 }
			
			
	}
	
	
	public String getScore(String lobby, String part, String p) {
		String Return = ""; 
		CCPlayer player;
		player = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(p);
		if(player == null) {
			plugin.api.getModuleForClass(CCPlayerModule.class).loadPlayer(p);
			player = plugin.api.getModuleForClass(CCPlayerModule.class).getPlayer(p);
		}
		switch (part.toLowerCase()) {
		case "kills":
			if(lobby.equalsIgnoreCase("ffa")) {
				Return = ""+player.getFFAKills();
			}
			break;
		case "deaths":
			if(lobby.equalsIgnoreCase("ffa")) {
				Return = ""+player.getFFADeaths();
			}
			break;
		case "wins":
			if(lobby.equalsIgnoreCase("ffa")) {
				Return = ""+player.getFFAWins();
			}
			break;
		case "losses":
			if(lobby.equalsIgnoreCase("ffa")) {
				Return = ""+player.getFFALosses();
			}
			break;
		case "point":
			Return = ""+player.getPoints();
		
			break;
		case "level": 
			Return = ""+player.getLevel();
			break;

		default:
			break;
		}
		return Return;
		
	}
	
	private void loadname(Player p, final String nameup) {
		String name = nameup.toLowerCase();
		int length = name.length();
		for(int i = 0; i < length; i++) {
			Character cha = name.charAt(i);
			String lettoget = String.valueOf(cha);
			Letter let = plugin.letters.get(lettoget);
			Segment seg = plugin.board.lines.get(0).segmants.get(i);
			updatesegment(p, seg, let);
		}
	}
	
	private void updatesegment(Player p, Segment seg, Letter let) {
		p.sendBlockChange(seg.loc1, 35, let.block1);
		p.sendBlockChange(seg.loc2, 35, let.block2);
		p.sendBlockChange(seg.loc3, 35, let.block3);
		p.sendBlockChange(seg.loc4, 35, let.block4);
		p.sendBlockChange(seg.loc5, 35, let.block5);
		p.sendBlockChange(seg.loc6, 35, let.block6);
		p.sendBlockChange(seg.loc7, 35, let.block7);
		p.sendBlockChange(seg.loc8, 35, let.block8);
		p.sendBlockChange(seg.loc9, 35, let.block9);
		p.sendBlockChange(seg.loc10, 35, let.block10);
		p.sendBlockChange(seg.loc11, 35, let.block11);
		p.sendBlockChange(seg.loc12, 35, let.block12);
		p.sendBlockChange(seg.loc13, 35, let.block13);
		p.sendBlockChange(seg.loc14, 35, let.block14);
		p.sendBlockChange(seg.loc15, 35, let.block15);
	}
	
	
}
