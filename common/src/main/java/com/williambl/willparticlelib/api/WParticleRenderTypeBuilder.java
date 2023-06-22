package com.williambl.willparticlelib.api;

import com.williambl.willparticlelib.impl.platform.Services;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public abstract class WParticleRenderTypeBuilder {
    protected final ResourceLocation name;
    protected final Map<ResourceLocation, RenderType> targets = new HashMap<>();
    protected ResourceLocation postShader = null;
    protected Blending blending = Blending.DEFAULT;

    protected WParticleRenderTypeBuilder(ResourceLocation name) {
        this.name = name;
    }

    public WParticleRenderTypeBuilder renderTarget(ResourceLocation targetName, RenderType targetRenderType) {
        this.targets.put(targetName, targetRenderType);
        return this;
    }

    public WParticleRenderTypeBuilder postShader(ResourceLocation postShaderLocation) {
        this.postShader = postShaderLocation;
        return this;
    }

    public WParticleRenderTypeBuilder blending(Blending blending) {
        this.blending = blending;
        return this;
    }

    public abstract WParticleRenderType build();
}
