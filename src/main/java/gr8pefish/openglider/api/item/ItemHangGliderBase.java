package gr8pefish.openglider.api.item;

import gr8pefish.openglider.api.lib.GliderHelper;
import gr8pefish.openglider.common.item.ItemHangGliderBasic;
import gr8pefish.openglider.common.network.PacketHandler;
import gr8pefish.openglider.common.network.PacketUpdateClientTarget;
import gr8pefish.openglider.common.util.OpenGliderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemHangGliderBase extends Item implements IGlider {

    //ToDo: NBT saving tags of upgrade (need IRecipe for them)

    private double angle;
    private double speed;
    private double windMultiplier;
    private int totalDurability;
    private ResourceLocation modelRL;

    public ItemHangGliderBase(double angle, double speed, double windMultiplier, int totalDurability, ResourceLocation modelRL) {
        this.angle = angle;
        this.speed = speed;
        this.windMultiplier = windMultiplier;
        this.totalDurability = totalDurability;
        this.modelRL = modelRL;

        setMaxDamage(totalDurability);
    }

    /**
     * Handles a right click of the item attempting to deploy the hang glider.
     *
     * @param itemStack - the stack clicked
     * @param world - the world this occurs in
     * @param player - the player clicking
     * @param hand - the hand used
     *
     * @return - an ActionResult of the occurrence
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        //ToDo: test enforce mainhand only
        ItemStack chestItem = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        //if no elytra equipped
        if (!(chestItem != null && chestItem.getItem() instanceof ItemElytra)) {

            if (this.isBroken(itemStack)) return ActionResult.newResult(EnumActionResult.PASS, itemStack); //nothing if broken
            if (!hand.equals(EnumHand.MAIN_HAND)) return ActionResult.newResult(EnumActionResult.PASS, itemStack); //return if not using main hand

            //old deployment state
            boolean isDeployed = GliderHelper.getIsGliderDeployed(player);

            //toggle state of glider deployment
            GliderHelper.setIsGliderDeployed(player, !isDeployed);

            //client only
            if (!world.isRemote) {
                //send packet to nearby players to update visually
                EntityTracker tracker = world.getMinecraftServer().worldServerForDimension(player.dimension).getEntityTracker();
                tracker.sendToAllTrackingEntity(player, PacketHandler.HANDLER.getPacketFrom(new PacketUpdateClientTarget(player, GliderHelper.getIsGliderDeployed(player))));
            }

        } else {
            if (world.isRemote) { //client
                player.addChatMessage(new TextComponentTranslation("openglider.elytra.error"));
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    /**
     * Handles the visual bobbing of reequipping the item.
     * Doesn't do so unless the item breaks, or it is a new slot.
     * This means that it won't bob when updating the damage value when flying.
     *
     * @param oldStack - the old stack selected
     * @param newStack - the new stack selected
     * @param slotChanged - if the slot was changed
     *
     * @return - True to cause re-equip, false otherwise
     */
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (slotChanged) return true;
        //ToDo: Allow broken stacks to reequip, need to alter fp rotation in the json files
//        if (newStack != null && newStack.getItem() != null && newStack.getItem() instanceof ItemHangGliderBasic && isBroken(newStack))
//            return true;
//        else
        return !(oldStack.getItem().equals(newStack.getItem())); //no more bobbing when damaged if it is the same exact item
    }

    //Helper method for checking if a hang glider is broken (1 durability left)
    @Override
    public boolean isBroken(ItemStack stack) {
        return stack.isItemDamaged() && (stack.getItemDamage() >= stack.getMaxDamage() - 1);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        ArrayList<ItemStack> upgrades = OpenGliderHelper.getUpgradesFromNBT(stack);
        for (ItemStack upgrade : upgrades) {
            tooltip.add(upgrade.getDisplayName() + " " + I18n.format("openglider.tooltip.upgrade"));
        }
    }


    //==============================================IGlider========================================



    @Override
    public double getFlightAngle() {
        return angle;
    }

    @Override
    public void setFlightAngle(double angleToSetTo) {
        angle = angleToSetTo;
    }

    @Override
    public double getFlightSpeed() {
        return speed;
    }

    @Override
    public void setFlightSpeed(double speedToSetTo) {
        speed = speedToSetTo;
    }

    @Override
    public double getShiftSpeedMultiplier() {
        return 0;
    }

    @Override
    public void setShiftSpeedMultiplier(double shiftSpeedMultiplier) {

    }

    @Override
    public double getShiftEfficiencyPercent() {
        return 0;
    }

    @Override
    public void setShiftEfficiencyPercentage(double shiftEfficiencyPercentage) {

    }

    @Override
    public double getWindMultiplier() {
        return windMultiplier;
    }

    @Override
    public void setWindMultiplier(double windMultiplierToSetTo) {
        windMultiplier = windMultiplierToSetTo;
    }

    @Override
    public int getTotalDurability() {
        return totalDurability;
    }

    @Override
    public void setTotalDurability(int durability) {
        totalDurability = durability;
    }

    @Override
    public int getCurrentDurability(ItemStack stack) {
        return stack.getItemDamage();
    }

    @Override
    public void setCurrentDurability(ItemStack stack, int durability) {
        setDamage(stack, durability);
        if (stack.getItemDamage() < 1)
            stack.setItemDamage(1);
    }

    @Override
    public ArrayList<ItemStack> getUpgrades(ItemStack glider) {
        return OpenGliderHelper.getUpgradesFromNBT(glider); //ToDo: too tightly tied, not API friendly
    }

    @Override
    public void removeUpgrade(ItemStack glider, ItemStack upgrade) {
        //ToDo
    }

    @Override
    public void addUpgrade(ItemStack glider, ItemStack upgrade) {
        //ToDo
    }

    @Override
    public ResourceLocation getModelTexture(ItemStack glider) {
        return modelRL;
    }

    @Override
    public void setModelTexture(ResourceLocation resourceLocation) {
        modelRL = resourceLocation;
    }


    @Override
    public NBTTagCompound serializeNBT() {
        return null; //ToDo
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        //ToDo
    }
}
