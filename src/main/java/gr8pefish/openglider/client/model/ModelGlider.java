package gr8pefish.openglider.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ModelGlider extends ModelBase {

    private ArrayList<ModelRenderer> parts;

    private static final float QUAD_HALF_SIZE = 2.4f;
    private static final float ONGROUND_ROTATION = 90f;

    public ModelGlider() {
        //empty, all calls are in render
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        GlStateManager.disableRescaleNormal();
        GlStateManager.disableCull();

        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(QUAD_HALF_SIZE, 0, QUAD_HALF_SIZE);

        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-QUAD_HALF_SIZE, 0, QUAD_HALF_SIZE);

        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-QUAD_HALF_SIZE, 0, -QUAD_HALF_SIZE);

        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(QUAD_HALF_SIZE, 0, -QUAD_HALF_SIZE);

        GL11.glEnd();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    /**
     * Set all the details of the backpack to render.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn){

        EntityPlayer player = (EntityPlayer) entityIn;
        final float rotation = interpolateRotation(player.prevRotationYaw, player.rotationYaw);

        final Minecraft minecraft = Minecraft.getMinecraft();
        final boolean isLocalPlayer = player == minecraft.thePlayer;
        final boolean isFpp = minecraft.gameSettings.thirdPersonView == 0;

        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);

//        if (isLocalPlayer) {
        // move up and closer to back
        GlStateManager.translate(0, -0.2, +0.3);
        if (isFpp) { //ToDo: something is wrong here
            // move over head when flying in FPP
            GlStateManager.translate(0, +0.2, 0);
        } else {
            // move closer to back and forward when flying in TDD
            GlStateManager.translate(0, -0.8, -1.0);
        }

        player.limbSwing = 0f;
        player.prevLimbSwingAmount = 0f;
        player.limbSwingAmount = 0f;


//        } else {
//            // move closer to back and forward when flying
//            GlStateManager.translate(0, -0.5, -1.0);
//        }

    }

    /**
     * Get the correct rotation of the player, so that the backpack can be parallel to the player's back.
     * @param prevRotation - initial rotation (yaw)
     * @param nextRotation - next rotation (yaw)
     * @return - angle to rotate the backpack to
     */
    private static float interpolateRotation(float prevRotation, float nextRotation) {
        float rotation = nextRotation - prevRotation;
        return prevRotation * rotation;
    }
}
