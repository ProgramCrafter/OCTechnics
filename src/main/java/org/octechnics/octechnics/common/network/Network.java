package org.octechnics.octechnics;

import net.minecraft.world.World;
import java.util.ArrayList;

public class Network {
    private ArrayList<Node> nodes;
    
    public Network(Node first_node) {
        nodes = new ArrayList();
        nodes.add(first_node);
    }
    
    public void join(Network other) {
        for (Node other_node : other.getNodes()) {
            nodes.add(other_node);
            other_node.setNetwork(this);
        }
    }
    
    public ArrayList<Node> getNodes() {
        return nodes;
    }
    
    public String getUid() {
        // This cannot happen ever, as the elements are never removed from `nodes`
        // And it gets its first element in the constructor, before this method can get called.
        if (nodes.size() == 0) return "null;null;null;null";
        
        Node first_node = nodes.get(0);
        World world = first_node.getWorld();
        
        return world.getWorldInfo().getWorldName() + ";" + 
               Integer.toString(first_node.x) + ";" +
               Integer.toString(first_node.y) + ";" +
               Integer.toString(first_node.z);
    }
}