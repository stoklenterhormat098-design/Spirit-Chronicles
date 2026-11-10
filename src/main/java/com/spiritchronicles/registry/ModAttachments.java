package com.spiritchronicles.registry;

import com.spiritchronicles.SpiritChronicles;
import com.spiritchronicles.spirit.SpiritPlayerData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/**
 * Persistent and synchronized data attachments used by Spirit Chronicles.
 */
public final class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SpiritChronicles.MOD_ID);

    public static final Supplier<AttachmentType<SpiritPlayerData>> PLAYER_DATA =
            ATTACHMENT_TYPES.register("player_data", () ->
                    AttachmentType.builder(SpiritPlayerData::new)
                            .serialize(SpiritPlayerData.CODEC)
                            .sync((holder, player) -> holder == player, SpiritPlayerData.STREAM_CODEC)
                            .copyOnDeath()
                            .build()
            );

    private ModAttachments() {
    }

    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }
}
