package com.williambl.willparticlelib.impl.platform.services;

import com.williambl.willparticlelib.impl.PostLevelRenderCallback;
import com.williambl.willparticlelib.api.WParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface IRenderingHelper {
    WParticleRenderType createParticleRenderType(
            ResourceLocation name,
            Map<ResourceLocation, RenderType> renderTargets,
            ResourceLocation postShader
    );

    void registerPostLevelRenderCallback(PostLevelRenderCallback callback);
}
