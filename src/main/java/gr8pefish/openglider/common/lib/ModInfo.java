package gr8pefish.openglider.common.lib;

import net.minecraft.util.ResourceLocation;

import java.util.Locale;

public class ModInfo {

    public static final String VERSION = "@VERSION@";
    public static final String MODID = "openglider";
    public static final String MOD_NAME = "Open Glider";
    public static final String DOMAIN = MODID.toLowerCase(Locale.ENGLISH) + ":"; //for resources
    public static final String GUI_FACTORY = "gr8pefish.openglider.client.gui.GuiFactory"; //for in-game config

    public static final String COMMON_PROXY = "gr8pefish.openglider.common.proxy.CommonProxy";
    public static final String CLIENT_PROXY = "gr8pefish.openglider.client.proxy.ClientProxy";

    public static final String NETWORK_CHANNEL = "opngldr";

    public static final String GLIDING_CAPABILITY_STRING = "gliding";

    public static final String ITEM_GLIDER_NAME = "hang_glider";
    public static final String ITEM_GLIDER_PART_NAME = "glider_part";

    public static final ResourceLocation MODEL_GLIDER_TEXTURE_RL = new ResourceLocation(MODID, "textures/models/hang_glider.png");

}
