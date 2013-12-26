package com.solidapt.citydefense.objects;

import com.solidapt.defense.TextureLoader;

public class TurretGun extends StaticObject {

	public TurretGun(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
		this.myTexture = TextureLoader.TURRET_GUN_TEXTURE;
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub

	}

}
