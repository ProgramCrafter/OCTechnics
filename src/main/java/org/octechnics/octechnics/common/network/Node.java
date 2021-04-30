package org.octechnics.octechnics;

import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class Node {
    private FactoryTile tile;
    private Network net;
    
    public int x;
    public int y;
    public int z;
    
    public Node(FactoryTile self) {
        this.tile = self;
        this.net = new Network(this);
        
        x = tile.getPos()[0];
        y = tile.getPos()[1];
        z = tile.getPos()[2];
        
        tryJoinNetwork(x + 1, y, z);
        tryJoinNetwork(x - 1, y, z);
        tryJoinNetwork(x, y + 1, z);
        tryJoinNetwork(x, y - 1, z);
        tryJoinNetwork(x, y, z + 1);
        tryJoinNetwork(x, y, z - 1);
    }
    
    public void tryJoinNetwork(int x, int y, int z) {
        World cur = this.tile.getWorld();
        if (cur == null) {
            OCTechnics.logger.info("org.octechnics.octechnics.Node.tryJoinNetwork: tile.world == null");
            return;
        }
        
        Node node_at = Node.getNodeAt(cur, x, y, z);
        if (node_at == null) return;
        
        this.net.join(node_at.getNetwork());
    }
    
    public Network getNetwork() {
        return net;
    }
    
    public void setNetwork(Network new_net) {
        net = new_net;
    }
    
    public World getWorld() {
        return tile.getWorld();
    }
    
    public Object[] getPos() {
        int[] pos = this.tile.getPos();
        return new Object[] {pos[0], pos[1], pos[2]};
    }
    public String getType() {
        return (String)this.tile.getComponentName();
    }
    
    public static Node getNodeAt(World world, int x, int y, int z) {
        if (world == null) throw new IllegalArgumentException("org.octechnics.octechnics.Node.getNodeAt does not accept null as world");
        
        if (y < 0 || y > 255) return null; // bigger worlds are not supported yet
        
        FactoryTile tile_at;
        try {
            tile_at = (FactoryTile)world.getTileEntity(x, y, z);
        } catch (ClassCastException exc) {
            return null;
        }
        if (tile_at == null) return null;
        
        return tile_at.getNode();
    }
}