package gr8pefish.openglider.common.item;

import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHangGlider extends Item {

    public ItemHangGlider(String unlocName) {
        setCreativeTab(OpenGlider.creativeTab);
        setUnlocalizedName(ModInfo.MODID +":" + unlocName);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        OpenGliderCapabilities.setIsGliding(player, !OpenGliderCapabilities.getIsGliding(player)); //set it to whatever it is not //ToDo: Test that it works both server and client side
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

}
