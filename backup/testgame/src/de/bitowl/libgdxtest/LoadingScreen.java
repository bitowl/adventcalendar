package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class LoadingScreen implements Screen{
	TestGame game;
	
	// standard font
	BitmapFont font;
	SpriteBatch batch;
	OrthographicCamera camera;
	
	// loading bar
	NinePatch empty;
	NinePatch full;
	Texture emptyT;
	Texture fullT;
	
	BitmapFont font40;
	BitmapFont font20;
	
	public LoadingScreen(TestGame pGame) {
		game=pGame;
		
		// load the assets for the loading screen :D
		font=new BitmapFont();
//		font.setColor(0.2f, 0.2f, 0.2f, 1);
		batch=new SpriteBatch();
		camera=new OrthographicCamera();
		camera.setToOrtho(false, 800,480);
		emptyT=new Texture(Gdx.files.internal("load/empty.png"));
		fullT=new Texture(Gdx.files.internal("load/full.png"));
		empty=new NinePatch(new TextureRegion(emptyT,24,24),8,8,8,8);
		full=new NinePatch(new TextureRegion(fullT,24,24),8,8,8,8);
		
		
		// which assets do we want to be loaded
		game.assets=new AssetManager();
		
		game.assets.load("ui/myskin.json", Skin.class);
		game.assets.load("graphics/testPack.atlas",TextureAtlas.class);
		game.assets.load("ui/test.fnt",BitmapFont.class);

		game.assets.load("sound/silent-night.wav",Music.class);
		game.assets.load("sound/page-turn.wav",Sound.class);
		game.assets.load("sound/smallboom.wav",Sound.class); //http://www.freesound.org/people/CGEffex/sounds/95749/
		game.assets.load("sound/shot.wav",Sound.class);// http://www.freesound.org/people/Shades/sounds/37236/
		
		// load options
		game.preferences=Gdx.app.getPreferences("settings");
		
		
		FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("font/RawengulkSans.ttf"));
		font40=generator.generateFont(40);
		font20=generator.generateFont(20);
		generator.dispose();
	}
	float lol=0.02f;
	
	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(game.assets.update()){
			game.setScreen(new MenuScreen(game));
		}
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		empty.draw(batch, 40, 225, 720, 30);
		if(game.assets.getProgress()>0){
		full.draw(batch, 40, 225, game.assets.getProgress()*720, 30);
		}
		
		font40.drawMultiLine(batch,"bitowl presents",400,300,0, BitmapFont.HAlignment.CENTER);
		font20.drawMultiLine(batch,"bitowl, 2012",400,200,0, BitmapFont.HAlignment.CENTER);
		
		font.drawMultiLine(batch,(int)(game.assets.getProgress()*100)+"% loaded...",400,247,0, BitmapFont.HAlignment.CENTER);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		emptyT.dispose();
		fullT.dispose();
		font40.dispose();
	}

}
