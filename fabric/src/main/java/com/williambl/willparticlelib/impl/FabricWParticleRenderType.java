package com.williambl.willparticlelib.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.williambl.willparticlelib.api.WParticleRenderType;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.williambl.willparticlelib.api.WillParticleLib.id;

public class FabricWParticleRenderType implements WParticleRenderType {
    private final ResourceLocation name;
    private final ManagedShaderEffect postShader;
    private final ManagedFramebuffer finalBuffer;
    private final Map<ResourceLocation, CustomRenderTypes.CustomRenderTarget> renderTargets;

    public FabricWParticleRenderType(ResourceLocation name, ResourceLocation albedoShader, ResourceLocation postShader) {
        this.name = name;
        this.postShader = ShaderEffectManager.getInstance().manage(postShader);
        this.finalBuffer = this.postShader.getTarget(id("final").toString());
        var albedoRenderType = new CustomRenderTypes.CustomRenderTarget(albedoShader, id("albedo"), this.postShader, CustomRenderTypes.PARTICLE); //todo customise
        this.renderTargets = new LinkedHashMap<>();
        this.renderTargets.put(id("albedo"), albedoRenderType);
        this.renderTargets.put(id("position"), new CustomRenderTypes.CustomRenderTarget(CustomRenderTypes.W_PARTICLE_POSITION_SHADER, id("position"), this.postShader, CustomRenderTypes.POSITION));
    }

    @Override
    public RenderType getRenderTypeForTarget(ResourceLocation targetName, @Nullable ResourceLocation texture) {
        var res = this.renderTargets.get(targetName);
        if (res == null) {
            throw new NoSuchElementException("No such render type %s in %s".formatted(targetName, this.name));
        }
        return texture == null ? res.getRenderType() : res.getRenderType(texture);
    }

    @Override
    public boolean hasTarget(ResourceLocation location) {
        return this.renderTargets.containsKey(location);
    }

    @Override
    public void renderPost(float tickDelta, long nanoTime) {
        this.postShader.getTarget(id("albedo").toString()).draw(500, 500, false);
        this.postShader.render(tickDelta);
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        this.finalBuffer.draw(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight(), false);
        RenderSystem.disableBlend();

        for (var target : this.renderTargets.values()) {
            target.clear();
        }
    }
}
