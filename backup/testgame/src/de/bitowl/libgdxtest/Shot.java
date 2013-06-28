package de.bitowl.libgdxtest;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Shot extends Image{

	Rectangle bounds;
	public Shot(AtlasRegion findRegion) {
		super(findRegion);
		bounds=new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	public Rectangle getBounds(){
		return bounds;
	}

	public void setXY(float x, float y) {
		setPosition(x, y);
		bounds.setX((int)x);
		bounds.setY((int)y);
	}
	@Override
	public void act(float delta) {
		super.act(delta);
		

		setPosition(getX(), getY()+delta*80);
		
		// update our bounds
		bounds.setY((int) (getY()+delta*80));
	}
}
