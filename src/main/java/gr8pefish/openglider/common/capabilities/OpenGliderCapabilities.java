package gr8pefish.openglider.common.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class OpenGliderCapabilities {

    //Inject capabilities
    @CapabilityInject(PlayerGlidingCapability.class)
    public static final Capability<PlayerGlidingCapability> GLIDING_CAPABILITY = null;

    //Get capabilities
    public static PlayerGlidingCapability getGlidingCapability(EntityPlayer player) {
        return player.getCapability(GLIDING_CAPABILITY, null);
    }

    //Registration
    public static void registerAllCapabilities(){
        PlayerGlidingCapability.register();
    }

    //Useful methods for other classes
    public static boolean getIsGliderDeployed(EntityPlayer player) {
        PlayerGlidingCapability cap = getGlidingCapability(player);
        if (cap != null)
            return cap.getIsGliderDeployed();
        return false;
    }

    public static void setIsGliderDeployed(EntityPlayer player, boolean gliding) {
        PlayerGlidingCapability cap = getGlidingCapability(player);
        if (cap != null)
            cap.setIsGliderDeployed(gliding);
    }
}
