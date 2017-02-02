package gr8pefish.openglider.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;

public interface IGlider extends INBTSerializable<NBTTagCompound> {
    //ToDo

    //==============Flight==================

    //Blocks traveled horizontally per each block down.
    double getFlightAngle();

    void setFlightAngle(double angle);

    //speed traveling that angle
    double getFlightSpeed();

    void setFlightSpeed(double speed);

    //speed up values
    double getShiftSpeedMultiplier();

    void setShiftSpeedMultiplier(double shiftSpeedMultiplier);

    double getShiftEfficiencyPercent();

    void setShiftEfficiencyPercentage(double shiftEfficiencyPercentage);

    //===============Wind====================

    double getWindMultiplier();

    void setWindMultiplier(double windMultiplier);

    //=============Durability================

    int getTotalDurability();

    void setTotalDurability(int durability);

    int getCurrentDurability(ItemStack glider);

    void setCurrentDurability(ItemStack glider, int durability);

    //==============Upgrades====================
    ArrayList<ItemStack> getUpgrades(ItemStack glider);

    void removeUpgrade(ItemStack glider, ItemStack upgrade);

    void addUpgrade(ItemStack glider, ItemStack upgrade);

}
