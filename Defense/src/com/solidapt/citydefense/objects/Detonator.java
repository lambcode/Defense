package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.TextureLoader;

public class Detonator extends Projectile {
	
	Projectile parent;

	public Detonator(int xCoord, int yCoord, int width, int height, int xTarget, int yTarget, double speedFactor, Projectile parent) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
		this.myTexture = TextureLoader.MARKER_TEXTURE;
		this.parent = parent;
		Explosion temp = new DetonatorExplosion(0, 0, (int)(this.getWidth()*5.8), (int)(this.getHeight()*5.8), this);
		flame = null;
		explosion = temp;
		this.setScore(20);
	}

	@Override
	public void render(GL10 gl) {
        if (!(this.getExploding() && explosion.getCurrentFrame() > 30)) {
        	this.draw(gl);
        }
        explosion.gameRenderLoop(gl);

	}
	
	@Override
	public void createExplosionAndTrack() {
		if (!this.getExploding() && parent.explosion.getCurrentFrame() == parent.explosion.myTexture.getFrames()) {
			this.setExploding();
			if (this.explosion !=null) ExplosionTracker.addExplosion(this.explosion);
		}
	}

}
