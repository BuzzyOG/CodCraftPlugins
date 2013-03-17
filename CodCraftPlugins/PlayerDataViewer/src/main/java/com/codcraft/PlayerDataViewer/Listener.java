package com.codcraft.PlayerDataViewer;



import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_5_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import com.CodCraft.api.modules.MYSQL;
import com.codcraft.lobby.event.PlayerToLobbyEvent;


public class Listener implements org.bukkit.event.Listener {
	private Main plugin;
	public Listener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onlobby(final PlayerToLobbyEvent e){
		Player p = e.getPlayer();
		String name = p.getName();
		
		for(final Line line : plugin.board.lines) {
			if(!line.letters.containsKey(name)) {
				line.letters.put(name, new ArrayList<Character>());
			}
			
			if(line.letters.get(name) == null) {
				line.letters.put(name, new ArrayList<Character>());
			}

			for(int i = 0; i< name.length(); i++) {
				char cha = name.charAt(i);
				line.letters.get(name).add(cha);
			}
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				Player p = e.getPlayer();
				String name = p.getName();
			      @SuppressWarnings("unchecked")
			      HashMap<String, String> hm = (HashMap<String, String>) MYSQL.SinglePlayerALLData(p).clone();
			      String kills = hm.get("PlayerKills");
			      ArrayList<Integer> killsa = new ArrayList<>();
			      
					for(int i = 0; i< kills.length(); i++) {
						char cha = kills.charAt(i);
						Integer ints = Character.getNumericValue(cha);
						killsa.add(ints);
					}
					int trys =  0;
					while(trys != killsa.size()) {
						Segment seg = plugin.kills.get(trys);
						String s;
						//if(trys < killsa.size()) {
							s = ""+killsa.get(trys);
						//}

						Byte Block1 = null;
						Byte Block2 = null;
						Byte Block3 = null;
						Byte Block4 = null;
						Byte Block5 = null;
						Byte Block6 = null;
						Byte Block7 = null;
						Byte Block8 = null;
						Byte Block9 = null;
						Byte Block10 = null;
						Byte Block11 = null;
						Byte Block12 = null;
						Byte Block13 = null;
						Byte Block14 = null;
						Byte Block15 = null;
						if (s.equalsIgnoreCase("1")) {
							Block1 = plugin.l1.block1;
							Block2 = plugin.l1.block2;
							Block3 = plugin.l1.block3;
							Block4 = plugin.l1.block4;
							Block5 = plugin.l1.block5;
							Block6 = plugin.l1.block6;
							Block7 = plugin.l1.block7;
							Block8 = plugin.l1.block8;
							Block9 = plugin.l1.block9;
							Block10 = plugin.l1.block10;
							Block11 = plugin.l1.block11;
							Block12 = plugin.l1.block12;
							Block13 = plugin.l1.block13;
							Block14 = plugin.l1.block14;
							Block15 = plugin.l1.block15;
						}else if (s.equalsIgnoreCase("2")) {
							Block1 = plugin.l2.block1;
							Block2 = plugin.l2.block2;
							Block3 = plugin.l2.block3;
							Block4 = plugin.l2.block4;
							Block5 = plugin.l2.block5;
							Block6 = plugin.l2.block6;
							Block7 = plugin.l2.block7;
							Block8 = plugin.l2.block8;
							Block9 = plugin.l2.block9;
							Block10 = plugin.l2.block10;
							Block11 = plugin.l2.block11;
							Block12 = plugin.l2.block12;
							Block13 = plugin.l2.block13;
							Block14 = plugin.l2.block14;
							Block15 = plugin.l2.block15;
						}else if (s.equalsIgnoreCase("3")) {
							Block1 = plugin.l3.block1;
							Block2 = plugin.l3.block2;
							Block3 = plugin.l3.block3;
							Block4 = plugin.l3.block4;
							Block5 = plugin.l3.block5;
							Block6 = plugin.l3.block6;
							Block7 = plugin.l3.block7;
							Block8 = plugin.l3.block8;
							Block9 = plugin.l3.block9;
							Block10 = plugin.l3.block10;
							Block11 = plugin.l3.block11;
							Block12 = plugin.l3.block12;
							Block13 = plugin.l3.block13;
							Block14 = plugin.l3.block14;
							Block15 = plugin.l3.block15;
						}else if (s.equalsIgnoreCase("4")) {
							Block1 = plugin.l4.block1;
							Block2 = plugin.l4.block2;
							Block3 = plugin.l4.block3;
							Block4 = plugin.l4.block4;
							Block5 = plugin.l4.block5;
							Block6 = plugin.l4.block6;
							Block7 = plugin.l4.block7;
							Block8 = plugin.l4.block8;
							Block9 = plugin.l4.block9;
							Block10 = plugin.l4.block10;
							Block11 = plugin.l4.block11;
							Block12 = plugin.l4.block12;
							Block13 = plugin.l4.block13;
							Block14 = plugin.l4.block14;
							Block15 = plugin.l4.block15;
						}else if (s.equalsIgnoreCase("5")) {
							Block1 = plugin.l5.block1;
							Block2 = plugin.l5.block2;
							Block3 = plugin.l5.block3;
							Block4 = plugin.l5.block4;
							Block5 = plugin.l5.block5;
							Block6 = plugin.l5.block6;
							Block7 = plugin.l5.block7;
							Block8 = plugin.l5.block8;
							Block9 = plugin.l5.block9;
							Block10 = plugin.l5.block10;
							Block11 = plugin.l5.block11;
							Block12 = plugin.l5.block12;
							Block13 = plugin.l5.block13;
							Block14 = plugin.l5.block14;
							Block15 = plugin.l5.block15;
						}else if (s.equalsIgnoreCase("6")) {
							Block1 = plugin.l6.block1;
							Block2 = plugin.l6.block2;
							Block3 = plugin.l6.block3;
							Block4 = plugin.l6.block4;
							Block5 = plugin.l6.block5;
							Block6 = plugin.l6.block6;
							Block7 = plugin.l6.block7;
							Block8 = plugin.l6.block8;
							Block9 = plugin.l6.block9;
							Block10 = plugin.l6.block10;
							Block11 = plugin.l6.block11;
							Block12 = plugin.l6.block12;
							Block13 = plugin.l6.block13;
							Block14 = plugin.l6.block14;
							Block15 = plugin.l6.block15;
						}else if (s.equalsIgnoreCase("7")) {
							Block1 = plugin.l7.block1;
							Block2 = plugin.l7.block2;
							Block3 = plugin.l7.block3;
							Block4 = plugin.l7.block4;
							Block5 = plugin.l7.block5;
							Block6 = plugin.l7.block6;
							Block7 = plugin.l7.block7;
							Block8 = plugin.l7.block8;
							Block9 = plugin.l7.block9;
							Block10 = plugin.l7.block10;
							Block11 = plugin.l7.block11;
							Block12 = plugin.l7.block12;
							Block13 = plugin.l7.block13;
							Block14 = plugin.l7.block14;
							Block15 = plugin.l7.block15;
						}else if (s.equalsIgnoreCase("8")) {
							Block1 = plugin.l8.block1;
							Block2 = plugin.l8.block2;
							Block3 = plugin.l8.block3;
							Block4 = plugin.l8.block4;
							Block5 = plugin.l8.block5;
							Block6 = plugin.l8.block6;
							Block7 = plugin.l8.block7;
							Block8 = plugin.l8.block8;
							Block9 = plugin.l8.block9;
							Block10 = plugin.l8.block10;
							Block11 = plugin.l8.block11;
							Block12 = plugin.l8.block12;
							Block13 = plugin.l8.block13;
							Block14 = plugin.l8.block14;
							Block15 = plugin.l8.block15;
						}else if (s.equalsIgnoreCase("9")) {
							Block1 = plugin.l9.block1;
							Block2 = plugin.l9.block2;
							Block3 = plugin.l9.block3;
							Block4 = plugin.l9.block4;
							Block5 = plugin.l9.block5;
							Block6 = plugin.l9.block6;
							Block7 = plugin.l9.block7;
							Block8 = plugin.l9.block8;
							Block9 = plugin.l9.block9;
							Block10 = plugin.l9.block10;
							Block11 = plugin.l9.block11;
							Block12 = plugin.l9.block12;
							Block13 = plugin.l9.block13;
							Block14 = plugin.l9.block14;
							Block15 = plugin.l9.block15;
						}else if (s.equalsIgnoreCase("0")) {
							Block1 = plugin.l0.block1;
							Block2 = plugin.l0.block2;
							Block3 = plugin.l0.block3;
							Block4 = plugin.l0.block4;
							Block5 = plugin.l0.block5;
							Block6 = plugin.l0.block6;
							Block7 = plugin.l0.block7;
							Block8 = plugin.l0.block8;
							Block9 = plugin.l0.block9;
							Block10 = plugin.l0.block10;
							Block11 = plugin.l0.block11;
							Block12 = plugin.l0.block12;
							Block13 = plugin.l0.block13;
							Block14 = plugin.l0.block14;
							Block15 = plugin.l0.block15;
						}else {
							Block1 = plugin.lunder.block1;
							Block2 = plugin.lunder.block2;
							Block3 = plugin.lunder.block3;
							Block4 = plugin.lunder.block4;
							Block5 = plugin.lunder.block5;
							Block6 = plugin.lunder.block6;
							Block7 = plugin.lunder.block7;
							Block8 = plugin.lunder.block8;
							Block9 = plugin.lunder.block9;
							Block10 = plugin.lunder.block10;
							Block11 = plugin.lunder.block11;
							Block12 = plugin.lunder.block12;
							Block13 = plugin.lunder.block13;
							Block14 = plugin.lunder.block14;
							Block15 = plugin.lunder.block15;
						}
						((CraftPlayer)p).sendBlockChange(seg.loc1, 35, Block1);
						((CraftPlayer)p).sendBlockChange(seg.loc2, 35, Block2);
						((CraftPlayer)p).sendBlockChange(seg.loc3, 35, Block3);
						((CraftPlayer)p).sendBlockChange(seg.loc4, 35, Block4);
						((CraftPlayer)p).sendBlockChange(seg.loc5, 35, Block5);
						((CraftPlayer)p).sendBlockChange(seg.loc6, 35, Block6);
						((CraftPlayer)p).sendBlockChange(seg.loc7, 35, Block7);
						((CraftPlayer)p).sendBlockChange(seg.loc8, 35, Block8);
						((CraftPlayer)p).sendBlockChange(seg.loc9, 35, Block9);
						((CraftPlayer)p).sendBlockChange(seg.loc10, 35, Block10);
						((CraftPlayer)p).sendBlockChange(seg.loc11, 35, Block11);
						((CraftPlayer)p).sendBlockChange(seg.loc12, 35, Block12);
						((CraftPlayer)p).sendBlockChange(seg.loc13, 35, Block13);
						((CraftPlayer)p).sendBlockChange(seg.loc14, 35, Block14);
						((CraftPlayer)p).sendBlockChange(seg.loc15, 35, Block15);
						trys++;
					}
					
			      
			      String deaths = hm.get("PlayerDeaths");
			      ArrayList<Integer> deathssa = new ArrayList<>();
			      
					for(int deathsfjf = 0; deathsfjf< deaths.length(); deathsfjf++) {
						char charr = deaths.charAt(deathsfjf);
						Integer cha = Character.getNumericValue(charr);
						deathssa.add(cha);
					}
					int deathss =  0;
					while(deathss != deathssa.size()) {
						Segment seg = plugin.deaths.get(deathss);
						String s = null;
						s = ""+deathssa.get(deathss);
						Byte Block1 = null;
						Byte Block2 = null;
						Byte Block3 = null;
						Byte Block4 = null;
						Byte Block5 = null;
						Byte Block6 = null;
						Byte Block7 = null;
						Byte Block8 = null;
						Byte Block9 = null;
						Byte Block10 = null;
						Byte Block11 = null;
						Byte Block12 = null;
						Byte Block13 = null;
						Byte Block14 = null;
						Byte Block15 = null;
						if (s.equalsIgnoreCase("1")) {
							Block1 = plugin.l1.block1;
							Block2 = plugin.l1.block2;
							Block3 = plugin.l1.block3;
							Block4 = plugin.l1.block4;
							Block5 = plugin.l1.block5;
							Block6 = plugin.l1.block6;
							Block7 = plugin.l1.block7;
							Block8 = plugin.l1.block8;
							Block9 = plugin.l1.block9;
							Block10 = plugin.l1.block10;
							Block11 = plugin.l1.block11;
							Block12 = plugin.l1.block12;
							Block13 = plugin.l1.block13;
							Block14 = plugin.l1.block14;
							Block15 = plugin.l1.block15;
						}else if (s.equalsIgnoreCase("2")) {
							Block1 = plugin.l2.block1;
							Block2 = plugin.l2.block2;
							Block3 = plugin.l2.block3;
							Block4 = plugin.l2.block4;
							Block5 = plugin.l2.block5;
							Block6 = plugin.l2.block6;
							Block7 = plugin.l2.block7;
							Block8 = plugin.l2.block8;
							Block9 = plugin.l2.block9;
							Block10 = plugin.l2.block10;
							Block11 = plugin.l2.block11;
							Block12 = plugin.l2.block12;
							Block13 = plugin.l2.block13;
							Block14 = plugin.l2.block14;
							Block15 = plugin.l2.block15;
						}else if (s.equalsIgnoreCase("3")) {
							Block1 = plugin.l3.block1;
							Block2 = plugin.l3.block2;
							Block3 = plugin.l3.block3;
							Block4 = plugin.l3.block4;
							Block5 = plugin.l3.block5;
							Block6 = plugin.l3.block6;
							Block7 = plugin.l3.block7;
							Block8 = plugin.l3.block8;
							Block9 = plugin.l3.block9;
							Block10 = plugin.l3.block10;
							Block11 = plugin.l3.block11;
							Block12 = plugin.l3.block12;
							Block13 = plugin.l3.block13;
							Block14 = plugin.l3.block14;
							Block15 = plugin.l3.block15;
						}else if (s.equalsIgnoreCase("4")) {
							Block1 = plugin.l4.block1;
							Block2 = plugin.l4.block2;
							Block3 = plugin.l4.block3;
							Block4 = plugin.l4.block4;
							Block5 = plugin.l4.block5;
							Block6 = plugin.l4.block6;
							Block7 = plugin.l4.block7;
							Block8 = plugin.l4.block8;
							Block9 = plugin.l4.block9;
							Block10 = plugin.l4.block10;
							Block11 = plugin.l4.block11;
							Block12 = plugin.l4.block12;
							Block13 = plugin.l4.block13;
							Block14 = plugin.l4.block14;
							Block15 = plugin.l4.block15;
						}else if (s.equalsIgnoreCase("5")) {
							Block1 = plugin.l5.block1;
							Block2 = plugin.l5.block2;
							Block3 = plugin.l5.block3;
							Block4 = plugin.l5.block4;
							Block5 = plugin.l5.block5;
							Block6 = plugin.l5.block6;
							Block7 = plugin.l5.block7;
							Block8 = plugin.l5.block8;
							Block9 = plugin.l5.block9;
							Block10 = plugin.l5.block10;
							Block11 = plugin.l5.block11;
							Block12 = plugin.l5.block12;
							Block13 = plugin.l5.block13;
							Block14 = plugin.l5.block14;
							Block15 = plugin.l5.block15;
						}else if (s.equalsIgnoreCase("6")) {
							Block1 = plugin.l6.block1;
							Block2 = plugin.l6.block2;
							Block3 = plugin.l6.block3;
							Block4 = plugin.l6.block4;
							Block5 = plugin.l6.block5;
							Block6 = plugin.l6.block6;
							Block7 = plugin.l6.block7;
							Block8 = plugin.l6.block8;
							Block9 = plugin.l6.block9;
							Block10 = plugin.l6.block10;
							Block11 = plugin.l6.block11;
							Block12 = plugin.l6.block12;
							Block13 = plugin.l6.block13;
							Block14 = plugin.l6.block14;
							Block15 = plugin.l6.block15;
						}else if (s.equalsIgnoreCase("7")) {
							Block1 = plugin.l7.block1;
							Block2 = plugin.l7.block2;
							Block3 = plugin.l7.block3;
							Block4 = plugin.l7.block4;
							Block5 = plugin.l7.block5;
							Block6 = plugin.l7.block6;
							Block7 = plugin.l7.block7;
							Block8 = plugin.l7.block8;
							Block9 = plugin.l7.block9;
							Block10 = plugin.l7.block10;
							Block11 = plugin.l7.block11;
							Block12 = plugin.l7.block12;
							Block13 = plugin.l7.block13;
							Block14 = plugin.l7.block14;
							Block15 = plugin.l7.block15;
						}else if (s.equalsIgnoreCase("8")) {
							Block1 = plugin.l8.block1;
							Block2 = plugin.l8.block2;
							Block3 = plugin.l8.block3;
							Block4 = plugin.l8.block4;
							Block5 = plugin.l8.block5;
							Block6 = plugin.l8.block6;
							Block7 = plugin.l8.block7;
							Block8 = plugin.l8.block8;
							Block9 = plugin.l8.block9;
							Block10 = plugin.l8.block10;
							Block11 = plugin.l8.block11;
							Block12 = plugin.l8.block12;
							Block13 = plugin.l8.block13;
							Block14 = plugin.l8.block14;
							Block15 = plugin.l8.block15;
						}else if (s.equalsIgnoreCase("9")) {
							Block1 = plugin.l9.block1;
							Block2 = plugin.l9.block2;
							Block3 = plugin.l9.block3;
							Block4 = plugin.l9.block4;
							Block5 = plugin.l9.block5;
							Block6 = plugin.l9.block6;
							Block7 = plugin.l9.block7;
							Block8 = plugin.l9.block8;
							Block9 = plugin.l9.block9;
							Block10 = plugin.l9.block10;
							Block11 = plugin.l9.block11;
							Block12 = plugin.l9.block12;
							Block13 = plugin.l9.block13;
							Block14 = plugin.l9.block14;
							Block15 = plugin.l9.block15;
						}else if (s.equalsIgnoreCase("0")) {
							Block1 = plugin.l0.block1;
							Block2 = plugin.l0.block2;
							Block3 = plugin.l0.block3;
							Block4 = plugin.l0.block4;
							Block5 = plugin.l0.block5;
							Block6 = plugin.l0.block6;
							Block7 = plugin.l0.block7;
							Block8 = plugin.l0.block8;
							Block9 = plugin.l0.block9;
							Block10 = plugin.l0.block10;
							Block11 = plugin.l0.block11;
							Block12 = plugin.l0.block12;
							Block13 = plugin.l0.block13;
							Block14 = plugin.l0.block14;
							Block15 = plugin.l0.block15;
						}else {
							Block1 = plugin.lunder.block1;
							Block2 = plugin.lunder.block2;
							Block3 = plugin.lunder.block3;
							Block4 = plugin.lunder.block4;
							Block5 = plugin.lunder.block5;
							Block6 = plugin.lunder.block6;
							Block7 = plugin.lunder.block7;
							Block8 = plugin.lunder.block8;
							Block9 = plugin.lunder.block9;
							Block10 = plugin.lunder.block10;
							Block11 = plugin.lunder.block11;
							Block12 = plugin.lunder.block12;
							Block13 = plugin.lunder.block13;
							Block14 = plugin.lunder.block14;
							Block15 = plugin.lunder.block15;
						}
						((CraftPlayer)p).sendBlockChange(seg.loc1, 35, Block1);
						((CraftPlayer)p).sendBlockChange(seg.loc2, 35, Block2);
						((CraftPlayer)p).sendBlockChange(seg.loc3, 35, Block3);
						((CraftPlayer)p).sendBlockChange(seg.loc4, 35, Block4);
						((CraftPlayer)p).sendBlockChange(seg.loc5, 35, Block5);
						((CraftPlayer)p).sendBlockChange(seg.loc6, 35, Block6);
						((CraftPlayer)p).sendBlockChange(seg.loc7, 35, Block7);
						((CraftPlayer)p).sendBlockChange(seg.loc8, 35, Block8);
						((CraftPlayer)p).sendBlockChange(seg.loc9, 35, Block9);
						((CraftPlayer)p).sendBlockChange(seg.loc10, 35, Block10);
						((CraftPlayer)p).sendBlockChange(seg.loc11, 35, Block11);
						((CraftPlayer)p).sendBlockChange(seg.loc12, 35, Block12);
						((CraftPlayer)p).sendBlockChange(seg.loc13, 35, Block13);
						((CraftPlayer)p).sendBlockChange(seg.loc14, 35, Block14);
						((CraftPlayer)p).sendBlockChange(seg.loc15, 35, Block15);
						deathss++;
					}

			      String Wins = hm.get("PlayerWins");
			      ArrayList<Integer> winssa = new ArrayList<>();
			      
					for(int deathsfjf = 0; deathsfjf< Wins.length(); deathsfjf++) {
						char cha = Wins.charAt(deathsfjf);
						Integer ints = Character.getNumericValue(cha);
						winssa.add(ints);
					}
					int winss =  0;
					while(winss != winssa.size()) {
						Segment seg = plugin.wins.get(winss);
						String s = null;
							s = ""+winssa.get(winss);
						
						Byte Block1 = null;
						Byte Block2 = null;
						Byte Block3 = null;
						Byte Block4 = null;
						Byte Block5 = null;
						Byte Block6 = null;
						Byte Block7 = null;
						Byte Block8 = null;
						Byte Block9 = null;
						Byte Block10 = null;
						Byte Block11 = null;
						Byte Block12 = null;
						Byte Block13 = null;
						Byte Block14 = null;
						Byte Block15 = null;
						if (s.equalsIgnoreCase("1")) {
							Block1 = plugin.l1.block1;
							Block2 = plugin.l1.block2;
							Block3 = plugin.l1.block3;
							Block4 = plugin.l1.block4;
							Block5 = plugin.l1.block5;
							Block6 = plugin.l1.block6;
							Block7 = plugin.l1.block7;
							Block8 = plugin.l1.block8;
							Block9 = plugin.l1.block9;
							Block10 = plugin.l1.block10;
							Block11 = plugin.l1.block11;
							Block12 = plugin.l1.block12;
							Block13 = plugin.l1.block13;
							Block14 = plugin.l1.block14;
							Block15 = plugin.l1.block15;
						}else if (s.equalsIgnoreCase("2")) {
							Block1 = plugin.l2.block1;
							Block2 = plugin.l2.block2;
							Block3 = plugin.l2.block3;
							Block4 = plugin.l2.block4;
							Block5 = plugin.l2.block5;
							Block6 = plugin.l2.block6;
							Block7 = plugin.l2.block7;
							Block8 = plugin.l2.block8;
							Block9 = plugin.l2.block9;
							Block10 = plugin.l2.block10;
							Block11 = plugin.l2.block11;
							Block12 = plugin.l2.block12;
							Block13 = plugin.l2.block13;
							Block14 = plugin.l2.block14;
							Block15 = plugin.l2.block15;
						}else if (s.equalsIgnoreCase("3")) {
							Block1 = plugin.l3.block1;
							Block2 = plugin.l3.block2;
							Block3 = plugin.l3.block3;
							Block4 = plugin.l3.block4;
							Block5 = plugin.l3.block5;
							Block6 = plugin.l3.block6;
							Block7 = plugin.l3.block7;
							Block8 = plugin.l3.block8;
							Block9 = plugin.l3.block9;
							Block10 = plugin.l3.block10;
							Block11 = plugin.l3.block11;
							Block12 = plugin.l3.block12;
							Block13 = plugin.l3.block13;
							Block14 = plugin.l3.block14;
							Block15 = plugin.l3.block15;
						}else if (s.equalsIgnoreCase("4")) {
							Block1 = plugin.l4.block1;
							Block2 = plugin.l4.block2;
							Block3 = plugin.l4.block3;
							Block4 = plugin.l4.block4;
							Block5 = plugin.l4.block5;
							Block6 = plugin.l4.block6;
							Block7 = plugin.l4.block7;
							Block8 = plugin.l4.block8;
							Block9 = plugin.l4.block9;
							Block10 = plugin.l4.block10;
							Block11 = plugin.l4.block11;
							Block12 = plugin.l4.block12;
							Block13 = plugin.l4.block13;
							Block14 = plugin.l4.block14;
							Block15 = plugin.l4.block15;
						}else if (s.equalsIgnoreCase("5")) {
							Block1 = plugin.l5.block1;
							Block2 = plugin.l5.block2;
							Block3 = plugin.l5.block3;
							Block4 = plugin.l5.block4;
							Block5 = plugin.l5.block5;
							Block6 = plugin.l5.block6;
							Block7 = plugin.l5.block7;
							Block8 = plugin.l5.block8;
							Block9 = plugin.l5.block9;
							Block10 = plugin.l5.block10;
							Block11 = plugin.l5.block11;
							Block12 = plugin.l5.block12;
							Block13 = plugin.l5.block13;
							Block14 = plugin.l5.block14;
							Block15 = plugin.l5.block15;
						}else if (s.equalsIgnoreCase("6")) {
							Block1 = plugin.l6.block1;
							Block2 = plugin.l6.block2;
							Block3 = plugin.l6.block3;
							Block4 = plugin.l6.block4;
							Block5 = plugin.l6.block5;
							Block6 = plugin.l6.block6;
							Block7 = plugin.l6.block7;
							Block8 = plugin.l6.block8;
							Block9 = plugin.l6.block9;
							Block10 = plugin.l6.block10;
							Block11 = plugin.l6.block11;
							Block12 = plugin.l6.block12;
							Block13 = plugin.l6.block13;
							Block14 = plugin.l6.block14;
							Block15 = plugin.l6.block15;
						}else if (s.equalsIgnoreCase("7")) {
							Block1 = plugin.l7.block1;
							Block2 = plugin.l7.block2;
							Block3 = plugin.l7.block3;
							Block4 = plugin.l7.block4;
							Block5 = plugin.l7.block5;
							Block6 = plugin.l7.block6;
							Block7 = plugin.l7.block7;
							Block8 = plugin.l7.block8;
							Block9 = plugin.l7.block9;
							Block10 = plugin.l7.block10;
							Block11 = plugin.l7.block11;
							Block12 = plugin.l7.block12;
							Block13 = plugin.l7.block13;
							Block14 = plugin.l7.block14;
							Block15 = plugin.l7.block15;
						}else if (s.equalsIgnoreCase("8")) {
							Block1 = plugin.l8.block1;
							Block2 = plugin.l8.block2;
							Block3 = plugin.l8.block3;
							Block4 = plugin.l8.block4;
							Block5 = plugin.l8.block5;
							Block6 = plugin.l8.block6;
							Block7 = plugin.l8.block7;
							Block8 = plugin.l8.block8;
							Block9 = plugin.l8.block9;
							Block10 = plugin.l8.block10;
							Block11 = plugin.l8.block11;
							Block12 = plugin.l8.block12;
							Block13 = plugin.l8.block13;
							Block14 = plugin.l8.block14;
							Block15 = plugin.l8.block15;
						}else if (s.equalsIgnoreCase("9")) {
							Block1 = plugin.l9.block1;
							Block2 = plugin.l9.block2;
							Block3 = plugin.l9.block3;
							Block4 = plugin.l9.block4;
							Block5 = plugin.l9.block5;
							Block6 = plugin.l9.block6;
							Block7 = plugin.l9.block7;
							Block8 = plugin.l9.block8;
							Block9 = plugin.l9.block9;
							Block10 = plugin.l9.block10;
							Block11 = plugin.l9.block11;
							Block12 = plugin.l9.block12;
							Block13 = plugin.l9.block13;
							Block14 = plugin.l9.block14;
							Block15 = plugin.l9.block15;
						}else if (s.equalsIgnoreCase("0")) {
							Block1 = plugin.l0.block1;
							Block2 = plugin.l0.block2;
							Block3 = plugin.l0.block3;
							Block4 = plugin.l0.block4;
							Block5 = plugin.l0.block5;
							Block6 = plugin.l0.block6;
							Block7 = plugin.l0.block7;
							Block8 = plugin.l0.block8;
							Block9 = plugin.l0.block9;
							Block10 = plugin.l0.block10;
							Block11 = plugin.l0.block11;
							Block12 = plugin.l0.block12;
							Block13 = plugin.l0.block13;
							Block14 = plugin.l0.block14;
							Block15 = plugin.l0.block15;
						}else {
							Block1 = plugin.lunder.block1;
							Block2 = plugin.lunder.block2;
							Block3 = plugin.lunder.block3;
							Block4 = plugin.lunder.block4;
							Block5 = plugin.lunder.block5;
							Block6 = plugin.lunder.block6;
							Block7 = plugin.lunder.block7;
							Block8 = plugin.lunder.block8;
							Block9 = plugin.lunder.block9;
							Block10 = plugin.lunder.block10;
							Block11 = plugin.lunder.block11;
							Block12 = plugin.lunder.block12;
							Block13 = plugin.lunder.block13;
							Block14 = plugin.lunder.block14;
							Block15 = plugin.lunder.block15;
						}
						((CraftPlayer)p).sendBlockChange(seg.loc1, 35, Block1);
						((CraftPlayer)p).sendBlockChange(seg.loc2, 35, Block2);
						((CraftPlayer)p).sendBlockChange(seg.loc3, 35, Block3);
						((CraftPlayer)p).sendBlockChange(seg.loc4, 35, Block4);
						((CraftPlayer)p).sendBlockChange(seg.loc5, 35, Block5);
						((CraftPlayer)p).sendBlockChange(seg.loc6, 35, Block6);
						((CraftPlayer)p).sendBlockChange(seg.loc7, 35, Block7);
						((CraftPlayer)p).sendBlockChange(seg.loc8, 35, Block8);
						((CraftPlayer)p).sendBlockChange(seg.loc9, 35, Block9);
						((CraftPlayer)p).sendBlockChange(seg.loc10, 35, Block10);
						((CraftPlayer)p).sendBlockChange(seg.loc11, 35, Block11);
						((CraftPlayer)p).sendBlockChange(seg.loc12, 35, Block12);
						((CraftPlayer)p).sendBlockChange(seg.loc13, 35, Block13);
						((CraftPlayer)p).sendBlockChange(seg.loc14, 35, Block14);
						((CraftPlayer)p).sendBlockChange(seg.loc15, 35, Block15);
						winss++;
					}
			      String losses = hm.get("Playerlosses");
			      ArrayList<Integer> losessa = new ArrayList<>();
			      for(int deathsfjf = 0; deathsfjf< losses.length(); deathsfjf++) {
						char cha = losses.charAt(deathsfjf);
						Integer ints = Character.getNumericValue(cha);
						losessa.add(ints);
					}
					int losess =  0;
					while(losess != losessa.size()) {
						Segment seg = plugin.losses.get(losess);
						String s = null;
						
							s = ""+losessa.get(losess);
						
						Byte Block1 = null;
						Byte Block2 = null;
						Byte Block3 = null;
						Byte Block4 = null;
						Byte Block5 = null;
						Byte Block6 = null;
						Byte Block7 = null;
						Byte Block8 = null;
						Byte Block9 = null;
						Byte Block10 = null;
						Byte Block11 = null;
						Byte Block12 = null;
						Byte Block13 = null;
						Byte Block14 = null;
						Byte Block15 = null;
						if (s.equalsIgnoreCase("1")) {
							Block1 = plugin.l1.block1;
							Block2 = plugin.l1.block2;
							Block3 = plugin.l1.block3;
							Block4 = plugin.l1.block4;
							Block5 = plugin.l1.block5;
							Block6 = plugin.l1.block6;
							Block7 = plugin.l1.block7;
							Block8 = plugin.l1.block8;
							Block9 = plugin.l1.block9;
							Block10 = plugin.l1.block10;
							Block11 = plugin.l1.block11;
							Block12 = plugin.l1.block12;
							Block13 = plugin.l1.block13;
							Block14 = plugin.l1.block14;
							Block15 = plugin.l1.block15;
						}else if (s.equalsIgnoreCase("2")) {
							Block1 = plugin.l2.block1;
							Block2 = plugin.l2.block2;
							Block3 = plugin.l2.block3;
							Block4 = plugin.l2.block4;
							Block5 = plugin.l2.block5;
							Block6 = plugin.l2.block6;
							Block7 = plugin.l2.block7;
							Block8 = plugin.l2.block8;
							Block9 = plugin.l2.block9;
							Block10 = plugin.l2.block10;
							Block11 = plugin.l2.block11;
							Block12 = plugin.l2.block12;
							Block13 = plugin.l2.block13;
							Block14 = plugin.l2.block14;
							Block15 = plugin.l2.block15;
						}else if (s.equalsIgnoreCase("3")) {
							Block1 = plugin.l3.block1;
							Block2 = plugin.l3.block2;
							Block3 = plugin.l3.block3;
							Block4 = plugin.l3.block4;
							Block5 = plugin.l3.block5;
							Block6 = plugin.l3.block6;
							Block7 = plugin.l3.block7;
							Block8 = plugin.l3.block8;
							Block9 = plugin.l3.block9;
							Block10 = plugin.l3.block10;
							Block11 = plugin.l3.block11;
							Block12 = plugin.l3.block12;
							Block13 = plugin.l3.block13;
							Block14 = plugin.l3.block14;
							Block15 = plugin.l3.block15;
						}else if (s.equalsIgnoreCase("4")) {
							Block1 = plugin.l4.block1;
							Block2 = plugin.l4.block2;
							Block3 = plugin.l4.block3;
							Block4 = plugin.l4.block4;
							Block5 = plugin.l4.block5;
							Block6 = plugin.l4.block6;
							Block7 = plugin.l4.block7;
							Block8 = plugin.l4.block8;
							Block9 = plugin.l4.block9;
							Block10 = plugin.l4.block10;
							Block11 = plugin.l4.block11;
							Block12 = plugin.l4.block12;
							Block13 = plugin.l4.block13;
							Block14 = plugin.l4.block14;
							Block15 = plugin.l4.block15;
						}else if (s.equalsIgnoreCase("5")) {
							Block1 = plugin.l5.block1;
							Block2 = plugin.l5.block2;
							Block3 = plugin.l5.block3;
							Block4 = plugin.l5.block4;
							Block5 = plugin.l5.block5;
							Block6 = plugin.l5.block6;
							Block7 = plugin.l5.block7;
							Block8 = plugin.l5.block8;
							Block9 = plugin.l5.block9;
							Block10 = plugin.l5.block10;
							Block11 = plugin.l5.block11;
							Block12 = plugin.l5.block12;
							Block13 = plugin.l5.block13;
							Block14 = plugin.l5.block14;
							Block15 = plugin.l5.block15;
						}else if (s.equalsIgnoreCase("6")) {
							Block1 = plugin.l6.block1;
							Block2 = plugin.l6.block2;
							Block3 = plugin.l6.block3;
							Block4 = plugin.l6.block4;
							Block5 = plugin.l6.block5;
							Block6 = plugin.l6.block6;
							Block7 = plugin.l6.block7;
							Block8 = plugin.l6.block8;
							Block9 = plugin.l6.block9;
							Block10 = plugin.l6.block10;
							Block11 = plugin.l6.block11;
							Block12 = plugin.l6.block12;
							Block13 = plugin.l6.block13;
							Block14 = plugin.l6.block14;
							Block15 = plugin.l6.block15;
						}else if (s.equalsIgnoreCase("7")) {
							Block1 = plugin.l7.block1;
							Block2 = plugin.l7.block2;
							Block3 = plugin.l7.block3;
							Block4 = plugin.l7.block4;
							Block5 = plugin.l7.block5;
							Block6 = plugin.l7.block6;
							Block7 = plugin.l7.block7;
							Block8 = plugin.l7.block8;
							Block9 = plugin.l7.block9;
							Block10 = plugin.l7.block10;
							Block11 = plugin.l7.block11;
							Block12 = plugin.l7.block12;
							Block13 = plugin.l7.block13;
							Block14 = plugin.l7.block14;
							Block15 = plugin.l7.block15;
						}else if (s.equalsIgnoreCase("8")) {
							Block1 = plugin.l8.block1;
							Block2 = plugin.l8.block2;
							Block3 = plugin.l8.block3;
							Block4 = plugin.l8.block4;
							Block5 = plugin.l8.block5;
							Block6 = plugin.l8.block6;
							Block7 = plugin.l8.block7;
							Block8 = plugin.l8.block8;
							Block9 = plugin.l8.block9;
							Block10 = plugin.l8.block10;
							Block11 = plugin.l8.block11;
							Block12 = plugin.l8.block12;
							Block13 = plugin.l8.block13;
							Block14 = plugin.l8.block14;
							Block15 = plugin.l8.block15;
						}else if (s.equalsIgnoreCase("9")) {
							Block1 = plugin.l9.block1;
							Block2 = plugin.l9.block2;
							Block3 = plugin.l9.block3;
							Block4 = plugin.l9.block4;
							Block5 = plugin.l9.block5;
							Block6 = plugin.l9.block6;
							Block7 = plugin.l9.block7;
							Block8 = plugin.l9.block8;
							Block9 = plugin.l9.block9;
							Block10 = plugin.l9.block10;
							Block11 = plugin.l9.block11;
							Block12 = plugin.l9.block12;
							Block13 = plugin.l9.block13;
							Block14 = plugin.l9.block14;
							Block15 = plugin.l9.block15;
						}else if (s.equalsIgnoreCase("0")) {
							Block1 = plugin.l0.block1;
							Block2 = plugin.l0.block2;
							Block3 = plugin.l0.block3;
							Block4 = plugin.l0.block4;
							Block5 = plugin.l0.block5;
							Block6 = plugin.l0.block6;
							Block7 = plugin.l0.block7;
							Block8 = plugin.l0.block8;
							Block9 = plugin.l0.block9;
							Block10 = plugin.l0.block10;
							Block11 = plugin.l0.block11;
							Block12 = plugin.l0.block12;
							Block13 = plugin.l0.block13;
							Block14 = plugin.l0.block14;
							Block15 = plugin.l0.block15;
						}else {
							Block1 = plugin.lunder.block1;
							Block2 = plugin.lunder.block2;
							Block3 = plugin.lunder.block3;
							Block4 = plugin.lunder.block4;
							Block5 = plugin.lunder.block5;
							Block6 = plugin.lunder.block6;
							Block7 = plugin.lunder.block7;
							Block8 = plugin.lunder.block8;
							Block9 = plugin.lunder.block9;
							Block10 = plugin.lunder.block10;
							Block11 = plugin.lunder.block11;
							Block12 = plugin.lunder.block12;
							Block13 = plugin.lunder.block13;
							Block14 = plugin.lunder.block14;
							Block15 = plugin.lunder.block15;
						}
						((CraftPlayer)p).sendBlockChange(seg.loc1, 35, Block1);
						((CraftPlayer)p).sendBlockChange(seg.loc2, 35, Block2);
						((CraftPlayer)p).sendBlockChange(seg.loc3, 35, Block3);
						((CraftPlayer)p).sendBlockChange(seg.loc4, 35, Block4);
						((CraftPlayer)p).sendBlockChange(seg.loc5, 35, Block5);
						((CraftPlayer)p).sendBlockChange(seg.loc6, 35, Block6);
						((CraftPlayer)p).sendBlockChange(seg.loc7, 35, Block7);
						((CraftPlayer)p).sendBlockChange(seg.loc8, 35, Block8);
						((CraftPlayer)p).sendBlockChange(seg.loc9, 35, Block9);
						((CraftPlayer)p).sendBlockChange(seg.loc10, 35, Block10);
						((CraftPlayer)p).sendBlockChange(seg.loc11, 35, Block11);
						((CraftPlayer)p).sendBlockChange(seg.loc12, 35, Block12);
						((CraftPlayer)p).sendBlockChange(seg.loc13, 35, Block13);
						((CraftPlayer)p).sendBlockChange(seg.loc14, 35, Block14);
						((CraftPlayer)p).sendBlockChange(seg.loc15, 35, Block15);
						losess++;
					}
					
					/*CCPlayerModule ccplayer = plugin.api.getModuleForClass(CCPlayerModule.class);
					CCPlayer ccplayers = ccplayer.getPlayer(p);
					Integer points = ccplayers.getPoints();
					String spoints = ""+points;
					Integer levels = ccplayers.getLevel();

					if(points > 999) {
					    ArrayList<Integer> pointsl = new ArrayList<>();
					    for(int pointsi = 0; pointsi< spoints.length(); pointsi++) {
					    	char cha = spoints.charAt(pointsi);
							Integer ints = Character.getNumericValue(cha);
							pointsl.add(ints);
						}
					    int pointsi = 0;
						while(pointsi != pointsl.size()) {
							Segment seg = plugin.losses.get(pointsi);
							String s = null;
							
								s = ""+pointsl.get(pointsi);
					    
						Byte Block1 = null;
						Byte Block2 = null;
						Byte Block3 = null;
						Byte Block4 = null;
						Byte Block5 = null;
						Byte Block6 = null;
						Byte Block7 = null;
						Byte Block8 = null;
						Byte Block9 = null;
						Byte Block10 = null;
						Byte Block11 = null;
						Byte Block12 = null;
						Byte Block13 = null;
						Byte Block14 = null;
						Byte Block15 = null;
						if (s.equalsIgnoreCase("1")) {
							Block1 = plugin.l1.block1;
							Block2 = plugin.l1.block2;
							Block3 = plugin.l1.block3;
							Block4 = plugin.l1.block4;
							Block5 = plugin.l1.block5;
							Block6 = plugin.l1.block6;
							Block7 = plugin.l1.block7;
							Block8 = plugin.l1.block8;
							Block9 = plugin.l1.block9;
							Block10 = plugin.l1.block10;
							Block11 = plugin.l1.block11;
							Block12 = plugin.l1.block12;
							Block13 = plugin.l1.block13;
							Block14 = plugin.l1.block14;
							Block15 = plugin.l1.block15;
						}else if (s.equalsIgnoreCase("2")) {
							Block1 = plugin.l2.block1;
							Block2 = plugin.l2.block2;
							Block3 = plugin.l2.block3;
							Block4 = plugin.l2.block4;
							Block5 = plugin.l2.block5;
							Block6 = plugin.l2.block6;
							Block7 = plugin.l2.block7;
							Block8 = plugin.l2.block8;
							Block9 = plugin.l2.block9;
							Block10 = plugin.l2.block10;
							Block11 = plugin.l2.block11;
							Block12 = plugin.l2.block12;
							Block13 = plugin.l2.block13;
							Block14 = plugin.l2.block14;
							Block15 = plugin.l2.block15;
						}else if (s.equalsIgnoreCase("3")) {
							Block1 = plugin.l3.block1;
							Block2 = plugin.l3.block2;
							Block3 = plugin.l3.block3;
							Block4 = plugin.l3.block4;
							Block5 = plugin.l3.block5;
							Block6 = plugin.l3.block6;
							Block7 = plugin.l3.block7;
							Block8 = plugin.l3.block8;
							Block9 = plugin.l3.block9;
							Block10 = plugin.l3.block10;
							Block11 = plugin.l3.block11;
							Block12 = plugin.l3.block12;
							Block13 = plugin.l3.block13;
							Block14 = plugin.l3.block14;
							Block15 = plugin.l3.block15;
						}else if (s.equalsIgnoreCase("4")) {
							Block1 = plugin.l4.block1;
							Block2 = plugin.l4.block2;
							Block3 = plugin.l4.block3;
							Block4 = plugin.l4.block4;
							Block5 = plugin.l4.block5;
							Block6 = plugin.l4.block6;
							Block7 = plugin.l4.block7;
							Block8 = plugin.l4.block8;
							Block9 = plugin.l4.block9;
							Block10 = plugin.l4.block10;
							Block11 = plugin.l4.block11;
							Block12 = plugin.l4.block12;
							Block13 = plugin.l4.block13;
							Block14 = plugin.l4.block14;
							Block15 = plugin.l4.block15;
						}else if (s.equalsIgnoreCase("5")) {
							Block1 = plugin.l5.block1;
							Block2 = plugin.l5.block2;
							Block3 = plugin.l5.block3;
							Block4 = plugin.l5.block4;
							Block5 = plugin.l5.block5;
							Block6 = plugin.l5.block6;
							Block7 = plugin.l5.block7;
							Block8 = plugin.l5.block8;
							Block9 = plugin.l5.block9;
							Block10 = plugin.l5.block10;
							Block11 = plugin.l5.block11;
							Block12 = plugin.l5.block12;
							Block13 = plugin.l5.block13;
							Block14 = plugin.l5.block14;
							Block15 = plugin.l5.block15;
						}else if (s.equalsIgnoreCase("6")) {
							Block1 = plugin.l6.block1;
							Block2 = plugin.l6.block2;
							Block3 = plugin.l6.block3;
							Block4 = plugin.l6.block4;
							Block5 = plugin.l6.block5;
							Block6 = plugin.l6.block6;
							Block7 = plugin.l6.block7;
							Block8 = plugin.l6.block8;
							Block9 = plugin.l6.block9;
							Block10 = plugin.l6.block10;
							Block11 = plugin.l6.block11;
							Block12 = plugin.l6.block12;
							Block13 = plugin.l6.block13;
							Block14 = plugin.l6.block14;
							Block15 = plugin.l6.block15;
						}else if (s.equalsIgnoreCase("7")) {
							Block1 = plugin.l7.block1;
							Block2 = plugin.l7.block2;
							Block3 = plugin.l7.block3;
							Block4 = plugin.l7.block4;
							Block5 = plugin.l7.block5;
							Block6 = plugin.l7.block6;
							Block7 = plugin.l7.block7;
							Block8 = plugin.l7.block8;
							Block9 = plugin.l7.block9;
							Block10 = plugin.l7.block10;
							Block11 = plugin.l7.block11;
							Block12 = plugin.l7.block12;
							Block13 = plugin.l7.block13;
							Block14 = plugin.l7.block14;
							Block15 = plugin.l7.block15;
						}else if (s.equalsIgnoreCase("8")) {
							Block1 = plugin.l8.block1;
							Block2 = plugin.l8.block2;
							Block3 = plugin.l8.block3;
							Block4 = plugin.l8.block4;
							Block5 = plugin.l8.block5;
							Block6 = plugin.l8.block6;
							Block7 = plugin.l8.block7;
							Block8 = plugin.l8.block8;
							Block9 = plugin.l8.block9;
							Block10 = plugin.l8.block10;
							Block11 = plugin.l8.block11;
							Block12 = plugin.l8.block12;
							Block13 = plugin.l8.block13;
							Block14 = plugin.l8.block14;
							Block15 = plugin.l8.block15;
						}else if (s.equalsIgnoreCase("9")) {
							Block1 = plugin.l9.block1;
							Block2 = plugin.l9.block2;
							Block3 = plugin.l9.block3;
							Block4 = plugin.l9.block4;
							Block5 = plugin.l9.block5;
							Block6 = plugin.l9.block6;
							Block7 = plugin.l9.block7;
							Block8 = plugin.l9.block8;
							Block9 = plugin.l9.block9;
							Block10 = plugin.l9.block10;
							Block11 = plugin.l9.block11;
							Block12 = plugin.l9.block12;
							Block13 = plugin.l9.block13;
							Block14 = plugin.l9.block14;
							Block15 = plugin.l9.block15;
						}else if (s.equalsIgnoreCase("0")) {
							Block1 = plugin.l0.block1;
							Block2 = plugin.l0.block2;
							Block3 = plugin.l0.block3;
							Block4 = plugin.l0.block4;
							Block5 = plugin.l0.block5;
							Block6 = plugin.l0.block6;
							Block7 = plugin.l0.block7;
							Block8 = plugin.l0.block8;
							Block9 = plugin.l0.block9;
							Block10 = plugin.l0.block10;
							Block11 = plugin.l0.block11;
							Block12 = plugin.l0.block12;
							Block13 = plugin.l0.block13;
							Block14 = plugin.l0.block14;
							Block15 = plugin.l0.block15;
						}else {
							Block1 = plugin.lunder.block1;
							Block2 = plugin.lunder.block2;
							Block3 = plugin.lunder.block3;
							Block4 = plugin.lunder.block4;
							Block5 = plugin.lunder.block5;
							Block6 = plugin.lunder.block6;
							Block7 = plugin.lunder.block7;
							Block8 = plugin.lunder.block8;
							Block9 = plugin.lunder.block9;
							Block10 = plugin.lunder.block10;
							Block11 = plugin.lunder.block11;
							Block12 = plugin.lunder.block12;
							Block13 = plugin.lunder.block13;
							Block14 = plugin.lunder.block14;
							Block15 = plugin.lunder.block15;
						}
						((CraftPlayer)p).sendBlockChange(seg.loc1, 35, Block1);
						((CraftPlayer)p).sendBlockChange(seg.loc2, 35, Block2);
						((CraftPlayer)p).sendBlockChange(seg.loc3, 35, Block3);
						((CraftPlayer)p).sendBlockChange(seg.loc4, 35, Block4);
						((CraftPlayer)p).sendBlockChange(seg.loc5, 35, Block5);
						((CraftPlayer)p).sendBlockChange(seg.loc6, 35, Block6);
						((CraftPlayer)p).sendBlockChange(seg.loc7, 35, Block7);
						((CraftPlayer)p).sendBlockChange(seg.loc8, 35, Block8);
						((CraftPlayer)p).sendBlockChange(seg.loc9, 35, Block9);
						((CraftPlayer)p).sendBlockChange(seg.loc10, 35, Block10);
						((CraftPlayer)p).sendBlockChange(seg.loc11, 35, Block11);
						((CraftPlayer)p).sendBlockChange(seg.loc12, 35, Block12);
						((CraftPlayer)p).sendBlockChange(seg.loc13, 35, Block13);
						((CraftPlayer)p).sendBlockChange(seg.loc14, 35, Block14);
						((CraftPlayer)p).sendBlockChange(seg.loc15, 35, Block15);
						pointsi++;
					
						
						}

						
						
						
						
						
					} else {
						
						
						
					}/*
					
					//Integer kds = k/d;
					//String kdss = ""+kds;
				    /*for(int kdsint = 0; kdsint< kdss.length(); kdsint++) {
							char cha = kdss.charAt(kdsint);
							Integer ints = Character.getNumericValue(cha);
							kd.add(""+ints);
					}
				    if(kd.get(1).equalsIgnoreCase(".")){
				    	Location loc = new Location(Bukkit.getWorld("world"), -137, 142, 42);
				    	Location loc1 = new Location(Bukkit.getWorld("world"), -139, 146, 40);
				    	Location loc2 = new Location(Bukkit.getWorld("world"), -139, 146, 39);
				    	Location loc3 = new Location(Bukkit.getWorld("world"), -139, 146, 38);
				    	Location loc4 = new Location(Bukkit.getWorld("world"), -139, 145, 40);
				    	Location loc5 = new Location(Bukkit.getWorld("world"), -139, 145, 39);
				    	Location loc6 = new Location(Bukkit.getWorld("world"), -139, 145, 38);
				    	Location loc7 = new Location(Bukkit.getWorld("world"), -139, 144, 40);
				    	Location loc8 = new Location(Bukkit.getWorld("world"), -139, 144, 39);
				    	Location loc9 = new Location(Bukkit.getWorld("world"), -139, 144, 38);
				    	Location loc10 = new Location(Bukkit.getWorld("world"), -139, 143, 40);
				    	Location loc11 = new Location(Bukkit.getWorld("world"), -139, 143, 39);
				    	Location loc12 = new Location(Bukkit.getWorld("world"), -139, 143, 38);
				    	Location loc13 = new Location(Bukkit.getWorld("world"), -139, 142, 40);
				    	Location loc14 = new Location(Bukkit.getWorld("world"), -139, 142, 39);
				    	Location loc15 = new Location(Bukkit.getWorld("world"), -139, 142, 38);
				    } else if (kd.get(2).equalsIgnoreCase(".")) {
						
					}*/
					
					
				
				for(Line line : plugin.board.lines) {
					int i = 0;
					
					while(i != line.segmants.size()){
						Segment segment = line.segmants.get(i);
						String s = null;
						if(i < line.letters.get(name).size()) {
							s = ""+line.letters.get(name).get(i);
						}
						
						Byte Block1 = null;
						Byte Block2 = null;
						Byte Block3 = null;
						Byte Block4 = null;
						Byte Block5 = null;
						Byte Block6 = null;
						Byte Block7 = null;
						Byte Block8 = null;
						Byte Block9 = null;
						Byte Block10 = null;
						Byte Block11 = null;
						Byte Block12 = null;
						Byte Block13 = null;
						Byte Block14 = null;
						Byte Block15 = null;
						if(s != null) {
						if(s.equalsIgnoreCase("a")) {
							Block1 = plugin.la.block1;
							Block2 = plugin.la.block2;
							Block3 = plugin.la.block3;
							Block4 = plugin.la.block4;
							Block5 = plugin.la.block5;
							Block6 = plugin.la.block6;
							Block7 = plugin.la.block7;
							Block8 = plugin.la.block8;
							Block9 = plugin.la.block9;
							Block10 = plugin.la.block10;
							Block11 = plugin.la.block11;
							Block12 = plugin.la.block12;
							Block13 = plugin.la.block13;
							Block14 = plugin.la.block14;
							Block15 = plugin.la.block15;
						}else if (s.equalsIgnoreCase("b")) {
							Block1 = plugin.lb.block1;
							Block2 = plugin.lb.block2;
							Block3 = plugin.lb.block3;
							Block4 = plugin.lb.block4;
							Block5 = plugin.lb.block5;
							Block6 = plugin.lb.block6;
							Block7 = plugin.lb.block7;
							Block8 = plugin.lb.block8;
							Block9 = plugin.lb.block9;
							Block10 = plugin.lb.block10;
							Block11 = plugin.lb.block11;
							Block12 = plugin.lb.block12;
							Block13 = plugin.lb.block13;
							Block14 = plugin.lb.block14;
							Block15 = plugin.lb.block15;
						}else if (s.equalsIgnoreCase("c")) {
							Block1 = plugin.lc.block1;
							Block2 = plugin.lc.block2;
							Block3 = plugin.lc.block3;
							Block4 = plugin.lc.block4;
							Block5 = plugin.lc.block5;
							Block6 = plugin.lc.block6;
							Block7 = plugin.lc.block7;
							Block8 = plugin.lc.block8;
							Block9 = plugin.lc.block9;
							Block10 = plugin.lc.block10;
							Block11 = plugin.lc.block11;
							Block12 = plugin.lc.block12;
							Block13 = plugin.lc.block13;
							Block14 = plugin.lc.block14;
							Block15 = plugin.lc.block15;
						}else if (s.equalsIgnoreCase("d")) {
							Block1 = plugin.ld.block1;
							Block2 = plugin.ld.block2;
							Block3 = plugin.ld.block3;
							Block4 = plugin.ld.block4;
							Block5 = plugin.ld.block5;
							Block6 = plugin.ld.block6;
							Block7 = plugin.ld.block7;
							Block8 = plugin.ld.block8;
							Block9 = plugin.ld.block9;
							Block10 = plugin.ld.block10;
							Block11 = plugin.ld.block11;
							Block12 = plugin.ld.block12;
							Block13 = plugin.ld.block13;
							Block14 = plugin.ld.block14;
							Block15 = plugin.ld.block15;
						}else if (s.equalsIgnoreCase("e")) {
							Block1 = plugin.le.block1;
							Block2 = plugin.le.block2;
							Block3 = plugin.le.block3;
							Block4 = plugin.le.block4;
							Block5 = plugin.le.block5;
							Block6 = plugin.le.block6;
							Block7 = plugin.le.block7;
							Block8 = plugin.le.block8;
							Block9 = plugin.le.block9;
							Block10 = plugin.le.block10;
							Block11 = plugin.le.block11;
							Block12 = plugin.le.block12;
							Block13 = plugin.le.block13;
							Block14 = plugin.le.block14;
							Block15 = plugin.le.block15;
						}else if (s.equalsIgnoreCase("f")) {
							Block1 = plugin.lf.block1;
							Block2 = plugin.lf.block2;
							Block3 = plugin.lf.block3;
							Block4 = plugin.lf.block4;
							Block5 = plugin.lf.block5;
							Block6 = plugin.lf.block6;
							Block7 = plugin.lf.block7;
							Block8 = plugin.lf.block8;
							Block9 = plugin.lf.block9;
							Block10 = plugin.lf.block10;
							Block11 = plugin.lf.block11;
							Block12 = plugin.lf.block12;
							Block13 = plugin.lf.block13;
							Block14 = plugin.lf.block14;
							Block15 = plugin.lf.block15;
						}else if (s.equalsIgnoreCase("g")) {
							Block1 = plugin.lg.block1;
							Block2 = plugin.lg.block2;
							Block3 = plugin.lg.block3;
							Block4 = plugin.lg.block4;
							Block5 = plugin.lg.block5;
							Block6 = plugin.lg.block6;
							Block7 = plugin.lg.block7;
							Block8 = plugin.lg.block8;
							Block9 = plugin.lg.block9;
							Block10 = plugin.lg.block10;
							Block11 = plugin.lg.block11;
							Block12 = plugin.lg.block12;
							Block13 = plugin.lg.block13;
							Block14 = plugin.lg.block14;
							Block15 = plugin.lg.block15;
						}else if (s.equalsIgnoreCase("h")) {
							Block1 = plugin.lh.block1;
							Block2 = plugin.lh.block2;
							Block3 = plugin.lh.block3;
							Block4 = plugin.lh.block4;
							Block5 = plugin.lh.block5;
							Block6 = plugin.lh.block6;
							Block7 = plugin.lh.block7;
							Block8 = plugin.lh.block8;
							Block9 = plugin.lh.block9;
							Block10 = plugin.lh.block10;
							Block11 = plugin.lh.block11;
							Block12 = plugin.lh.block12;
							Block13 = plugin.lh.block13;
							Block14 = plugin.lh.block14;
							Block15 = plugin.lh.block15;
						}else if (s.equalsIgnoreCase("i")) {
							Block1 = plugin.li.block1;
							Block2 = plugin.li.block2;
							Block3 = plugin.li.block3;
							Block4 = plugin.li.block4;
							Block5 = plugin.li.block5;
							Block6 = plugin.li.block6;
							Block7 = plugin.li.block7;
							Block8 = plugin.li.block8;
							Block9 = plugin.li.block9;
							Block10 = plugin.li.block10;
							Block11 = plugin.li.block11;
							Block12 = plugin.li.block12;
							Block13 = plugin.li.block13;
							Block14 = plugin.li.block14;
							Block15 = plugin.li.block15;
						}else if (s.equalsIgnoreCase("j")) {
							Block1 = plugin.lj.block1;
							Block2 = plugin.lj.block2;
							Block3 = plugin.lj.block3;
							Block4 = plugin.lj.block4;
							Block5 = plugin.lj.block5;
							Block6 = plugin.lj.block6;
							Block7 = plugin.lj.block7;
							Block8 = plugin.lj.block8;
							Block9 = plugin.lj.block9;
							Block10 = plugin.lj.block10;
							Block11 = plugin.lj.block11;
							Block12 = plugin.lj.block12;
							Block13 = plugin.lj.block13;
							Block14 = plugin.lj.block14;
							Block15 = plugin.lj.block15;
						}else if (s.equalsIgnoreCase("k")) {
							Block1 = plugin.lk.block1;
							Block2 = plugin.lk.block2;
							Block3 = plugin.lk.block3;
							Block4 = plugin.lk.block4;
							Block5 = plugin.lk.block5;
							Block6 = plugin.lk.block6;
							Block7 = plugin.lk.block7;
							Block8 = plugin.lk.block8;
							Block9 = plugin.lk.block9;
							Block10 = plugin.lk.block10;
							Block11 = plugin.lk.block11;
							Block12 = plugin.lk.block12;
							Block13 = plugin.lk.block13;
							Block14 = plugin.lk.block14;
							Block15 = plugin.lk.block15;
						}else if (s.equalsIgnoreCase("l")) {
							Block1 = plugin.ll.block1;
							Block2 = plugin.ll.block2;
							Block3 = plugin.ll.block3;
							Block4 = plugin.ll.block4;
							Block5 = plugin.ll.block5;
							Block6 = plugin.ll.block6;
							Block7 = plugin.ll.block7;
							Block8 = plugin.ll.block8;
							Block9 = plugin.ll.block9;
							Block10 = plugin.ll.block10;
							Block11 = plugin.ll.block11;
							Block12 = plugin.ll.block12;
							Block13 = plugin.ll.block13;
							Block14 = plugin.ll.block14;
							Block15 = plugin.ll.block15;
						}else if (s.equalsIgnoreCase("m")) {
							Block1 = plugin.lm.block1;
							Block2 = plugin.lm.block2;
							Block3 = plugin.lm.block3;
							Block4 = plugin.lm.block4;
							Block5 = plugin.lm.block5;
							Block6 = plugin.lm.block6;
							Block7 = plugin.lm.block7;
							Block8 = plugin.lm.block8;
							Block9 = plugin.lm.block9;
							Block10 = plugin.lm.block10;
							Block11 = plugin.lm.block11;
							Block12 = plugin.lm.block12;
							Block13 = plugin.lm.block13;
							Block14 = plugin.lm.block14;
							Block15 = plugin.lm.block15;
						}else if (s.equalsIgnoreCase("n")) {
							Block1 = plugin.ln.block1;
							Block2 = plugin.ln.block2;
							Block3 = plugin.ln.block3;
							Block4 = plugin.ln.block4;
							Block5 = plugin.ln.block5;
							Block6 = plugin.ln.block6;
							Block7 = plugin.ln.block7;
							Block8 = plugin.ln.block8;
							Block9 = plugin.ln.block9;
							Block10 = plugin.ln.block10;
							Block11 = plugin.ln.block11;
							Block12 = plugin.ln.block12;
							Block13 = plugin.ln.block13;
							Block14 = plugin.ln.block14;
							Block15 = plugin.ln.block15;
						}else if (s.equalsIgnoreCase("o")) {
							Block1 = plugin.lo.block1;
							Block2 = plugin.lo.block2;
							Block3 = plugin.lo.block3;
							Block4 = plugin.lo.block4;
							Block5 = plugin.lo.block5;
							Block6 = plugin.lo.block6;
							Block7 = plugin.lo.block7;
							Block8 = plugin.lo.block8;
							Block9 = plugin.lo.block9;
							Block10 = plugin.lo.block10;
							Block11 = plugin.lo.block11;
							Block12 = plugin.lo.block12;
							Block13 = plugin.lo.block13;
							Block14 = plugin.lo.block14;
							Block15 = plugin.lo.block15;
						}else if (s.equalsIgnoreCase("p")) {
							Block1 = plugin.lp.block1;
							Block2 = plugin.lp.block2;
							Block3 = plugin.lp.block3;
							Block4 = plugin.lp.block4;
							Block5 = plugin.lp.block5;
							Block6 = plugin.lp.block6;
							Block7 = plugin.lp.block7;
							Block8 = plugin.lp.block8;
							Block9 = plugin.lp.block9;
							Block10 = plugin.lp.block10;
							Block11 = plugin.lp.block11;
							Block12 = plugin.lp.block12;
							Block13 = plugin.lp.block13;
							Block14 = plugin.lp.block14;
							Block15 = plugin.lp.block15;
						}else if (s.equalsIgnoreCase("q")) {
							Block1 = plugin.lq.block1;
							Block2 = plugin.lq.block2;
							Block3 = plugin.lq.block3;
							Block4 = plugin.lq.block4;
							Block5 = plugin.lq.block5;
							Block6 = plugin.lq.block6;
							Block7 = plugin.lq.block7;
							Block8 = plugin.lq.block8;
							Block9 = plugin.lq.block9;
							Block10 = plugin.lq.block10;
							Block11 = plugin.lq.block11;
							Block12 = plugin.lq.block12;
							Block13 = plugin.lq.block13;
							Block14 = plugin.lq.block14;
							Block15 = plugin.lq.block15;
						}else if (s.equalsIgnoreCase("r")) {
							Block1 = plugin.lr.block1;
							Block2 = plugin.lr.block2;
							Block3 = plugin.lr.block3;
							Block4 = plugin.lr.block4;
							Block5 = plugin.lr.block5;
							Block6 = plugin.lr.block6;
							Block7 = plugin.lr.block7;
							Block8 = plugin.lr.block8;
							Block9 = plugin.lr.block9;
							Block10 = plugin.lr.block10;
							Block11 = plugin.lr.block11;
							Block12 = plugin.lr.block12;
							Block13 = plugin.lr.block13;
							Block14 = plugin.lr.block14;
							Block15 = plugin.lr.block15;
						}else if (s.equalsIgnoreCase("s")) {
							Block1 = plugin.ls.block1;
							Block2 = plugin.ls.block2;
							Block3 = plugin.ls.block3;
							Block4 = plugin.ls.block4;
							Block5 = plugin.ls.block5;
							Block6 = plugin.ls.block6;
							Block7 = plugin.ls.block7;
							Block8 = plugin.ls.block8;
							Block9 = plugin.ls.block9;
							Block10 = plugin.ls.block10;
							Block11 = plugin.ls.block11;
							Block12 = plugin.ls.block12;
							Block13 = plugin.ls.block13;
							Block14 = plugin.ls.block14;
							Block15 = plugin.ls.block15;
						}else if (s.equalsIgnoreCase("t")) {
							Block1 = plugin.lt.block1;
							Block2 = plugin.lt.block2;
							Block3 = plugin.lt.block3;
							Block4 = plugin.lt.block4;
							Block5 = plugin.lt.block5;
							Block6 = plugin.lt.block6;
							Block7 = plugin.lt.block7;
							Block8 = plugin.lt.block8;
							Block9 = plugin.lt.block9;
							Block10 = plugin.lt.block10;
							Block11 = plugin.lt.block11;
							Block12 = plugin.lt.block12;
							Block13 = plugin.lt.block13;
							Block14 = plugin.lt.block14;
							Block15 = plugin.lt.block15;
						}else if (s.equalsIgnoreCase("u")) {
							Block1 = plugin.lu.block1;
							Block2 = plugin.lu.block2;
							Block3 = plugin.lu.block3;
							Block4 = plugin.lu.block4;
							Block5 = plugin.lu.block5;
							Block6 = plugin.lu.block6;
							Block7 = plugin.lu.block7;
							Block8 = plugin.lu.block8;
							Block9 = plugin.lu.block9;
							Block10 = plugin.lu.block10;
							Block11 = plugin.lu.block11;
							Block12 = plugin.lu.block12;
							Block13 = plugin.lu.block13;
							Block14 = plugin.lu.block14;
							Block15 = plugin.lu.block15;
						}else if (s.equalsIgnoreCase("v")) {
							Block1 = plugin.lv.block1;
							Block2 = plugin.lv.block2;
							Block3 = plugin.lv.block3;
							Block4 = plugin.lv.block4;
							Block5 = plugin.lv.block5;
							Block6 = plugin.lv.block6;
							Block7 = plugin.lv.block7;
							Block8 = plugin.lv.block8;
							Block9 = plugin.lv.block9;
							Block10 = plugin.lv.block10;
							Block11 = plugin.lv.block11;
							Block12 = plugin.lv.block12;
							Block13 = plugin.lv.block13;
							Block14 = plugin.lv.block14;
							Block15 = plugin.lv.block15;
						}else if (s.equalsIgnoreCase("w")) {
							Block1 = plugin.lw.block1;
							Block2 = plugin.lw.block2;
							Block3 = plugin.lw.block3;
							Block4 = plugin.lw.block4;
							Block5 = plugin.lw.block5;
							Block6 = plugin.lw.block6;
							Block7 = plugin.lw.block7;
							Block8 = plugin.lw.block8;
							Block9 = plugin.lw.block9;
							Block10 = plugin.lw.block10;
							Block11 = plugin.lw.block11;
							Block12 = plugin.lw.block12;
							Block13 = plugin.lw.block13;
							Block14 = plugin.lw.block14;
							Block15 = plugin.lw.block15;
						}else if (s.equalsIgnoreCase("x")) {
							Block1 = plugin.lx.block1;
							Block2 = plugin.lx.block2;
							Block3 = plugin.lx.block3;
							Block4 = plugin.lx.block4;
							Block5 = plugin.lx.block5;
							Block6 = plugin.lx.block6;
							Block7 = plugin.lx.block7;
							Block8 = plugin.lx.block8;
							Block9 = plugin.lx.block9;
							Block10 = plugin.lx.block10;
							Block11 = plugin.lx.block11;
							Block12 = plugin.lx.block12;
							Block13 = plugin.lx.block13;
							Block14 = plugin.lx.block14;
							Block15 = plugin.lx.block15;
						}else if (s.equalsIgnoreCase("y")) {
							Block1 = plugin.ly.block1;
							Block2 = plugin.ly.block2;
							Block3 = plugin.ly.block3;
							Block4 = plugin.ly.block4;
							Block5 = plugin.ly.block5;
							Block6 = plugin.ly.block6;
							Block7 = plugin.ly.block7;
							Block8 = plugin.ly.block8;
							Block9 = plugin.ly.block9;
							Block10 = plugin.ly.block10;
							Block11 = plugin.ly.block11;
							Block12 = plugin.ly.block12;
							Block13 = plugin.ly.block13;
							Block14 = plugin.ly.block14;
							Block15 = plugin.ly.block15;
						}else if (s.equalsIgnoreCase("z")) {
							Block1 = plugin.lz.block1;
							Block2 = plugin.lz.block2;
							Block3 = plugin.lz.block3;
							Block4 = plugin.lz.block4;
							Block5 = plugin.lz.block5;
							Block6 = plugin.lz.block6;
							Block7 = plugin.lz.block7;
							Block8 = plugin.lz.block8;
							Block9 = plugin.lz.block9;
							Block10 = plugin.lz.block10;
							Block11 = plugin.lz.block11;
							Block12 = plugin.lz.block12;
							Block13 = plugin.lz.block13;
							Block14 = plugin.lz.block14;
							Block15 = plugin.lz.block15;
						}else if (s.equalsIgnoreCase("1")) {
							Block1 = plugin.l1.block1;
							Block2 = plugin.l1.block2;
							Block3 = plugin.l1.block3;
							Block4 = plugin.l1.block4;
							Block5 = plugin.l1.block5;
							Block6 = plugin.l1.block6;
							Block7 = plugin.l1.block7;
							Block8 = plugin.l1.block8;
							Block9 = plugin.l1.block9;
							Block10 = plugin.l1.block10;
							Block11 = plugin.l1.block11;
							Block12 = plugin.l1.block12;
							Block13 = plugin.l1.block13;
							Block14 = plugin.l1.block14;
							Block15 = plugin.l1.block15;
						}else if (s.equalsIgnoreCase("2")) {
							Block1 = plugin.l2.block1;
							Block2 = plugin.l2.block2;
							Block3 = plugin.l2.block3;
							Block4 = plugin.l2.block4;
							Block5 = plugin.l2.block5;
							Block6 = plugin.l2.block6;
							Block7 = plugin.l2.block7;
							Block8 = plugin.l2.block8;
							Block9 = plugin.l2.block9;
							Block10 = plugin.l2.block10;
							Block11 = plugin.l2.block11;
							Block12 = plugin.l2.block12;
							Block13 = plugin.l2.block13;
							Block14 = plugin.l2.block14;
							Block15 = plugin.l2.block15;
						}else if (s.equalsIgnoreCase("3")) {
							Block1 = plugin.l3.block1;
							Block2 = plugin.l3.block2;
							Block3 = plugin.l3.block3;
							Block4 = plugin.l3.block4;
							Block5 = plugin.l3.block5;
							Block6 = plugin.l3.block6;
							Block7 = plugin.l3.block7;
							Block8 = plugin.l3.block8;
							Block9 = plugin.l3.block9;
							Block10 = plugin.l3.block10;
							Block11 = plugin.l3.block11;
							Block12 = plugin.l3.block12;
							Block13 = plugin.l3.block13;
							Block14 = plugin.l3.block14;
							Block15 = plugin.l3.block15;
						}else if (s.equalsIgnoreCase("4")) {
							Block1 = plugin.l4.block1;
							Block2 = plugin.l4.block2;
							Block3 = plugin.l4.block3;
							Block4 = plugin.l4.block4;
							Block5 = plugin.l4.block5;
							Block6 = plugin.l4.block6;
							Block7 = plugin.l4.block7;
							Block8 = plugin.l4.block8;
							Block9 = plugin.l4.block9;
							Block10 = plugin.l4.block10;
							Block11 = plugin.l4.block11;
							Block12 = plugin.l4.block12;
							Block13 = plugin.l4.block13;
							Block14 = plugin.l4.block14;
							Block15 = plugin.l4.block15;
						}else if (s.equalsIgnoreCase("5")) {
							Block1 = plugin.l5.block1;
							Block2 = plugin.l5.block2;
							Block3 = plugin.l5.block3;
							Block4 = plugin.l5.block4;
							Block5 = plugin.l5.block5;
							Block6 = plugin.l5.block6;
							Block7 = plugin.l5.block7;
							Block8 = plugin.l5.block8;
							Block9 = plugin.l5.block9;
							Block10 = plugin.l5.block10;
							Block11 = plugin.l5.block11;
							Block12 = plugin.l5.block12;
							Block13 = plugin.l5.block13;
							Block14 = plugin.l5.block14;
							Block15 = plugin.l5.block15;
						}else if (s.equalsIgnoreCase("6")) {
							Block1 = plugin.l6.block1;
							Block2 = plugin.l6.block2;
							Block3 = plugin.l6.block3;
							Block4 = plugin.l6.block4;
							Block5 = plugin.l6.block5;
							Block6 = plugin.l6.block6;
							Block7 = plugin.l6.block7;
							Block8 = plugin.l6.block8;
							Block9 = plugin.l6.block9;
							Block10 = plugin.l6.block10;
							Block11 = plugin.l6.block11;
							Block12 = plugin.l6.block12;
							Block13 = plugin.l6.block13;
							Block14 = plugin.l6.block14;
							Block15 = plugin.l6.block15;
						}else if (s.equalsIgnoreCase("7")) {
							Block1 = plugin.l7.block1;
							Block2 = plugin.l7.block2;
							Block3 = plugin.l7.block3;
							Block4 = plugin.l7.block4;
							Block5 = plugin.l7.block5;
							Block6 = plugin.l7.block6;
							Block7 = plugin.l7.block7;
							Block8 = plugin.l7.block8;
							Block9 = plugin.l7.block9;
							Block10 = plugin.l7.block10;
							Block11 = plugin.l7.block11;
							Block12 = plugin.l7.block12;
							Block13 = plugin.l7.block13;
							Block14 = plugin.l7.block14;
							Block15 = plugin.l7.block15;
						}else if (s.equalsIgnoreCase("8")) {
							Block1 = plugin.l8.block1;
							Block2 = plugin.l8.block2;
							Block3 = plugin.l8.block3;
							Block4 = plugin.l8.block4;
							Block5 = plugin.l8.block5;
							Block6 = plugin.l8.block6;
							Block7 = plugin.l8.block7;
							Block8 = plugin.l8.block8;
							Block9 = plugin.l8.block9;
							Block10 = plugin.l8.block10;
							Block11 = plugin.l8.block11;
							Block12 = plugin.l8.block12;
							Block13 = plugin.l8.block13;
							Block14 = plugin.l8.block14;
							Block15 = plugin.l8.block15;
						}else if (s.equalsIgnoreCase("9")) {
							Block1 = plugin.l9.block1;
							Block2 = plugin.l9.block2;
							Block3 = plugin.l9.block3;
							Block4 = plugin.l9.block4;
							Block5 = plugin.l9.block5;
							Block6 = plugin.l9.block6;
							Block7 = plugin.l9.block7;
							Block8 = plugin.l9.block8;
							Block9 = plugin.l9.block9;
							Block10 = plugin.l9.block10;
							Block11 = plugin.l9.block11;
							Block12 = plugin.l9.block12;
							Block13 = plugin.l9.block13;
							Block14 = plugin.l9.block14;
							Block15 = plugin.l9.block15;
						}else if (s.equalsIgnoreCase("0")) {
							Block1 = plugin.l0.block1;
							Block2 = plugin.l0.block2;
							Block3 = plugin.l0.block3;
							Block4 = plugin.l0.block4;
							Block5 = plugin.l0.block5;
							Block6 = plugin.l0.block6;
							Block7 = plugin.l0.block7;
							Block8 = plugin.l0.block8;
							Block9 = plugin.l0.block9;
							Block10 = plugin.l0.block10;
							Block11 = plugin.l0.block11;
							Block12 = plugin.l0.block12;
							Block13 = plugin.l0.block13;
							Block14 = plugin.l0.block14;
							Block15 = plugin.l0.block15;
						}else if (s.equalsIgnoreCase("_")) {
							Block1 = plugin.lunder.block1;
							Block2 = plugin.lunder.block2;
							Block3 = plugin.lunder.block3;
							Block4 = plugin.lunder.block4;
							Block5 = plugin.lunder.block5;
							Block6 = plugin.lunder.block6;
							Block7 = plugin.lunder.block7;
							Block8 = plugin.lunder.block8;
							Block9 = plugin.lunder.block9;
							Block10 = plugin.lunder.block10;
							Block11 = plugin.lunder.block11;
							Block12 = plugin.lunder.block12;
							Block13 = plugin.lunder.block13;
							Block14 = plugin.lunder.block14;
							Block15 = plugin.lunder.block15;
						}else if (s.equalsIgnoreCase("-")) {
							Block1 = plugin.ldash.block1;
							Block2 = plugin.ldash.block2;
							Block3 = plugin.ldash.block3;
							Block4 = plugin.ldash.block4;
							Block5 = plugin.ldash.block5;
							Block6 = plugin.ldash.block6;
							Block7 = plugin.ldash.block7;
							Block8 = plugin.ldash.block8;
							Block9 = plugin.ldash.block9;
							Block10 = plugin.ldash.block10;
							Block11 = plugin.ldash.block11;
							Block12 = plugin.ldash.block12;
							Block13 = plugin.ldash.block13;
							Block14 = plugin.ldash.block14;
							Block15 = plugin.ldash.block15;
						}
						}
						

						if(Block1 != null) {
						((CraftPlayer)p).sendBlockChange(segment.loc1, 35, Block1);
						((CraftPlayer)p).sendBlockChange(segment.loc2, 35, Block2);
						((CraftPlayer)p).sendBlockChange(segment.loc3, 35, Block3);
						((CraftPlayer)p).sendBlockChange(segment.loc4, 35, Block4);
						((CraftPlayer)p).sendBlockChange(segment.loc5, 35, Block5);
						((CraftPlayer)p).sendBlockChange(segment.loc6, 35, Block6);
						((CraftPlayer)p).sendBlockChange(segment.loc7, 35, Block7);
						((CraftPlayer)p).sendBlockChange(segment.loc8, 35, Block8);
						((CraftPlayer)p).sendBlockChange(segment.loc9, 35, Block9);
						((CraftPlayer)p).sendBlockChange(segment.loc10, 35, Block10);
						((CraftPlayer)p).sendBlockChange(segment.loc11, 35, Block11);
						((CraftPlayer)p).sendBlockChange(segment.loc12, 35, Block12);
						((CraftPlayer)p).sendBlockChange(segment.loc13, 35, Block13);
						((CraftPlayer)p).sendBlockChange(segment.loc14, 35, Block14);
						((CraftPlayer)p).sendBlockChange(segment.loc15, 35, Block15);
						}
						i++;
					}
				}
				line.letters.get(name).clear();
			}
			
		}, 60L);
		
		}
}
		
	public <T extends Listener> T getModuleForClass(Class<T> clazz) {
		return clazz.cast(clazz);
	}

	/*private LetterO getLetter(char letter) {
		String s = ""+letter;
		if(s.equalsIgnoreCase("j")) {
			return plugin.lj;
		} else if(s.equalsIgnoreCase("o")) {
			return plugin.lo;
		}else if(s.equalsIgnoreCase("b")) {
			return LetterB.class;
		} else if (s.equalsIgnoreCase("y")) {
			return LetterY.class;
		} else if(s.equalsIgnoreCase("8")) {
			return Letter8.class;
		} else if(s.equalsIgnoreCase("9")) {
			return Letter9.class;
		} else if(s.equalsIgnoreCase("0")) {
			return Letter0.class;
		}
		return null; 
		
	}*/

}
