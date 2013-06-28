package de.bitowl.libgdxtest;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;


public class Mummy extends Image {
	Animation animation;
	
	public Mummy(Array<AtlasRegion> array){
		animation=new Animation(2f, array);
		animation.setPlayMode(Animation.LOOP);
	}
	
	float animationStateTime;
	@Override
	public void act(float delta) {
		super.act(delta);
		animationStateTime += delta;
		TextureRegion frame = animation.getKeyFrame( animationStateTime,true );
//		setDrawable((Drawable)frame);
		//setRegion( frame );
	//	this.setR
	}
	
}
