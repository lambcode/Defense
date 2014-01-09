package com.solidapt.defense.overlayMenu;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.Util;

public class OverlayButton extends ColorSquare {
	
	String text;

	public OverlayButton(String text, int xCoord, int yCoord, int width, int height,
			float red, float green, float blue, float alpha) {
		super(xCoord, yCoord, width, height, red, green, blue, alpha);

		this.text = text;
	}
	
	@Override
	public void render(GL10 gl) {
		super.render(gl);
		
		Util.textRenderer.begin(0f, 0f, 0f, 1);
		Util.textRenderer.setScale(.8f);
		Util.textRenderer.drawC(text, 0, 0);
		Util.textRenderer.end();
	}

}
