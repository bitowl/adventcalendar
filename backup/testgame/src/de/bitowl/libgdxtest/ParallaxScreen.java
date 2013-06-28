package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ParallaxScreen implements Screen{

	private TestGame game;
	
	ParallaxCamera camera;
	OrthographicCamera cam;
	
	SpriteBatch batch;

	TextureAtlas atlas;
	
	TextureRegion layer1,layer2,layer3;
	
	//ParallaxBackground background
	ParallaxBackground background;
	
	
	
	public ParallaxScreen(TestGame pGame){
		game=pGame;
		camera=new ParallaxCamera(400,240);
		cam=new OrthographicCamera(400,240);
		batch=new SpriteBatch();
		atlas=game.assets.get("graphics/testPack.atlas", TextureAtlas.class);
		layer1=atlas.findRegion("layer1");
		layer2=atlas.findRegion("layer2");
		layer3=atlas.findRegion("layer3");
		camera.position.x=200;
		camera.position.y=120;
		camera.update();
		
		/*ParallaxLayer l1=new ParallaxLayer(layer1, new Vector2(0,0), new Vector2(0,0));
		ParallaxLayer l2=new ParallaxLayer(layer2, new Vector2(0.5f,0), new Vector2(0,0));
		ParallaxLayer[] layers={l1,l2};
		background=new ParallaxBackground(layers, 400,240,new Vector2(50f,0f));*/
		ParallaxLayer l1=new ParallaxLayer(layer1,0,0,false);
		ParallaxLayer l2=new ParallaxLayer(layer2,0.5f,0,true);
		ParallaxLayer l3=new ParallaxLayer(layer3,1,0,true);
		ParallaxLayer[] layers={l1,l2,l3};
		background=new ParallaxBackground(layers, camera,batch);
	}
	
	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		background.moveX(30*delta);
		
		background.render();
		
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
		//	dx=-1;
			//player.setXY(player.getX()-Gdx.graphics.getDeltaTime()*300, player.getY());
			camera.position.x=camera.position.x-Gdx.graphics.getDeltaTime()*300;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			//dx=1;
			//player.setPosition(player.getX()+Gdx.graphics.getDeltaTime()*300, player.getY());
			camera.position.x=camera.position.x+Gdx.graphics.getDeltaTime()*300;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
//			dy=-1;
			//player.setPosition(player.getX(),player.getY()-Gdx.graphics.getDeltaTime()*300);
			camera.position.y=camera.position.y-Gdx.graphics.getDeltaTime()*300;
		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
	//		dy=1;
			//player.setPosition(player.getX(),player.getY()+Gdx.graphics.getDeltaTime()*300);
		camera.position.y=camera.position.y+Gdx.graphics.getDeltaTime()*300;
		}
		
	/*	camera.update();
		
		Gdx.app.log("ROFL",camera.position.toString());

		batch.setProjectionMatrix(camera.calculateParallaxMatrix(0, 1));
		batch.begin();
		batch.draw(layer1, -200,0);
		
		batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.3f, 1));
		batch.draw(layer2,0,0);
		
		batch.setProjectionMatrix(camera.calculateParallaxMatrix(0.6f, 1));
		batch.draw(layer3,0,0);
		//batch.draw(layer1, 400,0);
		//batch.draw(layer1, 400,240);
		//batch.draw(layer1, -400,0);
		batch.end();*/
		
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
		
	}

	class ParallaxCamera extends OrthographicCamera {
		Matrix4 parallaxView = new Matrix4();
		Matrix4 parallaxCombined = new Matrix4();
		Vector3 tmp = new Vector3();
		Vector3 tmp2 = new Vector3();

		public ParallaxCamera (float viewportWidth, float viewportHeight) {
			super(viewportWidth, viewportHeight);
		}

		public Matrix4 calculateParallaxMatrix (float parallaxX, float parallaxY) {
			update();
			tmp.set(position);
			tmp.x *= parallaxX;
			tmp.y *= parallaxY;

			parallaxView.setToLookAt(tmp, tmp2.set(tmp).add(direction), up);
			parallaxCombined.set(projection);
			Matrix4.mul(parallaxCombined.val, parallaxView.val);
			return parallaxCombined;
		}
	}
}
