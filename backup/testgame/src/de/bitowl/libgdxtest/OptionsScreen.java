package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen implements Screen {
	MenuScreen screen;
	
	Stage stage;
	Skin skin;
	TestGame game;
	/**
	 * by which menu Screen this option screen was called
	 * @param pScreen
	 */
	public OptionsScreen(MenuScreen pScreen){
		screen=pScreen;
		game=screen.game;
		stage=new Stage();
		stage.setViewport(800,480,false);
		skin = new Skin( Gdx.files.internal( "ui/myskin.json" ));
	
		
		Table table=new Table();
		table.setSize(800,480);
		
		Label title=new Label("options", skin);
		title.setFontScale(2f);
		table.add(title).colspan(2).align(Align.center);
		table.row();
		
		Label namel=new Label("name:",skin);
		table.add(namel);
		
		
		
		TextField name=new TextField("",skin);
		table.add(name);
		table.row();
		

		Label graphicl=new Label("graphic:",skin);
		table.add(graphicl);
		
		CheckBox graphic=new CheckBox("",skin);
		table.add(graphic);
		table.row();

		
		Label soundl=new Label("sound:",skin);
		table.add(soundl);

		final Slider sound=new Slider(0, 100, 1, false, skin);
		sound.setValue(game.preferences.getInteger("volume",100));
		table.add(sound);
		table.row();

		Label themel=new Label("theme:",skin);
		table.add(themel);
		
		String[] items={"cool","mega","awesome"};
		SelectBox theme=new SelectBox(items, skin);
		
		theme.getSelection();
		table.add(theme);
		table.row();
		
		TextButton back=new TextButton("back to menu",skin);
		back.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.moveTo(800,0,0.5f),new Action() {
					
					@Override
					public boolean act(float delta) {
						
						game.preferences.putInteger("volume", (int) sound.getValue());
						game.preferences.flush();
						
						Resources.page_turn.play();
						screen.game.setScreen(screen);
						return false;
					}
				}));
			}
		});
		table.add(back).colspan(2).align(Align.center).padTop(20);
		
		
		stage.addActor(table);
		stage.addAction(Actions.moveTo(800, 0));
		stage.addAction(Actions.moveTo(0, 0,0.5f));
		
		Gdx.input.setInputProcessor(stage);
		
	}
	
	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
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
		// TODO Auto-generated method stub
		skin.dispose();
	}

}
