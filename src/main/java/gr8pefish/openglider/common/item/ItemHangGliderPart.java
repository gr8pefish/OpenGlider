package gr8pefish.openglider.common.item;

import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import static gr8pefish.openglider.api.OpenGliderInfo.*;

public class ItemHangGliderPart extends Item {

    public static String[] names = {"wing_left", "wing_right", "scaffolding"};

    public ItemHangGliderPart() {
        super();
        setCreativeTab(OpenGlider.creativeTab);
        setHasSubtypes(true);
        setTranslationKey(MODID +":" + ModInfo.ITEM_GLIDER_PART_NAME+ ".");
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (isInCreativeTab(tab)) {
            for (int i = 0; i < names.length; i++)
                subItems.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack) + names[stack.getItemDamage()];
    }

}
