package de.bitowl.libgdxtest;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Enemy extends Image{
	
	Rectangle bounds;
	public Enemy(AtlasRegion findRegion) {
		super(findRegion);
		setPosition(0, 240-getHeight());
		bounds=new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	@Override
	public void act(float delta) {
		super.act(delta); 
		setPosition(getX(), getY()-delta*30);
		
		// update our bounds
		bounds.setY((int) (getY()-delta*30));
	}
	
	public void updateBounds(){
		bounds.setX((int) (getX()));
		bounds.setY((int) (getY()));
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
	
}
