package org.octechnics.octechnics;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import net.minecraft.server.MinecraftServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Arrays;

class OCTTestCommand extends CommandBase {
    private static String[] aliases = new String[0];
    
    private void echo(EntityPlayer player, String message) {
        player.addChatMessage(new ChatComponentText(message));
    }
    
    private int[] parseCoords(EntityPlayer player, String px, String py, String pz) {
        int[] coords = new int[3];
        if (px == "~") {
            coords[0] = (int)(player.posX);
        } else {
            coords[0] = Integer.parseInt(px);
        }
        if (py == "~") {
            coords[1] = (int)(player.posY);
        } else {
            coords[1] = Integer.parseInt(py);
        }
        if (pz == "~") {
            coords[2] = (int)(player.posZ);
        } else {
            coords[2] = Integer.parseInt(pz);
        }
        return coords;
    }
    
    @Override
    public String getCommandName() {
        return "oct";
    }
    
    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList(OCTTestCommand.aliases);
    }
    
    @Override
    public String getCommandUsage(ICommandSender source) {
        return "/oct [x y z] - Debugging OCTechnics";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender source) {
        return true;
    }
    
    @Override
    public void processCommand(ICommandSender source, String[] command) {
        EntityPlayer player = (EntityPlayer)source;
        if (player != null) {
            this.echo(player, "OK, OCTechnics works");
            if (command.length == 0) {return;}
            if (command.length != 3) {
                throw new WrongUsageException("Expected 0 or 3 arguments.");
            }
            
            int[] pos;
            try {
                pos = this.parseCoords(player, command[0], command[1], command[2]);
            } catch (NumberFormatException exc) {
                throw new WrongUsageException("Invalid coordinates.");
            }
            
            Block block = player.worldObj.getBlock(pos[0], pos[1], pos[2]);
            if (block == null) {
                this.echo(player, "No block at that position.");
                return;
            }
            
            AbstractFactoryBlock casted_block = (AbstractFactoryBlock)block;
            if (casted_block == null) {
                this.echo(player, "This block does not belong to OCTechnics.");
                return;
            }
            
            FactoryTile tile_entity = casted_block.getTileEntity();
            if (tile_entity == null) {
                this.echo(player, "This block does not have FactoryTile.");
                return;
            }
            
            int inventory_size = tile_entity.getSizeInventory();
            this.echo(player, "The block's inventory size: " + inventory_size);
            
            for (int i = 0; i < inventory_size; i++) {
                ItemStack content = tile_entity.getStackInSlot(i);
                if (content == null) {
                    this.echo(player, i + ": [empty]");
                } else {
                    this.echo(player, i + ": " + content.getDisplayName() + " x" + content.stackSize);
                }
            }
        } else {
            throw new WrongUsageException("Can only be used by players.");
        }
    }
}