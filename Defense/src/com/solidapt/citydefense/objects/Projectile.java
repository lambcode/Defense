package com.solidapt.citydefense.objects;


import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.SoundLoader;

import android.util.Log;

public abstract class Projectile extends StaticObject {
	private int xTargetCoord;
	private int yTargetCoord;
	private double speedFactor;
	private boolean exploding;
	private float angle;
	private float angleDegrees;
    private static final double piOver2 = Math.PI/2;

	protected GameObject flame;
	protected Explosion explosion;
	
	private float currentXMultiplier = 0;
	private float currentYMultiplier = 0;
	
	public Projectile(int xCoord, int yCoord, int width, int height, int xTarget, int yTarget, double speedFactor) {
		super(xCoord,yCoord, width, height);
		this.xTargetCoord = xTarget;
		this.yTargetCoord = yTarget;
		this.speedFactor = speedFactor;

		
		this.determineAngle();
	}
	
	
	@Override
	public void gameLoopLogic(double time) {
		//if (!this.exploding) {
		if (!this.exploding) {
			Explosion collideWith = ExplosionTracker.collisionDetected(this);
			if (collideWith != null) {
				createExplosionAndTrack();
				//Add points from collision object to this object
				explosionCausedBy(collideWith.parentObject);
			}
		}
		float x = this.getXCoord();
		float y = this.getYCoord();

		double xDistance = Math.abs(xTargetCoord - x);
		double yDistance = Math.abs(yTargetCoord - y);

		//Move the missile toward the target point
		double distance = time * speedFactor;

		x += currentXMultiplier*distance;
		y -= currentYMultiplier*distance;
		this.setXCoord(x);
		this.setYCoord(y);
		if (Math.abs(xTargetCoord - x) < xDistance || Math.abs(yTargetCoord - y) < yDistance) {

		}
		else if (!this.exploding){
			createExplosionAndTrack();
		}

		//}
		//this.updateFrame(time);
		flame.gameLoopLogic(time);
		if (this.exploding && explosion != null) explosion.gameLoopLogic(time);
		if (this.exploding && explosion != null && explosion.myTexture != null)
			if (explosion.getCurrentFrame() >= explosion.myTexture.getFrames()) {
				this.markForRemoval();
				ExplosionTracker.removeExplosion(this.explosion);
			}
	}


	public void createExplosionAndTrack() {
		if (!this.exploding) {
			this.exploding = true;
			if (this.explosion !=null) ExplosionTracker.addExplosion(this.explosion);
			SoundLoader.playExplosion((int) this.getXCoord(), (int) this.getYCoord());
		}
	}
	
	public void setExploding() {
		this.exploding = true;
	}
	
	private void determineAngle() {
		double initAngle = 0;
		if (yTargetCoord != this.getYCoord()) 
			initAngle = Math.atan((this.getXCoord()-xTargetCoord)/(double)(yTargetCoord-this.getYCoord()));
		if (yTargetCoord > this.getYCoord() || (yTargetCoord == this.getYCoord()))
				initAngle += Math.PI;
		
		this.angle = (float)((initAngle + (2*Math.PI)) % (2*Math.PI));
		angleDegrees = (float)Math.toDegrees(this.getAngle());
		currentXMultiplier = (float)Math.sin((double)this.getAngle());
        currentYMultiplier = (float)Math.cos((double)this.getAngle());
        this.setRotation(angleDegrees);
	}

	public boolean getExploding() {
		return this.exploding;
	}
	
	public float getAngle() {
		return this.angle;
	}
	
	public float getAngleDegrees() {
		return this.angleDegrees;
	}
	
	public void setTargetPoint(int xTarget, int yTarget) {
		if (!(xTarget == this.getXCoord() && yTarget == this.getYCoord())) {
			this.xTargetCoord = xTarget;
			this.yTargetCoord = yTarget;
			this.determineAngle();
		}
	}
	
	public int getCurrentExplosionRadius() {
		if (explosion != null) 
			return ((Explosion)explosion).getCurrentExplosionDiameter();
		else
			return 0;
	}
}
