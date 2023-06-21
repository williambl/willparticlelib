package com.williambl.willparticlelib;

import com.williambl.willparticlelib.api.WillParticleLib;
import net.minecraftforge.fml.common.Mod;

@Mod(WillParticleLib.MOD_ID)
public class ExampleMod {
    
    public ExampleMod() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        WillParticleLib.LOGGER.info("Hello Forge world!");
        CommonClass.init();
        
    }
}