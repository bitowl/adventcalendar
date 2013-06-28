package de.bitowl.libgdxtest;

import java.awt.Checkbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MenuScreen implements Screen {

    private static final int VIRTUAL_WIDTH = 800;
    private static final int VIRTUAL_HEIGHT = 480;
    private static final float ASPECT_RATIO =
        (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT; 
	
	
	Stage stage;
	Skin skin;
	NinePatch butPatch;
	TestGame game;
	
	TextureAtlas atlas;
	TextureAtlas atlasui;
	
	NinePatch patch;
	
	BitmapFont font;
	public MenuScreen(TestGame pGame){
		game=pGame;
		stage=new Stage();
		stage.setViewport(800,480,false);
		
		atlas = new TextureAtlas(Gdx.files.internal("graphics/testPack.atlas"));
		
		patch=atlas.createPatch("button");
		
		//atlasui = new TextureAtlas(Gdx.files.internal("ui/.atlas"));
		
		skin=game.assets.get("ui/myskin.json",Skin.class);
		
	//	skin=new Skin(Gdx.files.internal("ui/myskin.json"));
		//skin = new Skin();
		//skin.addRegions(atlas);
		//skin.addRegions(atlasui);
		//skin.load(Gdx.files.internal( "ui/myskin.json" ));


		font=new BitmapFont(Gdx.files.internal("ui/test.fnt"), false);
		
	/*	final TextButton button=new TextButton("pointless button", skin);
		button.setPosition(350,200);
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				button.addAction(Actions.fadeOut(0.7f));
			}
		});
		stage.addActor(button);*/
		
		
		Texture butx=new Texture(Gdx.files.internal("ui/button.png"));
		butx.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		butPatch=new NinePatch(new TextureRegion(butx,0,0,24,24), 8,8,8,8);
		NinePatchDrawable draw=new NinePatchDrawable(butPatch);
		NinePatchDrawable up=new NinePatchDrawable(new NinePatch(new TextureRegion(new Texture(Gdx.files.internal("ui/button_up.png")),0,0,24,24),8,8,8,8)); 
		//TextButton.TextButtonStyle style=new TextButton.TextButtonStyle(butPatch, butPatch, butPatch, 0, 0, 0, 0, new BitmapFont(), new Color(0.3f, 0.2f, 0.8f, 1f), new Color(0, 0, 0, 1f), new Color(0, 0, 0, 1f))
		
		//new TextButton.T
		
		//TextButtonStyle style = new TextButtonStyle(draw, draw, draw);//new TextButtonStyle(butPatch, butPatch, butPatch, 0, 0, 0, 0, new BitmapFont(), new Color(0.3f, 0.2f, 0.8f, 1f), new Color(0, 0, 0, 1f), new Color(0, 0, 0, 1f))
		
		Table table=new Table();
		table.setSize(800,480);
		
		
		//Button but=new Butt
		TextButton startGame=new TextButton("start game",skin);
		startGame.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new SpaceGameScreen(game));
			}
		});
		table.add(startGame).width(200).height(50);
		table.row();
		
		TextButton options=new TextButton("options",skin);
		options.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.moveTo(-880,0,0.5f),
						new Action() {
							@Override
							public boolean act(float delta) {
								Resources.page_turn.play();
								game.setScreen(new OptionsScreen(MenuScreen.this));
								return true;
							}
						}));
			}
		});
		table.add(options).width(150).padTop(10).padBottom(3);
		table.row();
		

		// options.addAction(Actions.moveTo(0, 0,5.5f));
		
		TextButton credits=new TextButton("credits",skin);
		table.add(credits).width(150);
		table.row();
		
		TextButton quit=new TextButton("quit",skin);
		table.add(quit).width(100).padTop(10);
		table.row();
		
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.moveTo(0,-480,0.8f),new Action() {
					@Override
					public boolean act(float delta) {
						// quit the app
						Gdx.app.exit();
						return false;
					}
				}));
				//
			}
		});
		//table.add(quit);
		
		
		stage.addActor(table);

		
	}

	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();

		stage.getSpriteBatch().begin();
		font.draw(stage.getSpriteBatch(), "super awesome game", 140,370);
		//butPatch.draw(stage.getSpriteBatch()
				//, 0, 0, Gdx.graphics.getWidth(), 100);
		stage.getSpriteBatch().end();
	}

	@Override
	public void resize(int width, int height) {

	      //  stage.setViewport(w,h,true);
//	        Gdx.gl.glViewport((int)crop.x,(int)crop.y,(int)w,(int)h);
	      //  viewport = new Rectangle(crop.x, crop.y, w, h); 		
	}

	@Override
	public void show() {
		// we need to claim the input again everytime this screen is shown again
		Gdx.input.setInputProcessor(stage);
		
		stage.addAction(Actions.moveTo(-800, 0));
		stage.addAction(Actions.moveTo(0, 0,0.5f));
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
		// TODO Auto-generated method stub
		skin.dispose();
		stage.dispose();
		butPatch.getTexture().dispose();
	}

}
