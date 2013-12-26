package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.Texture;

public abstract class StaticObject extends GameObject {

	public StaticObject(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
	}

	public void changeTexture(Texture texture) {
		this.myTexture = texture;
	}
}
