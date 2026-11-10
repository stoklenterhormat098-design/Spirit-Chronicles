package com.spiritchronicles.client;

import com.spiritchronicles.entity.TohkaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * Renders Tohka with her custom GeckoLib model.
 */
public final class TohkaRenderer extends GeoEntityRenderer<TohkaEntity> {
    public TohkaRenderer(EntityRendererProvider.Context context) {
        super(context, new TohkaModel());
        this.shadowRadius = 0.35F;
    }
}
