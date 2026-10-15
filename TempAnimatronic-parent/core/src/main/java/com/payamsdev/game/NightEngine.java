package com.payamsdev.game; // Updated package path

import com.payamsdev.animatronics.Animatronic;

public class NightEngine {

	private Night activeNight;
	private boolean isRunning = false;

	// Target 60 logic ticks per second (1/60th of a second per frame)
	private static final float TIME_STEP = 1f / 60f;
	private float accumulator = 0f;

	public void loadNight(Night night) {
		this.activeNight = night;
	}

	public void startNight() {
		if (activeNight == null) {
			throw new IllegalStateException("No night loaded to start.");
		}

		this.isRunning = true;

		// Activate all animatronics registered in the active night (Classic Java 8
		// loop)
		for (Animatronic animatronic : activeNight.getAnimatronics()) {
			animatronic.activate();
		}

		System.out.println("Starting Night " + activeNight.getConfig().nightNumber() + " at 12 AM...");
	}

	/**
	 * Natively driven by LibGDX update cycles. Drop this right into your render
	 * loop!
	 * 
	 * @param delta The fractional time slice elapsed since the last rendered frame.
	 */
	public void update(float delta) {
		if (!isRunning || activeNight == null) {
			return;
		}

		// Fixed Timestep Accumulator: Prevents game from running too fast or slow
		accumulator += delta;
		while (accumulator >= TIME_STEP) {
			gameTick();
			accumulator -= TIME_STEP;
		}
	}

	private void gameTick() {
		// Tick the night state
		activeNight.onFrameTick();

		// Check victory condition
		if (activeNight.isNightCompleted()) {
			stopNight();
		}
	}

	public void stopNight() {
		this.isRunning = false;
		System.out.println("Night Engine Stopped.");
	}

	public boolean isRunning() {
		return isRunning;
	}

	public Night getActiveNight() {
		return activeNight;
	}
}