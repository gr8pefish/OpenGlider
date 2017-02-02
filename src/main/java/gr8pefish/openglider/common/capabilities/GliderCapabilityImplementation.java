package gr8pefish.openglider.common.capabilities;

import gr8pefish.openglider.api.OpenGliderInfo;
import gr8pefish.openglider.api.capabilities.IGliderCapabilityHandler;
import gr8pefish.openglider.api.item.IGlider;
import gr8pefish.openglider.common.helper.Logger;
import gr8pefish.openglider.common.network.PacketHandler;
import gr8pefish.openglider.common.network.PacketSyncGliderDataToClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import static gr8pefish.openglider.api.OpenGliderInfo.MODID;
import static gr8pefish.openglider.api.capabilities.CapabilityHelper.GLIDER_CAPABILITY;

public final class GliderCapabilityImplementation {

    public static void init(){
        CapabilityManager.INSTANCE.register(IGliderCapabilityHandler.class, new Capability.IStorage<IGliderCapabilityHandler>() {

            @Override
            public NBTBase writeNBT(Capability<IGliderCapabilityHandler> capability, IGliderCapabilityHandler instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IGliderCapabilityHandler> capability, IGliderCapabilityHandler instance, EnumFacing side, NBTBase nbt) {
                if (nbt instanceof NBTTagCompound)
                    instance.deserializeNBT(((NBTTagCompound) nbt));
            }
        }, DefaultGliderCapImplementation.class);
    }

    public static class DefaultGliderCapImplementation implements IGliderCapabilityHandler {

        private boolean isPlayerGliding;
        private boolean isGliderDeployed;

        public DefaultGliderCapImplementation() {
            this.isPlayerGliding = false;
            this.isGliderDeployed = false;
        }

        //Glider data

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
            if (isPlayerGliding && isDeployed)
                Logger.error("Player is already flying, deploying now is not needed.");
            else
                isGliderDeployed = isDeployed;
            if (!isDeployed) isPlayerGliding = false; //if not deployed, cannot be flying either
        }

        //Serializing and Deserializing NBT

        private static final String CAP_PLAYER_GLIDING = MODID+".isPlayerGliding";
        private static final String CAP_GLIDER_DEPLOYED = MODID+".isGliderDeployed";
        private static final String CAP_GLIDER_USED = MODID+".gliderUsed";

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

        //Sync

        @Override
        public void sync(EntityPlayerMP player) {
            PacketHandler.HANDLER.sendTo(new PacketSyncGliderDataToClient(serializeNBT()), player);
        }

    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

        public static final ResourceLocation NAME = new ResourceLocation(OpenGliderInfo.MODID, "cap");

        private final IGliderCapabilityHandler capabilityImplementation = new DefaultGliderCapImplementation();

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return capability == GLIDER_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if (capability == GLIDER_CAPABILITY) {
                return GLIDER_CAPABILITY.cast(capabilityImplementation);
            }
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return capabilityImplementation.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            capabilityImplementation.deserializeNBT(nbt);
        }
    }

    //make it un-constructable, as it is just a container for the other nested classes and static methods
    private GliderCapabilityImplementation() {}

}
