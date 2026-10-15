package com.payamsdev.animatronics;

import java.util.concurrent.ThreadLocalRandom;

import com.payamsdev.position.Location;


public abstract class Animatronic {

    private final String name;
    private Location currentLocation;
    private Location startingLocation;
    protected int aiLevel;
    private boolean active = false;

    // FNAF frame counters
    private final int rollIntervalFrames;
    private int frameCountdown;

    public Animatronic(String name, Location startingLocation, int initialAiLevel, int rollIntervalFrames) {
        this.name = name;
        this.startingLocation = startingLocation;
        this.currentLocation = startingLocation;
        setAiLevel(initialAiLevel);
        this.rollIntervalFrames = rollIntervalFrames;
        this.frameCountdown = rollIntervalFrames;
    }

    /**
     * Called by the NightEngine once every frame tick (60 FPS).
     */
    public void onFrameTick() {
        if (!active) {
            return;
        }

        frameCountdown--;

        if (frameCountdown <= 0) {
            frameCountdown = rollIntervalFrames; // Reset internal countdown
            
            if (checkMoveOpportunity()) {
                move();
            }
        }
    }

    public boolean checkMoveOpportunity() {
        if (aiLevel <= 0) return false;
        if (aiLevel >= 20) return true;

        int roll = ThreadLocalRandom.current().nextInt(1, 21); // 1 to 20 inclusive
        return roll <= aiLevel;
    }

    public abstract void move();
    public abstract void jumpscare();

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void setAiLevel(int aiLevel) {
        this.aiLevel = Math.max(0, Math.min(20, aiLevel));
    }

    public String getName() {
        return name;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    protected void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public int getAiLevel() {
        return aiLevel;
    }

    public boolean isActive() {
        return active;
    }
}