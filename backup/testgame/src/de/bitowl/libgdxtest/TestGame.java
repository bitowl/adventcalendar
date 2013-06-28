package de.bitowl.libgdxtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/*
 * thanks:
 * angelus (irc) 
 * bach (irc) for 
 * 
 * 
 * day 7:
 * SpriteBatch schneller machen
 * http://code.google.com/p/libgdx/wiki/SpriteBatch#Performance_tuning
 */

/*
public class TestGame implements ApplicationListener {
	
	TextureRegion christmasTree;
	Texture ball;
	//SpriteBatch batch;
	OrthographicCamera camera;
	Stage stage;
	
	Texture snow; // http://openclipart.org/detail/98557/snowflake-by-jgm104
	
	// position of the ball
	int x, y=0;
	
	Image ballImage;
	
	@Override
	public void create() {
		// load assets
		Texture tree=new Texture(Gdx.files.internal("graphics/tree.png"));
		christmasTree=new TextureRegion(tree, 0, 0, 450,730);
		ball=new Texture(Gdx.files.internal("graphics/ball.png"));
		snow=new Texture(Gdx.files.internal("graphics/snow.png"));
		
//		batch=new SpriteBatch();
		
		// create viewport
		camera=new OrthographicCamera();
		camera.setToOrtho(false, 800,480);
		
		
		stage=new Stage();
		Image ctree=new Image(christmasTree);
		ctree.setSize(296, 480);
		ctree.setPosition(400 - 148, 0); // center the tree
		stage.addActor(ctree);

		stage.setCamera(camera);
		ballImage=new Image(ball);
		ballImage.setColor(1,1,1,0);
		ballImage.setOrigin(32, 32);
		stage.addActor(ballImage);
		ballImage.addAction(Actions.sequence(
				Actions.parallel(
				Actions.fadeIn(0.75f),Actions.rotateBy(90.0f,1.0f))
				,Actions.delay(1f),Actions.fadeOut(1f)));
		for(int i=0;i<10;i++){
			spawnSnowflake();
		}

	}

	public void spawnSnowflake(){
		final Image snowflake=new Image(snow);
		snowflake.setOrigin(64,64);
		int x=(int) (Math.random()*800);
		snowflake.setPosition(x,480);
		snowflake.setScale((float) (Math.random()*0.8f+0.2f));
		snowflake.addAction(Actions.parallel(
				Actions.forever(Actions.rotateBy(360,(float) (Math.random()*6))),
				Actions.sequence(Actions.moveTo(x,0,(float) (Math.random()*15)),Actions.fadeOut((float) (Math.random()*1)),new Action() {
					
					@Override
					public boolean act(float delta) {
						snowflake.remove(); // delete this snowflake
						spawnSnowflake(); // spawn a new snowflake
						return false;
					}
				}
				)));
		stage.addActor(snowflake);
	}
	
	@Override
	public void render() {		
		stage.act();
		Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// render our images
	//	batch.setProjectionMatrix(camera.combined);
//		batch.begin();
		//batch.draw(christmasTree,0,0,450/(730.0f/480.0f),480);
		
		//batch.draw(ball,x,y);
		
		//batch.end();
		stage.draw();
		ballImage.setPosition(x, y);
	}

	@Override
	public void resize(int width, int height) {
		//stage.setViewport(width, height, true);
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
	//	batch.dispose();
		stage.dispose();
	}

}

 */

