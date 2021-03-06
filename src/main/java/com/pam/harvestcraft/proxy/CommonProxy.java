package com.pam.harvestcraft.proxy;

import com.pam.harvestcraft.HarvestCraft;
import com.pam.harvestcraft.addons.RightClickHarvesting;
import com.pam.harvestcraft.blocks.BlockRegistry;
import com.pam.harvestcraft.blocks.CropRegistry;
import com.pam.harvestcraft.blocks.FruitRegistry;
import com.pam.harvestcraft.config.ConfigHandler;
import com.pam.harvestcraft.item.GeneralOreRegistry;
import com.pam.harvestcraft.item.ItemRegistry;
//import com.pam.harvestcraft.item.RecipeRegistry;
import com.pam.harvestcraft.item.SeedDropRegistry;
import com.pam.harvestcraft.loottables.LootTableLoadEventHandler;
import com.pam.harvestcraft.tileentities.MarketItems;
import com.pam.harvestcraft.tileentities.ShippingBinItems;
import com.pam.harvestcraft.tileentities.TileEntityApiary;
import com.pam.harvestcraft.tileentities.TileEntityGroundTrap;
import com.pam.harvestcraft.tileentities.TileEntityMarket;
import com.pam.harvestcraft.tileentities.TileEntityPresser;
import com.pam.harvestcraft.tileentities.TileEntityShippingBin;
import com.pam.harvestcraft.tileentities.TileEntityWaterTrap;
import com.pam.harvestcraft.worldgen.BeehiveWorldGen;
import com.pam.harvestcraft.worldgen.BushWorldGen;
import com.pam.harvestcraft.worldgen.FruitTreeWorldGen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        CropRegistry.registerCrops();
        FruitRegistry.registerFruits();
        BlockRegistry.initBlockRegistry();
        MinecraftForge.EVENT_BUS.register(new BlockRegistry());
        ItemRegistry.registerItems();
        MinecraftForge.EVENT_BUS.register(new ItemRegistry());
        //ModItems.init();
        
    }

    public void init(FMLInitializationEvent e) {
        GameRegistry.registerWorldGenerator(new BushWorldGen(), 0);
        GameRegistry.registerWorldGenerator(new FruitTreeWorldGen(), 0);
        GameRegistry.registerWorldGenerator(new BeehiveWorldGen(), 0);
        
        onBlocksAndItemsLoaded();
    }

    public void postInit(FMLPostInitializationEvent e) {
    	//BlockList.addFromString(ConfigHandler.seed, "seed");
       // BlockList.addFromString(ConfigHandler.crop, "crop");
        //BlockList.addFromString(ConfigHandler.sapling, "sapling");

    }

    public void onBlocksAndItemsLoaded() {
        HarvestCraft.config.configureGardenDrops();

        GeneralOreRegistry.initOreRegistry();

        //RecipeRegistry.registerRecipes();
        SeedDropRegistry.getSeedDrops();

        MarketItems.registerItems();
        ShippingBinItems.registerItems();
        PacketHandler.init();

        GameRegistry.registerTileEntity(TileEntityApiary.class, "PamApiary");
        GameRegistry.registerTileEntity(TileEntityMarket.class, "PamMarket");
        GameRegistry.registerTileEntity(TileEntityShippingBin.class, "PamShippingbin");
        GameRegistry.registerTileEntity(TileEntityPresser.class, "PamPresser");
        GameRegistry.registerTileEntity(TileEntityGroundTrap.class, "PamGroundTrap");
        GameRegistry.registerTileEntity(TileEntityWaterTrap.class, "PamWaterTrap");

        if (ConfigHandler.enableHCFish)
        {
        MinecraftForge.EVENT_BUS.register(new LootTableLoadEventHandler());
        }
        
        RightClickHarvesting.instance.register();
        
      //Closing container on item toss
        //MinecraftForge.EVENT_BUS.register(new ItemEvent());
        //For the pickup events
        //MinecraftForge.EVENT_BUS.register(new ItemDropEvent());
        //Player join and leave servers
        //MinecraftForge.EVENT_BUS.register(new PlayerServerEventHandler());
    }
}
