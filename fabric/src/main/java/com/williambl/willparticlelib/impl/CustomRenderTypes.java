package com.williambl.willparticlelib.impl;


import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.RenderLayerHelper;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.williambl.willparticlelib.api.WillParticleLib.id;

// have to extend RenderType to access a few of these things
public final class CustomRenderTypes extends RenderType {
    public static final ResourceLocation WHITE = new ResourceLocation("textures/misc/white.png");
    public static final ResourceLocation FLASH = new ResourceLocation("textures/block/red_stained_glass.png");
    public static final RenderStateShard.ShaderStateShard W_PARTICLE_POSITION_SHADER = new RenderStateShard.ShaderStateShard(ShaderEffectManager.getInstance().manageCoreShader(id("position"))::getProgram);
    //todo pass the correct matrices into this so it can actually work out the worldspace position
    public static final RenderType POSITION = create(id("position").toString(), DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.builder()
            .setShaderState(W_PARTICLE_POSITION_SHADER)
            .setTextureState(NO_TEXTURE)
            .setWriteMaskState(RenderStateShard.COLOR_WRITE)
            .createCompositeState(false));

    public static final RenderType PARTICLE = create(id("particle").toString(), DefaultVertexFormat.PARTICLE, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.builder()
            .setShaderState(new ShaderStateShard(GameRenderer::getParticleShader))
            .setTextureState(new TextureStateShard(FLASH, false, false))
            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
            .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
            .createCompositeState(false));

    public static final RenderType TRANSLUCENT_PARTICLE = create(id("translucent_particle").toString(), DefaultVertexFormat.PARTICLE, VertexFormat.Mode.QUADS, 256, RenderType.CompositeState.builder()
            .setShaderState(new ShaderStateShard(GameRenderer::getParticleShader))
            .setTextureState(new TextureStateShard(FLASH, false, false))
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
            .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
            .createCompositeState(false));

    public static class CustomRenderTarget {
        private final ResourceLocation name;
        private final ManagedFramebuffer framebuffer;
        private final OutputStateShard target;
        private final Function<ResourceLocation, RenderType> layerFunction;
        private final RenderType defaultType;
        private boolean hasBeenUsed;

        public CustomRenderTarget(ResourceLocation bufferName, ManagedShaderEffect postShader, RenderType defaultType) {
            this.name = bufferName;
            this.framebuffer = postShader.getTarget(bufferName.toString());
            this.target = new OutputStateShard(bufferName +"_target", this::begin, this::end);
            this.defaultType = this.getRenderType(defaultType);
            this.layerFunction = Util.memoize(id ->
                    RenderLayerHelper.copy(defaultType, this.name.toString(), builder ->
                            builder.setOutputState(this.target).setTextureState(new TextureStateShard(id, false, false))));
        }

        public void clear() {
            if (this.hasBeenUsed) {
                this.framebuffer.clear();
                this.hasBeenUsed = false;
            }
        }

        private void begin() {
            RenderTarget buffer = this.framebuffer.getFramebuffer();
            if (buffer != null) {
                buffer.bindWrite(false);
                this.hasBeenUsed = true;
            }
        }

        private void end() {
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        }

        public RenderType getRenderType(RenderType base) {
            return RenderLayerHelper.copy(base, this.name+" * "+ base, builder -> builder.setOutputState(this.target));
        }

        public RenderType getRenderType(ResourceLocation texture) {
            return this.layerFunction.apply(texture);
        }

        public RenderType getRenderType() {
            return this.defaultType;
        }
    }

    // no need to create instances of this
    private CustomRenderTypes(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
    }
}

