package de.bitowl.libgdxtest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;



// http://freesound.org/people/bilwiss/sounds/24725/
// http://freesound.org/people/Koops/sounds/20258/


public class SplashScreen implements Screen{

	Texture tree;
	TextureRegion christmasTree;
	AtlasRegion ball;
	AtlasRegion snow;
	Stage stage;
	BitmapFont font;

	TextureAtlas atlas;
	
	TestGame game;
	
	NinePatch patch;
	
	Animation testAnimUp;
	Animation testAnimLeft;
	Animation testAnimDown;
	Animation testAnimRight;
	float animTime;
	/**
	 * 0: top
	 * 1: left
	 * 2: down
	 * 3: right
	 */
	int direction;
	
	Image mummyImg;
	AnimationDrawable animUp;
	AnimationDrawable animLeft;
	AnimationDrawable animDown;
	AnimationDrawable animRight;
	
	
	// music 
	Music music;
	
	OrthographicCamera camera;
	
	
	public SplashScreen(TestGame pGame){
		game=pGame;
		
		
		atlas = game.assets.get("graphics/testPack.atlas", TextureAtlas.class);//new TextureAtlas(Gdx.files.internal("graphics/testPack.atlas"));
		christmasTree=atlas.findRegion("tree");
		ball=atlas.findRegion("ball");
		snow=atlas.findRegion("snow");
		
		
		TextureAtlas uiatlas = new TextureAtlas(Gdx.files.internal("ui/myskin.atlas"));
		patch=uiatlas.createPatch("button");
		System.out.println(ball);
		//snow=new Texture(Gdx.files.internal("graphics/snow.png"));
		
		font=new BitmapFont(Gdx.files.internal("ui/test.fnt"), atlas.findRegion("test"), false);
		
		stage=new Stage();
		
		
	/*	TextureRegion[] testframes=new TextureRegion[4];
		for(int i=0;i<4;i++){ // add all the frames of that animation
			testframes[i]=atlas.findRegion("mummyleft", i);
		}
		
		Animation testanim=new Animation(2f, testframes);
	*/
		

		
		Mummy mummy=new Mummy(atlas.findRegions("mummyleft"));
		mummy.setPosition(100, 200);
		stage.addActor(mummy);
		testAnimUp=new Animation(0.2f, atlas.findRegions("mummyup"));
		testAnimLeft=new Animation(0.2f, atlas.findRegions("mummyleft"));
		testAnimDown=new Animation(0.2f, atlas.findRegions("mummydown"));
		testAnimRight=new Animation(0.2f, atlas.findRegions("mummyright"));
		
		animUp=new AnimationDrawable(testAnimUp,true);
		animLeft=new AnimationDrawable(testAnimLeft,true);
		animDown=new AnimationDrawable(testAnimDown,true);
		animRight=new AnimationDrawable(testAnimRight,true);
		
		mummyImg=new Image(animUp);
		mummyImg.setPosition(50, 50);
		stage.addActor(mummyImg);
		

		// our christmas tree
		Image ctree=new Image(christmasTree);
		ctree.setSize(296, 480); // scale the tree to the right size
		ctree.setPosition(-300,0);
		ctree.addAction(moveTo(400-148, 0,1f));
		ctree.setZIndex(0);
		stage.addActor(ctree);

		Image ballImage=new Image(ball);
		ballImage.setPosition(400 - 148+60, 170);
		
		
		ballImage.setOrigin(32,32);
		ballImage.setColor(1,1,1,0);
		ballImage.addAction(sequence(delay(1),parallel(fadeIn(1),rotateBy(360,1)),delay(2f),
				new Action() { // custom action to switch to the menu screen
					@Override
					public boolean act(float delta) {
						Resources.page_turn.play();
						game.setScreen(new MenuScreen(game));
						return true;
					}
				}));
	
		
		stage.addActor(ballImage);
		
		// create the snowflakes
		for(int i=0;i<10;i++){
			spawnSnowflake();
		}
		
		
		
		
		// music
		//music=Gdx.audio.newMusic(Gdx.files.internal());
		music=game.assets.get("sound/silent-night.wav",Music.class);
		music.setLooping(true);
		music.play();
	//	music.setVolume(1.5f);
		Resources.page_turn=game.assets.get("sound/page-turn.wav",Sound.class);
		//Gdx.audio.newSound(Gdx.files.internal("sound/page-turn.wav"));
		//sound.play(1.5f);
		float viewportWidth;
		float viewportHeight;
		float aspectRatio;
		float virtualWidth=800;
		float virtualHeight=480;
		
	    aspectRatio = virtualWidth / virtualHeight;

		   float physicalWidth = Gdx.graphics.getWidth();
		    float physicalHeight = Gdx.graphics.getHeight();

		    if (physicalWidth / physicalHeight >= aspectRatio) {
//		      Letterbox left and right.
		         viewportHeight = virtualHeight;
		         viewportWidth = viewportHeight * physicalWidth / physicalHeight;
		         //barSize = ????;
		    }
		    else {
//		      Letterbox above and below.
		       viewportWidth = virtualWidth;
		       viewportHeight = viewportWidth * physicalHeight / physicalWidth;
		       //barSize = ????;
		    }
		    
		
		camera=new OrthographicCamera(viewportWidth,viewportHeight);
		camera.position.set(400,240,0);
		camera.update();
		stage.setCamera(camera);
	}
	
	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// let the stage act and draw
		stage.act(delta);
		stage.draw();
		//stage.setViewport(800,480,false);
		
