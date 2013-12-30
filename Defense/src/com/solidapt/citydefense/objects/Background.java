package com.solidapt.citydefense.objects;

import com.solidapt.defense.Util;


public abstract class Background extends StaticObject {

	public Background(double scale) {
		super(Util.getWidth()/2, Util.getHeight()/2, (int) (Util.getWidth() * scale), (int) (Util.getHeight() * scale));
	}
	
	public Background() {
		this(1);
	}

}
