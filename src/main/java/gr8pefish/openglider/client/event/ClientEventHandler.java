package gr8pefish.openglider.client.event;

import gr8pefish.openglider.client.model.ModelGlider;
import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.helper.OpenGliderPlayerHelper;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
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
                rotateToHorizontal(event.getEntityPlayer(), event.getX(), event.getY(), event.getZ());
                this.needToPop = true;
            }
        }
    }

    private void rotateToHorizontal(EntityPlayer playerEntity, double x, double y, double z){
        float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        //            double interpolatedPitch = (playerEntity.prevRotationPitch + (playerEntity.rotationPitch - playerEntity.prevRotationPitch) * partialTicks);
        double interpolatedYaw = (playerEntity.prevRotationYaw + (playerEntity.rotationYaw - playerEntity.prevRotationYaw) * partialTicks);

        //ToDo Ask about entityPlayerMP and related stuffs

        GlStateManager.pushMatrix();
        //7. Set position back to normal
        GlStateManager.translate(x, y, z);
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
        GlStateManager.translate(-x, -y, -z);

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
//                        renderTriangle(event);
                    }
                }
            }
        }
    }

    private void renderTriangle(RenderGameOverlayEvent event){
        int centeredScreenStartWidth = event.getResolution().getScaledWidth() / 2 - 128; //-x | x=2nd to last param of draw rectangle method/2
        int screenStartHeight = 0; //top of screen
        ResourceLocation rl = new ResourceLocation("openglider", "textures/hang_glider_overlay.png"); //seems to accept the texture, but wrong size?
        Minecraft.getMinecraft().getTextureManager().bindTexture(rl); //not working?
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.rotate(70, 1, 0, 0);
        this.drawTexturedModalRect(centeredScreenStartWidth, screenStartHeight, 0, 90, 256, 166); //testing
        GlStateManager.popMatrix();

    }

    @SubscribeEvent
    public void onRenderOverlay(RenderWorldLastEvent event){
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {//first person perspective
            EntityPlayer playerEntity = Minecraft.getMinecraft().thePlayer;
            if (OpenGliderCapabilities.getIsGliderDeployed(playerEntity)) {
                if (OpenGliderPlayerHelper.shouldBeGliding(playerEntity)) {
                    renderTriangle(event);
                }
            }
        }
    }

    private final ModelGlider modelGlider = new ModelGlider();

    private void renderTriangle(RenderWorldLastEvent event){

        EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;

        GlStateManager.pushMatrix(); //push matrix
        Minecraft.getMinecraft().getTextureManager().bindTexture(ModInfo.MODEL_GLIDER_TEXTURE_RL); //bind texture
        modelGlider.setRotationAngles(entityPlayer.limbSwing, entityPlayer.limbSwingAmount, entityPlayer.getAge(), entityPlayer.rotationYawHead, entityPlayer.rotationPitch, 1, entityPlayer); //update rotation (Not working)
        modelGlider.render(entityPlayer, entityPlayer.limbSwing, entityPlayer.limbSwingAmount, entityPlayer.getAge(), entityPlayer.rotationYawHead, entityPlayer.rotationPitch, 1); //render
        GlStateManager.popMatrix(); //pop matrix

        //doesn't rotate correctly
        rotateToHorizontal(entityPlayer, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
        GlStateManager.popMatrix();


    }



}
