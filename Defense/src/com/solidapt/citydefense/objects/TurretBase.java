package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;

public class TurretBase extends Structure {
	GameObject gun;

	public TurretBase(int xCoord, int yCoord, int width, int height, int spriteID, int health) {
		super(xCoord, yCoord, width, height, spriteID, health);
		this.myTexture = TextureLoader.TURRET_BASE_TEXTURE;
		
		gun = new TurretGun(0, 20, 160, 160);
	}
	
	@Override
	public void setRotation(float rotation) {
		gun.setRotation(rotation);
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(GL10 gl) {
		gun.gameRenderLoop(gl);
		this.draw(gl);
	}

}
