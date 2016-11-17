package gr8pefish.openglider.client.event;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.helper.OpenGliderPlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler extends Gui {

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
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {//first person perspective
            if (event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS) {
                EntityPlayer playerEntity = Minecraft.getMinecraft().thePlayer;
                if (OpenGliderCapabilities.getIsGliderDeployed(playerEntity)) {
                    if (OpenGliderPlayerHelper.shouldBeGliding(playerEntity)) {
                        renderTriangle(event);
                    }
                }
            }
        }
    }

    private void renderTriangle(RenderGameOverlayEvent event){
        int centeredScreenStartWidth = event.getResolution().getScaledWidth() / 2 - 64; //-x | x=2nd to last param of draw rectangle method/2
        int screenStartHeight = 0; //top of screen
        ResourceLocation rl = new ResourceLocation("openglider", "textures/hang_glider_overlay.png"); //seems to accept the texture, but wrong size?
        Minecraft.getMinecraft().getTextureManager().bindTexture(rl); //not working?
        TextureAtlasSprite textureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(rl);
        textureAtlasSprite.setIconWidth(128); //testing
        textureAtlasSprite.setIconHeight(64); //testing
        System.out.println(textureAtlasSprite.toString()); //TextureAtlasSprite{name='openglider:textures/hang_glider_overlay.png', frameCount=0, rotated=false, x=0, y=0, height=64, width=128, u0=0.0, u1=0.0, v0=0.0, v1=0.0}
        this.drawTexturedModalRect(centeredScreenStartWidth, screenStartHeight, textureAtlasSprite, 128, 64); //testing

    }

}
