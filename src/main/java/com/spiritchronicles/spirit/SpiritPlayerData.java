package com.spiritchronicles.spirit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Persistent gameplay data attached to each player.
 */
public record SpiritPlayerData(int energy, int maxEnergy, int level, int experience) {
    public static final int DEFAULT_MAX_ENERGY = 100;

    public static final Codec<SpiritPlayerData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("energy").forGetter(SpiritPlayerData::energy),
                    Codec.INT.fieldOf("max_energy").forGetter(SpiritPlayerData::maxEnergy),
                    Codec.INT.fieldOf("level").forGetter(SpiritPlayerData::level),
                    Codec.INT.fieldOf("experience").forGetter(SpiritPlayerData::experience)
            ).apply(instance, SpiritPlayerData::new)
    );

    public SpiritPlayerData {
        maxEnergy = Math.max(1, maxEnergy);
        energy = Math.max(0, Math.min(maxEnergy, energy));
        level = Math.max(1, level);
        experience = Math.max(0, experience);
    }

    public SpiritPlayerData() {
        this(DEFAULT_MAX_ENERGY, DEFAULT_MAX_ENERGY, 1, 0);
    }

    public SpiritPlayerData withEnergy(int newEnergy) {
        return new SpiritPlayerData(newEnergy, maxEnergy, level, experience);
    }

    public SpiritPlayerData regenerate() {
        return withEnergy(energy + 1);
    }
}
