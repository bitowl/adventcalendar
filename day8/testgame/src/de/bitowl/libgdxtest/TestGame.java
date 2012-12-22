package de.bitowl.libgdxtest;

import com.badlogic.gdx.Game;

public class TestGame extends Game{
	@Override
	public void create() {
		setScreen(new SplashScreen(this));
	}
}