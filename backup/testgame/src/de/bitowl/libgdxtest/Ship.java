package de.bitowl.libgdxtest;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Ship extends Image{
	int SPEED=70;
	Rectangle bounds;
	public Ship(AtlasRegion findRegion) {
		super(findRegion);
		
		bounds=new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}

	public Rectangle getBounds(){
		return bounds;
	}
	
	void setXY(float pX,float pY){
		setPosition(pX, pY);
		bounds.setX((int)pX);
		bounds.setY((int)pY);
	}
	
	@Override
	public void act(float delta) {
		// move ship
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			setXY(getX()+delta*SPEED,getY());
		}else if(Gdx.input.isKeyPressed(Keys.LEFT)){
			setXY(getX()-delta*SPEED,getY());
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)){
			setXY(getX(),getY()+delta*SPEED);
		}else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			setXY(getX(),getY()-delta*SPEED);
		}
	}
}
