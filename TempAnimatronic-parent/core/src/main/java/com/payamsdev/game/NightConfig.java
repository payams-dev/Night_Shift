package com.payamsdev.game; // Updated to your new package structure

/**
 * Immutable configuration for night settings.
 */
public final class NightConfig {
    
    public static final int DEFAULT_SECONDS_PER_HOUR = 86;

    private final int nightNumber;
    private final int enemyVariantAAi;
    private final int enemyVariantBAi;
    private final int temp_animatronicAi;
    private final int enemyVariantCAi;
    private final int enemyVariantDAi;
    private final int secondsPerHour;

    public NightConfig(int nightNumber, int enemyVariantAAi, int enemyVariantBAi, int temp_animatronicAi, int enemyVariantCAi, int enemyVariantDAi, int secondsPerHour) {
        this.nightNumber = nightNumber;
        this.enemyVariantAAi = enemyVariantAAi;
        this.enemyVariantBAi = enemyVariantBAi;
        this.temp_animatronicAi = temp_animatronicAi;
        this.enemyVariantCAi = enemyVariantCAi;
        this.enemyVariantDAi = enemyVariantDAi;
        this.secondsPerHour = secondsPerHour;
    }

    // Overloaded constructor that uses the standard 86-second hour length automatically
    public NightConfig(int nightNumber, int enemyVariantAAi, int enemyVariantBAi, int temp_animatronicAi, int enemyVariantCAi, int enemyVariantDAi) {
        this(nightNumber, enemyVariantAAi, enemyVariantBAi, temp_animatronicAi, enemyVariantCAi, enemyVariantDAi, DEFAULT_SECONDS_PER_HOUR);
    }

    public int nightNumber() {
        return nightNumber;
    }

    public int enemyVariantAAi() {
        return enemyVariantAAi;
    }

    public int enemyVariantBAi() {
        return enemyVariantBAi;
    }

    public int temp_animatronicAi() {
        return temp_animatronicAi;
    }

    public int enemyVariantCAi() {
        return enemyVariantCAi;
    }

    public int enemyVariantDAi() {
        return enemyVariantDAi;
    }

    public int secondsPerHour() {
        return secondsPerHour;
    }
}