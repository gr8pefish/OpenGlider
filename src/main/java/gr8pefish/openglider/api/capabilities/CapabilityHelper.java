package gr8pefish.openglider.api.capabilities;

import gr8pefish.openglider.common.capabilities.DefaultGliderCapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.Optional;

import static gr8pefish.openglider.api.capabilities.CapabilityRegistration.data;

public class CapabilityHelper {

    /** Holds the capability **/
    @CapabilityInject(IGliderCapabilityHandler.class)
    public static final Capability<IGliderCapabilityHandler> GLIDER_CAPABILITY = null;


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
     * @param isDeployed - the glider deployment state to set
     */
    public static void setIsGliderDeployed(EntityPlayer player, boolean isDeployed) {
        IGliderCapabilityHandler cap = getGlidingCapability(player);
        if (cap != null)
            cap.setIsGliderDeployed(isDeployed);
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
