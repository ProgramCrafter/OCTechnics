package org.octechnics.octechnics;

import cpw.mods.fml.common.Optional;

import li.cil.oc.api.network.SimpleComponent;
//import li.cil.oc.api.network.Network;
//import li.cil.oc.api.network.Node;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;

import net.minecraft.world.World;

import java.lang.reflect.*;

import java.util.ArrayList;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")
public class FactoryTile extends FactoryTileVanilla implements SimpleComponent {
    protected Node network_node;
    
    public FactoryTile(World world, String block_name, boolean is_controller_te) {
        super(block_name, is_controller_te);
        if (!this.hasWorldObj()) this.setWorldObj(world);
        this.network_node = new Node(this);
    }
    
    public Node getNode() {
        return network_node;
    }
    
    @Override
    public String getComponentName() {
        if (this.is_controller_te) return this.block_name;
        return null;
    }
    
    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] getPosition(Context context, Arguments args) {
        return new Object[] {this.xCoord, this.yCoord, this.zCoord};
    }
    
    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] uid(Context context, Arguments args) {
        Object[] result = new Object[] {this.network_node.getNetwork().getUid()};
        return result;
    }
    
    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] enumFactoryParts(Context context, Arguments args) {
        Network net = this.network_node.getNetwork();
        ArrayList<Node> parts = net.getNodes();
        
        li.cil.oc.api.network.Node computer_node = context.node();
        li.cil.oc.api.network.Network computer_net = computer_node.network();
        String computer_addr = computer_node.address();
        
        OCTechnics.logger.info("Enumerating factory parts, network id: " + net.getUid());
        for (Node node : parts) {
            OCTechnics.logger.info("    node " + node.toString());
            computer_net.sendToAddress(computer_node, computer_addr, "computer.signal",
                                       "factory_enum", this.getPos(), node.getType(), net.getUid());
        }
        return null;
    }
    
    @Callback
    @Optional.Method(modid = "OpenComputers")
    public Object[] enumerate_methods(Context context, Arguments args) {
        String methods = "";
        for (Method m : this.getClass().getMethods()) {
            methods += m.getName() + "\n";
        }
        methods += "\n";
        for (Field f : this.getClass().getFields()) {
            methods += f.getName() + "\n";
        }
        
        Object[] result = new Object[] {methods};
        return result;
    }
}