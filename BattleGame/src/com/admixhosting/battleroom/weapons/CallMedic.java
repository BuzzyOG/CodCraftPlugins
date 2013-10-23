package com.admixhosting.battleroom.weapons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.model.weapon.MethodType;
import com.CodCraft.api.model.weapon.Weapon;
import com.CodCraft.api.modules.GameManager;
import com.admixhosting.battleroom.BattleRoom;
import com.admixhosting.battleroom.FireworkEffectPlayer;
import com.admixhosting.battleroom.game.BattlePlayer;

public class CallMedic extends Weapon {
	
	private List<String> players = new ArrayList<>();
	private BattleRoom plugin;
	private FireworkEffectPlayer fplayer = new FireworkEffectPlayer();

	public CallMedic(String name, BattleRoom plugin) {
		super(name);
		putAction(Material.APPLE, Action.LEFT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.APPLE, Action.RIGHT_CLICK_AIR, MethodType.USE_WEAPON);
		putAction(Material.APPLE, Action.LEFT_CLICK_BLOCK, MethodType.USE_WEAPON);
		putAction(Material.APPLE, Action.RIGHT_CLICK_BLOCK, MethodType.USE_WEAPON);
		addGame(plugin);
		this.plugin = plugin;
	}

	@Override
	public void giveAmmo(Player p, Material mat, Action action) {}

	@Override
	public void giveWeapon(Player p, Material mat, Action action) {
		ItemStack is = new ItemStack(Material.APPLE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("CallDefroster");
		is.setItemMeta(im);
		p.getInventory().addItem(is);
	}

	@Override
	public void useWeapon(final Player p, Material mat, Action action, Event event) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		Game<?> g = gm.getGameWithPlayer(p);
		if(g != null) {
			if(g.getPlugin() == plugin) {
				BattlePlayer bp = (BattlePlayer) g.findTeamWithPlayer(p).findPlayer(p);
				if(bp.getFrozen()) {
					if(!players.contains(p.getName())) {
						for(TeamPlayer tp : g.findTeamWithPlayer(p).getPlayers()) {
							BattlePlayer bp1 = (BattlePlayer) tp;
							if(bp1.medic) {
								Player p11 = Bukkit.getPlayer(bp1.getName());
								if(p11 != null) {
									p11.sendMessage(p.getName() + " needs help!");
								}
							}
						}
						
						FireworkEffect fw;
						if(g.findTeamWithPlayer(p).getName().equalsIgnoreCase("Blue")) {
							fw = FireworkEffect.builder().withColor(Color.fromRGB(126, 150, 255)).with(Type.BALL_LARGE).build();
						} else {
							fw = FireworkEffect.builder().withColor(Color.fromRGB(255, 126, 126)).with(Type.BALL_LARGE).build();
						}
						try {
							fplayer.playFirework(p.getWorld(), p.getLocation(), fw);
						} catch (Exception e) {
							e.printStackTrace();
						}
						players.add(p.getName());
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							
							@Override
							public void run() {
								players.remove(p.getName());
								
							}
						}, 60);
					}
				}
			}
		}
	}

	@Override
	public void onPlace(Player p, Material mat, Action action, Location loc) {}

	@Override
	public void onDamage(Player hurt, Player hurter, Material mar, Action action, Event event) {}

}
