package com.solidapt.citydefense.objects;

public class DetonatorExplosion extends Explosion {
	
	Detonator parent;

	public DetonatorExplosion(int xCoord, int yCoord, int width, int height,
			Detonator parentObject) {
		super(xCoord, yCoord, width, height, parentObject);
		this.parent = parentObject;
	}

	@Override
	public float getParentX() {
		return parent.getXCoord() + parent.parent.getXCoord();
	}
	
	@Override
	public float getParentY() {
		return parent.getYCoord() + parent.parent.getYCoord();
	}
}
