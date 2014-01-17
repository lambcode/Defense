package com.solidapt.inGame;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.StaticObject;
import com.solidapt.defense.Texture;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;

public class SideBarToggle extends StaticObject {
	
	ColorSquare buttonBack;
	Integer link;

	public SideBarToggle(int xCoord, int yCoord, int width, int height, Texture imageTexture, Integer link) {
		super(xCoord, yCoord, width, height);
		buttonBack = new ColorSquare(0, 0, width, height, .6f, .6f, .6f, 1f);
		this.myTexture = imageTexture;
		this.link = link;
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub

	}
	
	public void setToggled(boolean value) {
		if (value == true) {
			buttonBack.changeColor(.1f, .1f, .1f, 1);
		}
		else {
			buttonBack.changeColor(.6f, .6f, .6f, 1f);
		}
	}
	
	@Override
	public void render(GL10 gl) {
		buttonBack.render(gl);
		gl.glRotatef(45, 0, 0, 1);
		this.draw(gl);
		gl.glRotatef(-45, 0, 0, 1);
		Util.textRenderer.begin(1,1,1,1);
		Util.textRenderer.setScale(.6f);
		Util.textRenderer.draw(link.toString(), -this.getWidth()/2, this.getHeight()/2 + Util.textRenderer.getHeight());
		Util.textRenderer.end();
	}
	
	public boolean getPressed(float x, float y) {
		float locX = this.getXCoord();
		float locY = this.getYCoord();
		int halfWidth = this.getWidth() / 2;
		int halfHeight = this.getHeight() / 2;
		
		if (x > locX - halfWidth && x < locX + halfWidth) {
			if (y > locY - halfHeight && y < locY + halfHeight) {
				return true;
			}
		}
		return false;
	}

}
