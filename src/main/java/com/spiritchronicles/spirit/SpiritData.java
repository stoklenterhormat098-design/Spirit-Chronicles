package com.spiritchronicles.spirit;

/**
 * Basic data model for a Spirit. This model is not yet persisted to a player.
 */
public final class SpiritData {
    private final String spiritName;
    private final String angelName;
    private int affection;

    public SpiritData(String spiritName, String angelName) {
        if (spiritName == null || spiritName.isBlank()) {
            throw new IllegalArgumentException("spiritName cannot be blank");
        }
        if (angelName == null || angelName.isBlank()) {
            throw new IllegalArgumentException("angelName cannot be blank");
        }

        this.spiritName = spiritName;
        this.angelName = angelName;
        this.affection = 0;
    }

    public String getSpiritName() {
        return spiritName;
    }

    public String getAngelName() {
        return angelName;
    }

    public int getAffection() {
        return affection;
    }

    public void increaseAffection(int amount) {
        if (amount > 0) {
            affection += amount;
        }
    }
}
