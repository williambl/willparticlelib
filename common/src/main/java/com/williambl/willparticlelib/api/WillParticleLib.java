package com.williambl.willparticlelib.api;

import com.williambl.willparticlelib.impl.platform.Services;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WillParticleLib {
	public static final String MOD_ID = "willparticlelib";
	public static final String MOD_NAME = "WillParticleLib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	private static final Map<ResourceLocation, WParticleRenderType> W_PARTICLE_RENDER_TYPES = new HashMap<>();

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static WParticleRenderType registerRenderType(ResourceLocation name, WParticleRenderType wParticleRenderType) {
		W_PARTICLE_RENDER_TYPES.put(name, wParticleRenderType);
		Services.RENDERING.registerPostLevelRenderCallback((poseStack, camera, tickDelta, nanoTime) -> {
			wParticleRenderType.renderPost(camera, tickDelta, nanoTime);
		});
		return wParticleRenderType;
	}
}