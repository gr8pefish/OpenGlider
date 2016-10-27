package gr8pefish.openglider.common.event;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.capabilities.PlayerGlidingCapability;
import gr8pefish.openglider.common.helper.OpenGliderPlayerHelper;
import gr8pefish.openglider.common.lib.ModInfo;
import gr8pefish.openglider.common.network.PacketClientGliding;
import gr8pefish.openglider.common.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ServerEventHandler {

    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            if (!event.getObject().hasCapability(OpenGliderCapabilities.GLIDING_CAPABILITY, null)) {
                event.addCapability(new ResourceLocation(ModInfo.MODID + "." + ModInfo.GLIDING_CAPABILITY_STRING), new PlayerGlidingCapability());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloning(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        if (!event.isWasDeath()) { //return from end (deal with dumb returning from the end code)
            if (event.getOriginal().hasCapability(OpenGliderCapabilities.GLIDING_CAPABILITY, null)) {
                PlayerGlidingCapability oldCap = event.getOriginal().getCapability(OpenGliderCapabilities.GLIDING_CAPABILITY, null);
                PlayerGlidingCapability newCap = event.getEntityPlayer().getCapability(OpenGliderCapabilities.GLIDING_CAPABILITY, null);

                //update new data with old
                newCap.setIsGliding(oldCap.getIsGliding());
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event){
        if (OpenGliderCapabilities.getIsGliding(event.player)){
            OpenGliderPlayerHelper.updatePosition(event.player);
        }
    }

    @SubscribeEvent
    public void onTrack(net.minecraftforge.event.entity.player.PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof EntityPlayer) {
            EntityPlayer tracked = (EntityPlayer) event.getTarget();
            if (OpenGliderCapabilities.getIsGliding(tracked)) {
                PacketHandler.HANDLER.sendTo(new PacketClientGliding(PacketClientGliding.IS_GLIDING), (EntityPlayerMP) event.getEntityPlayer());
            }
        }
    }
}
