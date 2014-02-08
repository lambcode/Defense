package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;

public class TurretBase extends Structure {
	GameObject gun;
	Explosion explosion = null;
	ColorSquare heatBar;
	private float heatValue = 0;
	private static final float OVERHEAT_COUNT = 1;
	private boolean overheatedFlag = false;

	public TurretBase(int xCoord, int yCoord, int width, int height, int spriteID, int health) {
		super(xCoord, yCoord, width, height, spriteID, health);
		this.myTexture = TextureLoader.TURRET_BASE_TEXTURE;
		
		//The colors set here for heatBar are overriden later
		//Do not try to change the heatBar color here!
		this.heatBar = new ColorSquare(0, 10, 100, 20, 1f, .1f, .1f, .7f);
		
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
			else {
				this.markForRemoval();
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
		
		synchronized(this) {
			heatValue -= .1 * time;
			if (heatValue < 0) {
				heatValue = 0;
			}
			else if (overheatedFlag && heatValue < 1) {
				//Reset heatValue to 0 after timout value has been passed
				heatValue = 0;
				overheatedFlag = false;
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
		
		float scaleValue = 0;
		synchronized(this) {
			scaleValue = heatValue;
		}
		
		if (scaleValue > 1) {
			scaleValue = (scaleValue - 1) / OVERHEAT_COUNT;
			heatBar.changeColor(1, 0, 0, .7f);
		}
		else {
			heatBar.changeColor(1, .8f, 0, .7f);
		}
		gl.glScalef(scaleValue, 1, 1);
		heatBar.gameRenderLoop(gl);
		gl.glScalef(-scaleValue, 1, 1);
	}


	public void addHeatValue(MissileInformation missileInformation) {
		synchronized(this) {
			if (heatValue < 1) {
				heatValue += missileInformation.getHeatValue();
				if (heatValue > 1) {
					heatValue = 1 + OVERHEAT_COUNT;
					overheatedFlag = true;
				}
			}
		}
	}

	public boolean ableToFire() {
		return heatValue < 1;
	}

}
