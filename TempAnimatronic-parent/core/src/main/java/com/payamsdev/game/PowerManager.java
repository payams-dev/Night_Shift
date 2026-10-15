package com.payamsdev.game; // Matches your new project structure

public class PowerManager {

	private int internalPower = 1000; // 100.0% represented as 1000 points
	private int foxyKnockCount = 0;
	private long frameCounter = 0;

	/**
	 * Ticks power usage on every frame tick (60 FPS).
	 */
	public void onFrameTick(int usageLevel, int nightNumber) {
		if (internalPower <= 0)
			return;

		frameCounter++;

		// FIX: Only drain if usageLevel is greater than 0 to prevent a crash
		if (usageLevel > 0) {
			// Base usage drain: 1 usage = -1 power per second (every 60 frames)
			if (frameCounter % (60 / usageLevel) == 0) {
				internalPower--;
			}
		}

		// Night-specific passive drain (Night 2+)
		int passiveDrainIntervalFrames = getPassiveDrainIntervalFrames(nightNumber);
		if (passiveDrainIntervalFrames > 0 && frameCounter % passiveDrainIntervalFrames == 0) {
			internalPower--;
		}
	}

	private int getPassiveDrainIntervalFrames(int nightNumber) {
		switch (nightNumber) {
		case 2:
			return 360; // -1 power every 6 seconds (360 frames)
		case 3:
			return 300; // -1 power every 5 seconds (300 frames)
		case 4:
			return 240; // -1 power every 4 seconds (240 frames)
		case 5:
		case 6:
		case 7:
			return 180; // -1 power every 3 seconds (180 frames)
		default:
			return 0; // Night 1 has no passive extra decay
		}
	}

	/**
	 * Applies Foxy's escalating power penalty when he bangs on the left door: 1st
	 * knock: -1% (10 points) 2nd knock: -6% (60 points) 3rd knock: -11% (110
	 * points), etc.
	 */
	public void applyFoxyDoorDrain() {
		foxyKnockCount++;
		int penaltyPercent = 1 + (foxyKnockCount - 1) * 5;
		int penaltyPoints = penaltyPercent * 10;

		internalPower = Math.max(0, internalPower - penaltyPoints);
		System.out.println("[POWER] Foxy banged on door! Lost " + penaltyPercent + "% power.");
	}

	public double getPowerPercentage() {
		return Math.max(0.0, internalPower / 10.0);
	}

	public boolean isPowerOut() {
		return internalPower <= 0;
	}
}