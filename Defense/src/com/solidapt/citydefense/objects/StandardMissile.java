package com.solidapt.citydefense.objects;


import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.TextureLoader;

public class StandardMissile extends Projectile {

	public StandardMissile(int xCoord, int yCoord, int width, int height, int xTarget, int yTarget, double speedFactor) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
		this.myTexture = TextureLoader.MISSILE_TEXTURE;
		Explosion temp = new Explosion(0, 0, (int)(this.getWidth()*2.8), (int)(this.getHeight()*2.8), this);
		flame = new BlueFlame(0, (int) (height*.9), (int) (width * .9), height);
		explosion = temp;
	}

	@Override
	public void render(GL10 gl) {
        if (!(this.getExploding() && explosion.getCurrentFrame() > 30)) {
        	flame.gameRenderLoop(gl);
        	this.draw(gl);
        }
        explosion.gameRenderLoop(gl);

	}
}
