package com.payamsdev.nightshift;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.payamsdev.game.Night;
import com.payamsdev.game.NightConfig;
import com.payamsdev.game.NightEngine;
import com.payamsdev.game.PlayerState;
import com.payamsdev.game.PowerManager;
import com.payamsdev.position.Camera;

import java.util.HashMap;
import java.util.Map;

public class GameScreen extends ScreenAdapter {

	private final Game game;
	private final NightEngine engine;
	private final Night night;
	private final PlayerState playerState;
	private final PowerManager powerManager;

	private SpriteBatch batch;
	private Stage hudStage;

	// Office Scenery
	private Texture officeSheet;
	private TextureRegion[] officeScreens;
	private TextureRegion currentOfficeView;

	private Texture buttonSheet;
	private TextureRegion[] leftButtons;
	private TextureRegion[] rightButtons;

	private Texture whitePixelCard;

	private Texture doorClosedTexture;
	private TextureRegion fullyClosedDoorAsset;
	private Texture doorSheetPart1;
	private Texture doorSheetPart2;
	private TextureRegion[] doorAnimationFrames;

	// Monitor Flipping Setup
	private Texture cameraSheet;
	private TextureRegion[] monitorFlipFrames;
	private float monitorFrameIndex = 0f;
	private final float MONITOR_ANIM_SPEED = 35f;
	private boolean actualCamFeedVisible = false;

	// Static Loop Assets
	private Texture staticSheet;
	private Animation<TextureRegion> staticAnimation;
	private float stateTime = 0f;

	// Individual Camera Feed Textures Matrix
	private TextureRegion[] cameraRoomFeeds;

	// Camera Clickable Map Buttons (Mapped by Enum)
	private final Map<Camera, Rectangle> camMapHitboxes = new HashMap<Camera, Rectangle>();

	// Animation frame interpolation clocks
	private float leftDoorFrameIndex = 0f;
	private float rightDoorFrameIndex = 0f;
	private final float DOOR_ANIM_SPEED = 28f;

	// Monitor Hover Logic
	private boolean monitorDebounceZone = false;
	private float monitorCooldownTimer = 0f;

	// HUD Elements
	private Label timeLabel;
	private Label powerLabel;
	private Label usageLabel;
	private BitmapFont font;

	// Panning Mechanics Parameters
	private float officeCameraX = 288f;
	private final float MAX_PAN_X = 1600f - 1024f;
	private final float PAN_SPEED = 1400f;

	// Position Registries
	private final float LEFT_BTN_CANVAS_X = 1f;
	private final float RIGHT_BTN_CANVAS_X = 1465f;
	private final float BTN_CANVAS_Y = 240f;
	private final float BTN_WIDTH = 97f;
	private final float BTN_HEIGHT = 253f;

	private final float LEFT_DOOR_CANVAS_X = 85f;
	private final float RIGHT_DOOR_CANVAS_X = 1265f;
	private final float DOOR_CANVAS_Y = 0f;
	private final float TARGET_DOOR_W = 220f;
	private final float TARGET_DOOR_H = 716f;

	private Rectangle leftButtonsHitbox;
	private Rectangle rightButtonsHitbox;

	public GameScreen(Game game) {
		this.game = game;
		this.batch = new SpriteBatch();

		NightConfig config = new NightConfig(1, 1, 0, 1, 0, 0);
		this.night = new Night(config);
		this.playerState = night.getPlayerState();
		this.powerManager = night.getPowerManager();

		this.engine = new NightEngine();
		engine.loadNight(night);
		engine.startNight();

		hudStage = new Stage(new FitViewport(1024, 768));

		leftButtonsHitbox = new Rectangle(LEFT_BTN_CANVAS_X, BTN_CANVAS_Y, BTN_WIDTH, BTN_HEIGHT);
		rightButtonsHitbox = new Rectangle(RIGHT_BTN_CANVAS_X, BTN_CANVAS_Y, BTN_WIDTH, BTN_HEIGHT);

		// HARDCODED CAMERA BUTTON HITBOXES (Placed over a virtual layout on the right
		// side of the screen)
		camMapHitboxes.put(Camera.CAM_1A, new Rectangle(750, 520, 80, 45)); // Show Stage
		camMapHitboxes.put(Camera.CAM_1B, new Rectangle(730, 440, 80, 45)); // Dining Area
		camMapHitboxes.put(Camera.CAM_1C, new Rectangle(670, 370, 80, 45)); // Pirate Cove
		camMapHitboxes.put(Camera.CAM_2A, new Rectangle(710, 240, 80, 45)); // West Hall
		camMapHitboxes.put(Camera.CAM_2B, new Rectangle(710, 170, 80, 45)); // West Hall Corner
		camMapHitboxes.put(Camera.CAM_3, new Rectangle(630, 210, 80, 45)); // Supply Closet
		camMapHitboxes.put(Camera.CAM_4A, new Rectangle(850, 240, 80, 45)); // East Hall
		camMapHitboxes.put(Camera.CAM_4B, new Rectangle(850, 170, 80, 45)); // East Hall Corner
		camMapHitboxes.put(Camera.CAM_5, new Rectangle(610, 480, 80, 45)); // Backstage
		camMapHitboxes.put(Camera.CAM_6, new Rectangle(910, 290, 80, 45)); // Kitchen
		camMapHitboxes.put(Camera.CAM_7, new Rectangle(910, 440, 80, 45)); // Restrooms

		loadOfficeAssets();
		setupHudUI();
		setupInputHandling();
	}

