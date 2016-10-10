package gr8pefish.openglider.common.network;

import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    private static final SimpleNetworkWrapper HANDLER = new SimpleNetworkWrapper(ModInfo.NETWORK_CHANNEL);

    public static void init() {
        int id = 0;
//        HANDLER.registerMessage(PacketBotaniaEffect.Handler.class, PacketBotaniaEffect.class, id++, Side.CLIENT);
//        HANDLER.registerMessage(PacketLeftClick.Handler.class, PacketLeftClick.class, id++, Side.SERVER);
//        HANDLER.registerMessage(PacketDodge.Handler.class, PacketDodge.class, id++, Side.SERVER);
    }

}

