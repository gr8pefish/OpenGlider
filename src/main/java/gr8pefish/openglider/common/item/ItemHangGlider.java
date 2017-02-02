package gr8pefish.openglider.common.item;

import gr8pefish.openglider.api.item.ItemHangGliderBase;
import gr8pefish.openglider.api.lib.GliderHelper;
import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.helper.OpenGliderPlayerHelper;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

import static gr8pefish.openglider.api.OpenGliderInfo.MODID;

public class ItemHangGlider extends ItemHangGliderBase {

    public ItemHangGlider() {
        super();
        setCreativeTab(OpenGlider.creativeTab);
        setUnlocalizedName(MODID +":" + ModInfo.ITEM_GLIDER_NAME);
        setMaxStackSize(1);

        //Add different icons for if the glider is deployed or not
        this.addPropertyOverride(new ResourceLocation("status"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return isGlidingGlider(entityIn, stack) ? 1.0F : isBroken(stack) ? 2.0F : 0.0F;
            }

            private boolean isGlidingGlider(EntityLivingBase entityIn, ItemStack stack){
                return entityIn != null && entityIn instanceof EntityPlayer && GliderHelper.getIsGliderDeployed((EntityPlayer)entityIn) && OpenGliderPlayerHelper.getGlider((EntityPlayer)entityIn) == stack;
            }

        });

//        angle = ConfigHandler.basicGliderFlightAngle;
//        speed = ConfigHandler.basicGliderFlightSpeed;

        setWindMultiplier(ConfigHandler.basicGliderWindModifier);

        totalDurability = ConfigHandler.basicGliderTotalDurability;
        setMaxDamage(totalDurability);

//        setFlightSpeed(ConfigHandler.basicGliderFlightSpeed);
//        setFlightAngle(ConfigHandler.basicGliderFlightAngle);
//        setShiftSpeedMultiplier(ConfigHandler.basicGliderFlightSpeedShifting);

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

    //ToDo: Needed?
    @Override
    public NBTTagCompound serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }
}
