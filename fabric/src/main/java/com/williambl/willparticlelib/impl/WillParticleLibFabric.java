package com.williambl.willparticlelib.impl;

import com.mojang.blaze3d.shaders.Shader;
import com.williambl.willparticlelib.api.WillParticleLib;
import com.williambl.willparticlelib.impl.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import static com.williambl.willparticlelib.api.WillParticleLib.id;

public class WillParticleLibFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        var particleRenderType = WillParticleLib.registerRenderType(id("test"), Services.RENDERING.createParticleRenderType(
                id("test"),
                id("test_albedo"),
                id("shaders/post/test_post.json")
        ));
        var particleType = Registry.register(BuiltInRegistries.PARTICLE_TYPE, id("test_particle"), new SimpleParticleType(true) {});
        ParticleFactoryRegistry.getInstance().register(particleType, new TestWParticle.Provider(particleRenderType));
    }
}
