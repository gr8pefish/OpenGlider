package gr8pefish.openglider.common.registry;

import gr8pefish.openglider.common.item.ItemHangGliderAdvanced;
import gr8pefish.openglider.common.item.ItemHangGliderBasic;
import gr8pefish.openglider.common.item.ItemHangGliderPart;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gr8pefish.openglider.api.OpenGliderInfo.MODID;

//import gr8pefish.openglider.common.recipe.AddUpgradeToGliderRecipe;
//import gr8pefish.openglider.common.recipe.RemoveUpgradeFromGliderRecipe;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(MODID)
public class ItemRegistry {

    // Items
    @GameRegistry.ObjectHolder(ModInfo.ITEM_GLIDER_PART_NAME)
    public static ItemHangGliderPart GLIDER_PART = null;
    @GameRegistry.ObjectHolder(ModInfo.ITEM_GLIDER_BASIC_NAME)
    public static ItemHangGliderBasic GLIDER_BASIC = null;
    @GameRegistry.ObjectHolder(ModInfo.ITEM_GLIDER_ADVANCED_NAME)
    public static ItemHangGliderAdvanced GLIDER_ADV = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        GLIDER_PART = (ItemHangGliderPart) new ItemHangGliderPart().setRegistryName(ModInfo.ITEM_GLIDER_PART_NAME);
        event.getRegistry().register(GLIDER_PART);
        event.getRegistry().register(new ItemHangGliderBasic().setRegistryName(ModInfo.ITEM_GLIDER_BASIC_NAME));
        event.getRegistry().register(new ItemHangGliderAdvanced().setRegistryName(ModInfo.ITEM_GLIDER_ADVANCED_NAME));
    }

    //Models
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        itemRender(GLIDER_BASIC, 0, ModInfo.ITEM_GLIDER_BASIC_NAME);
        itemRender(GLIDER_ADV, 0, ModInfo.ITEM_GLIDER_ADVANCED_NAME);
        itemRender(GLIDER_PART, 0, ItemHangGliderPart.names[0]);
        itemRender(GLIDER_PART, 1, ItemHangGliderPart.names[1]);
        itemRender(GLIDER_PART, 2, ItemHangGliderPart.names[2]);
    }

    private static void itemRender(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ModInfo.DOMAIN + name, "inventory"));
    }

    public static void registerRecipes() {

        //ToDo: 1.12 Recipe registry
        //Left wing
//        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 0),
//                " sl",
//                "sll",
//                "lll",
//                's', "stickWood", 'l', "leather").setMirrored(false));
//
//        //Right wing
//        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 1),
//                "ls ",
//                "lls",
//                "lll",
//                's', "stickWood", 'l', "leather").setMirrored(false));
//
//        //Scaffolding
//        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 2),
//                " i ",
//                "i i",
//                "iii",
//                'i', "ingotIron"));
//
//        //Glider Basic
//        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderBasic),
//                "   ",
//                "lsr",
//                "   ",
//                'l', new ItemStack(ItemRegistry.gliderPart, 1, 0), 's', new ItemStack(ItemRegistry.gliderPart, 1, 2),
//                'r', new ItemStack(ItemRegistry.gliderPart, 1, 1)).setMirrored(false));
//
//        //Glider Adv
//        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderAdv),
//                " e ",
//                "lbr",
//                " e ",
//                'l', new ItemStack(ItemRegistry.gliderPart, 1, 0), 'b', new ItemStack(ItemRegistry.gliderBasic, 1),
//                'r', new ItemStack(ItemRegistry.gliderPart, 1, 1), 'e', new ItemStack(Items.ELYTRA)).setMirrored(false));
//
//        //Upgrades
//        for (ItemStack upgrade : UpgradeItems.getPossibleUpgradeList()) {
//
//            //basic
//            GameRegistry.addRecipe(new AddUpgradeToGliderRecipe(new ItemStack(ItemRegistry.gliderBasic), new ItemStack(ItemRegistry.gliderBasic), upgrade));
//            GameRegistry.addRecipe(new RemoveUpgradeFromGliderRecipe(new ItemStack(ItemRegistry.gliderBasic), new ItemStack(ItemRegistry.gliderBasic)));
//
//            //advanced
//            GameRegistry.addRecipe(new AddUpgradeToGliderRecipe(new ItemStack(ItemRegistry.gliderAdv), new ItemStack(ItemRegistry.gliderAdv), upgrade));
//            GameRegistry.addRecipe(new RemoveUpgradeFromGliderRecipe(new ItemStack(ItemRegistry.gliderAdv), new ItemStack(ItemRegistry.gliderAdv)));
//        }

//        RecipeSorter.register("GliderAddUpgrade", AddUpgradeToGliderRecipe.class, RecipeSorter.Category.SHAPELESS, "");
//        RecipeSorter.register("GliderRemoveUpgrade", RemoveUpgradeFromGliderRecipe.class, RecipeSorter.Category.SHAPED, "");

    }

}

