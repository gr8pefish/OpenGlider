package gr8pefish.openglider.common.registry;

import gr8pefish.openglider.common.capabilities.GliderCapabilityImplementation;

public class CapabilityRegistry {

    public static void registerAllCapabilities(){
        GliderCapabilityImplementation.init();
    }

}
