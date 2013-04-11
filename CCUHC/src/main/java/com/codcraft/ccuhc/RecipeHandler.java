package com.codcraft.ccuhc;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGameListener;

public class RecipeHandler
  extends CCGameListener
{
	private UHC plugin;
  public RecipeHandler(UHC plugin){
	  this.plugin = plugin;
      ShapedRecipe goldenApple = new ShapedRecipe(new ItemStack(Material.GOLDEN_APPLE, 1));

      goldenApple.shape(new String[] { "AAA", "ABA", "AAA" });
      goldenApple.setIngredient('A', Material.GOLD_INGOT);
      goldenApple.setIngredient('B', Material.APPLE);

      Bukkit.addRecipe(goldenApple);
    

      ShapelessRecipe glisteringMelon = new ShapelessRecipe(new ItemStack(Material.SPECKLED_MELON, 1));

      glisteringMelon.addIngredient(Material.GOLD_BLOCK);
      glisteringMelon.addIngredient(Material.MELON);

      Bukkit.addRecipe(glisteringMelon);
    
  }

  @EventHandler
  public void onPrepareCraftItemEvent(PrepareItemCraftEvent e)
  {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer((Player) e.getViewers().get(0)) == null) {
			return;
		}
    Recipe r = e.getRecipe();
    if ((r.getResult().getType().equals(Material.GOLDEN_APPLE)) && (recipeContainsMaterial(r, Material.GOLD_NUGGET)))
  {
      e.getInventory().setResult(new ItemStack(Material.AIR));
    }
    	
    if ((r.getResult().getType().equals(Material.SPECKLED_MELON)) && (recipeContainsMaterial(r, Material.GOLD_NUGGET))) {
        e.getInventory().setResult(new ItemStack(Material.AIR));
    }

  }



  private boolean recipeContainsMaterial(Recipe r, Material mat)
  {
    Collection<ItemStack> ingredients = null;
    if ((r instanceof ShapedRecipe)) {
      ingredients = ((ShapedRecipe)r).getIngredientMap().values();
    }
    if ((r instanceof ShapelessRecipe)) {
      ingredients = ((ShapelessRecipe)r).getIngredientList();
    }
    if (ingredients == null) {
      return false;
    }
    for (ItemStack i : ingredients) {
      if (i.getType().equals(mat)) {
        return true;
      }
    }
    return false;
  }
}
