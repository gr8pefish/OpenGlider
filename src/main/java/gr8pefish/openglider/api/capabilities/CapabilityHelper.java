package gr8pefish.openglider.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityHelper {

    //ignore for now
    public static final String GLIDING_CAPABILITY_STRING = "glidingCap";

    /** Inject the capability **/
    @CapabilityInject(IGliderHandler.class)
    public static final Capability<IGliderHandler> GLIDER_CAPABILITY = null;

    //===================================== Helper Methods ==================================================

    /**
     * Wrapper method for {@link IGliderHandler#getIsPlayerGliding()}, taking into account capabilities.
     *
     * @param player - the player to check
     * @return - True if gliding, False otherwise (includes no capability)
     */
    public static boolean getIsPlayerGliding(EntityPlayer player) {
        IGliderHandler cap = getGlidingCapability(player);
        if (cap != null)
            return cap.getIsPlayerGliding();
        return false;
    }

    /**
     * Wrapper method for {@link IGliderHandler#setIsPlayerGliding(boolean)}, taking into account capabilities.
     *
     * @param player - the player to check
     * @param isGliding - the gliding state to set
     */
    public static void setIsPlayerGliding(EntityPlayer player, boolean isGliding) {
        IGliderHandler cap = getGlidingCapability(player);
        if (cap != null)
            cap.setIsPlayerGliding(isGliding);
    }

    /**
     * Wrapper method for {@link IGliderHandler#getIsGliderDeployed()}, taking into account capabilities.
     *
     * @param player - the player to check
     * @return - True if deployed, False otherwise (includes no capability)
     */
    public static boolean getIsGliderDeployed(EntityPlayer player) {
        IGliderHandler cap = getGlidingCapability(player);
        if (cap != null)
            return cap.getIsGliderDeployed();
        return false;
    }

    /**
     * Wrapper method for {@link IGliderHandler#setIsGliderDeployed(boolean)}, taking into account capabilities.
     *
     * @param player - the player to check
     * @param deployed - the glider deployment state to set
     */
    public static void setIsGliderDeployed(EntityPlayer player, boolean deployed) {
        IGliderHandler cap = getGlidingCapability(player);
        if (cap != null)
            cap.setIsGliderDeployed(deployed);
    }

    /**
     * Gets the gliding capability of a given player.
     *
     * @param player - the player to check
     * @return True if the player has the capability, False otherwise
     */
    public static IGliderHandler getGlidingCapability(EntityPlayer player) {
        return player.getCapability(GLIDER_CAPABILITY, null);
    }


}
