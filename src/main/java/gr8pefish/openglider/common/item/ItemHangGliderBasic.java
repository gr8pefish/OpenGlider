package gr8pefish.openglider.common.item;

import gr8pefish.openglider.api.item.ItemHangGliderBase;
import gr8pefish.openglider.client.model.ModelGlider;
import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.nbt.NBTTagCompound;

import static gr8pefish.openglider.api.OpenGliderInfo.MODID;

public class ItemHangGliderBasic extends ItemHangGliderBase {

    public ItemHangGliderBasic() {
        super(ConfigHandler.basicGliderHorizSpeed, ConfigHandler.basicGliderVertSpeed, ConfigHandler.basicGliderShiftHorizSpeed, ConfigHandler.basicGliderShiftVertSpeed, ConfigHandler.basicGliderWindModifier, ConfigHandler.basicGliderAirResistance, ConfigHandler.basicGliderTotalDurability, ModelGlider.MODEL_GLIDER_BASIC_TEXTURE_RL);
        setCreativeTab(OpenGlider.creativeTab);
        setTranslationKey(MODID +":" + ModInfo.ITEM_GLIDER_BASIC_NAME);
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
