package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsScreen implements Screen{
	
	Rectangle viewport;
	float virtualWidth=800;
	float virtualHeight=480;
	float virtualAspectRatio=virtualWidth/virtualHeight;
	
	OrthographicCamera camera;
	
	World world;
	Box2DDebugRenderer debugRenderer;
	
	TestGame game;
	
	public PhysicsScreen(TestGame pGame) {
		game=pGame;
		
		camera=new OrthographicCamera(virtualWidth,virtualHeight);
		viewport=new Rectangle();
		
		camera.position.x=400;//Gdx.graphics.getWidth()/2;//(map.width*map.tileWidth)/2;
		camera.position.y=240;
		
		camera.update();
		
		world=new World(new Vector2(0,-100), true);
		debugRenderer=new Box2DDebugRenderer();
		
	     //Ground body  
        BodyDef groundBodyDef =new BodyDef();  
        groundBodyDef.position.set(new Vector2(0, 20));  
        Body groundBody = world.createBody(groundBodyDef);  
        PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox((camera.viewportWidth) * 2, 20.0f);  
        groundBody.createFixture(groundBox, 0.0f);  
        
        //Dynamic Body  
        BodyDef bodyDef = new BodyDef();  
        bodyDef.type = BodyType.DynamicBody;  
        bodyDef.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2);  
        Body body = world.createBody(bodyDef);  
        CircleShape dynamicCircle = new CircleShape();  
        dynamicCircle.setRadius(5f);  
        FixtureDef fixtureDef = new FixtureDef();  
        fixtureDef.shape = dynamicCircle;  
        fixtureDef.density = 1.0f;  
        fixtureDef.friction = 0.0f;  
        fixtureDef.restitution = 0.7f;  
        body.createFixture(fixtureDef); 
        
        bodyDef.position.set(camera.viewportWidth / 2+30, camera.viewportHeight / 2-100);
        Body body2 = world.createBody(bodyDef);
        body2.createFixture(fixtureDef);

        
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
        // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                          (int) viewport.width, (int) viewport.height);
		
		debugRenderer.render(world,camera.combined);
		 world.step(1/60f, 6,2);  
	}

	@Override
	public void resize(int width, int height) {
		float aspectRatio=width/(float)height;
		
		float scale=1;
		float cropX=0;
		float cropY=0;
		
		// calculate scale and size of the letterbox
		if(aspectRatio > virtualAspectRatio){
			scale = (float)height/(float)virtualHeight;
			cropX = (width - virtualWidth*scale)/2f;
		}else if(aspectRatio < virtualAspectRatio){
			scale = (float)width/(float)virtualWidth;
			cropY = (height - virtualHeight*scale)/2f;
		} else {
			scale = (float)width/(float)virtualWidth;
		}
        
		// set the viewport
        viewport.set(cropX,cropY,virtualWidth*scale,virtualHeight*scale);
		
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

}
