package com.williambl.willparticlelib.impl;

import com.williambl.willparticlelib.api.Blending;
import com.williambl.willparticlelib.api.WParticleRenderType;
import com.williambl.willparticlelib.api.WParticleRenderTypeBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class FabricWParticleRenderTypeBuilder extends WParticleRenderTypeBuilder {
    private FabricWParticleSetupFunction setupFunction;

    public FabricWParticleRenderTypeBuilder(ResourceLocation name) {
        super(name);
    }

    @Override
    public WParticleRenderType build() {
        return new FabricWParticleRenderType(
                this.name,
                this.targets,
                this.postShader,
                this.blending,
                this.setupFunction,
                this.copyDepth
        );
    }

    public FabricWParticleRenderTypeBuilder setupFunction(FabricWParticleSetupFunction setupFunction) {
        this.setupFunction = setupFunction;
        return this;
    }

    @Override
    public FabricWParticleRenderTypeBuilder renderTarget(ResourceLocation targetName, RenderType targetRenderType) {
        return (FabricWParticleRenderTypeBuilder) super.renderTarget(targetName, targetRenderType);
    }

    @Override
    public FabricWParticleRenderTypeBuilder postShader(ResourceLocation postShaderLocation) {
        return (FabricWParticleRenderTypeBuilder) super.postShader(postShaderLocation);
    }

    @Override
    public FabricWParticleRenderTypeBuilder blending(Blending blending) {
        return (FabricWParticleRenderTypeBuilder) super.blending(blending);
    }

    @Override
    public FabricWParticleRenderTypeBuilder copyDepth() {
        return (FabricWParticleRenderTypeBuilder) super.copyDepth();
    }
}
