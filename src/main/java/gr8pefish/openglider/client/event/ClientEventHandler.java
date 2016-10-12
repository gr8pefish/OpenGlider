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


    @SubscribeEvent
    public void onPlayerBodyRenderPre(RenderPlayerEvent.Pre evt) {
        EntityPlayer player = evt.getEntityPlayer();
        if (OpenGliderCapabilities.getIsGliding(player)) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90F, -1F, 0F, 1F);
            //ToDo: Can't set statically, have to do it depending on player's view and stuffs

        }
    }

    @SubscribeEvent
    public void onPlayerBodyRenderPost(RenderPlayerEvent.Post evt) {
        EntityPlayer player = evt.getEntityPlayer();
        if (OpenGliderCapabilities.getIsGliding(player)) {
            GlStateManager.popMatrix();
        }
    }

}
