package gr8pefish.openglider.common.capabilities;

import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.concurrent.Callable;

public class PlayerGlidingCapability implements ICapabilitySerializable<NBTTagCompound> {

    public static final String CAP_GLIDING = ModInfo.MODID;

    private boolean isGliding;

    public PlayerGlidingCapability() {
        this.isGliding = false;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return OpenGliderCapabilities.GLIDING_CAPABILITY != null && capability == OpenGliderCapabilities.GLIDING_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return OpenGliderCapabilities.GLIDING_CAPABILITY != null && capability == OpenGliderCapabilities.GLIDING_CAPABILITY ? (T)this : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {

        //save the boolean
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean(CAP_GLIDING, isGliding);

        //return compound
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound compound) {

        //load and set
        setIsGliding(compound.getBoolean(CAP_GLIDING));

    }

    // Not sure what this does honestly
    public static class Storage implements Capability.IStorage<PlayerGlidingCapability> {

        @Override
        public NBTBase writeNBT(Capability<PlayerGlidingCapability> capability, PlayerGlidingCapability instance, EnumFacing side) {
            return null; //unused?
        }

        @Override
        public void readNBT(Capability<PlayerGlidingCapability> capability, PlayerGlidingCapability instance, EnumFacing side, NBTBase nbt) {
            //empty
        }

    }

    // Empty factory, just implemented here for ease of future expansion
    public static class Factory implements Callable<PlayerGlidingCapability> {
        @Override
        public PlayerGlidingCapability call() throws Exception {
            return null;
        }
    }


    //Getters and setters

    public boolean getIsGliding() {
        return isGliding;
    }

    public void setIsGliding(boolean gliding) {
        this.isGliding = gliding;
    }

    //Other helper methods

    public static void register() {
        CapabilityManager.INSTANCE.register(PlayerGlidingCapability.class, new PlayerGlidingCapability.Storage(), new PlayerGlidingCapability.Factory());
    }
}