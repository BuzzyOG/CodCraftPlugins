package com.codcraft.PlayerDataViewer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.codcraft.PlayerDataViewer.letters.Letter0;
import com.codcraft.PlayerDataViewer.letters.Letter1;
import com.codcraft.PlayerDataViewer.letters.Letter2;
import com.codcraft.PlayerDataViewer.letters.Letter3;
import com.codcraft.PlayerDataViewer.letters.Letter4;
import com.codcraft.PlayerDataViewer.letters.Letter5;
import com.codcraft.PlayerDataViewer.letters.Letter6;
import com.codcraft.PlayerDataViewer.letters.Letter7;
import com.codcraft.PlayerDataViewer.letters.Letter8;
import com.codcraft.PlayerDataViewer.letters.Letter9;
import com.codcraft.PlayerDataViewer.letters.LetterA;
import com.codcraft.PlayerDataViewer.letters.LetterB;
import com.codcraft.PlayerDataViewer.letters.LetterC;
import com.codcraft.PlayerDataViewer.letters.LetterD;
import com.codcraft.PlayerDataViewer.letters.LetterDash;
import com.codcraft.PlayerDataViewer.letters.LetterE;
import com.codcraft.PlayerDataViewer.letters.LetterF;
import com.codcraft.PlayerDataViewer.letters.LetterG;
import com.codcraft.PlayerDataViewer.letters.LetterH;
import com.codcraft.PlayerDataViewer.letters.LetterI;
import com.codcraft.PlayerDataViewer.letters.LetterJ;
import com.codcraft.PlayerDataViewer.letters.LetterK;
import com.codcraft.PlayerDataViewer.letters.LetterL;
import com.codcraft.PlayerDataViewer.letters.LetterM;
import com.codcraft.PlayerDataViewer.letters.LetterN;
import com.codcraft.PlayerDataViewer.letters.LetterO;
import com.codcraft.PlayerDataViewer.letters.LetterP;
import com.codcraft.PlayerDataViewer.letters.LetterQ;
import com.codcraft.PlayerDataViewer.letters.LetterR;
import com.codcraft.PlayerDataViewer.letters.LetterS;
import com.codcraft.PlayerDataViewer.letters.LetterT;
import com.codcraft.PlayerDataViewer.letters.LetterU;
import com.codcraft.PlayerDataViewer.letters.LetterUnder;
import com.codcraft.PlayerDataViewer.letters.LetterV;
import com.codcraft.PlayerDataViewer.letters.LetterW;
import com.codcraft.PlayerDataViewer.letters.LetterX;
import com.codcraft.PlayerDataViewer.letters.LetterY;
import com.codcraft.PlayerDataViewer.letters.LetterZ;

