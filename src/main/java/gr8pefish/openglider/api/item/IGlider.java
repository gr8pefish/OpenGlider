package gr8pefish.openglider.api.item;

public interface IGlider {
    //ToDo

    //==============Flight==================

    //Blocks traveled horizontally per each block down.
    double getFlightAngle();

    void setFlightAngle(double angle);

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
