package com.williambl.willparticlelib.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;

@FunctionalInterface
public interface PostLevelRenderCallback {
    void postLevelRender(PoseStack poseStack, Camera camera, float tickDelta, long nanoTime);
}
