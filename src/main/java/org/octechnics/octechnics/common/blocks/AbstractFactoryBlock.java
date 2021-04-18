package org.octechnics.octechnics;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.EntityLivingBase;

//import net.minecraft.block.IBlockState;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.util.BlockPos;

//import buildcraft.core.lib.inventory.SimpleInventory; // to forbid compiling before TileEntity is done

import cpw.mods.fml.common.registry.GameRegistry;

public class AbstractFactoryBlock extends BlockContainer {
    private String _name;
    private static boolean registeredTE = false;
    public AbstractFactoryBlock(String name, String tx_name, Boolean is_controller) {
        super(Material.rock);
        this.setBlockName("octechnics:" + name);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setBlockTextureName("octechnics:" + tx_name);
        this._name = name;
    }
    public void register() {
        GameRegistry.registerBlock(this, _name);
        if (!registeredTE) {
            GameRegistry.registerTileEntity(FactoryTile.class, "AbstractFactoryBlock.FactoryTile");
        }
        registeredTE = true;
    }
    
    
    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        OCTechnics.logger.info("*** void: placed block " + this._name);
        //worldIn.getTileEntity(pos);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return null;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        OCTechnics.logger.info("*** created new tile entity, block " + this._name);
        return new FactoryTile(this._name);
    }
}