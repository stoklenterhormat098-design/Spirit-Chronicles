package com.spiritchronicles.spirit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * Persistent gameplay data attached to each player.
 */
public record SpiritPlayerData(int energy, int maxEnergy, int level, int experience) {
    public static final int DEFAULT_MAX_ENERGY = 100;
    private static final int BASE_EXPERIENCE_REQUIREMENT = 100;

    public static final Codec<SpiritPlayerData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("energy").forGetter(SpiritPlayerData::energy),
                    Codec.INT.fieldOf("max_energy").forGetter(SpiritPlayerData::maxEnergy),
                    Codec.INT.fieldOf("level").forGetter(SpiritPlayerData::level),
                    Codec.INT.fieldOf("experience").forGetter(SpiritPlayerData::experience)
            ).apply(instance, SpiritPlayerData::new)
    );

    /**
     * Sends the player's current Spirit status to their client for the HUD.
     */
    public static final StreamCodec<RegistryFriendlyByteBuf, SpiritPlayerData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, SpiritPlayerData::energy,
                    ByteBufCodecs.VAR_INT, SpiritPlayerData::maxEnergy,
                    ByteBufCodecs.VAR_INT, SpiritPlayerData::level,
                    ByteBufCodecs.VAR_INT, SpiritPlayerData::experience,
                    SpiritPlayerData::new
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

    /**
     * Experience needed to reach the next Spirit level.
     */
    public int experienceToNextLevel() {
        return BASE_EXPERIENCE_REQUIREMENT + (level - 1) * 50;
    }

    /**
     * Adds experience and applies every level-up earned by this reward.
     * Each new level increases maximum Spirit Energy by 10 and restores 10 energy.
     */
    public SpiritPlayerData addExperience(int amount) {
        if (amount <= 0) {
            return this;
        }

        int newLevel = level;
        int newExperience = experience + amount;
        int newMaxEnergy = maxEnergy;
        int newEnergy = energy;

        while (newExperience >= BASE_EXPERIENCE_REQUIREMENT + (newLevel - 1) * 50) {
            newExperience -= BASE_EXPERIENCE_REQUIREMENT + (newLevel - 1) * 50;
            newLevel++;
            newMaxEnergy += 10;
            newEnergy = Math.min(newMaxEnergy, newEnergy + 10);
        }

        return new SpiritPlayerData(newEnergy, newMaxEnergy, newLevel, newExperience);
    }
}
