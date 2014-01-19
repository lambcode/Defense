package com.solidapt.citydefense.objects;

public class DetonatorExplosion extends Explosion {
	
	SecondaryProjectile parent;

	public DetonatorExplosion(int xCoord, int yCoord, int width, int height,
			SecondaryProjectile parentObject) {
		super(xCoord, yCoord, width, height, (Projectile)parentObject);
		this.parent = parentObject;
	}

	@Override
	public float getParentX() {
		return parent.getXCoord() + parent.getParent().getXCoord();
	}
	
	@Override
	public float getParentY() {
		return parent.getYCoord() + parent.getParent().getYCoord();
	}
}
