package com.payamsdev.game; // Updated package path

import com.payamsdev.position.Camera; // Updated import path

public class PlayerState {

	private boolean leftDoorClosed = false;
	private boolean rightDoorClosed = false;
	private boolean leftLightOn = false;
	private boolean rightLightOn = false;
	private boolean monitorUp = false;
	private Camera currentCamera = Camera.CAM_1A; // Default starting cam

	public void toggleLeftDoor() {
		this.leftDoorClosed = !this.leftDoorClosed;
	}

	public void toggleRightDoor() {
		this.rightDoorClosed = !this.rightDoorClosed;
	}

	public void toggleLeftLight() {
		this.leftLightOn = !this.leftLightOn;
		if (this.leftLightOn) {
			this.rightLightOn = false; // FNAF 1 rule: Only one light on at a time
		}
	}

	public void toggleRightLight() {
		this.rightLightOn = !this.rightLightOn;
		if (this.rightLightOn) {
			this.leftLightOn = false;
		}
	}

	public void toggleMonitor() {
		this.monitorUp = !this.monitorUp;
		if (this.monitorUp) {
			// Turning on monitor turns off hall lights
			this.leftLightOn = false;
			this.rightLightOn = false;
		}
	}

	/**
	 * Calculates active power usage bars (1 to 5).
	 */
	public int getUsageLevel() {
		int usage = 1; // Base fan usage
		if (leftDoorClosed)
			usage++;
		if (rightDoorClosed)
			usage++;
		if (leftLightOn)
			usage++;
		if (rightLightOn)
			usage++;
		if (monitorUp)
			usage++;
		return Math.min(5, usage);
	}

	public boolean isLeftDoorClosed() {
		return leftDoorClosed;
	}

	public boolean isRightDoorClosed() {
		return rightDoorClosed;
	}

	public boolean isLeftLightOn() {
		return leftLightOn;
	}

	public boolean isRightLightOn() {
		return rightLightOn;
	}

	public boolean isMonitorUp() {
		return monitorUp;
	}

	public Camera getCurrentCamera() {
		return currentCamera;
	}

	public void setCurrentCamera(Camera camera) {
		this.currentCamera = camera;
	}

	public void forcePowerOutageState() {
		this.leftDoorClosed = false;
		this.rightDoorClosed = false;
		this.leftLightOn = false;
		this.rightLightOn = false;
		this.monitorUp = false;
	}
}