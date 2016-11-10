package gr8pefish.openglider.common.item;

import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.lib.ModInfo;
import gr8pefish.openglider.common.network.PacketHandler;
import gr8pefish.openglider.common.network.PacketUpdateClientTarget;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Iterator;

public class ItemHangGlider extends Item {

    public ItemHangGlider() {
        super();
        setCreativeTab(OpenGlider.creativeTab);
        setUnlocalizedName(ModInfo.MODID +":" + ModInfo.ITEM_GLIDER_NAME);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        ItemStack chestItem = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (!(chestItem != null && chestItem.getItem() instanceof ItemElytra)) {
            OpenGliderCapabilities.setIsGliderDeployed(player, !OpenGliderCapabilities.getIsGliderDeployed(player)); //set it to whatever it is not

            //sending packet to nearby players
            if (!world.isRemote) {
                EntityTracker tracker = world.getMinecraftServer().worldServerForDimension(player.dimension).getEntityTracker();
                tracker.sendToAllTrackingEntity(player, PacketHandler.HANDLER.getPacketFrom(new PacketUpdateClientTarget(player, OpenGliderCapabilities.getIsGliderDeployed(player))));
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

}
