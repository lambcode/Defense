package com.solidapt.inGame;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.StaticObject;
import com.solidapt.defense.TextureLoader;

public class OverlayMenuButton extends StaticObject {

	public OverlayMenuButton(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
		this.myTexture = TextureLoader.OVERLAY_MENU_BUTTON;
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void render(GL10 gl) {
		gl.glColor4f(1f, 1f, 1f, .3f);
		this.draw(gl);
		gl.glColor4f(1f, 1f, 1f, 1f);
	}

}