	private void loadOfficeAssets() {
		officeSheet = new Texture(Gdx.files.internal("textures/office/office_sheet.png"));
		officeScreens = new TextureRegion[3];
		officeScreens[0] = new TextureRegion(officeSheet, 0, 0, 1600, 722);
		officeScreens[1] = new TextureRegion(officeSheet, 0, 722 * 2, 1600, 722);
		officeScreens[2] = new TextureRegion(officeSheet, 0, 722 * 3, 1600, 722);
		currentOfficeView = officeScreens[0];

		buttonSheet = new Texture(Gdx.files.internal("textures/office/button_sheet.png"));
		leftButtons = new TextureRegion[4];
		rightButtons = new TextureRegion[4];
		int btnW = 97;
		int btnH = 253;
		int trimBtn = 4;
		for (int i = 0; i < 4; i++) {
			leftButtons[i] = new TextureRegion(buttonSheet, (i * btnW) + trimBtn, trimBtn, btnW - (trimBtn * 2),
					btnH - (trimBtn * 2));
			rightButtons[i] = new TextureRegion(buttonSheet, (i * btnW) + trimBtn, btnH + trimBtn, btnW - (trimBtn * 2),
					btnH - (trimBtn * 2));
		}

		com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(1, 1,
				com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
		pixmap.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		pixmap.fill();
		this.whitePixelCard = new Texture(pixmap);
		pixmap.dispose();

		doorClosedTexture = new Texture(Gdx.files.internal("textures/office/door_closed.png"));
		fullyClosedDoorAsset = new TextureRegion(doorClosedTexture, 20, 0, 200, 716);

		doorSheetPart1 = new Texture(Gdx.files.internal("textures/office/door_sheet_close_anim.png"));
		doorSheetPart2 = new Texture(Gdx.files.internal("textures/office/door_sheet_anim_close_2.png"));
		doorAnimationFrames = new TextureRegion[13];
		int[] workbenchOffsets = { 0, 4, 52, 7, -37, 2, -42, 6, -38, -27, 2, 4, 6 };
		int[] workbenchWidths = { 0, 81, 82, 82, 82, 82, 82, 83, 83, 247, 220, 219, 220 };
		int s1W = 137;
		int s1H = 269;
		int s2W = 247;
		int s2H = 724;

		for (int frame = 1; frame <= 12; frame++) {
			int offset = workbenchOffsets[frame];
			int width = workbenchWidths[frame];
			if (frame <= 8) {
				int zeroIndexed = frame - 1;
				doorAnimationFrames[frame] = new TextureRegion(doorSheetPart1, (zeroIndexed % 4) * s1W + offset,
						(zeroIndexed / 4) * s1H + 5, width, s1H - 10);
			} else {
				doorAnimationFrames[frame] = new TextureRegion(doorSheetPart2, (frame - 9) * s2W + offset, 5, width,
						s2H - 10);
			}
		}

		cameraSheet = new Texture(Gdx.files.internal("textures/office/camera_sheet.png"));
		monitorFlipFrames = new TextureRegion[12];
		int cellW = 1282;
		int cellH = 722;
		int razorTrim = 2;
		int counter = 1;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 4; col++) {
				if (counter > 11)
					break;
				monitorFlipFrames[counter] = new TextureRegion(cameraSheet, (col * cellW) + razorTrim,
						(row * cellH) + razorTrim, cellW - (razorTrim * 2), cellH - (razorTrim * 2));
				counter++;
			}
		}

