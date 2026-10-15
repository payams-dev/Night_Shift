package com.payamsdev.animatronics;

import com.payamsdev.position.CameraLocationList;

public class EnemyVariantB extends Animatronic {

	// 301 frames = ~5.01 seconds
	public EnemyVariantB(int aiLevel) {
		super("Mr. Hippo", CameraLocationList.OFFICE, aiLevel, 301);
	}

	@Override
	public void move() {
		// very small chance to appear in office, and is dismissed by a one second camera flip
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