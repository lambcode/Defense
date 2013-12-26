package com.solidapt.defense;

import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class Texture {
	private int frames;
	public int textures[];
	private boolean loop;
	private double secondsPerFrame;

	public Texture(Bitmap textureFile, int frames, int framesPerSecond, boolean loop) {
		this.frames = frames-1;// to account for the 0 index
		this.secondsPerFrame = 1/(double)framesPerSecond;
		this.loop = loop;
		
		//loadTextures
		int chunks = (int)Math.ceil(Math.sqrt((double)frames));
		textures = new int[frames+1]; // fill array
		int chunkSize = textureFile.getWidth()/chunks;
		int textureCount = 0; 
		for (int y = 0; y < chunks; y++) { 
			for (int x = 0; x < chunks; x++) {
				loadGLTexture(Bitmap.createBitmap(textureFile, x*chunkSize, y*chunkSize, chunkSize-1, chunkSize-1), x+(y*chunks), Util.mygl, Util.context);
				
				textureCount++;
				if (textureCount == frames)
					break;
			}
			if (textureCount == frames)
				break;
		}
	}

    public void loadGLTexture(Bitmap bitmap, int frame, GL10 gl, Context context) {
    	// generate one texture pointer
    	gl.glGenTextures(1, textures, frame);
    	// ...and bind it to our array
    	gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[frame]);
    	
    	// create nearest filtered texture
    	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
    	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
    	
    	// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
    	GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
    	
    	// Clean up
    	bitmap.recycle();
    }
    
    public int getTexture(int x) {
    	return textures[x];
    }
    
    public int getFrames() {
    	return this.frames;
    }
    
    public double getSecondsPerFrame() {
    	return this.secondsPerFrame;
    }
    
    public boolean isLooping() {
    	return this.loop;
    }
}