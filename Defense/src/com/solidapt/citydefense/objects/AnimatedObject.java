package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public abstract class AnimatedObject extends GameObject {
    private int currentFrame;
    private double timeSinceLastFrame;

	public AnimatedObject(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
        timeSinceLastFrame = 0;
        currentFrame = 0;
	}
	
	public void updateFrame(double time) {
		if (myTexture != null && myTexture.getFrames() > 1) {
			timeSinceLastFrame += time;
			if (timeSinceLastFrame > myTexture.getSecondsPerFrame()) {
				if (this.currentFrame < myTexture.getFrames() || myTexture.isLooping())
					addFrames((int)Math.floor(timeSinceLastFrame/myTexture.getSecondsPerFrame()));
				timeSinceLastFrame =0;
			}
		}
	}
	

    private void addFrames(int x) {
    	int newValue = this.currentFrame + x; 
    	if (myTexture.isLooping())
    		this.currentFrame = newValue % myTexture.getFrames();
    	else
    		this.currentFrame = newValue <= myTexture.getFrames() ? newValue : myTexture.getFrames();
    }
    
    public int getCurrentFrame() {
    	return this.currentFrame;
    }
    
    @Override
    public void draw(GL10 gl) {
    	placemat.draw(gl, myTexture, currentFrame);
	}
}
