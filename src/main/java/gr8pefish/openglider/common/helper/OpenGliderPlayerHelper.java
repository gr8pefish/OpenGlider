package gr8pefish.openglider.common.helper;

import gr8pefish.openglider.api.item.IGlider;
import gr8pefish.openglider.api.lib.GliderHelper;
import gr8pefish.openglider.common.config.ConfigHandler;
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
            ItemStack glider = GliderHelper.getGlider(player);
            if (isValidGlider(glider)) {
                if (player.motionY < 0) {

                    //ToDo: this
//                    player.moveEntity(direction.x * motionStrength, ...)
//                    player.moveEntity(player.rotationYaw * iGlider.getFlightSpeed(), player.);

                    final double horizontalSpeed;
                    final double verticalSpeed;
//                    IGlider iGlider = (IGlider) glider.getItem();
//
                    if (!player.isSneaking()) {
                        horizontalSpeed = ConfigHandler.basicGliderFlightForward; //iGlider.getFlightSpeed();
                        verticalSpeed = ConfigHandler.basicGliderFlightVertical; //iGlider.getFlightAngle();
                    } else {
                        //basic * speed (* eff?)
                        //basic * speed (* eff?)
                        horizontalSpeed = ConfigHandler.basicGliderFlightForward * (3 * ConfigHandler.basicGliderShiftFlightSpeedMultiplier);//4 multiplier default//iGlider.getFlightSpeed() * iGlider.getShiftSpeedMultiplier(); //ConfigHandler.forwardMovement;
                        verticalSpeed = ConfigHandler.basicGliderFlightVertical * (1.15 * ConfigHandler.basicGliderShiftFlightSpeedMultiplier);//1.5 multiplier default// * ConfigHandler.basicGliderShiftInefficiencyPercentage; //iGlider.getFlightAngle() * iGlider.getShiftSpeedMultiplier() * iGlider.getShiftEfficiencyPercent(); //ConfigHandler.verticalMovement;
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
                            if (((IGlider)(glider.getItem())).isBroken(glider)) { //broken item
                                GliderHelper.setIsGliderDeployed(player, false);
                            }
                        }
                    }
                }

                //SetPositionAndUpdate on server only

            } else { //Invalid item (likely changed selected item slot, update)
                GliderHelper.setIsGliderDeployed(player, false);
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
            if (stack.getItem() instanceof IGlider && (!((IGlider)(stack.getItem())).isBroken(stack))) { //hang glider, not broken
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
//        if (ConfigHandler.holdingGliderEnforced)
              return player.getHeldItemMainhand();
//        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() instanceof ItemHangglider) {
//            return player.getHeldItemOffhand();
//        }
//        for (ItemStack stack : player.inventory.mainInventory) {
//            if (stack != null) {
//                if (stack.getItem() instanceof ItemHangglider) {
//                    return stack;
//                }
//            }
//        }
//        return null;
    }

}
