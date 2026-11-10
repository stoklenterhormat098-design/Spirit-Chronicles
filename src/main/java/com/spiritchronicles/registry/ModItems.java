package com.spiritchronicles.registry;

import com.spiritchronicles.SpiritChronicles;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Central registry for Spirit Chronicles items.
 */
public final class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(SpiritChronicles.MOD_ID);

    public static final DeferredItem<Item> SPIRIT_CRYSTAL =
            ITEMS.registerSimpleItem("spirit_crystal", new Item.Properties());

    public static final DeferredItem<Item> SPIRIT_FRAGMENT =
            ITEMS.registerSimpleItem("spirit_fragment", new Item.Properties());

    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(SPIRIT_CRYSTAL);
            event.accept(SPIRIT_FRAGMENT);
        }
    }
}
