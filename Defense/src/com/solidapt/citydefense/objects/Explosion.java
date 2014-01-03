package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.TextureLoader;

public class Explosion extends AnimatedObject {
	
	GameObject parentObject;
	int maxRadius;

	public  Explosion(int xCoord, int yCoord, int width, int height, GameObject parentObject) {
		super(xCoord, yCoord, width, height);
		this.myTexture = TextureLoader.EXPLOSION_TEXTURE;
		this.maxRadius = width > height ? width : height;
		
		this.parentObject = parentObject;
	}
	
	@Override
	public int getWidth() {
		return getCurrentExplosionDiameter();
	}
	
	@Override
	public int getHeight() {
		return getCurrentExplosionDiameter();
	}

	@Override
	public void gameLoopLogic(double time) {
		this.updateFrame(time);
			
	}
	
	/**
	 * Returns the explosion radius based on the current frame
	 * @return
	 */
	public int getCurrentExplosionDiameter() {
		double maxFrames = (double)this.myTexture.getFrames()/2; //the frame with max explosion radius (center frame)
		int currentFrame = this.getCurrentFrame();
		
		//calculates the diameter of explosion based on frame
		return (int)(((maxFrames - Math.abs((double)currentFrame - maxFrames)) / maxFrames) * (double)this.maxRadius);
	}

	public float getParentX() {
		return parentObject.getXCoord();
	}
	
	public float getParentY() {
		return parentObject.getYCoord();
	}

}
