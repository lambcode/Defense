package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.solidapt.defense.TextureLoader;

public class BlueFlame extends AnimatedObject {

	public BlueFlame(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
		this.myTexture = TextureLoader.BLUE_FLAME_TEXTURE;
	}

	@Override
	public void gameLoopLogic(double time) {
		this.updateFrame(time);
	}

}
