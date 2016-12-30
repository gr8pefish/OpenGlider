package gr8pefish.openglider.common.wind;

import gr8pefish.openglider.common.wind.generator.OpenSimplexNoise;

public class WindHelper {

    public static OpenSimplexNoise noiseGenerator;

    public static void initNoiseGenerator() {
        noiseGenerator = new OpenSimplexNoise();
    }
}
