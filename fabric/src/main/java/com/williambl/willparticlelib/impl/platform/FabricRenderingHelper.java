package com.williambl.willparticlelib.impl.platform;

import com.williambl.willparticlelib.api.Blending;
import com.williambl.willparticlelib.api.WParticleRenderType;
import com.williambl.willparticlelib.api.WParticleRenderTypeBuilder;
import com.williambl.willparticlelib.impl.FabricWParticleRenderType;
import com.williambl.willparticlelib.impl.FabricWParticleRenderTypeBuilder;
import com.williambl.willparticlelib.impl.FabricWParticleSetupFunction;
import com.williambl.willparticlelib.impl.PostLevelRenderCallback;
import com.williambl.willparticlelib.impl.platform.services.IRenderingHelper;
import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class FabricRenderingHelper implements IRenderingHelper {

    @Override
    public WParticleRenderTypeBuilder wParticleRenderTypebuilder(ResourceLocation name) {
        return new FabricWParticleRenderTypeBuilder(name);
    }

    @Override
    public void registerPostLevelRenderCallback(PostLevelRenderCallback callback) {
        PostWorldRenderCallbackV2.EVENT.register(callback::postLevelRender);
    }
}