public class Main extends JavaPlugin {
	public Board board;
	public Letter0 l0 = new Letter0();
	public Letter1 l1 = new Letter1();
	public Letter2 l2 = new Letter2();
	public Letter3 l3 =new Letter3();
	public Letter4 l4 = new Letter4();
	public Letter5 l5 = new Letter5();
	public Letter6 l6 = new Letter6();
	public Letter7 l7 = new Letter7();
	public Letter8 l8 = new Letter8();
	public Letter9 l9 = new Letter9();
	public LetterA la = new LetterA();
	public LetterB lb = new LetterB();
	public LetterC lc = new LetterC();
	public LetterD ld= new LetterD();
	public LetterE le = new LetterE();
	public LetterF lf = new LetterF();
	public LetterG lg = new LetterG();
	public LetterH lh = new LetterH();
	public LetterI li = new LetterI();
	public LetterJ lj = new LetterJ();
	public LetterK lk = new LetterK();
	public LetterL ll = new LetterL();
	public LetterM lm = new LetterM();
	public LetterN ln= new LetterN();
	public LetterO lo = new LetterO();
	public LetterP lp =new LetterP();
	public LetterQ lq =new LetterQ();
	public LetterR lr = new LetterR();
	public LetterS ls = new LetterS();
	public LetterT lt = new LetterT();
	public LetterU lu =new LetterU();
	public LetterV lv = new LetterV();
	public LetterW lw =  new LetterW();
	public LetterX lx = new LetterX();
	public LetterY ly = new LetterY();
	public LetterZ lz = new LetterZ();
	public LetterDash ldash = new LetterDash();
	public LetterUnder lunder = new LetterUnder();
	public List<Segment> kills = new ArrayList<>();
	public List<Segment> deaths = new ArrayList<>();
	public List<Segment> wins = new ArrayList<>();
	public List<Segment> losses = new ArrayList<>();
	public List<Segment> kd = new ArrayList<>();
	public CCAPI api;
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Listener(this), this);
		Line line = new Line() {
		};
		board = new Board() {
		};
		board.addLine(line);
		
		loadymls();
		final CCAPI ccapi = (CCAPI) Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getLogger().log(Level.WARNING, "API NOT FOUND");
		} else {
			this.api = ccapi;
		}

	}

	private void loadymls() {
		World world = Bukkit.getWorld("world");
		File Sign = new File(getDataFolder(), "games.yml");
		YamlConfiguration Gamesconfig = YamlConfiguration.loadConfiguration(Sign);
		for(String Signstring : Gamesconfig.getConfigurationSection("segment").getKeys(false)) {
			int i = 1;
			Segment seg = new Segment();
			for(String Location : Gamesconfig.getConfigurationSection("segment."+Signstring).getKeys(false)) {
				Location Block = new Location(world, 
						Double.parseDouble(Gamesconfig.getString("segment."+Signstring+"."+Location+".x")), 
						Double.parseDouble(Gamesconfig.getString("segment."+Signstring+"."+Location+".y")), 
						Double.parseDouble(Gamesconfig.getString("segment."+Signstring+"."+Location+".z")));
				if(i == 1) seg.loc1 = Block;
				else if (i == 2) seg.loc2 = Block;
				else if (i == 3) seg.loc3 = Block;
				else if (i == 4) seg.loc4 = Block;
				else if (i == 5) seg.loc5 = Block;
				else if (i == 6) seg.loc6 = Block;
				else if (i == 7) seg.loc7 = Block;
				else if (i == 8) seg.loc8 = Block;
				else if (i == 9) seg.loc9 = Block;
				else if (i == 10) seg.loc10 = Block;
				else if (i == 11) seg.loc11 = Block;
				else if (i == 12) seg.loc12 = Block;
				else if (i == 13) seg.loc13 = Block;
				else if (i == 14) seg.loc14 = Block;
				else if (i == 15) seg.loc15 = Block;
				i++;
				
			}
			board.lines.get(0).segmants.add(seg);
		}

		for(String name : Gamesconfig.getConfigurationSection("extra").getKeys(false)) {
			for(String name2 : Gamesconfig.getConfigurationSection("extra."+name).getKeys(false)) {
				int i = 1;
				Segment seg = new Segment();		
				for(String location : Gamesconfig.getConfigurationSection("extra."+name+"."+name2).getKeys(false)) {
					Location Block = new Location(world, 
							Double.parseDouble(Gamesconfig.getString("extra"+"."+name+"."+name2+"."+location+".x")), 
							Double.parseDouble(Gamesconfig.getString("extra"+"."+name+"."+name2+"."+location+".y")), 
							Double.parseDouble(Gamesconfig.getString("extra"+"."+name+"."+name2+"."+location+".z")));
					if(i == 1) seg.loc1 = Block;
					else if (i == 2) seg.loc2 = Block;
					else if (i == 3) seg.loc3 = Block;
					else if (i == 4) seg.loc4 = Block;
					else if (i == 5) seg.loc5 = Block;
					else if (i == 6) seg.loc6 = Block;
					else if (i == 7) seg.loc7 = Block;
					else if (i == 8) seg.loc8 = Block;
					else if (i == 9) seg.loc9 = Block;
					else if (i == 10) seg.loc10 = Block;
					else if (i == 11) seg.loc11 = Block;
					else if (i == 12) seg.loc12 = Block;
					else if (i == 13) seg.loc13 = Block;
					else if (i == 14) seg.loc14 = Block;
					else if (i == 15) seg.loc15 = Block;
					i++;
				}
				if(name.equalsIgnoreCase("kills")) {
					kills.add(0, seg);
				} else if (name.equalsIgnoreCase("deaths")) {
					deaths.add(0,seg);
				} else if (name.equalsIgnoreCase("kd")) {
					kd.add(0, seg);
				} else if (name.equalsIgnoreCase("wins")) {
					wins.add(0, seg);
				} else if (name.equalsIgnoreCase("losses")) {
					losses.add(0, seg);
				}
			}
		}

	}	
}
