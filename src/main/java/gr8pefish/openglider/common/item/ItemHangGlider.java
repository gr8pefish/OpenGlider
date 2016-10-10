package gr8pefish.openglider.common.item;

import com.google.common.collect.MapMaker;
import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.entity.EntityHangGlider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Map;

public class ItemHangGlider extends Item {

    private static Map<EntityPlayer, EntityHangGlider> spawnedGlidersMap = new MapMaker().weakKeys().weakValues().makeMap();

    public ItemHangGlider() {
        setCreativeTab(OpenGlider.creativeTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && player != null) {
            EntityHangGlider glider = spawnedGlidersMap.get(player);
            if (glider != null) despawnGlider(player, glider);
            else spawnGlider(player);
        }
        return ActionResult.newResult(EnumActionResult.PASS, itemStack); //ToDo: Test
    }

    private static void despawnGlider(EntityPlayer player, EntityHangGlider glider) {
        glider.setDead();
        spawnedGlidersMap.remove(player);
    }

    private static void spawnGlider(EntityPlayer player) {
        EntityHangGlider glider = new EntityHangGlider(player.worldObj, player);
        glider.setPositionAndRotation(player.posX, player.posY, player.posZ, player.rotationPitch, player.rotationYaw);
        player.worldObj.spawnEntityInWorld(glider);
        spawnedGlidersMap.put(player, glider);
    }
}