		animTime+=Gdx.graphics.getDeltaTime();
		
		
		// handle input
		
		/**
		 * 0: top
		 * 1: left
		 * 2: down
		 * 3: right
		 */
		if(Gdx.input.isKeyPressed(Keys.UP)){
			direction=0;
		}else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			direction=1;
		}else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			direction=2;
		}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			direction=3;
		}
		
		
		// draw our text
		stage.getSpriteBatch().begin();
		font.draw(stage.getSpriteBatch(), "mega awesome-game starts...", 50,80);
	//	patch.draw(stage.getSpriteBatch(), 0,0,800,100);
		
		switch(direction){
			case 0:
				mummyImg.setDrawable(animUp);
				//stage.getSpriteBatch().draw(testAnimUp.getKeyFrame(animTime), 50,50);
				break;
			case 1:
				//stage.getSpriteBatch().draw(testAnimLeft.getKeyFrame(animTime), 50,50);
				mummyImg.setDrawable(animLeft);
				break;
			case 2:
				//stage.getSpriteBatch().draw(testAnimDown.getKeyFrame(animTime), 50,50);
				mummyImg.setDrawable(animDown);
				break;
			case 3:
				//stage.getSpriteBatch().draw(testAnimRight.getKeyFrame(animTime), 50,50);
				mummyImg.setDrawable(animRight);
				break;
		}
		
		stage.getSpriteBatch().end();
	}

	
	public void spawnSnowflake(){
		final Image snowflake=new Image(snow);
		snowflake.setOrigin(64,64);
		int x=(int) (Math.random()*800);
		snowflake.setPosition(x,480);
		snowflake.setScale((float) (Math.random()*0.8f+0.2f));
		snowflake.addAction(parallel(
				forever(rotateBy(360,(float) (Math.random()*6))),
				sequence(moveTo(x,0,(float) (Math.random()*15)),fadeOut((float) (Math.random()*1)),
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
	public void resize(int width, int height) {
		float viewportWidth;
		float viewportHeight;
		float aspectRatio;
		float virtualWidth=800;
		float virtualHeight=480;
		
		   aspectRatio = virtualWidth / virtualHeight;
		   
		   float physicalWidth = Gdx.graphics.getWidth();
		    float physicalHeight = Gdx.graphics.getHeight();

		    if (physicalWidth / physicalHeight >= aspectRatio) {
//		      Letterbox left and right.
		         viewportHeight = virtualHeight;
		         viewportWidth = viewportHeight * physicalWidth / physicalHeight;
		         //barSize = ????;
		    }
		    else {
//		      Letterbox above and below.
		       viewportWidth = virtualWidth;
		       viewportHeight = viewportWidth * physicalHeight / physicalWidth;
		       //barSize = ????;
		    }
		    
		
		//camera=new OrthographicCamera(viewportWidth,viewportHeight);
		camera.setToOrtho(false,viewportWidth,viewportHeight);
		camera.position.set(400,240,0);
		//camera.rotate(180, 1, 0, 0);
		camera.update();
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
		stage.dispose();
		atlas.dispose();
		music.dispose();
	}
}
