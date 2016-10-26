package gr8pefish.openglider.client.event;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    private boolean needToPop = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRender(RenderLivingEvent.Pre<EntityPlayer> event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (OpenGliderCapabilities.getIsGliding((EntityPlayer) event.getEntity())) {
                System.out.println("gliding");
                EntityPlayer playerEntity = (EntityPlayer) event.getEntity();
                float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
                //            double interpolatedPitch = (playerEntity.prevRotationPitch + (playerEntity.rotationPitch - playerEntity.prevRotationPitch) * partialTicks);
                double interpolatedYaw = (playerEntity.prevRotationYaw + (playerEntity.rotationYaw - playerEntity.prevRotationYaw) * partialTicks);

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
    public void onRender(RenderLivingEvent.Post<EntityPlayer> event) {
        if (this.needToPop) {
            this.needToPop = false;
            GlStateManager.popMatrix();
        }
    }



//    @SubscribeEvent
//    public void onPlayerBodyRenderPre(RenderPlayerEvent.Pre evt) {
//        EntityPlayer player = evt.getEntityPlayer();
//        if (OpenGliderCapabilities.getIsGliding(player)) {
//            GlStateManager.pushMatrix();
//
//            float partialTicks = evt.getPartialRenderTick();
//            //ToDo: Can't set statically, have to do it depending on player's view and stuffs
//
//            //ToDo: Only does it in third person, have to add it as 1st person too
//                //OpenBlocks does it via ASM and a hook into rotateCorpse? Ask in #mcf
//
//            //Check it out: https://github.com/Mysteryem/Up_And_Down_And_All_Around/blob/master/src/main/java/uk/co/mysterymayhem/gravitymod/PlayerRenderListener.java
//
//
//            float yawDeg = MathHelper.wrapDegrees(player.rotationYaw);
//            float absYawDeg = MathHelper.abs(yawDeg);
//
////            System.out.println(yawDeg);
//
//            float changedyaw = absYawDeg / (float)Math.PI;
////            System.out.println(changedyaw);
//
//            if (-45 < (90 - MathHelper.abs(yawDeg)) && (90 - MathHelper.abs(yawDeg)) < 90) {
////                System.out.println("1");
//                GlStateManager.rotate(90F, 10F, 0.0F, yawDeg); //ToDo: 10 needs to be some variable value
//            } else {
////                System.out.println("2");
//                GlStateManager.rotate(90F, -1 * absYawDeg, 0.0F, 0F);
//            }
//
//
//        }
//    }
//
//    @SubscribeEvent
//    public void onPlayerBodyRenderPost(RenderPlayerEvent.Post evt) {
//        EntityPlayer player = evt.getEntityPlayer();
//        if (OpenGliderCapabilities.getIsGliding(player)) {
//            GlStateManager.popMatrix();
//        }
//    }

}
