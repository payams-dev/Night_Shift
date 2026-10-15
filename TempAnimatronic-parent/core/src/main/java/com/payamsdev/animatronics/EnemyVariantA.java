

package com.payamsdev.animatronics;

import com.payamsdev.position.CameraLocationList;

public class EnemyVariantA extends Animatronic {

	// 299 frames = ~4.98 seconds
	public EnemyVariantA(int aiLevel) {
		super("Happy Frog", CameraLocationList.SHOW_STAGE, aiLevel, 299);
	}

	@Override
	public void move() {
		// Happy Frog path logic
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
