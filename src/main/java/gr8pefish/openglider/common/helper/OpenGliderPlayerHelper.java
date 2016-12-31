package gr8pefish.openglider.common.helper;

import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.item.ItemHangGlider;
import gr8pefish.openglider.common.wind.WindHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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

            //damage the hang glider
            ItemStack glider = getGlider(player);
            if (glider != null) {
                if (player.worldObj.rand.nextInt(ConfigHandler.durabilityTimeframe) == 0) { //damage about once per x ticks
                    glider.damageItem(ConfigHandler.durabilityPerUse, player);
                }
            }

        } //If item holding enforced need to update/sync cap in an else clause here probably

    }

    public static boolean shouldBeGliding(EntityPlayer player){
        ItemStack glider = getGlider(player);
        if (player == null || player.isDead) return false;
//        ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
//        if (held == null || !(held.getItem() instanceof ItemHangGlider)) return false; //ToDo: Add back at some point?
//        if (glider == null || glider.getItemDamage() >= glider.getMaxDamage()) return false;
        if (player.onGround || player.isInWater()) return false;
        return true;

        //ToDo: make sure player can't move item around?
    }

    /**
     * Loop through player's inventory to get their hang glider.
     *
     * @param player - the player to search
     * @return - the first glider found (as an itemstack), null otherwise
     */
    public static ItemStack getGlider(EntityPlayer player) {
        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() instanceof ItemHangGlider) {
            return player.getHeldItemOffhand();
        }
        for (ItemStack stack : player.inventory.mainInventory) {
            if (stack != null) {
                if (stack.getItem() instanceof ItemHangGlider) {
                    return stack;
                }
            }
        }
        return null;
    }

}
