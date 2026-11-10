package com.spiritchronicles.item;

import com.spiritchronicles.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import com.spiritchronicles.entity.TohkaEntity;

/**
 * A reusable token that summons Tohka as a peaceful companion.
 */
public final class TohkaSummonItem extends Item {
    public TohkaSummonItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
        }

        Vec3 look = player.getLookAngle();
        BlockPos spawnPos = BlockPos.containing(
                player.getX() + look.x * 2.0D,
                player.getY(),
                player.getZ() + look.z * 2.0D
        );

        TohkaEntity tohka = ModEntities.TOHKA.get().create(level);
        if (tohka != null) {
            tohka.moveTo(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D,
                    player.getYRot(), 0.0F);
            if (level.addFreshEntity(tohka)) {
                player.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("Tohka has arrived!"),
                        true
                );
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, false);
    }
}
