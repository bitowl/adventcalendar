package de.bitowl.libgdxtest;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

// background:http://www.publicdomainpictures.net/view-image.php?image=22672&picture=ce-weltraum-szene-1&large=1

public class SpaceGameScreen implements Screen {
	TestGame game;
	
	TextureAtlas atlas;
	Sound boom;
	Sound shotSnd;
	
	Enemy enemy;
	
	Stage stage;
	
	/**
	 * when was the last enemy spawned
	 */
	float lastSpawned;
	
	
	Ship ship;
	ArrayList<Enemy> enemies;
	ArrayList<Shot> shots;

	private float shotThisTime;

	float shotThisTimeT;
	
	public SpaceGameScreen(TestGame pGame){
		game=pGame;
		
		enemies=new ArrayList<Enemy>();
		shots=new ArrayList<Shot>();
		
		atlas = game.assets.get("graphics/testPack.atlas", TextureAtlas.class);
		
		Image bg=new Image(atlas.findRegion("spacebg"));
		
				
		ship=new Ship(atlas.findRegion("ship"));
		enemy=new Enemy(atlas.findRegion("enemy"));
		enemies.add(enemy);
		
		
		boom=pGame.assets.get("sound/smallboom.wav",Sound.class);
		shotSnd=pGame.assets.get("sound/shot.wav",Sound.class);

		stage=new Stage();
		stage.addActor(bg);
		stage.addActor(ship);
		stage.addActor(enemy);
	}
	
	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		Gdx.app.log("FPS", Gdx.graphics.getFramesPerSecond()+"");
		
		// collision
		Iterator<Enemy> enIterator=enemies.iterator();
		while(enIterator.hasNext()){
			Enemy enemy=enIterator.next();
			
			// enemy vs. ship
			if(enemy.getBounds().overlaps(ship.getBounds())){
						
				
				// create explosion animation
				createExplosion(enemy.getX()+enemy.getWidth()/2, enemy.getY()+enemy
						.getHeight()/2);
				
				enemy.remove();
				enIterator.remove();
			}
			
			// enemy vs. shot
			Iterator<Shot> shotIterator=shots.iterator();
			while(shotIterator.hasNext()){
				Shot shot=shotIterator.next();
				if(enemy.getBounds().overlaps(shot.getBounds())){

					// create explosion animation
					createExplosion(enemy.getX()+enemy.getWidth()/2, enemy.getY()+enemy.getHeight()/2);
					
					enemy.remove();
					enIterator.remove();
					shot.remove();
					shotIterator.remove();
				}
			}
		}
		
		// remove shot out of screen
		for(int j=0;j<shots.size();j++){
			if(shots.get(j).getY()>240){
				shots.get(j).remove();
				shots.remove(j);
				j--;if(j<0){j=0;}
			}
		}
		
		// spawn shots
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
			if(shotThisTime<=0){
				Shot shot=new Shot(atlas.findRegion("shot"));
				shot.setXY(ship.getX()+ship.getWidth()/2,ship.getY()+ship.getHeight());
				shots.add(shot); 
				stage.addActor(shot);
				
				long id=shotSnd.play();
				shotSnd.setPitch(id, (float) Math.random()*0.6f+0.7f);
				shotThisTime=0.3f;
			}else{
				shotThisTime-=delta;
			}
		}else{
			shotThisTime=0;
		}
		
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			stage.getCamera().unproject(touchPos);
			ship.setXY(touchPos.x-ship.getWidth()/2,touchPos.y-ship.getHeight()/2);
			if(shotThisTimeT<=0){
				Shot shot=new Shot(atlas.findRegion("shot"));
				shot.setXY(ship.getX()+ship.getWidth()/2,ship.getY()+ship.getHeight());
				shots.add(shot); 
				stage.addActor(shot);
				
				long id=shotSnd.play();
				shotSnd.setPitch(id, (float) Math.random()*0.6f+0.7f);
				shotThisTimeT=0.3f;
			}else{
				shotThisTimeT-=delta;
			}
		}else{
			shotThisTimeT=0;
		}
		
		
//		if(enemy!=null&&enemy.getBounds().intersects(ship.getBounds())){
//			stage.act(-delta); // reverse last frame
//
//			
//	/*		// create explosion animation
//			Animation animation=new Animation(0.1f, atlas.findRegions("expl"));
//			Image expl=new Image(new AnimationDrawable(animation, false));
//			expl.setPosition(ship.getX()+ship.getWidth()/2-expl.getWidth()/2, ship.getY()+ship.getHeight()/2-expl.getHeight()/2);
//			stage.addActor(expl);
//			
//			ship.remove();
//			ship=null;
//			
//			*/
//			
//			// create explosion animation
//			Animation animation=new Animation(0.1f, atlas.findRegions("expl"));
//			Image expl=new Image(new AnimationDrawable(animation, false));
//			expl.setPosition(enemy.getX()+enemy.getWidth()/2-expl.getWidth()/2, enemy.getY()+enemy.getHeight()/2-expl.getHeight()/2);
//			stage.addActor(expl);
//			
//			enemy.remove();
//			enemy=null;
//		}
		
		stage.draw();
		
		
		lastSpawned+=delta;
		if(lastSpawned>0.1f ){
			// spawn an enemy
			Enemy enemy=new Enemy(atlas.findRegion("enemy"));
			enemies.add(enemy);
			enemy.setPosition((float) (Math.random()*400), 240);
			enemy.updateBounds();
			stage.addActor(enemy);
			lastSpawned=0;
		}
	}

	public void createExplosion(float f,float g){
		boom.play(); // BOOOM :D

		// create explosion animation
		Animation animation=new Animation(0.04f, atlas.findRegions("expl"));
		Image expl=new Image(new AnimationDrawable(animation, false));
		expl.setPosition(f-expl.getWidth()/2, g-expl.getHeight()/2);
		expl.addAction(Actions.delay(14*0.04f, new com.badlogic.gdx.scenes.scene2d.Action() {
			
			@Override
			public boolean act(float delta) {
				getActor().remove();
				return false;
			}
		}));
		stage.addActor(expl);
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport(400,240,false);
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
		atlas.dispose();
	}
	
}
