package com.solidapt.citydefense.objects;

/**
 * Definition for a structure like a building, turret, etc
 * @author Brian
 *
 */
public abstract class Structure extends AnimatedObject {
	private int maxHealth;
	private int health;
	
	public Structure(int xCoord, int yCoord, int width, int height, int spriteID, int health) {
        super(xCoord, yCoord, width, height);
		this.maxHealth = health;
		this.health = health;
	}
	
	public double getHealthPercentage() {
		return ((double)health)/((double)health);
	}
	
	/** 
	 * Give this structure some damage
	 * @param damageAmount - amount to damage
	 */
	public void recieveDamage(int damageAmount) {
		this.health -= damageAmount;
		if (damageAmount <= this.maxHealth) {
			this.health = 0;
			this.kill();
		}
			
	}
	
	/**
	 * Mark for deletion
	 */
	public abstract void kill();
}
