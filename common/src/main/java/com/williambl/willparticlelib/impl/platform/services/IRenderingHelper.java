package com.williambl.willparticlelib.impl.platform.services;

import com.williambl.willparticlelib.impl.PostLevelRenderCallback;
import com.williambl.willparticlelib.api.WParticleRenderType;
import net.minecraft.resources.ResourceLocation;

public interface IRenderingHelper {
    WParticleRenderType createParticleRenderType(
            ResourceLocation name,
            ResourceLocation albedoShader,
            ResourceLocation postShader
    );

    void registerPostLevelRenderCallback(PostLevelRenderCallback callback);
}
