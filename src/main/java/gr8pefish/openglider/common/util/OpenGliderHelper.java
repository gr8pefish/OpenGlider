package gr8pefish.openglider.common.util;

import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

public class OpenGliderHelper {

    public static ArrayList<ItemStack> getUpgradesFromNBT(ItemStack glider) {
        ArrayList<ItemStack> upgradesArrayList = new ArrayList<>();
        if (glider != null) {
            NBTTagCompound nbtTagCompound = glider.getTagCompound();
            if (nbtTagCompound != null) {
                if(nbtTagCompound.hasKey(ModInfo.NBT_KEYS.UPGRADES)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(ModInfo.NBT_KEYS.UPGRADES, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        ItemStack upgrade = ItemStack.loadItemStackFromNBT(stackTag);
                        if (upgrade != null)
                            upgradesArrayList.add(upgrade);
                    }
                }
            }
        }
        return upgradesArrayList;
    }
}
