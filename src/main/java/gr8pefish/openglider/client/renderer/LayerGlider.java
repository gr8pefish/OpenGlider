package gr8pefish.openglider.client.renderer;

import gr8pefish.openglider.api.item.IGlider;
import gr8pefish.openglider.api.helper.GliderHelper;
import gr8pefish.openglider.client.model.ModelGlider;
import gr8pefish.openglider.common.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import java.util.Map;
import javax.annotation.Nonnull;

public class LayerGlider implements LayerRenderer<AbstractClientPlayer> {

    /** Instance of the player renderer. */
    private final RenderPlayer playerRenderer;
    /** The model used by the gliderBasic. */
    private final ModelGlider modelGlider = new ModelGlider();
//    private final ModelBars modelBars = new ModelBars();

    public LayerGlider(RenderPlayer playerRendererIn) {
        this.playerRenderer = playerRendererIn;
    }

    public void doRenderLayer(@Nonnull AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (!entitylivingbaseIn.isInvisible() && ConfigHandler.enableRendering3PP) { //if not invisible and should render

            boolean gliding = GliderHelper.getIsGliderDeployed(entitylivingbaseIn); //get if gliding (to render or not)
            if (gliding) { //if there is one

                //bind texture of the current glider
                ItemStack gliderStack = GliderHelper.getGlider(entitylivingbaseIn);
                this.playerRenderer.bindTexture(((IGlider)gliderStack.getItem()).getModelTexture(gliderStack));

                //push matrix
                GlStateManager.pushMatrix();

                //set rotation angles of the glider and render it
                this.modelGlider.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
                this.modelGlider.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

                //render bars
//                this.playerRenderer.bindTexture(ModelBars.MODEL_GLIDER_BARS_RL);
//                this.modelBars.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

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
        	Map<String, RenderPlayer> skinMap = ObfuscationReflectionHelper.getPrivateValue(RenderManager.class, renderManager, "skinMap", "field_178636_l");
        	skinMap.forEach((key, value) -> {
                value.addLayer(new LayerGlider(value));
            });
            //RenderPlayer renderPlayer = ObfuscationReflectionHelper.getPrivateValue(RenderManager.class, renderManager, "playerRenderer", "field_178637_m");
            //renderPlayer.addLayer(new LayerGlider(renderPlayer));
            System.out.println("added layer");
        } catch (Exception e) {
            System.out.println("error?");
            System.out.println(e);
        }
    }
}
