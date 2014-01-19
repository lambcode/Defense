package com.solidapt.citydefense.objects;

import com.solidapt.defense.ExplosionTracker;

public class HorizonSmallMissile extends StandardMissile implements SecondaryProjectile {
	
	private float angleChangeSpeed;
	private float speedFactor;
	private int initialXCoord;
	private Projectile parent;

	public HorizonSmallMissile(int xCoord, int yCoord, int width, int height, int targetXCoord, int initialXCoord, double speedFactor, float angleChangeSpeed, Projectile parent) {
		//Always start this missile pointing upwards.
		//To ensure this 
		//the target y coord is above the yCoord
		super(xCoord, yCoord, width, height, targetXCoord, yCoord - 20, speedFactor);
		this.explosion = new DetonatorExplosion(0, 0, (int)(this.getWidth()*3.8), (int)(this.getHeight()*3.8), this);
		this.angleChangeSpeed = angleChangeSpeed;
		this.speedFactor = (float)speedFactor;
		this.initialXCoord = initialXCoord;
		this.parent = parent;
	}
	
	@Override
	public void createExplosionAndTrack() {
		if (!this.getExploding() && parent.explosion.getCurrentFrame() == parent.explosion.myTexture.getFrames()) {
			super.createExplosionAndTrack();
		}
	}

	@Override
	public void gameLoopLogic(double time) {
		
		if (!this.getExploding()) {
			Explosion collideWith = ExplosionTracker.collisionDetected(this);
			if (collideWith != null) {
				createExplosionAndTrack();
				//Add points from collision object to this object
				explosionCausedBy(collideWith.parentObject);
			}


			this.setRotation((float) (this.getRotation() + (time * angleChangeSpeed)));

		}
		float x = this.getXCoord();
		float y = this.getYCoord();

		//Move the missile toward the target point
		double distance = time * speedFactor;
		
		float currentXMultiplier = (float) Math.sin(Math.toRadians(this.getRotation()));
		float currentYMultiplier = (float) Math.cos(Math.toRadians(this.getRotation()));

		x += currentXMultiplier * distance;
		if (this.getExploding()) {
			//apply gravity
			if (currentYMultiplier > -.6) {
				currentYMultiplier -= .7 * time;
			}
		}
		y -= currentYMultiplier*distance;
		this.setXCoord(x);
		this.setYCoord(y);
		if (y < initialXCoord || Math.abs(this.getRotation()) < 45 ) {

		}
		else if (!this.getExploding()){
			createExplosionAndTrack();
		}

		if (flame != null) flame.gameLoopLogic(time);
		if (this.getExploding() && explosion != null) explosion.gameLoopLogic(time);
		if (this.getExploding() && explosion != null && explosion.myTexture != null)
			if (explosion.getCurrentFrame() >= explosion.myTexture.getFrames()) {
				this.markForRemoval();
				ExplosionTracker.removeExplosion(this.explosion);
			}
	}

	
	@Override
	public void addToScore(int amount) {
		parent.addToScore(amount);
	}
	
	@Override
	public int getScore() {
		return parent.getScore();
	}

	@Override
	public Projectile getParent() {
		return parent;
	}
	
}