		staticSheet = new Texture(Gdx.files.internal("textures/static_sheet.png"));
		int staticW = staticSheet.getWidth() / 8;
		int staticH = staticSheet.getHeight();
		TextureRegion[][] tmpStatic = TextureRegion.split(staticSheet, staticW, staticH);
		TextureRegion[] staticFrames = new TextureRegion[8];
		for (int i = 0; i < 8; i++) {
			staticFrames[i] = tmpStatic[0][i];
		}
		staticAnimation = new Animation<TextureRegion>(0.04f, staticFrames);
		staticAnimation.setPlayMode(Animation.PlayMode.LOOP);

		cameraRoomFeeds = new TextureRegion[Camera.values().length];
		cameraRoomFeeds[Camera.CAM_1A.ordinal()] = new TextureRegion(
				new Texture(Gdx.files.internal("textures/cams/Show Stage.png")));
		cameraRoomFeeds[Camera.CAM_1B.ordinal()] = new TextureRegion(
				new Texture(Gdx.files.internal("textures/cams/dining_area.png")));
		cameraRoomFeeds[Camera.CAM_1C.ordinal()] = new TextureRegion(
				new Texture(Gdx.files.internal("textures/cams/Pirate Cove.png")));
		cameraRoomFeeds[Camera.CAM_2A.ordinal()] = new TextureRegion(
				new Texture(Gdx.files.internal("textures/cams/West Hall.png")));
		cameraRoomFeeds[Camera.CAM_4A.ordinal()] = new TextureRegion(
				new Texture(Gdx.files.internal("textures/cams/East Hall.png")));
		cameraRoomFeeds[Camera.CAM_5.ordinal()] = new TextureRegion(
				new Texture(Gdx.files.internal("textures/cams/backstage.png")));
		cameraRoomFeeds[Camera.CAM_7.ordinal()] = new TextureRegion(
				new Texture(Gdx.files.internal("textures/cams/Restrooms.png")));

