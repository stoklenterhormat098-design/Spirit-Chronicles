package com.spiritchronicles.item;

import com.spiritchronicles.registry.ModAttachments;
import com.spiritchronicles.spirit.SpiritPlayerData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

/**
 * A starter Spirit skill that releases a short-range burst of energy.
 */
public final class SpiritBurstItem extends Item {
    private static final int ENERGY_COST = 30;
    private static final int COOLDOWN_TICKS = 40;
    private static final double RANGE = 4.5D;
    private static final float DAMAGE = 8.0F;

    public SpiritBurstItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        SpiritPlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        if (data.energy() < ENERGY_COST) {
            player.displayClientMessage(
                    Component.literal("Not enough Spirit Energy! Need " + ENERGY_COST + "."),
                    true
            );
            return InteractionResultHolder.fail(stack);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        player.setData(ModAttachments.PLAYER_DATA, data.withEnergy(data.energy() - ENERGY_COST));
        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

        int hitCount = 0;
        for (LivingEntity target : level.getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(RANGE),
                entity -> entity != player
                        && entity.isAlive()
                        && entity.getType().getCategory() == MobCategory.MONSTER
        )) {
            if (target.hurt(player.damageSources().playerAttack(player), DAMAGE)) {
                hitCount++;
            }
        }

        player.displayClientMessage(
                Component.literal("Spirit Burst! Hit " + hitCount + " monster(s). -" + ENERGY_COST + " Energy"),
                true
        );

        return InteractionResultHolder.sidedSuccess(stack, false);
    }
}
