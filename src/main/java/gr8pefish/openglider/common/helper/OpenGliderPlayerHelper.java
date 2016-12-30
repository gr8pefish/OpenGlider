package gr8pefish.openglider.common.helper;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.item.ItemHangGlider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

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

                //ToDo: tweak speeds/make sure okay (change with tiers), make configurable
                if (player.isSneaking()) {
                    horizontalSpeed = 0.09;
                    verticalSpeed = 0.7;
                } else {
                    horizontalSpeed = 0.02;
                    verticalSpeed = 0.5;
                }

                player.motionY *= verticalSpeed;
                double x = Math.cos(Math.toRadians(player.rotationYawHead + 90)) * horizontalSpeed;
                double z = Math.sin(Math.toRadians(player.rotationYawHead + 90)) * horizontalSpeed;
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
