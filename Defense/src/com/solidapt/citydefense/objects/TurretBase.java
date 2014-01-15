package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;

public class TurretBase extends Structure {
	GameObject gun;
	Explosion explosion = null;

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
		
	}

	GameObject lastExplosion = null;
	
	@Override
	public void gameLoopLogic(double time) {
		GameObject testExplosionResult = ExplosionTracker.collisionDetected(this);
		if (testExplosionResult != null && testExplosionResult != lastExplosion && !this.isExploding()) {
			lastExplosion = testExplosionResult;
			SoundLoader.playTwang((int)this.getXCoord(), (int)this.getYCoord());
			startExplosion();
			
			if (this.getCurrentFrame() < 2) {
				this.setCurrentFrame(this.getCurrentFrame() + 1);
			}
		}
		if (this.explosion != null) {
			if (this.explosion.getCurrentFrame() >= this.explosion.myTexture.getFrames()) {
				this.explosion = null;
			}
			else {
				this.explosion.gameLoopLogic(time);
			}
		}
	}
	
	private void startExplosion() {
		if (this.explosion == null) {
			this.explosion = new Explosion(0, 10, this.getWidth(), (int) (this.getWidth() * 1.3), this);
		}
	}
	
	@Override
	public void render(GL10 gl) {
		gun.gameRenderLoop(gl);
		this.draw(gl);
		if (this.explosion != null) {
			this.explosion.gameRenderLoop(gl);
		}
	}

}
