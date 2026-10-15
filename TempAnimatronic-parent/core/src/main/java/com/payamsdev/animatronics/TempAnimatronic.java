package com.payamsdev.animatronics;

import com.payamsdev.position.CameraLocationList;

public class TempAnimatronic extends Animatronic {
	// 181 frames = ~3.02 seconds
	public TempAnimatronic(int aiLevel) {
		super("Nedd Bear", CameraLocationList.SHOW_STAGE, aiLevel, 181);
	}

	@Override
	public void move() {
		// Nedd Bear move logic (e.g. check if camera is actively watched)
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