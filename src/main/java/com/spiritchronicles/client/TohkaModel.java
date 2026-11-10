package com.spiritchronicles.client;

import com.spiritchronicles.SpiritChronicles;
import com.spiritchronicles.entity.TohkaEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/**
 * GeckoLib model resources for Tohka's Astral Dress form.
 */
public final class TohkaModel extends GeoModel<TohkaEntity> {
    private static final ResourceLocation MODEL =
            ResourceLocation.fromNamespaceAndPath(SpiritChronicles.MOD_ID, "geo/tohka.geo.json");
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(SpiritChronicles.MOD_ID, "textures/entity/tohka.png");
    private static final ResourceLocation ANIMATION =
            ResourceLocation.fromNamespaceAndPath(SpiritChronicles.MOD_ID, "animations/tohka.animation.json");

    @Override
    public ResourceLocation getModelResource(TohkaEntity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(TohkaEntity animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(TohkaEntity animatable) {
        return ANIMATION;
    }
}
