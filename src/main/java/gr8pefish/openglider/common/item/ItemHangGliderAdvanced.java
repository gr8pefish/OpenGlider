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

public class ItemHangGliderAdvanced extends ItemHangGliderBase {

    public ItemHangGliderAdvanced() {
        super(ConfigHandler.advancedGliderHorizSpeed, ConfigHandler.advancedGliderVertSpeed, ConfigHandler.advancedGliderShiftHorizSpeed, ConfigHandler.advancedGliderShiftVertSpeed, ConfigHandler.advancedGliderWindModifier, ConfigHandler.advancedGliderTotalDurability, ModInfo.MODEL_GLIDER_ADVANCED_TEXTURE_RL);
        setCreativeTab(OpenGlider.creativeTab);
        setUnlocalizedName(MODID +":" + ModInfo.ITEM_GLIDER_ADVANCED_NAME);
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
