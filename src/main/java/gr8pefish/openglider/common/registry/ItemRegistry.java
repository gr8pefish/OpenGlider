package gr8pefish.openglider.common.registry;

import gr8pefish.openglider.api.upgrade.UpgradeItems;
import gr8pefish.openglider.common.item.ItemHangGliderAdvanced;
import gr8pefish.openglider.common.item.ItemHangGliderBasic;
import gr8pefish.openglider.common.item.ItemHangGliderPart;
import gr8pefish.openglider.common.lib.ModInfo;
import gr8pefish.openglider.common.recipe.AddUpgradeToGliderRecipe;
import gr8pefish.openglider.common.recipe.RemoveUpgradeFromGliderRecipe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemRegistry {

    //ToDo: Some registry for tiers of gliders

    public static ItemHangGliderBasic gliderBasic;
    public static ItemHangGliderAdvanced gliderAdv;
    public static ItemHangGliderPart gliderPart;

    public static void registerItems(){
        gliderBasic = (ItemHangGliderBasic)registerItem(new ItemHangGliderBasic(), ModInfo.ITEM_GLIDER_BASIC_NAME);
        gliderAdv = (ItemHangGliderAdvanced)registerItem(new ItemHangGliderAdvanced(), ModInfo.ITEM_GLIDER_ADVANCED_NAME);
        gliderPart = (ItemHangGliderPart)registerItem(new ItemHangGliderPart(), ModInfo.ITEM_GLIDER_PART_NAME);
    }

    public static void registerRecipes() {

        //Left wing
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 0),
                " sl",
                "sll",
                "lll",
                's', "stickWood", 'l', "leather").setMirrored(false));

        //Right wing
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 1),
                "ls ",
                "lls",
                "lll",
                's', "stickWood", 'l', "leather").setMirrored(false));

        //Scaffolding
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 2),
                " i ",
                "i i",
                "iii",
                'i', "ingotIron"));

        //Glider Basic
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderBasic),
                "   ",
                "lsr",
                "   ",
                'l', new ItemStack(ItemRegistry.gliderPart, 1, 0), 's', new ItemStack(ItemRegistry.gliderPart, 1, 2),
                'r', new ItemStack(ItemRegistry.gliderPart, 1, 1)).setMirrored(false));

        //Glider Adv
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderAdv),
                " e ",
                "lbr",
                " e ",
                'l', new ItemStack(ItemRegistry.gliderPart, 1, 0), 'b', new ItemStack(ItemRegistry.gliderBasic, 1),
                'r', new ItemStack(ItemRegistry.gliderPart, 1, 1), 'e', new ItemStack(Items.ELYTRA)).setMirrored(false));

        //Upgrades
        for (ItemStack upgrade : UpgradeItems.getPossibleUpgradeList()) {

            //basic
            GameRegistry.addRecipe(new AddUpgradeToGliderRecipe(new ItemStack(ItemRegistry.gliderBasic), new ItemStack(ItemRegistry.gliderBasic), upgrade));
            GameRegistry.addRecipe(new RemoveUpgradeFromGliderRecipe(new ItemStack(ItemRegistry.gliderBasic), new ItemStack(ItemRegistry.gliderBasic)));

            //advanced
            GameRegistry.addRecipe(new AddUpgradeToGliderRecipe(new ItemStack(ItemRegistry.gliderAdv), new ItemStack(ItemRegistry.gliderAdv), upgrade));
            GameRegistry.addRecipe(new RemoveUpgradeFromGliderRecipe(new ItemStack(ItemRegistry.gliderAdv), new ItemStack(ItemRegistry.gliderAdv)));
        }

    }

    public static void registerRenders(){
        itemRender(gliderBasic, 0, ModInfo.ITEM_GLIDER_BASIC_NAME);
        itemRender(gliderAdv, 0, ModInfo.ITEM_GLIDER_ADVANCED_NAME);
        itemRender(gliderPart, 0, ItemHangGliderPart.names[0]);
        itemRender(gliderPart, 1, ItemHangGliderPart.names[1]);
        itemRender(gliderPart, 2, ItemHangGliderPart.names[2]);
    }

    //Helper methods for registration

    private static Item registerItem(Item item, String name) {
        item.setRegistryName(name);
        GameRegistry.register(item);
        return item;
    }

    private static void itemRender(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ModInfo.DOMAIN + name, "inventory"));
    }


}
