package com.spiritchronicles.client;

import com.spiritchronicles.SpiritChronicles;
import com.spiritchronicles.registry.ModAttachments;
import com.spiritchronicles.spirit.SpiritPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.GuiLayer;

@EventBusSubscriber(modid = SpiritChronicles.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SpiritHud {
    private static final ResourceLocation LAYER_ID =
            ResourceLocation.fromNamespaceAndPath(SpiritChronicles.MOD_ID, "spirit_status");

    private SpiritHud() {
    }

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(LAYER_ID, SpiritHud::render);
    }

    private static void render(net.minecraft.client.gui.GuiGraphics graphics,
                               net.minecraft.client.DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.options.hideGui) {
            return;
        }

        SpiritPlayerData data = minecraft.player.getData(ModAttachments.PLAYER_DATA);

        int x = 10;
        int y = 10;
        int barWidth = 110;
        int barHeight = 7;
        int filledWidth = Math.round((float) data.energy() / data.maxEnergy() * barWidth);

        graphics.fill(x - 4, y - 4, x + barWidth + 4, y + 27, 0x90000000);
        graphics.drawString(minecraft.font,
                Component.literal("Spirit Energy: " + data.energy() + " / " + data.maxEnergy()),
                x, y, 0xFFFFFF, false);

        graphics.fill(x, y + 12, x + barWidth, y + 12 + barHeight, 0xFF333333);
        if (filledWidth > 0) {
            graphics.fill(x, y + 12, x + filledWidth, y + 12 + barHeight, 0xFF8A42D6);
        }

        graphics.drawString(minecraft.font,
                Component.literal("Spirit Level: " + data.level() + "  |  EXP: " + data.experience()),
                x, y + 22, 0xFFE6D8FF, false);
    }
}
