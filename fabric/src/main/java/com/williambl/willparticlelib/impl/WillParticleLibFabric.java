package com.williambl.willparticlelib.impl;

import com.mojang.blaze3d.shaders.Shader;
import com.williambl.willparticlelib.api.Blending;
import com.williambl.willparticlelib.api.WillParticleLib;
import com.williambl.willparticlelib.impl.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
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
        var additiveParticleRenderType = WillParticleLib.registerRenderType(id("additive"), Services.RENDERING.createParticleRenderType(
                id("additive"),
                Map.of(
                        id("albedo"), CustomRenderTypes.PARTICLE
                ),
                id("shaders/post/test_post.json"),
                Blending.ADDITIVE,
                ((FabricWParticleSetupFunction) (shader, cam, tickDelta, time) -> {})
        ));

        var translucentParticleRenderType = WillParticleLib.registerRenderType(id("translucent"), Services.RENDERING.createParticleRenderType(
                id("translucent"),
                Map.of(
                        id("albedo"), CustomRenderTypes.TRANSLUCENT_PARTICLE_DEPTH_TESTED
                ),
                id("shaders/post/soft_particle.json"),
                Blending.DEFAULT,
                ((FabricWParticleSetupFunction) (shader, cam, tickDelta, time) -> {
                    shader.setUniformValue("NearPlane", (float) cam.getNearPlane().getPointOnPlane(0, 0).length());
                })
        ));

        var additiveParticleType = Registry.register(BuiltInRegistries.PARTICLE_TYPE, id("additive_particle"), new SimpleParticleType(true) {});
        ParticleFactoryRegistry.getInstance().register(additiveParticleType, new TestWParticle.Provider(additiveParticleRenderType));
        var translucentParticle = Registry.register(BuiltInRegistries.PARTICLE_TYPE, id("translucent_particle"), new SimpleParticleType(true) {});
        ParticleFactoryRegistry.getInstance().register(translucentParticle, new TestWParticle.Provider(translucentParticleRenderType));
    }
}
