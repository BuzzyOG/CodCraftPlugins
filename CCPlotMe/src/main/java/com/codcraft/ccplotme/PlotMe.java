package com.codcraft.ccplotme;

import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;
import com.worldcretornica.plotme.PlotMapInfo;
import com.worldcretornica.plotme.SqlManager;

public class PlotMe extends CCGamePlugin {

	public CCAPI api;
	
	public void onEnable() {
		 final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	      if(api != null || !(api instanceof CCAPI)) {
	         this.api = (CCAPI) api;
	      } else {
	         // Disable if we cannot get the api.
	         getLogger().warning("Could not find API. Disabling...");
	         getServer().getPluginManager().disablePlugin(this);
	         return;
	      }
	      getServer().getPluginManager().registerEvents(new PlotMeListener(this), this);
	}
	
	@Override
	public String getTag() {
		return "[MapMaking]";
	}

	public PlotMapInfo addDefaultpmi(String worldname, PlotMapInfo pmi) {
		pmi.AddCommentPrice = 0;
		pmi.AddPlayerPrice = 0;
		pmi.AuctionWallBlockId = "44:1";
		pmi.AutoLinkPlots = false;
		pmi.BiomeChangePrice = 1;
		pmi.BottomBlockId = 7;
		pmi.BottomBlockValue = 0;
		pmi.BuyFromBankPrice = 0;
		pmi.CanCustomizeSellPrice = false;
		pmi.CanPutOnSale = false;
		pmi.CanSellToBank = false;
		pmi.ClaimPrice = 0;
		pmi.ClearPrice = 0;
		pmi.DaysToExpiration = 7;
		pmi.DenyPlayerPrice = 0;
		pmi.DisableExplosion = true;
		pmi.DisableIgnition = true;
		pmi.DisposePrice = 0;
		pmi.ForSaleWallBlockId = "";
		pmi.PathWidth = 3;
		pmi.PlotAutoLimit = 1000;
		pmi.PlotFillingBlockId = 0;
		pmi.PlotFillingBlockValue = 0;
		pmi.PlotFloorBlockId = 0;
		pmi.PlotHomePrice = 0;
		pmi.plots = SqlManager.getPlots(worldname);
		pmi.PlotSize = 512;
		pmi.PreventedItems = new ArrayList<>();
		pmi.ProtectedBlocks = new ArrayList<>();
		pmi.ProtectedWallBlockId = "44:4";
		pmi.ProtectPrice = 0;
		pmi.RefundClaimPriceOnReset = false;
		pmi.RefundClaimPriceOnSetOwner = false;
		pmi.RemovePlayerPrice = 0;
		pmi.RoadHeight = 65;
		pmi.RoadMainBlockId = 5;
		pmi.RoadMainBlockValue = 0;
		pmi.RoadStripeBlockId = 5;
		pmi.RoadStripeBlockValue = 2;
		pmi.SellToBankPrice = 0;
		pmi.SellToPlayerPrice = 0;
		pmi.UndenyPlayerPrice = 0;
		pmi.UseEconomy = false;
		pmi.WallBlockId = 44;
		pmi.WallBlockValue = 0;
		return pmi;
	}
	@Override
	public void makegame(String[] string) {
		PlotMeGame game = new PlotMeGame(this);
		game.setName(string[0]);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}
	

}
