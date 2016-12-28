package gr8pefish.openglider.common.helper;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.item.ItemHangGlider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class OpenGliderPlayerHelper {

    public static void updatePosition(EntityPlayer player){
        if (OpenGliderCapabilities.getIsGliderDeployed(player)) {
            player.fallDistance = 0; //don't get hurt if glider deployed
//            System.out.println(player.worldObj.isRemote ? "client" : "server");
            if (shouldBeGliding(player)) {
                if (player.motionY < 0) {
                    final double horizontalSpeed;
                    final double verticalSpeed;

                    //ToDo: tweak speeds/make sure okay (change with tiers)
                    if (player.isSneaking()) {
                        horizontalSpeed = 0.1;
                        verticalSpeed = 0.8;
                    } else {
                        horizontalSpeed = 0.03;
                        verticalSpeed = 1.0; //0.5
                    }

                    player.motionY *= verticalSpeed;
                    double x = Math.cos(Math.toRadians(player.rotationYawHead + 90)) * horizontalSpeed;
                    double z = Math.sin(Math.toRadians(player.rotationYawHead + 90)) * horizontalSpeed;
                    player.motionX += x;
                    player.motionZ += z;
//                    player.fallDistance = 0f; /* Don't like getting hurt :( */
                }

                //no wild arm swinging while flying
                if (player.worldObj.isRemote) {
                    player.limbSwing = 0;
                    player.limbSwingAmount = 0;
                    player.fallDistance = 0;
                } else {
                    player.fallDistance = 0;
                }
            }
        }

    }

    public static boolean shouldBeGliding(EntityPlayer player){
        if (player == null || player.isDead) return false;
        ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
        if (held == null || !(held.getItem() instanceof ItemHangGlider)) return false; //ToDo: Remove at some point? (some upgrade/better tier?)
        if (player.onGround || player.isInWater()) return false;
        return true;

    }
}
