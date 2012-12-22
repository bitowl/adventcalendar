package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen implements Screen{
	TestGame game;
	
	Stage stage;
	Skin skin;
	
	public OptionsScreen(TestGame pGame){
		game=pGame;
		
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
				game.setScreen(new MenuScreen(game));
				
			}
		});
		table.add(back).colspan(2).align(Align.center).padTop(20);
		
		
		stage.addActor(table);
		
		// give input to the stage
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
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
		
	}
}