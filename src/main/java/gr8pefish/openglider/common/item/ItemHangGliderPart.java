package gr8pefish.openglider.common.item;

import gr8pefish.openglider.common.OpenGlider;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static gr8pefish.openglider.api.OpenGliderInfo.MODID;

public class ItemHangGliderPart extends Item {

    public static String[] names = {"wing_left", "wing_right", "scaffolding"};

    public ItemHangGliderPart() {
        super();
        setCreativeTab(OpenGlider.creativeTab);
        setHasSubtypes(true);
        setUnlocalizedName(MODID +":" + ModInfo.ITEM_GLIDER_PART_NAME+ ".");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (int i = 0; i < names.length; i++)
            subItems.add(new ItemStack(itemIn, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + names[stack.getItemDamage()];
    }

}
