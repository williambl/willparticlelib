package com.williambl.willparticlelib.impl.platform.services;

import com.williambl.willparticlelib.api.Blending;
import com.williambl.willparticlelib.impl.PostLevelRenderCallback;
import com.williambl.willparticlelib.api.WParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface IRenderingHelper {
    //TODO replace with builder
    WParticleRenderType createParticleRenderType(
            ResourceLocation name,
            Map<ResourceLocation, RenderType> renderTargets,
            ResourceLocation postShader,
            Blending blending,
            Object setupFunction
    );

    void registerPostLevelRenderCallback(PostLevelRenderCallback callback);
}
