package gr8pefish.openglider.client.renderer;

import gr8pefish.openglider.client.model.ModelGlider;
import gr8pefish.openglider.common.capabilities.OpenGliderCapabilities;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;

public class LayerGlider implements LayerRenderer<AbstractClientPlayer> {

    /** Instance of the player renderer. */
    private final RenderPlayer playerRenderer;
    /** The model used by the glider. */
    private final ModelGlider modelGlider = new ModelGlider();

    public LayerGlider(RenderPlayer playerRendererIn) {
        this.playerRenderer = playerRendererIn;
    }

    public void doRenderLayer(@Nonnull AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (!entitylivingbaseIn.isInvisible()) { //if not invisible and should render
            //ToDo: config option to disable rendering?

            boolean gliding = OpenGliderCapabilities.getIsGliderDeployed(entitylivingbaseIn); //get if gliding (to render or not)
            if (gliding) { //if there is one

                //bind texture of the current backpack
                this.playerRenderer.bindTexture(ModInfo.MODEL_GLIDER_TEXTURE_RL);

                //push matrix
                GlStateManager.pushMatrix();

                //set rotation angles of the backpack and render it
                this.modelGlider.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
                this.modelGlider.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

                //special glint for me xD
                if (entitylivingbaseIn.getName().equals("gr8pefish")) {
//                    LayerArmorBase.renderEnchantedGlint(this.playerRenderer, entitylivingbaseIn, this.modelGlider, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }

                //pop matrix
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public static void addLayer(){
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        try {
            RenderPlayer renderPlayer = ObfuscationReflectionHelper.getPrivateValue(RenderManager.class, renderManager, "playerRenderer", "field_178637_m");
            renderPlayer.addLayer(new LayerGlider(renderPlayer));
            System.out.println("added layer");
        } catch (Exception e) {
            // failed to add layer
            //ToDo: Add logger class
        }
    }
}
