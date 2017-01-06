package gr8pefish.openglider.api.item;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IGlider extends INBTSerializable<NBTTagCompound> {
    //ToDo

    //==============Flight==================

    //Blocks traveled horizontally per each block down.
    double getFlightAngle();

    void setFlightAngle(double angle);

    //speed traveling that angle
    double getFlightSpeed();

    void setFlightSpeed(double speed);

    //===============Wind====================

    double getWindMultiplier();

    void setWindMultiplier(double windMultiplier);

    //=============Durability================

    int getDurability();

    void setDurability(int durability);

    //==============Other====================
    //Some way of notifying additional abilities, such as compass upgrade

    Object getSpecialProperties();

    void setSpecialProperties(Object specialProperties);

}
