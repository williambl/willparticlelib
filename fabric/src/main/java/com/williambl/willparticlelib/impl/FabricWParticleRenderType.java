package com.williambl.willparticlelib.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.williambl.willparticlelib.api.Blending;
import com.williambl.willparticlelib.api.WParticleRenderType;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.williambl.willparticlelib.api.WillParticleLib.id;

// note:
// render type = vanilla RenderType
// particle render type = vanilla PArticleRenderType
// w-particle render type = WParticleRenderType
// render target = CustomRenderTypes.CustomRenderTarget
public class FabricWParticleRenderType implements WParticleRenderType {
    private final ResourceLocation name;
    private final ManagedShaderEffect postShader;
    private final Blending blending;
    private final FabricWParticleSetupFunction setupFunction;
    private final ManagedFramebuffer finalBuffer;
    private final Map<ResourceLocation, CustomRenderTypes.CustomRenderTarget> renderTargets;

    public FabricWParticleRenderType(
            ResourceLocation name,
            Map<ResourceLocation, RenderType> renderTargets,
            ResourceLocation postShader,
            Blending blending,
            FabricWParticleSetupFunction setupFunction,
            boolean copyDepth) {
        this.name = name;
        this.postShader = ShaderEffectManager.getInstance().manage(postShader);
        this.blending = blending;
        this.setupFunction = setupFunction;
        this.finalBuffer = this.postShader.getTarget(id("final").toString());
        this.renderTargets = new LinkedHashMap<>(renderTargets.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> new CustomRenderTypes.CustomRenderTarget(e.getKey(), this.postShader, e.getValue(), copyDepth)
        )));
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
    public void renderPost(Camera camera, float tickDelta, long nanoTime) {
        if (this.setupFunction != null) {
            this.setupFunction.setup(this.postShader, camera, tickDelta, nanoTime);
        }
        this.postShader.render(tickDelta);
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(this.blending.srcRGB(), this.blending.destRGB(), this.blending.srcA(), this.blending.destA());
        this.finalBuffer.draw(Minecraft.getInstance().getWindow().getScreenWidth(), Minecraft.getInstance().getWindow().getScreenHeight(),false);
        this.finalBuffer.clear();
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();

        for (var target : this.renderTargets.values()) {
            target.readyForNextFrame();
        }
    }
}
