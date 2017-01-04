package gr8pefish.openglider.common.registry;

import gr8pefish.openglider.common.capabilities.DefaultGliderCapabilityHandler;

public class CapabilityRegistry {

    public static void registerAllCapabilities(){
        DefaultGliderCapabilityHandler.register();
    }

}
