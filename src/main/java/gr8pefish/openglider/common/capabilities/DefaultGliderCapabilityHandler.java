package gr8pefish.openglider.common.capabilities;

import gr8pefish.openglider.api.capabilities.IGliderCapabilityHandler;
import gr8pefish.openglider.common.helper.Logger;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.concurrent.Callable;

import static gr8pefish.openglider.api.ModNameInfo.MODID;
import static gr8pefish.openglider.api.capabilities.CapabilityHelper.GLIDER_CAPABILITY;

public class DefaultGliderCapabilityHandler implements ICapabilitySerializable<NBTTagCompound>, IGliderCapabilityHandler {

    private static final String CAP_PLAYER_GLIDING = MODID+"isPlayerGliding";
    private static final String CAP_GLIDER_DEPLOYED = MODID+"isGliderDeployed";

    private boolean isPlayerGliding;
    private boolean isGliderDeployed;

    public DefaultGliderCapabilityHandler() {
        this.isPlayerGliding = false;
        this.isGliderDeployed = false;
    }

    //==========================================IGliderCapabilityHandler===========================================

    @Override
    public boolean getIsPlayerGliding() {
        return isGliderDeployed && isPlayerGliding;
    }

    @Override
    public void setIsPlayerGliding(boolean isGliding) {
        if (!isGliderDeployed && isGliding)
            Logger.error("Can't set a player to be gliding if they don't have a deployed glider!");
        else
            isPlayerGliding = isGliding;
    }

    @Override
    public boolean getIsGliderDeployed() {
        return isGliderDeployed;
    }

    @Override
    public void setIsGliderDeployed(boolean isDeployed) {
        isGliderDeployed = isDeployed;
    }

    //============================================ICapabilitySerializable==================================

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return GLIDER_CAPABILITY != null && capability == GLIDER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return GLIDER_CAPABILITY != null && capability == GLIDER_CAPABILITY ? (T)this : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        //save the boolean
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean(CAP_PLAYER_GLIDING, isPlayerGliding);
        compound.setBoolean(CAP_GLIDER_DEPLOYED, isGliderDeployed);

        //return compound
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {

        //load and set
        setIsPlayerGliding(compound.getBoolean(CAP_PLAYER_GLIDING));
        setIsGliderDeployed(compound.getBoolean(CAP_GLIDER_DEPLOYED));

    }

    // Not sure what this does honestly
    public static class Storage implements Capability.IStorage<DefaultGliderCapabilityHandler> {

        @Override
        public NBTBase writeNBT(Capability<DefaultGliderCapabilityHandler> capability, DefaultGliderCapabilityHandler instance, EnumFacing side) {
            return null; //unused?
        }

        @Override
        public void readNBT(Capability<DefaultGliderCapabilityHandler> capability, DefaultGliderCapabilityHandler instance, EnumFacing side, NBTBase nbt) {
            //empty
        }

    }

    // Empty factory, just implemented here for ease of future expansion
    public static class Factory implements Callable<DefaultGliderCapabilityHandler> {
        @Override
        public DefaultGliderCapabilityHandler call() throws Exception {
            return null;
        }
    }

    //============================================Miscellaneous==================================

    //Register the capability
    public static void register() {
        CapabilityManager.INSTANCE.register(DefaultGliderCapabilityHandler.class, new DefaultGliderCapabilityHandler.Storage(), new DefaultGliderCapabilityHandler.Factory());
    }
}