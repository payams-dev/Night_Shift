package com.payamsdev.nightshift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DoorTestScreen extends ScreenAdapter {

	private SpriteBatch batch;
	private Texture sheet1;
	private Texture sheet2;
	private TextureRegion currentInspectFrame;

	// Solid white pixel texture to generate our dynamic contrast back-card
	private Texture whitePixelCard;

	// Persistent Memory Structure to save edits on the fly (Indices 0 to 12)
	private final int[] savedXOffsets = new int[13];
	private final int[] savedWidths = new int[13];

	// Active State Parameters
	private int activeFrameTarget = 1;
	private int cropXOffset = 0;
	private int cropWidthMultiplier = 137;

	// Slicing Constraints
	private final int s1W = 137;
	private final int s1H = 269;
	private final int s2W = 247;
	private final int s2H = 724;

	public DoorTestScreen() {
		this.batch = new SpriteBatch();
		this.sheet1 = new Texture(Gdx.files.internal("textures/office/door_sheet_close_anim.png"));
		this.sheet2 = new Texture(Gdx.files.internal("textures/office/door_sheet_anim_close_2.png"));

		// Generate a single white pixel in memory dynamically to build our background
		// block
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		this.whitePixelCard = new Texture(pixmap);
		pixmap.dispose();

		// Initialize the runtime database loop with baseline dimension presets
		for (int i = 1; i <= 12; i++) {
			savedXOffsets[i] = 0;
			savedWidths[i] = (i <= 8) ? s1W : s2W;
		}

		// Load initial history states
		loadSavedFrameData(1);
	}

	private void loadSavedFrameData(int targetFrame) {
		this.activeFrameTarget = targetFrame;
		this.cropXOffset = savedXOffsets[targetFrame];
		this.cropWidthMultiplier = savedWidths[targetFrame];

		calculateActiveFrameSlice();
	}

	private void saveCurrentFrameData() {
		savedXOffsets[activeFrameTarget] = cropXOffset;
		savedWidths[activeFrameTarget] = cropWidthMultiplier;
	}

	private void calculateActiveFrameSlice() {
		currentInspectFrame = null;

		if (activeFrameTarget <= 8) {
			int zeroIndexedFrame = activeFrameTarget - 1;
			int row = zeroIndexedFrame / 4;
			int col = zeroIndexedFrame % 4;

			int rawX = col * s1W;
			int rawY = row * s1H;

			currentInspectFrame = new TextureRegion(sheet1, rawX + cropXOffset, rawY + 3, cropWidthMultiplier, s1H - 6);
		} else {
			int zeroIndexedFrame = activeFrameTarget - 9;
			int rawX = zeroIndexedFrame * s2W;

			currentInspectFrame = new TextureRegion(sheet2, rawX + cropXOffset, 3, cropWidthMultiplier, s2H - 6);
		}

		// Print complete configuration profile directly into your terminal console
		System.out.println(String.format("[WORKBENCH] Frame: %d | Saved X-Offset: %d | Saved Crop-Width: %d",
				activeFrameTarget, cropXOffset, cropWidthMultiplier));
	}

	@Override
	public void render(float delta) {
		// Clear background to bright neon red
		Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleDeveloperInputs();

		// Calculate drawing bounds centered exactly on screen at 220x716
		float drawX = (1024 - 220) / 2f;
		float drawY = (768 - 716) / 2f;
		float drawW = 220;
		float drawH = 716;

		batch.begin();

		// --- STEP 1: RENDER THE TRIMMABLE WHITE CARD CONTRAST LAYER ---
		// This block scales dynamically. If you see RED behind it, you are looking past
		// the crop box edges!
		batch.draw(whitePixelCard, drawX, drawY, drawW, drawH);

		// --- STEP 2: RENDER THE TRANSPARENT DOOR OVERLAY ---
		if (currentInspectFrame != null) {
			batch.draw(currentInspectFrame, drawX, drawY, drawW, drawH);
		}

		batch.end();
	}

	private void handleDeveloperInputs() {
		boolean stateChanged = false;

		// Intercept navigation commands - Saves active work, then hops context lines
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			saveCurrentFrameData();
			loadSavedFrameData(1);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			saveCurrentFrameData();
			loadSavedFrameData(2);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			saveCurrentFrameData();
			loadSavedFrameData(3);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
			saveCurrentFrameData();
			loadSavedFrameData(4);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
			saveCurrentFrameData();
			loadSavedFrameData(5);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
			saveCurrentFrameData();
			loadSavedFrameData(6);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
			saveCurrentFrameData();
			loadSavedFrameData(7);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
			saveCurrentFrameData();
			loadSavedFrameData(8);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
			saveCurrentFrameData();
			loadSavedFrameData(9);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
			saveCurrentFrameData();
			loadSavedFrameData(10);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
			saveCurrentFrameData();
			loadSavedFrameData(11);
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
			saveCurrentFrameData();
			loadSavedFrameData(12);
			return;
		}

		// Incremental Alignment adjustment tracking
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			cropXOffset--;
			stateChanged = true;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			cropXOffset++;
			stateChanged = true;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			cropWidthMultiplier++;
			stateChanged = true;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			cropWidthMultiplier--;
			stateChanged = true;
		}

		if (stateChanged) {
			saveCurrentFrameData();
			calculateActiveFrameSlice();
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		sheet1.dispose();
		sheet2.dispose();
		whitePixelCard.dispose();
	}
}
