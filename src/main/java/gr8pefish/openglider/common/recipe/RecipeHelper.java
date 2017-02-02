package gr8pefish.openglider.common.recipe;

import gr8pefish.openglider.api.item.IGlider;
import gr8pefish.openglider.api.upgrade.UpgradeItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class RecipeHelper {

    /**
     * Helper method for getting the first glider in the recipes grid (which will be the one used)
     * @param inventoryCrafting - the inventory to search
     * @return - the glider to be crafted
     */
    public static ItemStack getFirstUpgradableGlider(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (itemstack != null && (itemstack.getItem() instanceof IGlider)) {
                    return itemstack;
                }
            }
        }
        return null;
    }

    /**
     * Helper method for getting the first upgrade in the recipes grid (which will be the one used)
     * @param inventoryCrafting - the inventory to search
     * @return - the upgrade to be used
     */
    public static ItemStack getFirstUpgrade(InventoryCrafting inventoryCrafting){
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(j, i);
                if (itemstack != null) {
                    for (ItemStack upgrade : UpgradeItems.getPossibleUpgradeList()) {
                        if (ItemStack.areItemStacksEqual(upgrade, itemstack)) {
                            ItemStack returnStack = itemstack.copy(); //copy stack
                            returnStack.stackSize = 1; //only apply 1 upgrade (stack size of 1)
                            return returnStack;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Helper method for getting the first glider in the recipes grid (which will be the one used)
     * @param inventoryCrafting - the inventory to search
     * @return - the integer of the slot number that the glider is in (-1 if no slot found)
     */
    public static int getFirstGliderInGridSlotNumber(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemstack = inventoryCrafting.getStackInSlot(i);
            if (itemstack != null && (itemstack.getItem() instanceof IGlider))
                return i;
        }
        return -1;
    }

    public static boolean containsStack(ArrayList<ItemStack> list, ItemStack stack) {
        for (ItemStack listItem : list) {
            if (ItemStack.areItemStacksEqual(listItem, stack)) {
                return true;
            }
        }
        return false;
    }

}
