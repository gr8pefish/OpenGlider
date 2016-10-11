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

    private static ItemHangGlider glider;
    private static ItemHangGliderPart gliderPart;

    public static void registerItems(){
        glider = (ItemHangGlider) registerItem(new ItemHangGlider(), "hang_glider"); //ToDO: ask in IRC name enforcement for 1.11
        gliderPart = (ItemHangGliderPart) registerItem(new ItemHangGliderPart(), "hang_glider_part");
    }

    public static void registerRecipes() {

        //Left wing
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 0),
                " sl",
                "sll",
                "lll",
                's', "stickWood", 'l', "leather"));

        //Right wing
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 1),
                "ls ",
                "lls",
                "lll",
                's', "stickWood", 'l', "leather"));

        //Scaffolding
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.gliderPart, 1, 2),
                "iii",
                "isi",
                " i ",
                'i', "ingotIron", 'l', "string"));

        //Glider
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.glider, 1),
                "",
                "lsr",
                "",
                'l', new ItemStack(ItemRegistry.gliderPart, 1, 0), 's', new ItemStack(ItemRegistry.gliderPart, 1, 2),
                'r', new ItemStack(ItemRegistry.gliderPart, 1, 1)));
    }

    public static void registerRenders(){
        itemRender(glider, 0, "hang_glider");
        itemRender(gliderPart, 0, "wing_left");
        itemRender(gliderPart, 1, "wing_right");
        itemRender(gliderPart, 2, "scaffolding");
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
