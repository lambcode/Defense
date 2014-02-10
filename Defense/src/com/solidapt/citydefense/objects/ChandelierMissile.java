package com.solidapt.citydefense.objects;

import com.solidapt.defense.TextureLoader;

public class ChandelierMissile extends HorizonMissile {

	public ChandelierMissile(int xCoord, int yCoord, int width, int height,
			int xTarget, int yTarget, double speedFactor) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
		this.myTexture = TextureLoader.CHANDELIER_TEXTURE;
	}
	
	@Override
	protected HorizonSmallMissile getMissileToAdd(int i) {
		return new HorizonSmallMissile(0, 0, 10, 20, 0, (int) (10 * Math.signum(i)), 250, (Math.signum(i) * 100) + (50 * i), this);
	}

}
