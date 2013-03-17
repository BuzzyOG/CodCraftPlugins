package me.jne.AntiAd;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 *
 * @author Franz
 */
public class ADListener implements Listener {

    private final AntiAd plugin;

    public ADListener(AntiAd plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(plugin.getAdfinder().check(e.getPlayer(), e.getMessage(), 1,true));
        Player p = e.getPlayer();
        if (p instanceof Player) {
			if (p.hasPermission("CodCraft.Owner")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[O] "+ e.getPlayer().getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE   + m);
			} else if (p.hasPermission("CodCraft.Admin")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[A] "+ e.getPlayer().getName() +": "+ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE  + m);
			}  else if (p.hasPermission("CodCraft.Mod")) {
				String m = e.getMessage();
				e.setFormat(ChatColor.RED + "[M] " + e.getPlayer().getName()+": "+ ChatColor.WHITE  + m);
				e.setMessage(ChatColor.WHITE +  m);
			} else {
				String m = e.getMessage();
				e.setMessage(e.getPlayer().getName()+": "+ ChatColor.WHITE  + m);
			}
        }
    }


    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignCreation(SignChangeEvent sign) {

        for (int i = 0; i < sign.getLines().length; i++) {

            if (plugin.getAdfinder().check(sign.getPlayer(), sign.getLine(i), 3, false)) {
                i = sign.getLines().length;
                sign.setCancelled(true);
                sign.getBlock().breakNaturally();
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onCommandSent(PlayerCommandPreprocessEvent chat) {


        String CL = chat.getMessage().split("\\s+")[0];
        List<String> Commands = plugin.getConfig().getStringList("Detected-Commands");
        if (Commands.contains(CL)) {
            chat.setCancelled(plugin.getAdfinder().check(chat.getPlayer(), chat.getMessage(), 2,true));
        }
    }
}
