package com.solidapt.citydefense.objects;

import com.solidapt.defense.TextureLoader;

public class Marker extends StaticObject {

	public Marker(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
		this.myTexture = TextureLoader.MARKER_TEXTURE;
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub

	}

}
