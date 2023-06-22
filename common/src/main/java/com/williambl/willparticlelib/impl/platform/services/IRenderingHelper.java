package com.williambl.willparticlelib.impl.platform.services;

import com.williambl.willparticlelib.api.Blending;
import com.williambl.willparticlelib.api.WParticleRenderTypeBuilder;
import com.williambl.willparticlelib.impl.PostLevelRenderCallback;
import com.williambl.willparticlelib.api.WParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface IRenderingHelper {
    WParticleRenderTypeBuilder wParticleRenderTypebuilder(
            ResourceLocation name
    );

    void registerPostLevelRenderCallback(PostLevelRenderCallback callback);
}