/*public class TestGame extends Game{//implements ApplicationListener {
	
	Texture tree;
	TextureRegion christmasTree;
	Texture ball;
	Texture snow;
	OrthographicCamera camera;
	Stage stage;
	
	Skin skin;
	
	BitmapFont font;
	
	
	@Override
	public void create() {
		// load assets
		tree=new Texture(Gdx.files.internal("graphics/tree.png"));
		christmasTree=new TextureRegion(tree, 0, 0, 450,730);
		ball=new Texture(Gdx.files.internal("graphics/ball.png"));
		snow=new Texture(Gdx.files.internal("graphics/snow.png"));
		
		skin = new Skin( Gdx.files.internal( "ui/uiskin.json" )); 
		
		font=new BitmapFont(Gdx.files.internal("ui/test.fnt"), false);
		
		// create viewport
		camera=new OrthographicCamera();
		camera.setToOrtho(false, 800,480);
		
		stage=new Stage();
		stage.setCamera(camera);

		// our christmas tree
		Image ctree=new Image(christmasTree);
		ctree.setSize(296, 480); // scale the tree to the right size
		ctree.setPosition(-300,0);
		ctree.addAction(Actions.moveTo(400-148, 0,1f));
		ctree.setZIndex(0);
		stage.addActor(ctree);
		
//		Table table=new Table();
//		table.setSize(800,480);
//		table.setZIndex(10);
//		table.align(Align.top);
//		
//		final TextButton but=new TextButton("Spiel starten",skin);
//		but.setColor(1,1,1,0);
//		but.setZIndex(20);
//		but.addListener(new ClickListener(){
//			public void clicked(InputEvent event, float x, float y) {
//				but.addAction(Actions.moveTo(800,480,3,Interpolation.bounceOut));	
//			}
//		});
//		
//		but.addAction(Actions.fadeIn(3));
//		table.add(but);
//		
//		TextButton quit=new TextButton("Spiel beenden",skin);
//		quit.setZIndex(20);
//		quit.addListener(new ClickListener(){
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				stage.addAction(Actions.sequence(Actions.moveTo(0,-480,0.8f),new Action() {
//					@Override
//					public boolean act(float delta) {
//						// quit the app
//						Gdx.app.exit();
//						return false;
//					}
//				}));
//				//
//			}
//		});
//		table.add(quit);
//		
//		stage.addActor(table);

		

		Image ballImage=new Image(ball);
		ballImage.setPosition(400 - 148+60, 170);
		
		ballImage.setOrigin(32,32);
		ballImage.setColor(1,1,1,0);
		ballImage.addAction(Actions.sequence(Actions.delay(1),Actions.parallel(Actions.fadeIn(1),Actions.rotateBy(360,1))));
	
		
		stage.addActor(ballImage);
		
		// create the snowflakes
		for(int i=0;i<10;i++){
			spawnSnowflake();
		}
		
		Gdx.input.setInputProcessor(stage);
	}

	public void spawnSnowflake(){
		final Image snowflake=new Image(snow);
		snowflake.setOrigin(64,64);
		int x=(int) (Math.random()*800);
		snowflake.setPosition(x,480);
		snowflake.setScale((float) (Math.random()*0.8f+0.2f));
		snowflake.addAction(Actions.parallel(
				Actions.forever(Actions.rotateBy(360,(float) (Math.random()*6))),
				Actions.sequence(Actions.moveTo(x,0,(float) (Math.random()*15)),Actions.fadeOut((float) (Math.random()*1)),
					new Action() { // we can define custom actions :)
					
					@Override
					public boolean act(float delta) {
						snowflake.remove(); // delete this snowflake
						spawnSnowflake(); // spawn a new snowflake
						return false;
					}
				}
				)));
		stage.addActor(snowflake);
		
		

	}
	
	@Override
	public void render() {		
	
		Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// update the stage
		stage.act();
		// draw the stage
		stage.draw();
		stage.getSpriteBatch().begin();
		font.draw(stage.getSpriteBatch(), "mega-awesome font :D", 50,80);
		stage.getSpriteBatch().end();
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
		tree.dispose();
		ball.dispose();
		snow.dispose();
		stage.dispose();
	}

}
*/

public class TestGame extends Game{
	AssetManager assets;
	Preferences preferences;

	@Override
	public void create() {
		setScreen(new LoadingScreen(this));
	}
}