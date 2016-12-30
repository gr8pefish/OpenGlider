package gr8pefish.openglider.common.config;

import gr8pefish.openglider.common.lib.ModInfo;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {

    public static Configuration config;
    public static List<String> categories = new ArrayList<String>();

    public static float forwardMovement;
    public static float verticalMovement;
    public static float forwardMovementShift;
    public static float verticalMovementShift;

    public static boolean noNerfs;

    public static boolean durabilityEnabled;
    public static int durabilityTotal;
    public static int durabilityPerUse;
    public static boolean incaccuracyEnabled;
    public static float inaccuracyMultiplier;

    public static boolean enableRendering;

    @SubscribeEvent
    public void configChanged(ConfigChangedEvent event) {
        if (event.getModID().equals(ModInfo.MODID)) {
            syncConfig();
        }
    }

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
        categories.clear();

        String category;

        category = "1) Basic Balancing";
        categories.add(category);
        forwardMovement = config.getFloat("Normal Forward Movement", category, 0.02F, 0, 100,"The amount of blocks to move forwards (per-tick) while gliding normally.");
        verticalMovement = config.getFloat("Normal Fall Distance", category, 0.5F, 0, 100,"The amount of blocks a player falls (per-tick) while gliding normally.");
        forwardMovementShift = config.getFloat("Fast Forward Movement", category, 0.09F, 0, 100,"The amount of blocks to move forwards (per-tick) while gliding fast (pressing 'Shift').");
        verticalMovementShift = config.getFloat("Fast Fall Distance", category, 0.7F, 0, 100,"The amount of blocks to fall (per-tick) while gliding fast (pressing 'Shift').");

        category = "2) Advanced Balancing";
        categories.add(category);
        noNerfs = config.getBoolean("No Nerfs", category, false, "Enables the old-style glider (as of MC 1.7), with no durability usage, and perfect accuracy. Enabling this inherently ignores the other config options in this category.");
        durabilityEnabled = config.getBoolean("Enable Durability", category, true, "Enables durability usage of the hang glider when gliding.");
        durabilityTotal = config.getInt("Total Durability", category, 100, 1, 10000, "The maximum durabilty of an undamaged/unused hang glider.");
        durabilityPerUse = config.getInt("Durability Per-Use", category, 1, 0, 10000, "The maximum durabilty used up each time the hang glider is used.");
        incaccuracyEnabled = config.getBoolean("Enable Inaccuracy", category, true, "Enables the glider not going exactly where you point it.");
        inaccuracyMultiplier = config.getFloat("Inaccuracy Multiplier", category, 0.05F, 0, 1, "The percentage of inaccuracy the hang glider has when directing its movement. Setting to 0 means perfect accuracy.");

        category = "3) Client Options";
        categories.add(category);
        enableRendering = config.getBoolean("Enable Rendering", category, true, "Enables rendering of the hang glider on the player");

        if (config.hasChanged())
            config.save();
    }

}
