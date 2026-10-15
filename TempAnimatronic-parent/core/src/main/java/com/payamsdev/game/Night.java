package com.payamsdev.game; // Maps to your core gameplay directory

import com.payamsdev.animatronics.*;
import com.payamsdev.game.PlayerState; // Adjust package path if you place these in a .game subfolder
import com.payamsdev.game.PowerManager; // Adjust package path if you place these in a .game subfolder
import com.payamsdev.game.NightConfig; // Adjust package path if you place these in a .game subfolder
import java.util.List;

public class Night {

	private final NightConfig config;
	private final PlayerState playerState;
	private final PowerManager powerManager;

	// The New Mediocre Melodies Roster
	private final HappyFrog happyFrog;
	private final MrHippo mrHippo;
	private final TempAnimatronic temp_animatronic;
	private final OrvilleElephant orvilleElephant;
	private final PigPatch pigPatch;
	private final List<Animatronic> animatronics;

	private int currentHour = 0;
	private long totalFramesElapsed = 0;
	private final long framesPerHour;
	private boolean nightCompleted = false;

	public Night(NightConfig config) {
		this.config = config;
		this.framesPerHour = config.secondsPerHour() * 60L;
		this.playerState = new PlayerState();
		this.powerManager = new PowerManager();

		// Instantiate your brand-new custom roster using the new config values
		this.happyFrog = new HappyFrog(config.happyFrogAi());
		this.mrHippo = new MrHippo(config.mrHippoAi());
		this.temp_animatronic = new TempAnimatronic(config.temp_animatronicAi());
		this.orvilleElephant = new OrvilleElephant(config.orvilleElephantAi());
		this.pigPatch = new PigPatch(config.pigPatchAi());

		// Pack them all safely into your tick tracking list
		this.animatronics = List.of(happyFrog, mrHippo, temp_animatronic, orvilleElephant, pigPatch);
	}

	/**
	 * Natively powered frame loop step. Called every render step by LibGDX context.
	 */
	public void onFrameTick() {
		if (nightCompleted)
			return;

		totalFramesElapsed++;

		// 1. Tick Power
		if (!powerManager.isPowerOut()) {
			powerManager.onFrameTick(playerState.getUsageLevel(), config.nightNumber());
			if (powerManager.isPowerOut()) {
				System.out.println("--- POWER OUTAGE! ---");
				playerState.forcePowerOutageState();
			}
		}

		// 2. Check 1-Hour Clock Progression
		if (totalFramesElapsed >= (currentHour + 1) * framesPerHour) {
			advanceHour();
		}

		// 3. Tick Animatronics
		for (Animatronic animatronic : animatronics) {
			// Camera/audio stall behaviors should wrap animatronic.onFrameTick()
			animatronic.onFrameTick();
		}
	}

	private void advanceHour() {
		currentHour++;
		System.out.println("--- THE TIME IS NOW " + getFormattedHour() + " ---");
		applyHourlyAggressionBoosts(currentHour);

		if (currentHour >= 6) {
			nightCompleted = true;
			System.out.println("6 AM! Night " + config.nightNumber() + " Completed!");
		}
	}

	private void applyHourlyAggressionBoosts(int hour) {
		// Custom progression tracking customized to step up the new crew's AI levels
		// (Java 8 format)
		switch (hour) {
		case 2:
			happyFrog.incrementAI(1);
			break;
		case 3:
			happyFrog.incrementAI(1);
			temp_animatronic.incrementAI(1);
			pigPatch.incrementAI(1);
			break;
		case 4:
			happyFrog.incrementAI(1);
			temp_animatronic.incrementAI(1);
			orvilleElephant.incrementAI(1);
			break;
		default:
			// No boosts for hours 1, 5, or 6
			break;
		}
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public PowerManager getPowerManager() {
		return powerManager;
	}

	public String getFormattedHour() {
		return (currentHour == 0 ? 12 : currentHour) + " AM";
	}

	public List<Animatronic> getAnimatronics() {
		return animatronics;
	}

	public boolean isNightCompleted() {
		return nightCompleted;
	}

	public NightConfig getConfig() {
		return config;
	}
}