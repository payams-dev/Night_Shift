package com.payamsdev.nightshift;

import com.badlogic.gdx.Game;

public class Main extends Game {
	@Override
	public void create() {
		// Route execution back to the starting menu hub!
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		super.render(); 
	}
}