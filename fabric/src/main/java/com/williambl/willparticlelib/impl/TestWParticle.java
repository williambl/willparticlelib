package com.williambl.willparticlelib.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.williambl.willparticlelib.api.WParticle;
import com.williambl.willparticlelib.api.WParticleRenderType;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Consumer;

public class TestWParticle extends WParticle {
    private final float yRot = 0;
    private final float xRot = 0;

    private static final Vector3f ROTATION_VECTOR = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    protected TestWParticle(ClientLevel $$0, double $$1, double $$2, double $$3, WParticleRenderType wRenderType) {
        super($$0, $$1, $$2, $$3, wRenderType);
    }

    public TestWParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, WParticleRenderType wRenderType) {
        super($$0, $$1, $$2, $$3, $$4, $$5, $$6, wRenderType);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource.BufferSource buffers, LightTexture light, Camera camera, float tickDelta) {
        for (var target : List.of(this.wRenderType().albedoRenderType(CustomRenderTypes.FLASH))) {
            var buffer = buffers.getBuffer(target);
            this.renderRotatedParticle(buffer, camera, tickDelta, quaternion -> {
                quaternion.mul(Axis.YP.rotation(this.yRot));
                quaternion.mul(Axis.XP.rotation(this.xRot));
            });
            this.renderRotatedParticle(buffer, camera, tickDelta, quaternion -> {
                quaternion.mul(Axis.YP.rotation(this.yRot - (float) Math.PI));
                quaternion.mul(Axis.XP.rotation(-this.xRot));
            });
            buffers.endBatch(target);
        }
    }

    private void renderRotatedParticle(VertexConsumer consumer, Camera camera, float f, Consumer<Quaternionf> quaternion) {
        Vec3 vec3 = camera.getPosition();
        float g = (float)(Mth.lerp(f, this.xo, this.x) - vec3.x()) + (float) this.random.nextGaussian()*0.01f;
        float h = (float)(Mth.lerp(f, this.yo, this.y) - vec3.y()) + (float) this.random.nextGaussian()*0.01f;
        float i = (float)(Mth.lerp(f, this.zo, this.z) - vec3.z()) + (float) this.random.nextGaussian()*0.01f;
        Quaternionf quaternion2 = new Quaternionf().setAngleAxis(0.0F, ROTATION_VECTOR.x(), ROTATION_VECTOR.y(), ROTATION_VECTOR.z());
        quaternion.accept(quaternion2);
        Vector3f[] vector3fs = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
        };
        float j = 3f;

        for(int k = 0; k < 4; ++k) {
            Vector3f vector3f = vector3fs[k];
            vector3f.rotate(quaternion2);
            vector3f.mul(j);
            vector3f.add(g, h, i);
        }

        int k = LightTexture.FULL_BRIGHT;
        this.makeCornerVertex(consumer, vector3fs[0], 1f, 1f, k);
        this.makeCornerVertex(consumer, vector3fs[1], 1f, 0f, k);
        this.makeCornerVertex(consumer, vector3fs[2], 0f, 0f, k);
        this.makeCornerVertex(consumer, vector3fs[3], 0f, 1f, k);
    }

    private void makeCornerVertex(VertexConsumer consumer, Vector3f vec3f, float f, float g, int i) {
        consumer.vertex(vec3f.x(), vec3f.y(), vec3f.z()).uv(f, g).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(i).endVertex();
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final WParticleRenderType type;

        public Provider(WParticleRenderType type) {
            this.type = type;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new TestWParticle(clientLevel, d, e, f, g, h, i, this.type);
        }
    }
}
