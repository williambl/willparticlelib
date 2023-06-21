package com.williambl.willparticlelib.impl.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.williambl.willparticlelib.api.WParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Queue;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
    @Shadow
    private @Final Map<ParticleRenderType, Queue<Particle>> particles;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
    private void wpl$renderCustomParticles(PoseStack poseStack, MultiBufferSource.BufferSource buffers, LightTexture light, Camera camera, float tickDelta, CallbackInfo ci) {
        var particles = this.particles.get(ParticleRenderType.NO_RENDER);
        for (var particle : particles) {
            if (particle instanceof WParticle wParticle) {
                poseStack.pushPose();
                wParticle.render(poseStack, buffers, light, camera, tickDelta);
                poseStack.popPose();
            }
        }
    }
}
