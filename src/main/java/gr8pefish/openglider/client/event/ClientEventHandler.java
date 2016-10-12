package gr8pefish.openglider.client.event;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

//    @SubscribeEvent
//    public void onRenderTickStart(TickEvent.RenderTickEvent evt) {
//        if (evt.phase == TickEvent.Phase.START && Minecraft.getMinecraft().theWorld != null) {
//            preRenderTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld, evt.renderTickTime);
//        }
//    }
//
//    public void preRenderTick(Minecraft mc, World world, float renderTick) {
////        EntityHangGlider.updateGliders(world);
//    }

    //ToDo: Layers for client with the item

    @SubscribeEvent
    public void onPlayerBodyRender(RenderPlayerEvent.Pre evt) {
        final EntityPlayer player = evt.getEntityPlayer();
        if (OpenGliderCapabilities.getIsGliding(player)) {
//            player.limbSwing = 0f;
//            player.prevLimbSwingAmount = 0f;
//            player.limbSwingAmount = 0f;
//            player.rotationPitch = 5;
//            GlStateManager.rotate(75, -1, 0, 0);
        }
    }

}
