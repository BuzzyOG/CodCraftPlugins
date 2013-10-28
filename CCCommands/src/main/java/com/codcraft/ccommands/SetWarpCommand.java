package com.codcraft.ccommands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

	  public CCCommands plugin;
	  int fileCount = 0;
	  
	  public SetWarpCommand(CCCommands plugin) {
			this.plugin = plugin;
		}
	
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		  
		  if (cmd.getName().equalsIgnoreCase("remwarp") || cmd.getName().equalsIgnoreCase("delwarp")){
			  
			  Player p = ((Player)sender);
			  
			  if (args.length == 0){
				  s(p, "&cTry /remwarp <name>.");
				  return true;
			  }
			  
			  File warpFile = new File("./plugins/CCCommands/Warps/" + args[0] + ".yml");
			  
			  if (!warpFile.exists()) {
				 s(p, "&cThat warp does not exist!");
				  return true;
			  }
			  
			  warpFile.delete();
			  fileCount = getFileAmount();
			  s(p, "&aWarp removed. There are now &6" + fileCount + " &awarps.");
		  }
		  
		  if (cmd.getName().equalsIgnoreCase("setwarp")){
			  
			  Player p = ((Player)sender);
			  
			  if (args.length == 0){
				  s(p, "&cTry /setwarp <name>.");
				  return true;
			  }
			  
			  File warpFile = new File("./plugins/CCCommands/Warps/" + args[0] + ".yml");
			  
			  if (!warpFile.exists()) {
				  try {
					warpFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}   
			  }
		      
			  YamlConfiguration warpLoad = YamlConfiguration.loadConfiguration(warpFile);
			  
		      warpLoad.set("world", p.getWorld().getName());
		      warpLoad.set("x", p.getLocation().getX());
		      warpLoad.set("y", p.getLocation().getY());
		      warpLoad.set("z", p.getLocation().getZ());
		      warpLoad.set("yaw", p.getLocation().getYaw());
		      warpLoad.set("pitch", p.getLocation().getPitch());
			  try {
				warpLoad.save(warpFile);
			  } catch (IOException e) {
				e.printStackTrace();
			  }
			  fileCount = getFileAmount();
		      s(p, "Set warp &6" + args[0].toLowerCase() + "&d. There are now &6" + fileCount + " &dwarps.");

		  }
		  
		  if (cmd.getName().equalsIgnoreCase("warp") || cmd.getName().equalsIgnoreCase("w")){
			  
			  final Player p = ((Player)sender);
			  
			  if (args.length == 0 || isInteger(args[0])){
				    String path  = "./plugins/CCCommands/Warps/";
	                File folder = new File(path);
	                String[] fileNames = folder.list();
	                Arrays.sort(fileNames);
	      			SortedMap<Integer, String> map = new TreeMap<Integer, String>();
	                
	                for(int i = 0; i < fileNames.length; i++){
	    			  map.put(i, fileNames[i]);
	                }
	                
	                if (args.length >= 1 && isInteger(args[0])){
	                	if (Integer.parseInt(args[0]) > Math.round((double) (fileNames.length / 20))){
		                	s(p, "There aren't that many pages!");
		                	return true;
		                }
	                	paginate(sender, map, Integer.parseInt(args[0]), 20, fileNames.length);
		                return true;
	                }
	                
	                paginate(sender, map, 1, 20, fileNames.length);
	                return true;
			  }
			  
			  File warpFile = new File("./plugins/CCCommands/Warps/" + args[0] + ".yml");
			  
			  if (!warpFile.exists()) {
				  s(p, "That warp does not exist!");
			    	return true;  
			  }
		      
			  YamlConfiguration warpLoad = YamlConfiguration.loadConfiguration(warpFile);
		
			  World w = Bukkit.getWorld(warpLoad.getString("world"));
			  double x = warpLoad.getInt("x");
			  double y = warpLoad.getInt("y");
			  double z = warpLoad.getInt("z");
			  float yaw = warpLoad.getInt("yaw");
			  float pitch = warpLoad.getInt("pitch");
			  Location warpTo = new Location(w, x, y+1, z, yaw, pitch);
			    
			    if (args.length == 2){
			    	OfflinePlayer tpOther = Bukkit.getOfflinePlayer(args[1]);
			    		if (!tpOther.isOnline()){
			    			s(p, "That player is not online!");
			    			return true;
			    		}
			    	Player other = Bukkit.getPlayer(args[1]);	
					double xP = other.getLocation().getX();
					double yP = other.getLocation().getY();
					double zP = other.getLocation().getZ();

					String warpSimple = Math.round(xP) + "&f, &6" + Math.round(yP) + "&f, &6" + Math.round(zP); 
					other.teleport(warpTo);
				    s(other, "Warped to &6" + args[0] + " &afrom &6" + warpSimple + " &aby " + p.getDisplayName() + "&a.");
				    return true;
			    }
			    
			  double xP = p.getLocation().getX();
			  double yP = p.getLocation().getY();
			  double zP = p.getLocation().getZ();

			  String warpSimple = Math.round(xP) + "&f, &6" + Math.round(yP) + "&f, &6" + Math.round(zP) + "&d."; 
			  p.teleport(warpTo);
		      List<Location> circleblocks = circle(p, p.getLocation(), 3, 1, true, false, 0);
			  // Because fancy effects are cool! :D
				for (Location l : circleblocks){
					p.getWorld().playEffect(l, Effect.SMOKE, 0);
					p.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 0);
					p.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 0);
				}

			  s(p, "Warped to &6" + args[0] + " &dfrom &6" + warpSimple);
		  }
		  
	  return true;
	  
	  }
	  
	  public void paginate(CommandSender sender, SortedMap<Integer, String> map, int page, int pageLength, int warps) {
		  
		  	      StringBuilder sb = new StringBuilder();
		  	      
			      s((Player)sender, "&aWarps &f// &aPage &6" + String.valueOf(page) + " &aof &6" + (((map.size() % pageLength) == 0) ? map.size() / pageLength : (map.size() / pageLength) + 1) + "&a. &f(&6" + warps + "&f)&a.");
			      int i = 0, k = 0;
			      page--;
			      for (final Entry<Integer, String> e : map.entrySet()) {
			          k++;
			          if ((((page * pageLength) + i + 1) == k) && (k != ((page * pageLength) + pageLength + 1))) {
			              i++;
			              sb.append(e.getValue() + "&f, &a");
			          }
			      }
			      
			      String msg = sb.toString().trim();
			      msg = msg.substring(0, msg.length() - 6) + " ";
			      s((Player)sender, "&a" + msg.replace(".yml", ""));
			  }
	  
	  private void s (Player p, String m){
		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
	  }
	  
	  public int getFileAmount(){
		  String path  = "./plugins/CCCommands/Warps";
          File folder = new File(path);
          String[] fileNames = folder.list();
          return fileNames.length;
	  }
	  
	  public boolean isInteger(String s) {
		    try {
		      Integer.parseInt(s);
		    } catch (NumberFormatException e) {
		      return false;
		    }
		      return true;
	  }
	  
	  public List<Location> circle (Player player, Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
	        List<Location> circleblocks = new ArrayList<Location>();
	        int cx = loc.getBlockX();
	        int cy = loc.getBlockY();
	        int cz = loc.getBlockZ();
	        for (int x = cx - r; x <= cx +r; x++)
	            for (int z = cz - r; z <= cz +r; z++)
	                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
	                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
	                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
	                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
	                        circleblocks.add(l);
	                        }
	                    }
	     
	        return circleblocks;
	  }
}
