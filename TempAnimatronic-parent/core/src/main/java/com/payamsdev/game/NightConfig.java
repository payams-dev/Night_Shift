package com.payamsdev.game; // Updated to your new package structure

/**
 * Immutable configuration for night settings.
 */
public final class NightConfig {
    
    public static final int DEFAULT_SECONDS_PER_HOUR = 86;

    private final int nightNumber;
    private final int happyFrogAi;
    private final int mrHippoAi;
    private final int temp_animatronicAi;
    private final int orvilleElephantAi;
    private final int pigPatchAi;
    private final int secondsPerHour;

    public NightConfig(int nightNumber, int happyFrogAi, int mrHippoAi, int temp_animatronicAi, int orvilleElephantAi, int pigPatchAi, int secondsPerHour) {
        this.nightNumber = nightNumber;
        this.happyFrogAi = happyFrogAi;
        this.mrHippoAi = mrHippoAi;
        this.temp_animatronicAi = temp_animatronicAi;
        this.orvilleElephantAi = orvilleElephantAi;
        this.pigPatchAi = pigPatchAi;
        this.secondsPerHour = secondsPerHour;
    }

    // Overloaded constructor that uses the standard 86-second hour length automatically
    public NightConfig(int nightNumber, int happyFrogAi, int mrHippoAi, int temp_animatronicAi, int orvilleElephantAi, int pigPatchAi) {
        this(nightNumber, happyFrogAi, mrHippoAi, temp_animatronicAi, orvilleElephantAi, pigPatchAi, DEFAULT_SECONDS_PER_HOUR);
    }

    public int nightNumber() {
        return nightNumber;
    }

    public int happyFrogAi() {
        return happyFrogAi;
    }

    public int mrHippoAi() {
        return mrHippoAi;
    }

    public int temp_animatronicAi() {
        return temp_animatronicAi;
    }

    public int orvilleElephantAi() {
        return orvilleElephantAi;
    }

    public int pigPatchAi() {
        return pigPatchAi;
    }

    public int secondsPerHour() {
        return secondsPerHour;
    }
}