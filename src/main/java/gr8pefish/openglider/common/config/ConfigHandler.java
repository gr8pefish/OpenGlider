package gr8pefish.openglider.common.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static gr8pefish.openglider.api.OpenGliderInfo.MODID;

public class ConfigHandler {

    public static Configuration config;
    public static List<String> categories = new ArrayList<>();

    //Basic Glider
    public static float basicGliderFlightForward;
    public static float basicGliderFlightVertical;
    public static float basicGliderShiftInefficiencyPercentage;
    public static float basicGliderShiftFlightSpeedMultiplier;

    public static float basicGliderWindModifier;
    public static int basicGliderTotalDurability;

    //Advanced Glider
    public static float advancedGliderFlightAngle;
    public static float advancedGliderFlightSpeed;
    public static float advancedGliderFlightAngleShifting;
    public static float advancedGliderFlightSpeedShifting;

    public static float advancedGliderWindModifier;
    public static int advancedGliderTotalDurability;

    //Wind
    public static boolean windEnabled;
    public static float windOverallPower;
    public static float windGustSize;
    public static float windFrequency;
    public static float windRainingMultiplier;
    public static float windSpeedMultiplier;
    public static float windHeightMultiplier;
    public static float windDurabilityMultiplier;

    //Durability
    public static boolean durabilityEnabled;
    public static int durabilityPerUse;
    public static int durabilityTimeframe;

    //Client
    public static boolean enableRendering3PP;
    public static boolean enableRenderingFPP;
    public static float gliderVisibilityFPPShiftAmount;
    public static boolean disableOffhandRenderingWhenGliding;

