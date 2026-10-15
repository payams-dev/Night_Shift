package com.payamsdev.animatronics;

import com.payamsdev.position.CameraLocationList;

public class PigPatch extends Animatronic {

	// 298 frames = ~4.97 seconds
	public PigPatch(int aiLevel) {
		super("Pig Patch", CameraLocationList.SHOW_STAGE, aiLevel, 298);
	}

	@Override
	public void move() {
		// Pig Patch path logic
	}

	@Override
	public void jumpscare() {
	}

	public void incrementAI(int amount) {
		setAiLevel(this.aiLevel + amount);
	}

	public void setAiLevel(int aiLevel) {
		// Clamps AI between 0 and 20
		this.aiLevel = Math.max(0, Math.min(20, aiLevel));
	}

}