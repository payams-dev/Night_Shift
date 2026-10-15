package com.payamsdev.animatronics;

import com.payamsdev.position.CameraLocationList;

public class OrvilleElephant extends Animatronic {

	// 301 frames = ~5.01 seconds
	public OrvilleElephant(int aiLevel) {
		super("Orville Elephant", CameraLocationList.PIRATE_COVE, aiLevel, 301);
	}

	@Override
	public void move() {
		// Magician's cove stage progression / magic teleportation logic (the same as
		// foxy sprint, hear the noise, and you have a set time to react)
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
