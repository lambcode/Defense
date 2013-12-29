package com.solidapt.citydefense.objects;

import com.solidapt.defense.Util;


public abstract class Background extends StaticObject {

	public Background() {
		super(Util.getWidth()/2, Util.getHeight()/2, Util.getWidth(), Util.getHeight());
	}

}
