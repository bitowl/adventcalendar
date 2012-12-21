package de.bitowl.libgdxtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TestGame implements ApplicationListener {
	
	Texture tree;
	TextureRegion christmasTree;
	Texture ball;
	Texture snow;
	OrthographicCamera camera;
	Stage stage;
	
	
	@Override
	public void create() {
		// load assets
		tree=new Texture(Gdx.files.internal("graphics/tree.png"));// http://openclipart.org/detail/1936/tree-by-harmonic
		christmasTree=new TextureRegion(tree, 0, 0, 450,730);
		ball=new Texture(Gdx.files.internal("graphics/ball.png"));// http://openclipart.org/detail/170738/decorative-ball-by-merlin2525-170738
		snow=new Texture(Gdx.files.internal("graphics/snow.png"));// http://openclipart.org/detail/170738/decorative-ball-by-merlin2525-170738
		
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
		
		stage.addActor(ctree);
		

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

	}

	public void spawnSnowflake(){
		final Image snowflake=new Image(snow);
		snowflake.setOrigin(64,64);
		int x=(int) (Math.random()*800);
		snowflake.setPosition(x,480);
		snowflake.setScale((float) (Math.random()*0.8f+0.2f));
		// animate the snowflake randomly
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