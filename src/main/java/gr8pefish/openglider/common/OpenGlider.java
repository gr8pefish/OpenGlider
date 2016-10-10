package gr8pefish.openglider.common;

import gr8pefish.openglider.common.lib.ModInfo;
import gr8pefish.openglider.common.network.PacketHandler;
import gr8pefish.openglider.common.proxy.IProxy;
import gr8pefish.openglider.common.registry.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
public class OpenGlider {

    //Proxies
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static IProxy proxy;

    //Creative Tab
    public static final CreativeTabs creativeTab = new CreativeTabs(ModInfo.MODID) {
        @Override
        public Item getTabIconItem() {
            return Items.ELYTRA;
        }
    };

    //Mod Instance
    @Mod.Instance
    public static OpenGlider instance;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        //packets
        PacketHandler.init();

        //items
        ItemRegistry.registerItems();

        //init keybindings and renderers
        proxy.preInit(event);
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        //recipes
        ItemRegistry.registerRecipes();

        proxy.init(event);
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit(event);
    }
}
