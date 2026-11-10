package com.spiritchronicles.spirit;

/**
 * Simple Spirit Energy value object.
 *
 * This is the first gameplay prototype. Player persistence, networking,
 * regeneration ticks and ability costs will be connected in a later step.
 */
public final class SpiritEnergy {
    public static final int DEFAULT_MAX_ENERGY = 100;

    private int energy;
    private final int maxEnergy;

    public SpiritEnergy() {
        this(DEFAULT_MAX_ENERGY, DEFAULT_MAX_ENERGY);
    }

    public SpiritEnergy(int initialEnergy, int maxEnergy) {
        if (maxEnergy < 1) {
            throw new IllegalArgumentException("maxEnergy must be at least 1");
        }

        this.maxEnergy = maxEnergy;
        this.energy = clamp(initialEnergy);
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public boolean consume(int amount) {
        if (amount <= 0 || energy < amount) {
            return false;
        }

        energy -= amount;
        return true;
    }

    public void restore(int amount) {
        if (amount > 0) {
            energy = clamp(energy + amount);
        }
    }

    public void regenerate() {
        restore(1);
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(maxEnergy, value));
    }
}
