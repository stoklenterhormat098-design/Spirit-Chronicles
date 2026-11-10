package com.spiritchronicles.item;

import com.spiritchronicles.registry.ModAttachments;
import com.spiritchronicles.spirit.SpiritPlayerData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * A defensive Spirit skill that temporarily reduces incoming damage.
 */
public final class SpiritGuardItem extends Item {
    private static final int ENERGY_COST = 25;
    private static final int COOLDOWN_TICKS = 200;
    private static final int DURATION_TICKS = 100;

    public SpiritGuardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        SpiritPlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        if (data.energy() < ENERGY_COST) {
            player.displayClientMessage(
                    Component.literal("Not enough Spirit Energy! Need " + ENERGY_COST + "."),
                    true
            );
            return InteractionResultHolder.fail(stack);
        }

        player.setData(ModAttachments.PLAYER_DATA, data.withEnergy(data.energy() - ENERGY_COST));
        player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, DURATION_TICKS, 0));
        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

        player.displayClientMessage(
                Component.literal("Spirit Guard activated! Resistance for 5 seconds. -" + ENERGY_COST + " Energy"),
                true
        );

        return InteractionResultHolder.sidedSuccess(stack, false);
    }
}
