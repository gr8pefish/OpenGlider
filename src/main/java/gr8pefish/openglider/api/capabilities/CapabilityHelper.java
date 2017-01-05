package gr8pefish.openglider.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityHelper {

    //ignore for now
    public static final String GLIDING_CAPABILITY_STRING = "glidingCap";


//    @CapabilityInject(IGliderCapabilityHandler.class)
    /** Holds the capability **/
    public static Capability<IGliderCapabilityHandler> GLIDER_CAPABILITY = null;

    /** Inject the capability **/
    @CapabilityInject(IGliderCapabilityHandler.class)
    public static void initCapability(Capability<IGliderCapabilityHandler> capability) {
        if (capability != null)
            GLIDER_CAPABILITY = capability;
    }


    //===================================== Helper Methods ==================================================

    /**
     * Wrapper method for {@link IGliderCapabilityHandler#getIsPlayerGliding()}, taking into account capabilities.
     *
     * @param player - the player to check
     * @return - True if gliding, False otherwise (includes no capability)
     */
    public static boolean getIsPlayerGliding(EntityPlayer player) {
        IGliderCapabilityHandler cap = getGlidingCapability(player);
        if (cap != null)
            return cap.getIsPlayerGliding();
        return false;
    }

    /**
     * Wrapper method for {@link IGliderCapabilityHandler#setIsPlayerGliding(boolean)}, taking into account capabilities.
     *
     * @param player - the player to check
     * @param isGliding - the gliding state to set
     */
    public static void setIsPlayerGliding(EntityPlayer player, boolean isGliding) {
        IGliderCapabilityHandler cap = getGlidingCapability(player);
        if (cap != null)
            cap.setIsPlayerGliding(isGliding);
    }

    /**
     * Wrapper method for {@link IGliderCapabilityHandler#getIsGliderDeployed()}, taking into account capabilities.
     *
     * @param player - the player to check
     * @return - True if deployed, False otherwise (includes no capability)
     */
    public static boolean getIsGliderDeployed(EntityPlayer player) {
        IGliderCapabilityHandler cap = getGlidingCapability(player);
        if (cap != null)
            return cap.getIsGliderDeployed();
        return false;
    }

    /**
     * Wrapper method for {@link IGliderCapabilityHandler#setIsGliderDeployed(boolean)}, taking into account capabilities.
     *
     * @param player - the player to check
     * @param deployed - the glider deployment state to set
     */
    public static void setIsGliderDeployed(EntityPlayer player, boolean deployed) {
        IGliderCapabilityHandler cap = getGlidingCapability(player);
        if (cap != null)
            cap.setIsGliderDeployed(deployed);
    }

    /**
     * Gets the gliding capability of a given player.
     *
     * @param player - the player to check
     * @return True if the player has the capability, False otherwise
     */
    public static IGliderCapabilityHandler getGlidingCapability(EntityPlayer player) {
        return player.getCapability(GLIDER_CAPABILITY, null);
    }


}
