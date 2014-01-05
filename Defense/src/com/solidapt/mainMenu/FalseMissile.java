package com.solidapt.mainMenu;

import com.solidapt.citydefense.objects.StandardMissile;


public class FalseMissile extends StandardMissile {

	public FalseMissile(int xCoord, int yCoord, int width, int height,
			int xTarget, int yTarget, double speedFactor) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
	}

	//Overridden to delete missile instead of exploding
	@Override
	protected void createExplosionAndTrack() {
		this.markForRemoval();
	}
}
