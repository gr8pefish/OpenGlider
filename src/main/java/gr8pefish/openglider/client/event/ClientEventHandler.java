package gr8pefish.openglider.client.event;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.helper.OpenGliderPlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    private boolean needToPop = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRender(RenderPlayerEvent.Pre event) {

        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer playerEntity = (EntityPlayer) event.getEntity();
            if (OpenGliderCapabilities.getIsGliderDeployed((EntityPlayer) event.getEntity())) {
                if (!OpenGliderPlayerHelper.shouldBeGliding(playerEntity)) return;

                float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
                //            double interpolatedPitch = (playerEntity.prevRotationPitch + (playerEntity.rotationPitch - playerEntity.prevRotationPitch) * partialTicks);
                double interpolatedYaw = (playerEntity.prevRotationYaw + (playerEntity.rotationYaw - playerEntity.prevRotationYaw) * partialTicks);

                //ToDo Ask about entityPlayerMP and related stuffs

                GlStateManager.pushMatrix();
                //7. Set position back to normal
                GlStateManager.translate(event.getX(), event.getY(), event.getZ());
                //6. Redo yaw rotation
                GlStateManager.rotate((float) -interpolatedYaw, 0, 1, 0);
                //5. Move back to (0, 0, 0)
                GlStateManager.translate(0, playerEntity.height / 2f, 0);
                //4. Rotate about x, so player leans over forward (z direction is forwards)
                GlStateManager.rotate(90, 1, 0, 0);
                //3. So we rotate around the centre of the player instead of the bottom of the player
                GlStateManager.translate(0, -playerEntity.height / 2f, 0);
                //2. Undo yaw rotation (this will make the player face +z (south)
                GlStateManager.rotate((float) interpolatedYaw, 0, 1, 0);
                //1. Set position to (0, 0, 0)
                GlStateManager.translate(-event.getX(), -event.getY(), -event.getZ());
                this.needToPop = true;
                return;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public void onRender(RenderPlayerEvent.Post event) {
        if (this.needToPop) {
            this.needToPop = false;
            GlStateManager.popMatrix();
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Pre event){
        if (event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS) {
            EntityPlayer playerEntity = Minecraft.getMinecraft().thePlayer;
            if (OpenGliderCapabilities.getIsGliderDeployed(playerEntity)) {
                if (OpenGliderPlayerHelper.shouldBeGliding(playerEntity)) {
                    FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
                    GlStateManager.pushMatrix();
                    fontRenderer.drawString("CONGRATULATIONS, YOU ARE NOW GLIDING", 0, 0, 1); //ToDo: Nice image overlay in the right spot
                    GlStateManager.popMatrix();
                }
            }
        }
    }

}
