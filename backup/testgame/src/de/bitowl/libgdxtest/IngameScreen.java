package de.bitowl.libgdxtest;

import java.nio.ByteBuffer;

import sun.java2d.jules.TileWorker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class IngameScreen implements Screen{
	
	// http://www.widgetworx.com/widgetworx/portfolio/spritelib.html
	
	TiledMap map;
	TileAtlas atlas;
	TileMapRenderer tileMapRenderer;
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Player player;
	
	
	TestGame game;
	TiledLayer collision;
	TextureAtlas textAtlas;
	

	
	int freeTile;
	
	Rectangle viewport;
	float virtualWidth=800;
	float virtualHeight=480;
	float virtualAspectRatio=virtualWidth/virtualHeight;
	
	Sprite playerSprite;
	
	World world;
	Box2DDebugRenderer debugRenderer;
	
	public IngameScreen(TestGame pGame){
		game=pGame;
		
		// load tilemap
		map = TiledLoader.createMap(Gdx.files.internal("maps/testmap.tmx"));
		textAtlas = game.assets.get("graphics/testPack.atlas", TextureAtlas.class);
		
		// search the collision layer
		for(int i=0;i<map.layers.size();i++){
			if(map.layers.get(i).name.equals("collision")){
				collision=map.layers.get(i);
				map.layers.remove(i);
				break;
			}
		}
		freeTile=collision.tiles[0][0];
		
		
		atlas = new TileAtlas(map, Gdx.files.internal("maps"));
		
		tileMapRenderer = new TileMapRenderer(map, atlas, 8, 8);
		
		
		
		camera=new OrthographicCamera(virtualWidth,virtualHeight);
		viewport=new Rectangle();
		
		camera.position.x=400;//Gdx.graphics.getWidth()/2;//(map.width*map.tileWidth)/2;
		camera.position.y=240;
		//camera.position.y=-240;//(map.height*map.tileHeight)/2;
		//camera.position.set(-map.width/2,-map.height/2,0);
		
		AtlasRegion pl=textAtlas.findRegion("player");
		pl.setRegion(pl.getRegionX()+1, pl.getRegionY()+1, pl.getRegionWidth()-2, pl.getRegionHeight()-2);
		

		//Pixmap pixmap = new Pixmap( 32, 32, Format.RGBA8888 );
		
		
		
		//Texture texture = new Texture( pixmap );
		//pixmap.dispose();
		
		player=new Player(pl);
		
		player.setXY(50, 50);
		
		
		playerSprite=new Sprite(pl);
		
		batch=new SpriteBatch();
		Gdx.input.setInputProcessor(new GestureDetector(new MyGestureListener()));
		
		
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
		
	}

	@Override
	public void render(float delta) {


		//  direction the player should move
		int dx=0,dy=0;
		
		// move player
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			dx=-1;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			dx=1;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			dy=-1;
		}
		if(Gdx.input.isKeyPressed(Keys.UP)){
			dy=1;
		}
		
		///// COLLISION DETECTION /////
		
		// which tiles the hero will be standing on after this frame
		int xtile=(int) ( (player.getX()+dx*delta*300) /map.tileWidth);
		int ytile=(int) ( (player.getY()+dy*delta*300) /map.tileHeight);
		
		
		getMyCorners(player.getX(),player.getY()+dy*delta*300);
		
		if(dy==1){
			if(upleft&&upright){
				player.setY(player.getY()+dy*delta*300);
			}else{
				player.setY( (ytile+1)*map.tileHeight-player.getHeight());
			}
		}else if(dy==-1){
			if(downleft&&downright){
				player.setY(player.getY()+dy*delta*300);
			}else{
				player.setY( (ytile+1)*map.tileHeight+1);
			}
		}
		
		getMyCorners(player.getX()+dx*delta*300,player.getY());
				
		if(dx==-1){
			if(downleft && upleft){
				player.setX(player.getX()+dx*delta*300);
			}else{
				player.setX( (xtile+1)*map.tileWidth);
			}
		}else if(dx==1){
			if(downright && upright){
				player.setX(player.getX()+dx*delta*300);
			}else{
				player.setX( (xtile+1)*map.tileWidth-player.getWidth()-1);
			}
		}
		
		// collision with x-borders
		if(player.getX()<=0){
			player.setX(0);
		}else if(player.getX()>map.tileWidth*map.width-player.getWidth()){
			player.setX(map.tileWidth*map.width-player.getWidth());
		}
		
		// collision with y-borders
		if(player.getY()<=0){
			player.setY(0);
		}else if(player.getY()>map.tileHeight*map.height-player.getHeight()){
			player.setY(-player.getHeight()+map.tileHeight*map.height);
		}
		

		// scroll so that the player is in the center
		camera.position.x=player.getX()-player.getWidth()/2;
		camera.position.y=player.getY()-player.getHeight()/2;
		
		// do not let the camera show places, where there is no map
		if(camera.position.x<400){
			camera.position.x=400;
		}else if(camera.position.x>map.width*map.tileWidth-400){
			camera.position.x=map.width*map.tileWidth-400;
		}
		
		if(camera.position.y<240){
			camera.position.y=240;
		}else if(camera.position.y>map.height*map.tileHeight-240){
			camera.position.y=map.height*map.tileHeight-240;
		}
		
		camera.update();
		
		

		// doors, that you can enter
		if(xtile==15&&map.height-1-ytile==17){
			game.setScreen(new MenuScreen(game));
		}
		
		

        
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                          (int) viewport.width, (int) viewport.height);
		
		// render map
		tileMapRenderer.render(camera,new int[]{0,1});
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.draw(batch, 1f);
		batch.end();
		
		tileMapRenderer.render(camera,new int[]{2});
		
		
		if(Gdx.input.isTouched()){
			saveScreenshot(Gdx.files.external("test.png"), 0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		}
		
        debugRenderer.render(world, camera.combined);  
        world.step(1/60f, 6,2);  

		
	}
	
	boolean upleft,downleft,upright,downright;
	
	/**
	 * calculate the corners
	 * @param pX
	 * @param pY
	 */
	public void getMyCorners(float pX,float pY){
		
		// calculate corner coordinates
		int downY=(int) Math.floor(map.height-(pY)/map.tileHeight);
		int upY=(int) Math.floor(map.height-(pY+player.getHeight())/map.tileHeight);
		int leftX=(int) Math.floor((pX)/map.tileWidth);
		int rightX=(int) Math.floor((pX+player.getWidth())/map.tileWidth);

		
		Gdx.app.log("pos",downY+" "+upY+" "+leftX+" "+rightX);
		
		// check if the in the corner is a wall
		
	
		
		upleft=isFree(leftX,upY);
		downleft=isFree(leftX,downY);
		upright=isFree(rightX,upY);
		downright=isFree(rightX,downY);
		
		Gdx.app.log("col", upleft+" "+downleft+" "+upright+" "+downright);
	}
	
	/**
	 * checks if a tile is free to walk on
	 * @param pX
	 * @param pY
	 * @return
	 */
	public boolean isFree(int pX,int pY){
		if(pX<0||pY<0||pX>=map.width||pY>=map.height){return true;}
		return collision.tiles[pY][pX]==freeTile;
	}
	
	public boolean blocked(int pX,int pY){
		return collision.tiles[map.height-1-pY][pX]!=freeTile;
	}
	
	public void checkForCollision(int pTileX,int pTileY){
		Gdx.app.log("check", pTileX+"..."+pTileY);
		if(collision.tiles[pTileY][pTileX]==freeTile){return;} // this tile can be walked
		float overlapX,overlapY;
		if((pTileX+1)*map.tileWidth-player.getX()>pTileX*map.tileWidth-player.getX()+player.getWidth()){
			overlapX=pTileX*map.tileWidth-player.getX();
		}else{
			overlapX=(pTileX)*map.tileWidth-player.getX()-player.getWidth();
		}
		overlapY=pTileY*map.tileHeight-player.getY();
		
		if(Math.abs(overlapY)<Math.abs(overlapX)){
			player.setXY(player.getX(), player.getY()+overlapY);
		}else{
			player.setXY(player.getX()+overlapX, player.getY());
		}
		//if(abs(overlap.y) < abs(overlap.x)) { position.y += overlap.y; } else { position.x += overlap.x; }
		
	}

	@Override
	public void resize(int width, int height) {

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
	
public class MyGestureListener implements GestureListener {

	
	float orgDistance;
	float orgCamera;
	
	@Override
	public boolean zoom (float originalDistance, float currentDistance) {
		if(originalDistance!=orgDistance){
			orgDistance=originalDistance;
			orgCamera=camera.zoom;
		}
		camera.zoom=orgCamera*originalDistance/currentDistance;
		return false;
	}

	@Override
	public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}
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

	
	public static void saveScreenshot(FileHandle pFile, int pX, int pY, int pWidth, int pHeight){
        Pixmap pixmap = getScreenshot(pX, pY, pWidth, pHeight, true);
        
        PixmapIO.writePNG(pFile, pixmap);
        pixmap.dispose();
	}
	
    public static Pixmap getScreenshot(int pX, int pY, int pWidth, int pHeight, boolean pFlipY) {
        Gdx.gl.glPixelStorei(GL10.GL_PACK_ALIGNMENT, 1);
        
        Pixmap pixmap = new Pixmap(pWidth, pHeight, Format.RGBA8888);
        ByteBuffer pixels = pixmap.getPixels();
        Gdx.gl.glReadPixels(pX, pY, pWidth, pHeight, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, pixels);
        
        final int numBytes = pWidth * pHeight * 4;
        byte[] lines = new byte[numBytes];
        if (pFlipY) {
                final int numBytesPerLine = pWidth * 4;
                for (int i = 0; i < pHeight; i++) {
                        pixels.position((pHeight - i - 1) * numBytesPerLine);
                        pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
                }
                pixels.clear();
                pixels.put(lines);
        } else {
                pixels.clear();
                pixels.get(lines);
        }
        
        return pixmap;
    }
	
}
