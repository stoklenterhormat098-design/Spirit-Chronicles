package com.spiritchronicles;

import com.spiritchronicles.event.SpiritEvents;
import com.spiritchronicles.registry.ModAttachments;
import com.spiritchronicles.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Main entry point for Spirit Chronicles.
 */
@Mod(SpiritChronicles.MOD_ID)
public final class SpiritChronicles {
    public static final String MOD_ID = "spirit_chronicles";

    public SpiritChronicles(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModAttachments.register(modEventBus);
        modEventBus.addListener(ModItems::addCreative);
        NeoForge.EVENT_BUS.addListener(SpiritEvents::onPlayerTick);
    }
}