		for (Camera cam : Camera.values()) {
			if (cameraRoomFeeds[cam.ordinal()] == null) {
				cameraRoomFeeds[cam.ordinal()] = officeScreens[0];
			}
		}
	}

	private void setupInputHandling() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override

			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if (button != Input.Buttons.LEFT)
					return false;
				// --- MONITOR BUTTON INTERACTION DISPATCH ---
				if (playerState.isMonitorUp() && actualCamFeedVisible) {
					float clickX = screenX;
					float clickY = 768 - screenY; // Flip Y for UI coordinates
					// Scan our buttons map registry to see which box node was clicked!
					for (Map.Entry<Camera, Rectangle> entry : camMapHitboxes.entrySet()) {
						if (entry.getValue().contains(clickX, clickY)) {
							playerState.setCurrentCamera(entry.getKey());
							System.out.println("[CAM MAP] Switched to: " + entry.getKey());
							return true;
						}
					}
					return true;
				}
				if (actualCamFeedVisible)
					return true;
				float worldX = screenX + officeCameraX;
				float worldY = 768 - screenY;
				if (leftButtonsHitbox.contains(worldX, worldY)) {
					if (worldY > BTN_CANVAS_Y + (BTN_HEIGHT / 2))
						playerState.toggleLeftDoor();
					else
						playerState.toggleLeftLight();
					return true;
				}
				if (rightButtonsHitbox.contains(worldX, worldY)) {
					if (worldY > BTN_CANVAS_Y + (BTN_HEIGHT / 2))
						playerState.toggleRightDoor();
					else
						playerState.toggleRightLight();
					return true;
				}
				return false;
			}
		});
	}

	private void setupHudUI() {
		font = new BitmapFont();
		font.getData().setScale(1.5f);
		LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
		Table hudTable = new Table();
		hudTable.top().left().setFillParent(true);
		hudTable.pad(15);
		timeLabel = new Label("12 AM", labelStyle);
		powerLabel = new Label("Power: 100%", labelStyle);
		usageLabel = new Label("Usage: I", labelStyle);
		hudTable.add(timeLabel).expandX().align(Align.left);
		hudTable.add(powerLabel).expandX().align(Align.center);
		hudTable.add(usageLabel).expandX().align(Align.right);
		hudStage.addActor(hudTable);
	}

	@Override
	public void render(float delta) {
		if (monitorCooldownTimer > 0) {
			monitorCooldownTimer -= delta;
		}
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.input.getY();
		if (mouseY > 768 - 35) {
			if (!monitorDebounceZone && monitorCooldownTimer <= 0) {
				playerState.toggleMonitor();
				monitorCooldownTimer = 0.4f;
				monitorDebounceZone = true;
			}
		} else {
			monitorDebounceZone = false;
		}
		stateTime += delta;
		engine.update(delta);
		updateDoorAnimations(delta);
		updateMonitorFlipAnimations(delta);
		refreshHudData();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (playerState.isLeftLightOn())
			currentOfficeView = officeScreens[1];
		else if (playerState.isRightLightOn())
			currentOfficeView = officeScreens[2];
		else
			currentOfficeView = officeScreens[0];
		TextureRegion activeStaticFrame = staticAnimation.getKeyFrame(stateTime);
// --- DRAW LOOP LAYER ---
		batch.begin();
		if (actualCamFeedVisible) {
// --- MONITOR UP: SURVEILLANCE CAM SYSTEM ---
			Camera activeCamera = playerState.getCurrentCamera();
			TextureRegion currentRoomFeed = cameraRoomFeeds[activeCamera.ordinal()];
			batch.draw(currentRoomFeed, 0, 0, 1024, 768);
// Heavy Camera Static Overlay (18% Opacity)
			batch.setColor(1f, 1f, 1f, 0.18f);
			batch.draw(activeStaticFrame, 0, 0, 1024, 768);
			batch.setColor(1f, 1f, 1f, 1f);
// --- RENDER DYNAMIC RADAR MAP OVERLAY HUD BUTTONS ---
			drawCameraRadarMapUI();
		} else {
// --- MONITOR DOWN: SECURITY OFFICE VIEW ---
			updateOfficePanning(delta);
			batch.draw(currentOfficeView, -officeCameraX, 0, 1600, 768);
// Subtle Office Ambient Static Overlay (7% Opacity)
			batch.setColor(1f, 1f, 1f, 0.07f);
			batch.draw(activeStaticFrame, -officeCameraX, 0, 1600, 768);
			batch.setColor(1f, 1f, 1f, 1f);
			int leftFrame = (int) leftDoorFrameIndex;
			if (leftFrame >= 12) {
				batch.draw(fullyClosedDoorAsset, LEFT_DOOR_CANVAS_X - officeCameraX + TARGET_DOOR_W, DOOR_CANVAS_Y,
						-TARGET_DOOR_W, TARGET_DOOR_H);
			} else if (leftFrame > 0) {
				batch.draw(doorAnimationFrames[leftFrame], LEFT_DOOR_CANVAS_X - officeCameraX + TARGET_DOOR_W,
						DOOR_CANVAS_Y, -TARGET_DOOR_W, TARGET_DOOR_H);
			}
			int rightFrame = (int) rightDoorFrameIndex;
			if (rightFrame >= 12) {
				batch.draw(fullyClosedDoorAsset, RIGHT_DOOR_CANVAS_X - officeCameraX, DOOR_CANVAS_Y, TARGET_DOOR_W,
						TARGET_DOOR_H);
			} else if (rightFrame > 0) {
				batch.draw(doorAnimationFrames[rightFrame], RIGHT_DOOR_CANVAS_X - officeCameraX, DOOR_CANVAS_Y,
						TARGET_DOOR_W, TARGET_DOOR_H);
			}
			batch.setColor(Color.BLACK);
			batch.draw(whitePixelCard, LEFT_DOOR_CANVAS_X - officeCameraX, 0f, TARGET_DOOR_W, 15f);
			batch.draw(whitePixelCard, RIGHT_DOOR_CANVAS_X - officeCameraX, 0f, TARGET_DOOR_W, 15f);
			batch.setColor(Color.WHITE);
			int leftStateIndex = 0;
			if (playerState.isLeftLightOn() && playerState.isLeftDoorClosed())
				leftStateIndex = 3;
			else if (playerState.isLeftDoorClosed())
				leftStateIndex = 2;
			else if (playerState.isLeftLightOn())
				leftStateIndex = 1;
			batch.draw(leftButtons[leftStateIndex], LEFT_BTN_CANVAS_X - officeCameraX, BTN_CANVAS_Y, BTN_WIDTH,
					BTN_HEIGHT);
			int rightStateIndex = 0;
			if (playerState.isRightLightOn() && playerState.isRightDoorClosed())
				rightStateIndex = 3;
			else if (playerState.isRightDoorClosed())
				rightStateIndex = 2;
			else if (playerState.isRightLightOn())
				rightStateIndex = 1;
			batch.draw(rightButtons[rightStateIndex], RIGHT_BTN_CANVAS_X - officeCameraX, BTN_CANVAS_Y, BTN_WIDTH,
					BTN_HEIGHT);
		}
// Monitor flip transition overlay
		int flipFrame = (int) monitorFrameIndex;
		if (flipFrame > 0 && flipFrame <= 11) {
			batch.draw(monitorFlipFrames[flipFrame], 0, 0, 1024, 768);
		}
// Bottom hover bar graphic
		batch.setColor(1f, 1f, 1f, 0.40f);
		batch.draw(whitePixelCard, (1024 - 400) / 2f, 0f, 400f, 15f);
		batch.setColor(1f, 1f, 1f, 1f);
		batch.end();
		hudStage.act(delta);
		hudStage.draw();
	}

	/**
	 * Draws the radar selector nodes layout onto the active camera view screen
	 */
	private void drawCameraRadarMapUI() {
		Camera activeCam = playerState.getCurrentCamera();
		for (Map.Entry<Camera, Rectangle> entry : camMapHitboxes.entrySet()) {
			Rectangle box = entry.getValue();
			boolean isActive = (entry.getKey() == activeCam);
// Draw the button button backing card block
			batch.setColor(isActive ? Color.GREEN : Color.DARK_GRAY);
			batch.draw(whitePixelCard, box.x, box.y, box.width, box.height);
			batch.setColor(Color.WHITE);
// Draw the crisp overlay text code labels centered inside the blocks
			font.setColor(isActive ? Color.BLACK : Color.WHITE);
			font.draw(batch, entry.getKey().toString().replace("Camera ", "CAM "), box.x + 8,
					box.y + box.height / 2f + 6);
		}
	}

	private void updateOfficePanning(float delta) {
		float mouseX = Gdx.input.getX();
		float leftScrollZone = 1024f * 0.33f;
		float rightScrollZone = 1024f * 0.67f;
		if (mouseX < leftScrollZone) {
			float speedFactor = (leftScrollZone - mouseX) / leftScrollZone;
			officeCameraX -= PAN_SPEED * speedFactor * delta;
		} else if (mouseX > rightScrollZone) {
			float speedFactor = (mouseX - rightScrollZone) / (1024f - rightScrollZone);
			officeCameraX += PAN_SPEED * speedFactor * delta;
		}
		officeCameraX = Math.max(0f, Math.min(MAX_PAN_X, officeCameraX));
	}

	private void updateDoorAnimations(float delta) {
		if (playerState.isLeftDoorClosed()) {
			leftDoorFrameIndex += DOOR_ANIM_SPEED * delta;
			if (leftDoorFrameIndex > 12f)
				leftDoorFrameIndex = 12f;
		} else {
			leftDoorFrameIndex -= DOOR_ANIM_SPEED * delta;
			if (leftDoorFrameIndex < 0f)
				leftDoorFrameIndex = 0f;
		}
		if (playerState.isRightDoorClosed()) {
			rightDoorFrameIndex += DOOR_ANIM_SPEED * delta;
			if (rightDoorFrameIndex > 12f)
				rightDoorFrameIndex = 12f;
		} else {
			rightDoorFrameIndex -= DOOR_ANIM_SPEED * delta;
			if (rightDoorFrameIndex < 0f)
				rightDoorFrameIndex = 0f;
		}
	}

	private void updateMonitorFlipAnimations(float delta) {
		if (playerState.isMonitorUp()) {
			monitorFrameIndex += MONITOR_ANIM_SPEED * delta;
			if (monitorFrameIndex >= 11f) {
				monitorFrameIndex = 11f;
				actualCamFeedVisible = true;
			}
		} else {
			actualCamFeedVisible = false;
			monitorFrameIndex -= MONITOR_ANIM_SPEED * delta;
			if (monitorFrameIndex < 0f) {
				monitorFrameIndex = 0f;
			}
		}
	}

	private void refreshHudData() {
		timeLabel.setText(night.getFormattedHour());
		powerLabel.setText(String.format("Power: %.1f%%", powerManager.getPowerPercentage()));
		int usage = playerState.getUsageLevel();
		StringBuilder usageBars = new StringBuilder("Usage: ");
		for (int i = 0; i < usage; i++) {
			usageBars.append("I");
		}
		usageLabel.setText(usageBars.toString());
	}

	@Override
	public void resize(int width, int height) {
		hudStage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
		hudStage.dispose();
		officeSheet.dispose();
		buttonSheet.dispose();
		doorClosedTexture.dispose();
		doorSheetPart1.dispose();
		doorSheetPart2.dispose();
		cameraSheet.dispose();
		staticSheet.dispose();
		whitePixelCard.dispose();
		font.dispose();
		engine.stopNight();
	}
}