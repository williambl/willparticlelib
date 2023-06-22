package com.williambl.willparticlelib.impl;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import net.minecraft.client.Camera;

@FunctionalInterface
public interface FabricWParticleSetupFunction {
    void setup(ManagedShaderEffect shader, Camera camera, float tickDelta, long nanoTime);
}
