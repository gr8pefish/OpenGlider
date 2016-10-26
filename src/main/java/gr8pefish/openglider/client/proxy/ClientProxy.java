package gr8pefish.openglider.client.proxy;

import gr8pefish.openglider.client.event.ClientEventHandler;
import gr8pefish.openglider.client.renderer.LayerGlider;
import gr8pefish.openglider.common.proxy.IProxy;
import gr8pefish.openglider.common.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ItemRegistry.registerRenders();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        //Add rendering layer
        LayerGlider.addLayer();

        //register client events
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().thePlayer;
    }
}
