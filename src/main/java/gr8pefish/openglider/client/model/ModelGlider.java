package gr8pefish.openglider.client.model;

import gr8pefish.openglider.common.helper.OpenGliderPlayerHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
     * Set all the details of the glider to render.
     */
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn){

        EntityPlayer player = (EntityPlayer) entityIn;

        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F); //set it to the back (no rotation)

        GlStateManager.rotate(ONGROUND_ROTATION, 1, 0, 0); //on same plane as player
        GlStateManager.rotate(180F, 0, 2, 0); //front facing
        if (player.isSneaking())
            GlStateManager.translate(0, -0.5, 0); //move to on the back (more away than fpp)
        else
            GlStateManager.translate(0, -0.35, 0); //move to on the back (quite close)

        if (!OpenGliderPlayerHelper.shouldBeGliding(player)) {
            GlStateManager.scale(0.9, 0.9, 0.8); //scale slightly smaller
            GlStateManager.translate(0, 0, -.5); // move up if on ground
        }

    }

}
