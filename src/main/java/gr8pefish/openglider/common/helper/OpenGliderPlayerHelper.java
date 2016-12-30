package gr8pefish.openglider.common.helper;

import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.wind.WindHelper;
import net.minecraft.entity.player.EntityPlayer;

public class OpenGliderPlayerHelper {

    /**
     * Updates the position of the player when gliding.
     * Glider is assumed to be deployed already.
     *
     * @param player - the player gliding
     */
    public static void updatePosition(EntityPlayer player){

        if (shouldBeGliding(player)) {
            if (player.motionY < 0) {
                final double horizontalSpeed;
                final double verticalSpeed;

                if (player.isSneaking()) {
                    horizontalSpeed = ConfigHandler.forwardMovementShift;
                    verticalSpeed = ConfigHandler.verticalMovementShift;
                } else {
                    horizontalSpeed = ConfigHandler.forwardMovement;
                    verticalSpeed = ConfigHandler.verticalMovement;
                }

                WindHelper.applyWind(player);

                player.motionY *= verticalSpeed;

                double x = Math.cos(Math.toRadians(player.rotationYaw + 90)) * horizontalSpeed;
                double z = Math.sin(Math.toRadians(player.rotationYaw + 90)) * horizontalSpeed;
                player.motionX += x;
                player.motionZ += z;
                player.fallDistance = 0f; /* Don't like getting hurt :( */
            }

            //no wild arm swinging while flying
            if (player.worldObj.isRemote) {
                player.limbSwing = 0;
                player.limbSwingAmount = 0;
            }
        } //If item holding enforced need to update/sync cap in an else clause here probably

    }

    public static boolean shouldBeGliding(EntityPlayer player){
        if (player == null || player.isDead) return false;
//        ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
//        if (held == null || !(held.getItem() instanceof ItemHangGlider)) return false; //ToDo: Add back at some point?
        if (player.onGround || player.isInWater()) return false;
        return true;

    }


}
