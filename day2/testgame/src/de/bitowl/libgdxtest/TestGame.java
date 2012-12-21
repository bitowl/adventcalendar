package de.bitowl.libgdxtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TestGame implements ApplicationListener {

	TextureRegion christmasTree;
	Texture ball;
	SpriteBatch batch;
	OrthographicCamera camera;

	@Override
	public void create() {
		// load assets
		Texture tree = new Texture(Gdx.files.internal("graphics/tree.png")); // http://openclipart.org/detail/1936/tree-by-harmonic
		christmasTree = new TextureRegion(tree, 0, 0, 450, 730);
		ball = new Texture(Gdx.files.internal("graphics/ball.png")); // http://openclipart.org/detail/170738/decorative-ball-by-merlin2525-170738

		batch = new SpriteBatch();

		// create viewport
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// render our images
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(christmasTree, 0, 0, 450 / (730.0f / 480.0f), 480);
		batch.draw(ball, 60, 170);
		batch.end();
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
		// dispose all the trash :P
		christmasTree.getTexture().dispose();
		ball.dispose();
		batch.dispose();
	}
}