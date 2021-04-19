package org.octechnics.octechnics;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import cpw.mods.fml.common.Optional;

import li.cil.oc.api.network.SimpleComponent;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")
public class FactoryTile extends TileEntity implements SimpleComponent {
    private ItemStack[] contents;
    private String block_name;
    private int ticksSinceSync;
    
    public FactoryTile(String block_name) {
        super();
        this.block_name = block_name;
        this.contents = new ItemStack[this.getSizeInventory()];
    }
    
    @Override
    public String getComponentName() {
        return this.block_name;
    }
    
    public int getSizeInventory() {
        if (this.block_name == "factory_items_storage") {return 9;} else {return 0;}
    }
    public ItemStack getStackInSlot(int index) {
        if (index >= 0 && index < this.getSizeInventory()) {return contents[index];}
        return null;
    }
    public ItemStack decrStackSize(int index, int count) {
        if (this.contents[index] != null) {
            if (this.contents[index].stackSize <= count) {
                ItemStack itemstack1 = this.contents[index];
                this.contents[index] = null;
                this.markDirty();
                return itemstack1;
            } else {
                ItemStack itemstack = this.contents[index].splitStack(count);
                
                if (this.contents[index].stackSize == 0) { this.contents[index] = null; }
                
                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }
    public ItemStack removeStackFromSlot(int index) {
        if (this.contents[index] != null) {
            ItemStack itemstack = this.contents[index];
            this.contents[index] = null;
            this.markDirty();
            return itemstack;
        } else {
            return null;
        }
    }
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.contents[index] = stack;
        
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
        
        this.markDirty();
    }
    public int getInventoryStackLimit() {return 64;}
    
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        
        NBTTagList items_nbt = data.getTagList("Items", 10);
        this.contents = new ItemStack[this.getSizeInventory()];
        
        for (int i = 0; i < items_nbt.tagCount(); ++i) {
            NBTTagCompound tag = items_nbt.getCompoundTagAt(i);
            int j = tag.getByte("Slot") & 255;
            if (j >= 0 && j < this.contents.length) {
                this.contents[j] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        
        NBTTagList items_nbt = new NBTTagList();
        for (int i = 0; i < this.contents.length; ++i) {
            if (this.contents[i] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte)i);
                this.contents[i].writeToNBT(tag);
                items_nbt.appendTag(tag);
            }
        }
        
        data.setTag("Items", items_nbt);
    }
}