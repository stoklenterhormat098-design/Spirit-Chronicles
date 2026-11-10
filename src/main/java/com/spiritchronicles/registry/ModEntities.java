package com.spiritchronicles.registry;

import com.spiritchronicles.SpiritChronicles;
import com.spiritchronicles.entity.TohkaEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Entity registry for Spirit Chronicles characters.
 */
public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, SpiritChronicles.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<TohkaEntity>> TOHKA =
            ENTITY_TYPES.register("tohka", () -> EntityType.Builder
                    .of(TohkaEntity::new, MobCategory.CREATURE)
                    .sized(0.65F, 1.9F)
                    .clientTrackingRange(8)
                    .build("tohka"));

    private ModEntities() {
    }

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(TOHKA.get(), TohkaEntity.createAttributes().build());
    }
}
