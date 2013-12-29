package com.solidapt.citydefense.objects;

import android.graphics.BitmapFactory;

import com.solidapt.defense.R;
import com.solidapt.defense.Texture;
import com.solidapt.defense.Util;

public class Logo extends Background {
	
	public Logo() {
		super();
		this.setWidth((int) (this.getWidth() * .7));
		this.setHeight((int) (this.getHeight() * .7));
		BitmapFactory.Options options = new BitmapFactory.Options();
    	options.inScaled = false;
    	this.myTexture = new Texture(BitmapFactory.decodeResource(
				Util.context.getResources(), R.drawable.logo, options), 1, 1, false);
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub

	}

}
