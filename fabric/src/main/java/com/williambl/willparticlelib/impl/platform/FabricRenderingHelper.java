package com.williambl.willparticlelib.impl.platform;

import com.williambl.willparticlelib.api.Blending;
import com.williambl.willparticlelib.api.WParticleRenderType;
import com.williambl.willparticlelib.impl.FabricWParticleRenderType;
import com.williambl.willparticlelib.impl.FabricWParticleSetupFunction;
import com.williambl.willparticlelib.impl.PostLevelRenderCallback;
import com.williambl.willparticlelib.impl.platform.services.IRenderingHelper;
import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class FabricRenderingHelper implements IRenderingHelper {
    @Override
    public WParticleRenderType createParticleRenderType(ResourceLocation name, Map<ResourceLocation, RenderType> renderTargets, ResourceLocation postShader, Blending blending, Object setupFunction) {
        return new FabricWParticleRenderType(name, renderTargets, postShader, blending, (FabricWParticleSetupFunction) setupFunction);
    }

    @Override
    public void registerPostLevelRenderCallback(PostLevelRenderCallback callback) {
        PostWorldRenderCallbackV2.EVENT.register(callback::postLevelRender);
    }
}
