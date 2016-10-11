package gr8pefish.openglider.common.registry;

import gr8pefish.openglider.common.item.ItemHangGlider;
import gr8pefish.openglider.common.item.ItemHangGliderPart;
import gr8pefish.openglider.common.item.ItemHangGliderWing;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemRegistry {

    private static ItemHangGlider glider;
    private static ItemHangGliderPart gliderPart;

    public static void registerItems(){
        glider = new ItemHangGlider();
        gliderPart = new ItemHangGliderPart();
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

}
