package com.solidapt.citydefense.objects;

import java.util.Iterator;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.TextureLoader;

public class HorizonMissile extends StandardMissile {
	
	LinkedList<Projectile> smallMissiles = new LinkedList<Projectile>();
	boolean removeMe = false;

	public HorizonMissile(int xCoord, int yCoord, int width, int height, int xTarget, int yTarget, double speedFactor) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
		this.myTexture = TextureLoader.HORIZON_MISSILE_TEXTURE;
		this.setScore(20);
		
		for(int i = -8; i <= 8; i++) {
			if (i == 0) {
				i++;
			}
			
			//cool w shaped thing
			// ----> smallMissiles.add(new HorizonSmallMissile(0, 0, 30, 30, (int) (10 * Math.signum(i)), 250, (Math.signum(i) * 100) + (50 * i), this));
			smallMissiles.add(getMissileToAdd(i));
		}
	}
	
	protected HorizonSmallMissile getMissileToAdd(int i) {
		return new HorizonSmallMissile(0, 0, 10, 20, (int) (1 * Math.signum(i)), -18 * Math.abs(i),  (Math.abs(i) * 50), 120 * Math.signum(i), this);
	}
	
	@Override
	public void gameLoopLogic(double time) {
		if (this.explosion.getCurrentFrame() > 0) {
			if (this.currentYMultiplier > 0) {
				this.currentYMultiplier -= time * .4;
			}
			else {
				this.currentYMultiplier = 0;
			}
		}

		if (this.explosion.getCurrentFrame() >= this.explosion.myTexture.getFrames()) {
			if (this.currentXMultiplier != 0) {
				this.currentXMultiplier -= Math.signum(currentXMultiplier) * time * .4;
			}
		}
		
		if ((this.currentXMultiplier != 0 && this.currentYMultiplier != 0) || this.explosion.getCurrentFrame() < this.explosion.myTexture.getFrames()) {
			super.gameLoopLogic(time);
		}
		
		if (this.explosion.getCurrentFrame() > 0) {
			Iterator<Projectile> iterator = smallMissiles.iterator();
			while(iterator.hasNext()) {
				GameObject i = iterator.next();
				i.gameLoopLogic(time);
				if (i.needsRemoval()) {
					iterator.remove();
				}
			}
		}
	}
	
	@Override
	public void render(GL10 gl) {
		super.render(gl);
		float rotation = this.getRotation();
		float x = this.getXCoord();
		float y = this.getYCoord();

		gl.glRotatef(-rotation, 0, 0, 1);
		if (this.explosion.getCurrentFrame() > 0) {
			for (Projectile i : smallMissiles) {
				i.gameRenderLoop(gl);
			}
		}
		gl.glRotatef(rotation, 0, 0, 1);
	}

	@Override
	public boolean needsRemoval() {
		return smallMissiles.size() == 0;
	}

}
