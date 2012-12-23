package de.bitowl.libgdxtest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class TestGame extends Game{
	AssetManager assets;
	
	@Override
	public void create() {
		setScreen(new LoadingScreen(this));
	}
}