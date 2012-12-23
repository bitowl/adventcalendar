package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {
	
	TestGame game;
	
	Stage stage;
	Skin skin;
	
	
	public MenuScreen(TestGame pGame) {
		game = pGame;
		stage=new Stage();
		stage.setViewport(800,480,false);
		skin = new Skin( Gdx.files.internal( "ui/myskin.json" ));
		
		Table table=new Table();
		table.setSize(800,480);

		TextButton startGame=new TextButton("start game",skin);
		startGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new IngameScreen(game));
			}
		});
			
		table.add(startGame).width(200).height(50);
		table.row();

		TextButton options=new TextButton("options",skin);
		options.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new OptionsScreen(game));
			}
		});
			
		table.add(options).width(150).padTop(10).padBottom(3);
		table.row();
				
		TextButton credits=new TextButton("credits",skin);
		table.add(credits).width(150);
		table.row();
				
		TextButton quit=new TextButton("quit",skin);
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.fadeOut(0.7f),new Action() {
					@Override
					public boolean act(float delta) {
						Gdx.app.exit();
						return false;
					}
				}));
			}
		});
		table.add(quit).width(100).padTop(10);

		stage.addActor(table);
		
		// give input to the stage
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
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
		skin.dispose();
	}

}
