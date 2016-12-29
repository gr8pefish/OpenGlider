package gr8pefish.openglider.common.registry;

import gr8pefish.openglider.common.item.ItemHangGlider;
import gr8pefish.openglider.common.item.ItemHangGliderPart;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemRegistry {

    public static ItemHangGlider glider;
    public static ItemHangGliderPart gliderPart;

    public static void registerItems(){
        glider = (ItemHangGlider)registerItem(new ItemHangGlider(), ModInfo.ITEM_GLIDER_NAME);
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

        //Glider
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.glider, 1),
                "   ",
                "lsr",
                "   ",
                'l', new ItemStack(ItemRegistry.gliderPart, 1, 0), 's', new ItemStack(ItemRegistry.gliderPart, 1, 2),
                'r', new ItemStack(ItemRegistry.gliderPart, 1, 1)).setMirrored(false));
    }

    public static void registerRenders(){
        itemRender(glider, 0, ModInfo.ITEM_GLIDER_NAME);
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
