package com.codcraft.weapons;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.weapon.Mag;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;
import com.codcraft.cac.Cac;
import com.codcraft.codcraft.game.CodCraftGame;
import com.codcraft.ffa.CodCraftFFA;
import com.codcraft.infected.CodCraftInfected;
import com.codcraft.tdm.CodCraftTDM;
import com.codcraft.weapons.killstreaks.KillStreakManager;


public class Weapons extends JavaPlugin {
	
	public CCAPI api;
	public Cac cac;
	public List<Weapon> weapons = new ArrayList<>();
	public List<String> reloaders = new ArrayList<>();
	public CodCraftFFA ffa;
	public CodCraftInfected Infected;
	public CodCraftTDM TDM;
	public Map<String, BukkitTask> firing;
	public Map<String, BukkitTask> reloders;

	public void onEnable() {
		firing = new HashMap<String, BukkitTask>();
		reloders = new HashMap<String, BukkitTask>();
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		api = (CCAPI) ccapi;
		api.registerModule(WeaponModule.class, new WeaponModule(this, api));
		api.registerModule(KillStreakManager.class, new KillStreakManager(this, api));
		final Plugin cccac = Bukkit.getPluginManager().getPlugin("Cac");
		if(cccac == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		cac = (Cac) cccac;
		
		getServer().getPluginManager().registerEvents(new WeaponsListener(this), this);
		getServer().getPluginManager().registerEvents(new SniperWeapon(this), this);
		getServer().getPluginManager().registerEvents(new C4(this), this);
		
		final Plugin ffa = getServer().getPluginManager().getPlugin("FFA");
		if(ffa == null) {
			getLogger().info("ffa not found disabling");
			getServer().getPluginManager().disablePlugin(this);
		} else {
			this.ffa = (CodCraftFFA) ffa;
		}	
		final Plugin infected = getServer().getPluginManager().getPlugin("Infected");
		if(infected == null) {
			getLogger().info("infected not found disabling");
		} else {
			this.Infected = (CodCraftInfected) infected;
		}
		final Plugin TDM = getServer().getPluginManager().getPlugin("TDM");
		if(TDM == null) {
			getLogger().info("tdm not found disabling");
		} else {
			this.TDM = (CodCraftTDM) TDM;
		}
		Load();
	}
	
	private void Load() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/CCWeapon/config.yml"));
		com.CodCraft.api.modules.Weapons weapons = api.getModuleForClass(com.CodCraft.api.modules.Weapons.class);
		for(String WeaponName : config.getConfigurationSection("weapons").getKeys(false)) {
			//Weapon weap = new Weapon(WeaponName);
			
			String s = config.getString("weapons."+WeaponName+".CaC");
			String permisison = config.getString("weapons." + WeaponName + "permission");

			Material mat = Material.valueOf(config.getString("weapons."+WeaponName+".Mat"));
			Action act = Action.valueOf(config.getString("weapons."+WeaponName+".Action"));
			Integer ammo = Integer.parseInt(config.getString("weapons."+WeaponName+".Ammo"));
			Integer reloads = Integer.parseInt(config.getString("weapons."+WeaponName+".Reloads"));
			Integer reloadtime = Integer.parseInt(config.getString("weapons."+WeaponName+".ReloadTime"));
			Integer damage = Integer.parseInt(config.getString("weapons."+WeaponName+".Damage"));
			Integer rpm = Integer.parseInt(config.getString("weapons."+WeaponName+".Rpm"));
			Long blife = Long.parseLong(config.getString("weapons."+WeaponName+".Blife"));
			DefaultWeapon weapon = new DefaultWeapon(WeaponName, weapons, mat, rpm, 3, damage, new Mag(Material.SNOW_BALL, ammo ), blife, getGames(), reloadtime, reloads);
			System.out.println(weapon.getReloadtime());
			System.out.println(weapon.getReloads());
			if(s.equalsIgnoreCase("Weapon")) {
				cac.weapons.put(weapon.getName(), permisison);
			}
			weapons.addWeapon(weapon);
			//weapons.add(weap);
		}
	}
	
	private List<CCGamePlugin> getGames() {
		List<CCGamePlugin> games = new ArrayList<>();
		/*for(Game<?> g : api.getModuleForClass(GameManager.class).getAllGames()) {
			if(g instanceof CodCraftGame<?>) {
				games.add(g.getPlugin());
			}
		}*/
		if(ffa != null) {
			games.add(ffa);
		}
		if(TDM != null){
			games.add(TDM);
		}
		if(Infected != null){
			games.add(Infected);
		}


		return games;
	}

	public boolean checkIfGameIsInstanceOfPlugin(Game<?> g) {
		if(g == null) {
			return false;			
		}
		Plugin plug = g.getPlugin();
		if(plug == ffa) return true;
		if(plug == Infected) return true;
		if(plug == TDM) return true;
		return false;
	}
	

	
	
	

}
