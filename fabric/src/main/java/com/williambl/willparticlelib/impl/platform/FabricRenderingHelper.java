package com.williambl.willparticlelib.impl.platform;

import com.williambl.willparticlelib.api.WParticleRenderType;
import com.williambl.willparticlelib.impl.FabricWParticleRenderType;
import com.williambl.willparticlelib.impl.PostLevelRenderCallback;
import com.williambl.willparticlelib.impl.platform.services.IRenderingHelper;
import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import net.minecraft.resources.ResourceLocation;

public class FabricRenderingHelper implements IRenderingHelper {
    @Override
    public WParticleRenderType createParticleRenderType(ResourceLocation name, ResourceLocation albedoShader, ResourceLocation postShader) {
        return new FabricWParticleRenderType(name, albedoShader, postShader);
    }

    @Override
    public void registerPostLevelRenderCallback(PostLevelRenderCallback callback) {
        PostWorldRenderCallbackV2.EVENT.register(callback::postLevelRender);
    }
}
