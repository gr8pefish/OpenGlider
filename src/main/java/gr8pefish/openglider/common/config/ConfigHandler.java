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
    public static List<String> categories = new ArrayList<>();

    public static boolean holdingGliderEnforced;

    public static float forwardMovement;
    public static float verticalMovement;
    public static float forwardMovementShift;
    public static float verticalMovementShift;

    public static boolean windEnabled;
    public static float windOverallPower;
    public static float windGustSize;
    public static float windFrequency;
    public static float windRainingMultiplier;
    public static float windSpeedMultiplier;
    public static float windHeightMultiplier;
    public static float windDurabilityMultiplier;

    public static boolean durabilityEnabled;
    public static int durabilityTotal;
    public static int durabilityPerUse;
    public static int durabilityTimeframe;

    public static boolean enableRendering;
    public static float gliderVisibilityFPPShiftAmount;

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

        category = "1) Balance/Miscellaneous";
        categories.add(category);
        forwardMovement = config.getFloat("1) Normal Forward Movement", category, 0.02F, 0, 100,"The amount of blocks to move forwards (per-tick) while gliding normally.");
        verticalMovement = config.getFloat("2) Normal Fall Distance", category, 0.55F, 0, 100,"The amount of blocks a player falls (per-tick) while gliding normally.");
        forwardMovementShift = config.getFloat("3) Fast Forward Movement", category, 0.08F, 0, 100,"The amount of blocks to move forwards (per-tick) while gliding fast (pressing 'Shift').");
        verticalMovementShift = config.getFloat("4) Fast Fall Distance", category, 0.8F, 0, 100,"The amount of blocks to fall (per-tick) while gliding fast (pressing 'Shift').");
        holdingGliderEnforced = config.getBoolean("5) Holding Glider Enforced", category, true, "Must be holding the hang glider and have it selected to fly.");

        category = "2) Wind";
        categories.add(category);
        windEnabled = config.getBoolean("1) Enable Wind", category, true, "Enables wind, making the player move unpredictably around when gliding.");
        windOverallPower = config.getFloat("2) Overall Power", category, 1.3F, 0.001F, 10, "A quality-of-life option to quickly change the overall power of the wind effect. Default is an overall relatively weak wind, with moderate gusts that occur semi-commonly. Note that this value can be a decimal (i.e. 0.5 would be half as strong). More fine-grained options are available below.");
        windGustSize = config.getFloat("3) Gust Size", category, 19, 1, 100, "The size of the wind gusts, larger values mean the gusts push the player around in greater angles from their intended direction. Default is moderately sized. Observable gameplay effects are highly tied with wind frequency.");
        windFrequency = config.getFloat("4) Wind Frequency", category, 0.15F, 0, 5, "The frequency of the wind gusts, larger values mean the wind effects occur more often. 0 removes wind. Default is semi-common. Observable gameplay effects are highly tied with gust size.");
        windRainingMultiplier = config.getFloat("5) Rain Multiplier", category, 5, 1, 10, "How much stronger the wind should be while it is raining. 1 means the wind is the same if raining or not, 10 means the wind is 10x stronger while it is raining.");
        windSpeedMultiplier = config.getFloat("6) Speed Multiplier", category, 0.4F, -10, 10, "When going fast, the overall wind effect is changed by this multiplier. Default is that going fast reduces the wind effect by a moderate amount. 0 means the player's speed has no effect on the wind.");
        windHeightMultiplier = config.getFloat("7) Height Multiplier", category, 1.8F, -10, 10, "The player's y-level/height changes the overall wind effect by this multiplier. Default is that the higher you are up in the world the stronger the wind is, but only by a moderate amount. 0 means the player's height has no effect on the wind.");
        windDurabilityMultiplier = config.getFloat("8) Durability Multiplier", category, 0.7F, 0, 5, "The glider's durability remaining changes the overall wind effect by this additional amount. 0 means the glider's durability won't effect the wind power, whereas 1 will mean a nearly broken glider is affected by wind about twice as much as a new one.");

        category = "3) Durability";
        categories.add(category);
        durabilityEnabled = config.getBoolean("Enable Durability", category, true, "Enables durability usage of the hang glider when gliding.");
        durabilityTotal = config.getInt("Total Durability", category, 206, 1, 10000, "The maximum durability of an unused hang glider.");
        durabilityPerUse = config.getInt("Durability Per-Use", category, 1, 0, 10000, "The durability used up each time.");
        durabilityTimeframe = config.getInt("Durability Timeframe", category, 100, 1, 10000, "The timeframe for durability usage, in ticks. Recall that there are 20 ticks in a second, so a value of 20 would damage the item about once a second. Default is 1 damage about every 5 seconds of flight, so with the default durability (206) means about 15 minutes of flight time with an undamaged glider.");

        category = "4) Visuals";
        categories.add(category);
        enableRendering = config.getBoolean("1) Enable Rendering", category, true, "Enables rendering of the hang glider on the player.");
        gliderVisibilityFPPShiftAmount = config.getFloat("2) First-Person Glider Visibility", category, 1.8F, 1, 4, "How high above the player's head the glider appears as in first person perspective while flying. Lower values will make it more visible/intrusive.");

        if (config.hasChanged())
            config.save();
    }

}
