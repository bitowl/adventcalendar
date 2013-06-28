package de.bitowl.libgdxtest;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * a layer from a parallex background
 * 
 * http://www.badlogicgames.com/forum/viewtopic.php?f=17&t=1795
 * 
 * @author bitowl
 *
 */
public class ParallaxLayer {
	/**
	 * the Texture sitting on this layer
	 */
	public TextureRegion region;

	/**
	 * how much shall this layer (in percent) be moved if the whole background is moved
	 * 0.5f is half as fast as the speed
	 * 2.0f is twice the speed
	 */
	float ratioX, ratioY;

	/**
	 * current position
	 */
	float positionX, positionY;

	/**
	 * sehr einfache wiederholung des bildes
	 */
	boolean repeat;
	
	/**
	 * 
	 * @param pRegion
	 * @param pRatioX
	 * @param pRatioY
	 */
	public ParallaxLayer(TextureRegion pRegion, float pRatioX, float pRatioY, boolean pRepeat) {
		region = pRegion;
		ratioX = pRatioX;
		ratioY = pRatioY;
		repeat = pRepeat;
	}

	/**
	 * move this layer
	 * @param pDelta
	 */
	protected void moveX(float pDelta) {
		positionX += pDelta * ratioX;
		if(repeat){
			positionX%=region.getRegionWidth();
		}
	}

	/**
	 * move this layer
	 * @param pDelta
	 */
	protected void moveY(float pDelta) {
		positionY += pDelta * ratioY;
		if(repeat){
			positionY%=region.getRegionHeight();
		}
	}
}
