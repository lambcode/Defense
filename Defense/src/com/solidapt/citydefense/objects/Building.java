package com.solidapt.citydefense.objects;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.TextureLoader;

/**
 * Buildings that missiles attract to
 * @author Brian
 *
 */
public class Building extends Structure {
	boolean exploding = false;
	
	public Building(int xCoord, int yCoord, int width, int height, int spriteID, int health) {
		super(xCoord, yCoord, width, height, spriteID, health);
		this.myTexture = TextureLoader.BUILDING_TEXTURE;
	}

	private boolean dead;
	
	@Override
	public void kill() {
		this.dead = true;
	}

	@Override
	public void gameLoopLogic(double time) {
		if (ExplosionTracker.collisionDetected(this) && !this.exploding) {
			this.exploding = true;
		}

		if (this.exploding) this.updateFrame(time);
	}
	
	//@Override
	public int getCurrentExplosionRadius() {
		return 0;
	}

}
