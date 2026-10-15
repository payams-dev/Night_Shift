package com.payamsdev.nightshift;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.Random;

public class MainMenuScreen extends ScreenAdapter {

	private final Game game;
	private Stage stage;
	private SpriteBatch batch;

	private Texture normalBackground;
	private Texture glitchBackground;
	private Texture staticSheet;

	private Music menuAmbient;
	private Sound menuBlip;

	// Pure Native Bitmaps - No FreeType extensions needed!
	private BitmapFont menuFont;

	private Animation<TextureRegion> staticAnimation;
	private float stateTime = 0f;
	private boolean showGlitchFrame = false;
	private float glitchTimer = 0f;
	private final Random random = new Random();

	private TextButton newGameBtn;
	private TextButton continueBtn;
	private TextButton exitBtn;
	private int savedNight = 1;

	public MainMenuScreen(Game game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.stage = new Stage(new FitViewport(1024, 768));
		Gdx.input.setInputProcessor(stage);

		loadAssets();
		setupMenu();
	}

	private void loadAssets() {
		normalBackground = new Texture(Gdx.files.internal("textures/temp_animatronic_normal.png"));
		glitchBackground = new Texture(Gdx.files.internal("textures/temp_animatronic_glitch.png"));

		staticSheet = new Texture(Gdx.files.internal("textures/static_sheet.png"));
		int frameWidth = staticSheet.getWidth() / 8;
		int frameHeight = staticSheet.getHeight();
		TextureRegion[][] tmp = TextureRegion.split(staticSheet, frameWidth, frameHeight);
		TextureRegion[] staticFrames = new TextureRegion[8];
		for (int i = 0; i < 8; i++) {
			staticFrames[i] = tmp[0][i];
		}
		
		staticAnimation = new Animation<TextureRegion>(0.05f, staticFrames);
		staticAnimation.setPlayMode(Animation.PlayMode.LOOP);

	
		
		menuAmbient = Gdx.audio.newMusic(Gdx.files.internal("audio/night_ambient.ogg"));
		menuAmbient.setLooping(true);
		menuAmbient.setVolume(0.7f);
		menuAmbient.play();

		menuBlip = Gdx.audio.newSound(Gdx.files.internal("audio/ui_select.ogg"));

	

		// LOAD YOUR CRISP CONVERTED FNAF FONT DIRECTLY FROM ASSETS!
		menuFont = new BitmapFont(Gdx.files.internal("fonts/nightshift_font.fnt"));
		menuFont.setUseIntegerPositions(false);
	}

	private void setupMenu() {
		// Use native font scales to differentiate sizes safely
		LabelStyle titleStyle = new LabelStyle(menuFont, Color.WHITE);
		LabelStyle subStyle = new LabelStyle(menuFont, Color.GRAY);

		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.font = menuFont;
		btnStyle.fontColor = Color.WHITE;
		btnStyle.downFontColor = Color.LIGHT_GRAY; // NO RED ON CLICKS

		Table rootTable = new Table();
		rootTable.setFillParent(true);
		rootTable.left().padLeft(80);

		// Multi-line Title Layout - All text is White, uniformly scaled
		Table titleTable = new Table();
        
		Label line1 = new Label("Night", titleStyle); line1.setFontScale(1.6f);
		Label line2 = new Label("Shift", titleStyle); line2.setFontScale(1.6f);
		Label line3 = new Label("at", titleStyle); line3.setFontScale(1.6f);
		Label line4 = new Label("Hare's", titleStyle); line4.setFontScale(1.6f);

		titleTable.add(line1).left().row();
		titleTable.add(line2).left().row();
		titleTable.add(line3).left().row();
		titleTable.add(line4).left().padBottom(40).row();

		rootTable.add(titleTable).left().row();

		// Create Menu Text buttons
		newGameBtn = new TextButton("NEW GAME", btnStyle);
		continueBtn = new TextButton("CONTINUE", btnStyle);
		
		
		Label nightSubLabel = new Label("Night " + savedNight, subStyle);
		nightSubLabel.setFontScale(0.9f); 
		
		exitBtn = new TextButton("EXIT", btnStyle);

		newGameBtn.getLabel().setAlignment(Align.left);
		continueBtn.getLabel().setAlignment(Align.left);
		exitBtn.getLabel().setAlignment(Align.left);

		// Button listeners with hover sounds and ">> " prepending logic
		setupMenuButtonBehavior(newGameBtn, "NEW GAME", new Runnable() {
			@Override
			public void run() {
				menuAmbient.stop();
				game.setScreen(new GameScreen(game));
			}
		});

		setupMenuButtonBehavior(continueBtn, "CONTINUE", new Runnable() {
			@Override
			public void run() {
				menuAmbient.stop();
				game.setScreen(new GameScreen(game));
			}
		});

		setupMenuButtonBehavior(exitBtn, "EXIT", new Runnable() {
			@Override
			public void run() {
				Gdx.app.exit();
			}
		});

		rootTable.add(newGameBtn).left().padBottom(15).row();
		rootTable.add(continueBtn).left().row();
		rootTable.add(nightSubLabel).left().padLeft(45).padBottom(20).row(); // Perfectly centered underneath Continue
		rootTable.add(exitBtn).left().row();

		stage.addActor(rootTable);
	}

	private void setupMenuButtonBehavior(final TextButton button, final String originalText, final Runnable clickAction) {
		button.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if (pointer == -1) {
					menuBlip.play(0.4f);
					button.setText(">> " + originalText);
				}
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if (pointer == -1) {
					button.setText(originalText);
				}
			}

			@Override
			public void clicked(InputEvent event, float x, float y) {
				clickAction.run();
			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateTime += delta;
		updateGlitchLogic(delta);

		batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
		batch.begin();

		if (showGlitchFrame) {
			batch.draw(glitchBackground, 0, 0, 1024, 768);
		} else {
			batch.draw(normalBackground, 0, 0, 1024, 768);
		}

		TextureRegion currentStaticFrame = staticAnimation.getKeyFrame(stateTime);
		
		// Render visual static grain mix at 35% opacity
		batch.setColor(1f, 1f, 1f, 0.35f); 
		batch.draw(currentStaticFrame, 0, 0, 1024, 768);
		batch.setColor(1f, 1f, 1f, 1f);

		batch.end();

		stage.act(delta);
		stage.draw();
	}

	private void updateGlitchLogic(float delta) {
		glitchTimer -= delta;
		if (glitchTimer <= 0) {
			if (!showGlitchFrame) {
				showGlitchFrame = true;
				glitchTimer = 0.04f + random.nextFloat() * (0.15f - 0.04f); 
			} else {
				showGlitchFrame = false;
				glitchTimer = 2.0f + random.nextFloat() * (7.0f - 2.0f);   
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		normalBackground.dispose();
		glitchBackground.dispose();
		staticSheet.dispose();
		menuAmbient.dispose();
		menuBlip.dispose();
		menuFont.dispose();
	}
}
