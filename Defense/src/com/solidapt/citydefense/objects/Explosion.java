package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.TextureLoader;

public class Explosion extends AnimatedObject {

	public  Explosion(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
		this.myTexture = TextureLoader.EXPLOSION_TEXTURE;
	}

	@Override
	public void gameLoopLogic(double time) {
		this.updateFrame(time);
			
	}
	
	/**
	 * Returns the explosion radius based on the current frame
	 * @return
	 */
	public int getCurrentExplosionRadius() {
		double maxFrames = (double)this.myTexture.getFrames()/2; //the frame with max explosion radius (center frame)
		int currentFrame = this.getCurrentFrame();
		
		//calculates the radius of explosion based on frame
		return (int)(((maxFrames - Math.abs((double)currentFrame - maxFrames)) / maxFrames) * (double)this.getWidth() * .4);
	}

}
