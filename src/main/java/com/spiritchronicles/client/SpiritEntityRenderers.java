package com.spiritchronicles.client;

import com.spiritchronicles.SpiritChronicles;
import com.spiritchronicles.registry.ModEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * Client-only entity renderer registration.
 */
@EventBusSubscriber(modid = SpiritChronicles.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SpiritEntityRenderers {
    private SpiritEntityRenderers() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TOHKA.get(), TohkaRenderer::new);
    }
}
