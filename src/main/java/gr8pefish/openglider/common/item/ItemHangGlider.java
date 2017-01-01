package gr8pefish.openglider.common.item;

import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.lib.ModInfo;
import gr8pefish.openglider.common.network.PacketHandler;
import gr8pefish.openglider.common.network.PacketUpdateClientTarget;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHangGlider extends Item {

    public ItemHangGlider() {
        super();
        setCreativeTab(OpenGlider.creativeTab);
        setUnlocalizedName(ModInfo.MODID +":" + ModInfo.ITEM_GLIDER_NAME);
        setMaxStackSize(1);

        //Add different icons for if the glider is deployed or not
        this.addPropertyOverride(new ResourceLocation("status"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn instanceof EntityPlayer && OpenGliderCapabilities.getIsGliderDeployed((EntityPlayer)entityIn) ? 1.0F : isBroken(stack) ? 2.0F : 0.0F;
            }
        });

        setMaxDamage(ConfigHandler.durabilityTotal);
    }

    public static boolean isBroken(ItemStack stack) {
        return stack.isItemDamaged() && (stack.getItemDamage() >= stack.getMaxDamage() - 1);
    }


    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !(oldStack.getItem().equals(newStack.getItem())); //no more bobbing when damaged if it is the same exact item
    }

    /**
     * Return whether this item is repairable in an anvil. Uses leather.
     */
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {

        List<ItemStack> leathers = OreDictionary.getOres("leather");
        for (ItemStack stack : leathers) {
            if (stack.getItem() == repair.getItem()) return true;
        }
        return false;

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {

        ItemStack chestItem = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        //if no elytra equipped
        if (!(chestItem != null && chestItem.getItem() instanceof ItemElytra)) {

            if (ItemHangGlider.isBroken(itemStack)) return ActionResult.newResult(EnumActionResult.PASS, itemStack); //nothing if broken

            //old deployment state
            boolean isDeployed = OpenGliderCapabilities.getIsGliderDeployed(player);

            //toggle state of glider deployment
            OpenGliderCapabilities.setIsGliderDeployed(player, !isDeployed);

            //client only
            if (!world.isRemote) {
                //send packet to nearby players to update visually
                EntityTracker tracker = world.getMinecraftServer().worldServerForDimension(player.dimension).getEntityTracker();
                tracker.sendToAllTrackingEntity(player, PacketHandler.HANDLER.getPacketFrom(new PacketUpdateClientTarget(player, OpenGliderCapabilities.getIsGliderDeployed(player))));
            }

        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

}
