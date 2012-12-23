package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IngameScreen implements Screen {
	TestGame game;
	
	Stage stage;
	TextureAtlas atlas;
	AnimationDrawable upDraw,leftDraw,downDraw,rightDraw;
	
	/**
	 * a counter to control the animation
	 */
	float animTime;
	
	Image mummy;
	
	public IngameScreen(TestGame pGame){
		game=pGame;
		stage=new Stage(800,480,false);
		atlas = new TextureAtlas(Gdx.files.internal("graphics/testPack.atlas"));
		
		// create animations as drawables
		upDraw=new AnimationDrawable(new Animation(0.2f,atlas.findRegions("mummyup")), true);
		leftDraw=new AnimationDrawable(new Animation(0.2f,atlas.findRegions("mummyleft")), true);
		downDraw=new AnimationDrawable(new Animation(0.2f,atlas.findRegions("mummydown")), true);
		rightDraw=new AnimationDrawable(new Animation(0.2f,atlas.findRegions("mummyright")), true);
		
		mummy=new Image(downDraw);
		mummy.setPosition(300, 200);
		stage.addActor(mummy);
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
	}
	
	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	class MyInputProcessor implements InputProcessor{

		@Override
		public boolean keyDown(int keycode) {
			// change the drawable if a key in another direction is pressed
			switch(keycode){
				case Keys.UP:
					mummy.setDrawable(upDraw);
					break;
				case Keys.LEFT:
					mummy.setDrawable(leftDraw);
					break;
				case Keys.DOWN:
					mummy.setDrawable(downDraw);
					break;
				case Keys.RIGHT:
					mummy.setDrawable(rightDraw);
					break;
			}
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}
		
	}
	
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
	}

}