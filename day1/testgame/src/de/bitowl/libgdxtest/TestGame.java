package de.bitowl.libgdxtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class TestGame implements ApplicationListener {
	
	@Override
	public void create() {		
	
	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl.glClearColor((float) Math.random(),(float) Math.random(), (float) Math.random(), 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	@Override
	public void dispose() {
	}

}