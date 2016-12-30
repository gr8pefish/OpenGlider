package gr8pefish.openglider.common.wind;

import gr8pefish.openglider.common.wind.generator.OpenSimplexNoise;
import net.minecraft.entity.player.EntityPlayer;

public class WindHelper {

    public static OpenSimplexNoise noiseGenerator;

    public static void initNoiseGenerator() {
        noiseGenerator = new OpenSimplexNoise();
    }

    public static void applyWind(EntityPlayer player){

        double windGustSize = 18;
        double windIntensity = 0.15;
        double windRainingMultiplier = 4;
        double windSpeedMultiplier = 0.4;
        double windHeightMultiplier = 0.2;

        //downscale for gust size/occurrence amount
        double wind = WindHelper.noiseGenerator.eval(player.posX / windGustSize, player.posZ / windGustSize); //occurrence amount

        //multiply by intensity factor (increase by multiplier if raining)
        wind *= player.worldObj.isRaining() ? windRainingMultiplier*windIntensity : windIntensity;

        //stabilize somewhat depending on velocity
        double velocity = Math.sqrt(Math.pow(player.motionX, 2) + Math.pow(player.motionZ, 2)); //player's velocity
        double speedStabilized = wind * 1/((velocity+1) * windSpeedMultiplier); //stabilize somewhat with higher speeds

        //increase wind depending on world height
        double height = player.posY < 256 ? 1 + ((player.posY / 256) * windHeightMultiplier) : 1 + windHeightMultiplier; //world height clamp

        //apply stabilized speed wind with height
        double modifier = speedStabilized * height;

        //apply final rotation based on all the above
        player.rotationYaw += modifier;
    }

}
