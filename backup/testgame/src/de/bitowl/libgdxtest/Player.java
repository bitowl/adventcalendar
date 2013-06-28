package de.bitowl.libgdxtest;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image {
	Rectangle bounds;
	public Player(TextureRegion textureRegion) {
		super(textureRegion);
		
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
}
