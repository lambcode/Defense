package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.mainMenu.FalseMissile;

public class RadioActiveMissile extends StandardMissile {
	
	Detonator[] smallerOnes2 = new Detonator[14];
	boolean smallExplosionsAdded = false;

	public RadioActiveMissile(int xCoord, int yCoord, int width, int height,
			int xTarget, int yTarget, double speedFactor) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
		this.myTexture = TextureLoader.RADIO_ACTIVE_MISSILE_TEXTURE;
		
		this.explosion = new Explosion(0, 0, (int)(this.getWidth()*2), (int)(this.getHeight()*2), this);

		int mywidth = this.getWidth() > this.getHeight() ? this.getWidth(): this.getHeight();
		int locAdd = (int) (mywidth * 1.5);
		
		smallerOnes2[0] = new Detonator(0, 0, 10, 10, locAdd, -1, 100, this);
		smallerOnes2[1] = new Detonator(0, 0, 10, 10, 1, -locAdd, 100, this);
		smallerOnes2[2] = new Detonator(0, 0, 10, 10, -1, locAdd, 100, this);
		smallerOnes2[3] = new Detonator(0, 0, 10, 10, -locAdd, 1, 100, this);
		smallerOnes2[4] = new Detonator(0, 0, 10, 10, (int) (locAdd * .707), (int) (locAdd * .707), 100, this);
		smallerOnes2[5] = new Detonator(0, 0, 10, 10, -(int) (locAdd * .707), -(int) (locAdd * .707), 100, this);
		smallerOnes2[6] = new Detonator(0, 0, 10, 10, -(int) (locAdd * .707), (int) (locAdd * .707), 100, this);
		smallerOnes2[7] = new Detonator(0, 0, 10, 10, (int) (locAdd * .707), -(int) (locAdd * .707), 100, this);
		

		locAdd = (int) (mywidth * .5);
		smallerOnes2[8] = new Detonator(0, 0, 10, 10, locAdd, 1, 55, this);
		smallerOnes2[9] = new Detonator(0, 0, 10, 10, (int) (locAdd * .5), -(int) (locAdd * .867), 55, this);
		smallerOnes2[10] = new Detonator(0, 0, 10, 10, -(int) (locAdd * .5), -(int) (locAdd * .867), 55, this);
		smallerOnes2[11] = new Detonator(0, 0, 10, 10, -locAdd, -1, 55, this);
		smallerOnes2[12] = new Detonator(0, 0, 10, 10, -(int) (locAdd * .5), (int) (locAdd * .867), 55, this);
		smallerOnes2[13] = new Detonator(0, 0, 10, 10, (int) (locAdd * .5), (int) (locAdd * .867), 55, this);
		
		this.setScore(20);
	}
	
	boolean firstExplosion = true;
	boolean removeMe = false;
	
	@Override
	public void gameLoopLogic(double time) {
		super.gameLoopLogic(time);
		
		
		if (this.explosion.getCurrentFrame() > 0) {
			boolean needsRemoval = true;
			if (firstExplosion && smallerOnes2[0].getExploding()) {
				SoundLoader.playExplosion((int) this.getXCoord(), (int) this.getYCoord());
				firstExplosion = false;
			}
			for (Projectile i : smallerOnes2) {
				i.gameLoopLogic(time);
				if (!i.needsRemoval()) {
					needsRemoval = false;
				}
			}
			removeMe = needsRemoval;
		}
	}
	
	@Override
	public void render(GL10 gl) {
		super.render(gl);

		if (this.explosion.getCurrentFrame() > 0) {
			for (Projectile i : smallerOnes2) {
				i.gameRenderLoop(gl);
			}
		}
	}
	
	@Override
	public boolean needsRemoval() {
		return removeMe;
	}

}
