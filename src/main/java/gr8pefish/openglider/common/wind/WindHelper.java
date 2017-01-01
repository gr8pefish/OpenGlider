package gr8pefish.openglider.common.wind;

import gr8pefish.openglider.common.config.ConfigHandler;
import gr8pefish.openglider.common.wind.generator.OpenSimplexNoise;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class WindHelper {

    public static OpenSimplexNoise noiseGenerator;

    public static void initNoiseGenerator() {
        noiseGenerator = new OpenSimplexNoise();
    }

    public static void applyWind(EntityPlayer player, ItemStack glider){

        if (!ConfigHandler.windEnabled) return; //if no wind, then do nothing

        double windGustSize = ConfigHandler.windGustSize; //18;
        double windFrequency = ConfigHandler.windFrequency; //0.15;
        double windRainingMultiplier = ConfigHandler.windRainingMultiplier; //4;
        double windSpeedMultiplier = ConfigHandler.windSpeedMultiplier; //0.4;
        double windHeightMultiplier = ConfigHandler.windHeightMultiplier; //1.2;
        double windOverallPower = ConfigHandler.windOverallPower; //1;

        //downscale for gust size/occurrence amount
        double noise = WindHelper.noiseGenerator.eval(player.posX / windGustSize, player.posZ / windGustSize); //occurrence amount

        //multiply by intensity factor (alter by multiplier if raining)
        noise *= player.worldObj.isRaining() ? windRainingMultiplier * windFrequency : windFrequency;

        //stabilize somewhat depending on velocity
        double velocity = Math.sqrt(Math.pow(player.motionX, 2) + Math.pow(player.motionZ, 2)); //player's velocity
        double speedStabilized = noise * 1/((velocity * windSpeedMultiplier) + 1); //stabilize somewhat with higher speeds //ToDo: test

        //increase wind depending on world height
        double height = player.posY < 256 ? (player.posY / 256) * windHeightMultiplier : windHeightMultiplier; //world height clamp

        //apply stabilized speed with height
        double wind = speedStabilized * height;

        //apply durability modifier
        double damagePercentage = glider.isItemDamaged() ? ConfigHandler.windDurabilityMultiplier * ((double)glider.getItemDamage() / (glider.getMaxDamage())) : 0; //1.x where x is the percent of durability used
        System.out.println(damagePercentage);
        System.out.println(1 + damagePercentage);
        wind *= ConfigHandler.windDurabilityMultiplier == 0 ? 1 : 1 + damagePercentage;

        //apply overall wind power multiplier
        wind *= windOverallPower;

        //apply final rotation based on all the above
        player.rotationYaw += wind;
    }

}
