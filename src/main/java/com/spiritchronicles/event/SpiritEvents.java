package com.spiritchronicles.event;

import com.spiritchronicles.registry.ModAttachments;
import com.spiritchronicles.spirit.SpiritPlayerData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * Server-side gameplay events for Spirit Chronicles.
 */
public final class SpiritEvents {
    private static final int MONSTER_KILL_EXPERIENCE = 25;

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

    /**
     * Rewards Spirit experience when a player defeats a hostile monster.
     */
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (event.getEntity() instanceof Player
                || event.getEntity().getType().getCategory() != MobCategory.MONSTER) {
            return;
        }

        SpiritPlayerData oldData = player.getData(ModAttachments.PLAYER_DATA);
        SpiritPlayerData newData = oldData.addExperience(MONSTER_KILL_EXPERIENCE);
        player.setData(ModAttachments.PLAYER_DATA, newData);

        if (newData.level() > oldData.level()) {
            player.displayClientMessage(
                    Component.literal("Spirit Level Up! You are now level " + newData.level() + "."),
                    true
            );
        }
    }
}
