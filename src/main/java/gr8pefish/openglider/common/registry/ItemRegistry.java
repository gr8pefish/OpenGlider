package gr8pefish.openglider.common.registry;

import gr8pefish.openglider.common.item.ItemHangGlider;
import gr8pefish.openglider.common.item.ItemHangGliderWing;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemRegistry {

    private static ItemHangGlider glider;
    private static ItemHangGliderWing wing;

    public static void registerItems(){
        glider = new ItemHangGlider();
        wing = new ItemHangGliderWing();
    }

    public static void registerRecipes() {
        recipeList.add(new ShapedOreRecipe(glider, "wsw", 'w', wing, 's', "stickWood"));
    }
}
