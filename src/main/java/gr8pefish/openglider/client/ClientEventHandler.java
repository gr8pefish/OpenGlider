package gr8pefish.openglider.client;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.entity.EntityHangGlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void onRenderTickStart(TickEvent.RenderTickEvent evt) {
        if (evt.phase == TickEvent.Phase.START && Minecraft.getMinecraft().theWorld != null) {
            preRenderTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld, evt.renderTickTime);
        }
    }

    public void preRenderTick(Minecraft mc, World world, float renderTick) {
        EntityHangGlider.updateGliders(world);
    }

    @SubscribeEvent
    public void onPlayerBodyRender(RenderPlayerEvent evt) {
        final EntityPlayer player = evt.getEntityPlayer();
        if (OpenGliderCapabilities.getIsGliding(player)) {
            player.limbSwing = 0f;
            player.prevLimbSwingAmount = 0f;
            player.limbSwingAmount = 0f;
            GlStateManager.rotate(75, -1, 0, 0);
        }
    }

}
