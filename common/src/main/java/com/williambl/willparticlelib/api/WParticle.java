package com.williambl.willparticlelib.api;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;

public abstract class WParticle extends Particle {
    private final WParticleRenderType wRenderType;

    protected WParticle(ClientLevel $$0, double $$1, double $$2, double $$3, WParticleRenderType wRenderType) {
        super($$0, $$1, $$2, $$3);
        this.wRenderType = wRenderType;
    }

    public WParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, WParticleRenderType wRenderType) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
        this.wRenderType = wRenderType;
    }

    @Override
    public final void render(@NotNull VertexConsumer var1, @NotNull Camera var2, float var3) {
        //no-op
    }

    @Override
    public final @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.NO_RENDER;
    }

    public WParticleRenderType wRenderType() {
        return this.wRenderType;
    }

    public abstract void render(PoseStack poseStack, MultiBufferSource.BufferSource buffers, LightTexture light, Camera camera, float tickDelta);
}
