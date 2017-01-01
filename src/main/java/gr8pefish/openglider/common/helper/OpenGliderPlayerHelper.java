package gr8pefish.openglider.common.helper;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.item.ItemHangGlider;
import gr8pefish.openglider.common.network.PacketHandler;
import gr8pefish.openglider.common.network.PacketUpdateGliderDamage;
import gr8pefish.openglider.common.wind.WindHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
            ItemStack glider = getGlider(player);
            if (isValidGlider(glider)) {
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

                    WindHelper.applyWind(player, glider);

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
                if (ConfigHandler.durabilityEnabled) { //durability should be taken away
                    if (!player.worldObj.isRemote) { //server
                        if (player.worldObj.rand.nextInt(ConfigHandler.durabilityTimeframe) == 0) { //damage about once per x ticks
                            PacketHandler.HANDLER.sendTo(new PacketUpdateGliderDamage(), (EntityPlayerMP) player); //send to client
                            glider.damageItem(ConfigHandler.durabilityPerUse, player);
                            if (ItemHangGlider.isBroken(glider)) { //broken item
                                OpenGliderCapabilities.setIsGliderDeployed(player, false);
                            }
                        }
                    }
                }

            } else { //Invalid item (likely changed selected item slot, update)
                OpenGliderCapabilities.setIsGliderDeployed(player, false);
            }
        }

    }

    /**
     * Check if the player should be gliding.
     * Checks if the player is alive, and not on the ground or in water.
     *
     * @param player - the player to check
     * @return - true if the conditions are met, false otherwise
     */
    public static boolean shouldBeGliding(EntityPlayer player){
        if (player == null || player.isDead) return false;
        if (player.onGround || player.isInWater()) return false;
        return true;
    }

    /**
     * Check if the itemStack is an unbroken HangGlider.
     *
     * @param stack - the itemstack to check
     * @return - true if the item is an unbroken glider, false otherwise
     */
    private static boolean isValidGlider(ItemStack stack) {
        if (stack != null) {
            if (stack.getItem() instanceof ItemHangGlider && (!ItemHangGlider.isBroken(stack))) { //hang glider, not broken
                return true;
            }
        }
        return false;
    }

    /**
     * Loop through player's inventory to get their hang glider.
     *
     * @param player - the player to search
     * @return - the first glider found (as an itemstack), null otherwise
     */
    public static ItemStack getGlider(EntityPlayer player) {
        if (ConfigHandler.holdingGliderEnforced) return player.getHeldItemMainhand();
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
