package gr8pefish.openglider.client.event;

import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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

            float partialTicks = evt.getPartialRenderTick();
            //ToDo: Can't set statically, have to do it depending on player's view and stuffs


            float yawDeg = MathHelper.wrapDegrees(player.rotationYaw);
            float absYawDeg = MathHelper.abs(yawDeg);

//            System.out.println(yawDeg);

            float changedyaw = absYawDeg / (float)Math.PI;
//            System.out.println(changedyaw);

            if (-45 < (90 - MathHelper.abs(yawDeg)) && (90 - MathHelper.abs(yawDeg)) < 90) {
//                System.out.println("1");
                GlStateManager.rotate(90F, 10F, 0.0F, yawDeg); //ToDo: 10 needs to be some variable value
            } else {
//                System.out.println("2");
                GlStateManager.rotate(90F, -1 * absYawDeg, 0.0F, 0F);
            }


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