    @SubscribeEvent
    public void configChanged(ConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
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

        category = "1) Basic Hang Glider Stats";
        categories.add(category);
        //ToDo: Angle and speed eventually, right now it's not right either even
//        basicGliderFlightForward = config.getFloat("1) Normal Forward Movement", category, 0.02F, 0, 100,"The angle of flight. A steeper angle will result in less overall distance traveled.");
//        basicGliderFlightVertical = config.getFloat("2) Normal Vertical Movement", category, 0.55F, 0, 100,"How fast the player travels when gliding.");
        basicGliderFlightForward = config.getFloat("1) Normal Forward Movement", category, 0.02F, 0, 100,"The amount of blocks to move forwards (per-tick) while gliding normally.");
        basicGliderFlightVertical = config.getFloat("2) Normal Vertical Movement", category, 0.55F, 0, 100,"The amount of blocks a player falls (per-tick) while gliding normally.");
        basicGliderShiftInefficiencyPercentage = config.getFloat("3) Fast Forward Inefficiency Percentage", category, 1.20F, 0, 10,"The efficiency of pressing flying fast. Note that it is 20% less blocks traveled overall to discourage just pressing shift all the time.");
        basicGliderShiftFlightSpeedMultiplier = config.getFloat("4) Fast Forward Speed Multiplier", category, 1.3F, 0, 100,"How much faster (multiplicative) it is to fly in fast mode.");
        basicGliderWindModifier = config.getFloat("5) Overall Wind Power", category, 1.3F, 0.001F, 10, "A quality-of-life option to quickly change the overall power of the wind effect for this glider. Default is an overall relatively weak wind, with moderate gusts that occur semi-commonly. Note that this value can be a decimal (i.e. 0.5 would be half as strong). More fine-grained options are available in the 'wind' section of this config.");
        basicGliderTotalDurability = config.getInt("6) Total Durability", category, 206, 1, 10000, "The maximum durability of an unused hang glider.");

        category = "2) Advanced Hang Glider Stats";
        categories.add(category);
        advancedGliderFlightAngle = config.getFloat("1) Normal Forward Angle", category, 0.02F, 0, 100,"The angle of flight. A steeper angle will result in less overall distance traveled.");
        advancedGliderFlightSpeed = config.getFloat("2) Normal Forward Speed", category, 0.55F, 0, 100,"How fast the player travels when gliding.");
        advancedGliderFlightAngleShifting = config.getFloat("3) Fast Forward Angle", category, 0.02F, 0, 100,"When pressing Shift/Speed-Up, the angle of flight. A steeper angle will result in less overall distance traveled. Note that the angle is higher here than slow flight to discourage just pressing shift all the time.");
        advancedGliderFlightSpeedShifting = config.getFloat("4) Fast Forward Speed", category, 0.55F, 0, 100,"When pressing Shift/Speed-Up, how fast the player travels when gliding.");
        advancedGliderWindModifier = config.getFloat("5) Overall Wind Power", category, 0.5F, 0.001F, 10, "A quality-of-life option to quickly change the overall power of the wind effect for this glider. Default is an overall quite weak wind, with mild gusts that occur semi-commonly. Note that this value can be a decimal (i.e. 0.5 would be half as strong). More fine-grained options are available in the 'wind' section of this config.");
        advancedGliderTotalDurability = config.getInt("6) Total Durability", category, 2602, 1, 100000, "The maximum durability of an unused advanced hang glider.");

        category = "3) Wind";
        categories.add(category);
        windEnabled = config.getBoolean("1) Enable Wind", category, true, "Enables wind, making the player move unpredictably around when gliding.");
        windOverallPower = config.getFloat("2) Overall Power", category, 1.0F, 0.001F, 10, "A quality-of-life option to quickly change the overall power of the wind effect for all gliders. Default is an overall relatively weak wind, with moderate gusts that occur semi-commonly. Note that this value can be a decimal (i.e. 0.5 would be half as strong). More fine-grained options are available below.");
        windGustSize = config.getFloat("3) Gust Size", category, 19, 1, 100, "The size of the wind gusts, larger values mean the gusts push the player around in greater angles from their intended direction. Default is moderately sized. Observable gameplay effects are highly tied with wind frequency.");
        windFrequency = config.getFloat("4) Wind Frequency", category, 0.15F, 0, 5, "The frequency of the wind gusts, larger values mean the wind effects occur more often. 0 removes wind. Default is semi-common. Observable gameplay effects are highly tied with gust size.");
        windRainingMultiplier = config.getFloat("5) Rain Multiplier", category, 5, 1, 10, "How much stronger the wind should be while it is raining. 1 means the wind is the same if raining or not, 10 means the wind is 10x stronger while it is raining.");
        windSpeedMultiplier = config.getFloat("6) Speed Multiplier", category, 0.4F, -10, 10, "When going fast, the overall wind effect is changed by this multiplier. Default is that going fast reduces the wind effect by a moderate amount. 0 means the player's speed has no effect on the wind.");
        windHeightMultiplier = config.getFloat("7) Height Multiplier", category, 1.8F, -10, 10, "The player's y-level/height changes the overall wind effect by this multiplier. Default is that the higher you are up in the world the stronger the wind is, but only by a moderate amount. 0 means the player's height has no effect on the wind.");
        windDurabilityMultiplier = config.getFloat("8) Durability Multiplier", category, 0.7F, 0, 5, "The glider's durability remaining changes the overall wind effect by this additional amount. 0 means the glider's durability won't effect the wind power, whereas 1 will mean a nearly broken glider is affected by wind about twice as much as a new one.");

        category = "4) Durability";
        categories.add(category);
        durabilityEnabled = config.getBoolean("Enable Durability", category, true, "Enables durability usage of the hang glider when gliding.");
        durabilityPerUse = config.getInt("Durability Per-Use", category, 1, 0, 10000, "The durability used up each time.");
        durabilityTimeframe = config.getInt("Durability Timeframe", category, 100, 1, 10000, "The timeframe for durability usage, in ticks. Recall that there are 20 ticks in a second, so a value of 20 would damage the item about once a second. Default is 1 damage about every 5 seconds of flight, so with the default durability (206) means about 15 minutes of flight time with an undamaged glider.");

        category = "5) Visuals";
        categories.add(category);
        enableRendering3PP = config.getBoolean("1) Enable Rendering 3PP", category, true, "Enables rendering of the hang glider on the player in third-person perspective (or to others).");
        enableRenderingFPP = config.getBoolean("1) Enable Rendering FPP", category, true, "Enables rendering of the hang glider above the player's head in first person perspective.");
        gliderVisibilityFPPShiftAmount = config.getFloat("2) First-Person Glider Visibility", category, 1.9F, 1, 4, "How high above the player's head the glider appears as in first person perspective while flying. Lower values will make it more visible/intrusive.");
        disableOffhandRenderingWhenGliding = config.getBoolean("3) Disable Offhand Rendering While Gliding", category, true, "Disables rendering of the offhand while the player is gliding.");

        if (config.hasChanged())
            config.save();
    }

}
