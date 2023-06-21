package com.williambl.willparticlelib.api;

import com.mojang.blaze3d.platform.GlStateManager;

public record Blending(GlStateManager.SourceFactor srcRGB, GlStateManager.SourceFactor srcA, GlStateManager.DestFactor destRGB, GlStateManager.DestFactor destA) {
    public Blending(GlStateManager.SourceFactor src, GlStateManager.DestFactor dest) {
        this(src, src, dest, dest);
    }
    public static Blending DEFAULT = new Blending(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.DestFactor.ZERO);
    public static Blending ADDITIVE = new Blending(GlStateManager.SourceFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE, GlStateManager.DestFactor.ONE);
}
