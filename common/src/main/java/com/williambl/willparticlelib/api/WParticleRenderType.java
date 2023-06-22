package com.williambl.willparticlelib.api;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import static com.williambl.willparticlelib.api.WillParticleLib.id;

public interface WParticleRenderType {
    default RenderType positionRenderType() {
        return this.getRenderTypeForTarget(id("position"));
    }
    default RenderType albedoRenderType(ResourceLocation texture) {
        return this.getRenderTypeForTarget(id("albedo"), texture);
    }
    default RenderType getRenderTypeForTarget(ResourceLocation targetName) {
        return this.getRenderTypeForTarget(targetName, null);
    }
    RenderType getRenderTypeForTarget(ResourceLocation targetName, @Nullable ResourceLocation texture);
    boolean hasTarget(ResourceLocation location);
    void renderPost(Camera camera, float tickDelta, long nanoTime);
}
