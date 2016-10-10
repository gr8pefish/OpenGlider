package gr8pefish.openglider.client;

import gr8pefish.openglider.common.entity.EntityHangGlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

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
    public void onPlayerBodyRender(PlayerBodyRenderEvent evt) {
        final AbstractClientPlayer player = evt.player;
        if (!EntityHangGlider.isGliderDeployed(player)) {
            player.limbSwing = 0f;
            player.prevLimbSwingAmount = 0f;
            player.limbSwingAmount = 0f;
            GL11.glRotatef(75, -1, 0, 0);
        }
    }

}
