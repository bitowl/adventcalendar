package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements Screen {

	TextureRegion christmasTree;
	TextureRegion ball;
	TextureRegion snow;
	Stage stage;
	BitmapFont font;
	TextureAtlas atlas;
	
	TestGame game;
	
	Music music;
	Sound sound;

	public SplashScreen(TestGame pGame) {
		game = pGame;

		atlas = new TextureAtlas(Gdx.files.internal("graphics/testPack.atlas"));
		
		christmasTree=atlas.findRegion("tree");// http://openclipart.org/detail/1936/tree-by-harmonic
		ball=atlas.findRegion("ball");// http://openclipart.org/detail/170738/decorative-ball-by-merlin2525-170738
		snow=atlas.findRegion("snow");// http://openclipart.org/detail/98557/snowflake-by-jgm104
		
		font = new BitmapFont(Gdx.files.internal("ui/test.fnt"), false);

		stage = new Stage();

		// our christmas tree
		Image ctree = new Image(christmasTree);
		ctree.setSize(296, 480); // scale the tree to the right size
		ctree.setPosition(-300, 0);
		ctree.addAction(moveTo(400 - 148, 0, 1f));
		ctree.setZIndex(0);
		stage.addActor(ctree);

		Image ballImage = new Image(ball);
		ballImage.setPosition(400 - 148 + 60, 170);

		ballImage.setOrigin(32, 32);
		ballImage.setColor(1, 1, 1, 0);
		ballImage.addAction(sequence(delay(1),
				parallel(fadeIn(0.5f), rotateBy(360, 0.5f)), delay(1f), new Action() {
			// custom action to switch to the menu screen
					@Override
					public boolean act(float delta) {
						sound.play();
						game.setScreen(new MenuScreen(game));
						return false;
					}
				}));

		stage.addActor(ballImage);

		// create the snowflakes
		for (int i = 0; i < 10; i++) {
			spawnSnowflake();
		}
		

		music=Gdx.audio.newMusic(Gdx.files.internal("sound/silent-night.wav"));
		music.setLooping(true);
		music.play();

		sound=Gdx.audio.newSound(Gdx.files.internal("sound/page-turn.wav"));
	}

	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// let the stage act and draw
		stage.act(delta);
		stage.draw();
		stage.setViewport(800, 480, false);

		// draw our text
		stage.getSpriteBatch().begin();
		font.draw(stage.getSpriteBatch(), "mega awesome-game starts...", 50, 80);
		stage.getSpriteBatch().end();
	}

	public void spawnSnowflake() {
		final Image snowflake = new Image(snow);
		snowflake.setOrigin(64, 64);
		int x = (int) (Math.random() * 800);
		snowflake.setPosition(x, 480);
		snowflake.setScale((float) (Math.random() * 0.8f + 0.2f));
		snowflake.addAction(parallel(
				forever(rotateBy(360, (float) (Math.random() * 6))),
				sequence(moveTo(x, 0, (float) (Math.random() * 15)),
						fadeOut((float) (Math.random() * 1)), new Action() {
							@Override
							public boolean act(float delta) {
								snowflake.remove(); // delete this snowflake
								spawnSnowflake(); // spawn a new snowflake
								return false;
							}
						})));
		stage.addActor(snowflake);
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
		atlas.dispose();
		
		stage.dispose();
		font.dispose();
		
		music.dispose();
		sound.dispose();
	}
}