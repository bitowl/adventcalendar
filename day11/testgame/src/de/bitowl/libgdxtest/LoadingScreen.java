package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LoadingScreen implements Screen {
	TestGame game;
	private BitmapFont font;
	private SpriteBatch batch;
	private Texture emptyT;
	private Texture fullT;
	private NinePatch empty;
	private NinePatch full;
	
	public LoadingScreen(TestGame pGame){
		game=pGame;
		
		game.assets=new AssetManager();
		
		game.assets.load("ui/myskin.json",Skin.class);
		game.assets.load("graphics/testPack.atlas",TextureAtlas.class);
		game.assets.load("ui/test.fnt",BitmapFont.class);
		game.assets.load("graphics/testPack.atlas",TextureAtlas.class);
		game.assets.load("sound/silent-night.wav",Music.class);
		game.assets.load("sound/page-turn.wav",Sound.class);
		
		// load the assets for the loading screen :D 
		font=new BitmapFont();
		batch=new SpriteBatch();
		emptyT=new Texture(Gdx.files.internal("load/empty.png"));
		fullT=new Texture(Gdx.files.internal("load/full.png"));
		empty=new NinePatch(new TextureRegion(emptyT,24,24),8,8,8,8);
		full=new NinePatch(new TextureRegion(fullT,24,24),8,8,8,8);
	}
	
	@Override
	public void render(float delta) {
		if(game.assets.update()){
			// all the assets are loaded
			game.setScreen(new SplashScreen(game));
		}
		batch.begin();
		empty.draw(batch, 40, 225, 720, 30);
		full.draw(batch, 40, 225, game.assets.getProgress()*720, 30);
		font.drawMultiLine(batch,(int)(game.assets.getProgress()*100)+"% loaded",400,247,0, BitmapFont.HAlignment.CENTER);
		batch.end();

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
		emptyT.dispose();
		fullT.dispose();

	}

}
