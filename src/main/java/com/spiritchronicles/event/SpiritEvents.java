package com.spiritchronicles.event;

import com.spiritchronicles.registry.ModAttachments;
import com.spiritchronicles.spirit.SpiritPlayerData;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Server-side gameplay events for Spirit Chronicles.
 */
public final class SpiritEvents {
    private SpiritEvents() {
    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // Only the logical server changes persistent player data.
        if (player.level().isClientSide || player.tickCount % 20 != 0) {
            return;
        }

        SpiritPlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        if (data.energy() < data.maxEnergy()) {
            player.setData(ModAttachments.PLAYER_DATA, data.regenerate());
        }
    }
}
