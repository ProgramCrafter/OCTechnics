// TestMod.java

package org.octechnics.octechnics;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

@Mod( modid = "octechnics", version = "1.0" )
public class OCTechnics {
    public static AbstractFactoryBlock FACTORY_CONTROLLER  = new AbstractFactoryBlock("factory_controller", "factory_controller", true);
    public static AbstractFactoryBlock BASIC_FACTORY_BLOCK = new AbstractFactoryBlock("basic_factory_block", "factory_base", false);
    public static AbstractFactoryBlock FACTORY_CRAFT_TABLE = new AbstractFactoryBlock("factory_crafting_table", "factory_craft_table_s0", false);
    public static AbstractFactoryBlock FACTORY_FURNACE = new AbstractFactoryBlock("factory_furnace", "factory_furnace_s1", false);
    public static AbstractFactoryBlock FACTORY_ITEM_ST = new AbstractFactoryBlock("factory_items_storage", "factory_items_storage_s4", false);
    public static AbstractFactoryBlock FACTORY_ENRG_ST = new AbstractFactoryBlock("factory_energy_storage", "factory_energy_storage_s9", false);
    public static AbstractFactoryBlock FACTORY_ITEM_BS = new AbstractFactoryBlock("factory_items_bus", "factory_items_bus_s0", false);
    public static Logger logger;
    
    public OCTechnics() {
        logger = LogManager.getLogger();
        logger.info("octechnics - class instantiated");
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        logger.info("octechnics - got FMLPreInitializationEvent");
        BASIC_FACTORY_BLOCK.register();
        FACTORY_CRAFT_TABLE.register();
        FACTORY_CONTROLLER.register();
        FACTORY_FURNACE.register();
        FACTORY_ITEM_ST.register();
        FACTORY_ENRG_ST.register();
        FACTORY_ITEM_BS.register();
        logger.info("octechnics - registered blocks");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent evt) {
        logger.info("octechnics - got FMLInitializationEvent");
        GameRegistry.addRecipe(new ItemStack(BASIC_FACTORY_BLOCK, 1),
            new Object[] {"XXX","XYX","XXX", ('Y'), Blocks.stone, ('X'), Items.iron_ingot} );
        GameRegistry.addShapelessRecipe(new ItemStack(FACTORY_CONTROLLER, 1),
            new Object[] {BASIC_FACTORY_BLOCK} );
        GameRegistry.addShapelessRecipe(new ItemStack(FACTORY_CRAFT_TABLE, 1),
            new Object[] {BASIC_FACTORY_BLOCK, Blocks.crafting_table} );
        GameRegistry.addShapelessRecipe(new ItemStack(FACTORY_FURNACE, 1),
            new Object[] {BASIC_FACTORY_BLOCK, Blocks.furnace} );
        GameRegistry.addShapelessRecipe(new ItemStack(FACTORY_ITEM_ST, 1),
            new Object[] {BASIC_FACTORY_BLOCK, Blocks.chest} );
        GameRegistry.addShapelessRecipe(new ItemStack(FACTORY_ENRG_ST, 1),
            new Object[] {BASIC_FACTORY_BLOCK, Items.redstone} );
        GameRegistry.addShapelessRecipe(new ItemStack(FACTORY_ITEM_BS, 1),
            new Object[] {BASIC_FACTORY_BLOCK, Blocks.dispenser} );
        logger.info("octechnics - registered crafts");
    }
    
    @EventHandler
    public void serverStart(FMLServerStartingEvent evt) {
        logger.info("octechnics - got FMLServerStartingEvent");
        
        evt.registerServerCommand(new OCTTestCommand());
    }
}