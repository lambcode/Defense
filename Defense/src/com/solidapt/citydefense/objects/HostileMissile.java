package com.solidapt.citydefense.objects;


import javax.microedition.khronos.opengles.GL10;

import android.graphics.Point;

import com.solidapt.defense.CollisionDetector;
import com.solidapt.defense.ScoreTracker;
import com.solidapt.defense.TextureLoader;

public class HostileMissile extends Projectile {

	public HostileMissile(int xCoord, int yCoord, int width, int height, int xTarget, int yTarget, double speedFactor) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
		this.myTexture = TextureLoader.MISSILE_TEXTURE;
		Explosion temp = new Explosion(0, 0, (int)(this.getWidth()*2.8), (int)(this.getHeight()*2.8), this);
		flame = new Flame(0, (int) (height*.9), (int) (width * .9), height);
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
	
	private boolean haveAddedScore = false;
	@Override
	public void gameLoopLogic(double time) {
		super.gameLoopLogic(time);
		
		if (this.needsRemoval() && this.getScore() > 0 && !haveAddedScore) {
			ScoreTracker.addScore(this.getScore(), (int)this.getXCoord(), (int)this.getYCoord());
			haveAddedScore = true;
		}
	}
	
	@Override
	public void explosionCausedBy(GameObject g) {
		this.addToScore(g.getScore());
		g.addToScore(g.getScore());
	}
}
