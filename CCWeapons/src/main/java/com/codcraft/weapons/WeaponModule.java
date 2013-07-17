package com.codcraft.weapons;

import org.bukkit.entity.Player;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCModule;
import com.codcraft.codcraftplayer.CCPlayerModule;

public class WeaponModule extends CCModule {

	private Weapons plugin;
	private CCPlayerModule cpm;

	public WeaponModule(Weapons plugin, CCAPI api) {
		super(api);
		this.plugin = plugin;
		this.cpm = plugin.api.getModuleForClass(CCPlayerModule.class);
	}
	
	public Weapon getWeaponForPlayer(Player p) {
		Weapon weapon = null;
		String pweapon;
		if(cpm.getPlayer(p) == null) {
			pweapon = null;
		} else {
			pweapon = cpm.getPlayer(p).getClass(cpm.getPlayer(p).getCurrentclass()).getGun();
		}
		for(Weapon weap : plugin.weapons) {
			if(pweapon.equalsIgnoreCase(weap.getName())) {
				weapon = weap;
			}
		}
		return weapon;
	}

	@Override
	public void starting() {
		
	}

	@Override
	public void closing() {
		
	}

}
