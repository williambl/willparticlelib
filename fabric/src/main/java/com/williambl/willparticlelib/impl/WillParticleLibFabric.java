package com.williambl.willparticlelib.impl;

import com.mojang.blaze3d.shaders.Shader;
import com.williambl.willparticlelib.api.Blending;
import com.williambl.willparticlelib.api.WillParticleLib;
import com.williambl.willparticlelib.impl.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

import static com.williambl.willparticlelib.api.WillParticleLib.id;

public class WillParticleLibFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        var additiveParticleRenderType = WillParticleLib.registerRenderType(id("additive_translucent"), ((FabricWParticleRenderTypeBuilder)Services.RENDERING.wParticleRenderTypebuilder(id("additive")))
                .renderTarget(id("albedo"), CustomRenderTypes.TRANSLUCENT_PARTICLE_DEPTH_TESTED)
                .postShader(id("shaders/post/soft_particle.json"))
                .blending(Blending.ADDITIVE)
                .copyDepth()
                .build());

        var softParticleRenderType = WillParticleLib.registerRenderType(id("soft"), ((FabricWParticleRenderTypeBuilder)Services.RENDERING.wParticleRenderTypebuilder(id("soft")))
                .renderTarget(id("albedo"), CustomRenderTypes.TRANSLUCENT_PARTICLE_DEPTH_TESTED)
                .postShader(id("shaders/post/soft_particle.json"))
                .setupFunction((shader, cam, tickDelta, time) -> shader.setUniformValue("NearPlane", (float) cam.getNearPlane().getPointOnPlane(0, 0).length()))
                .copyDepth()
                .build());

        var additiveParticleType = Registry.register(BuiltInRegistries.PARTICLE_TYPE, id("additive_particle"), new SimpleParticleType(true) {});
        ParticleFactoryRegistry.getInstance().register(additiveParticleType, new TestWParticle.Provider(additiveParticleRenderType));
        var softParticle = Registry.register(BuiltInRegistries.PARTICLE_TYPE, id("soft_particle"), new SimpleParticleType(true) {});
        ParticleFactoryRegistry.getInstance().register(softParticle, new TestWParticle.Provider(softParticleRenderType));
    }
}
