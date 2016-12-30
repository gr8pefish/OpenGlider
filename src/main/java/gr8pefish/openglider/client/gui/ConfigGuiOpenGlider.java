package gr8pefish.openglider.client.gui;

import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ConfigGuiOpenGlider extends GuiConfig {

    public ConfigGuiOpenGlider(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(parentScreen), ModInfo.MODID, false, false, ModInfo.MOD_NAME);
    }

    private static List<IConfigElement> getConfigElements(GuiScreen parentScreen) {
        List<IConfigElement> list = new ArrayList<>();

        for (String category : ConfigHandler.categories)
            list.add(new ConfigElement(ConfigHandler.config.getCategory(category)));

        return list;
    }
}
