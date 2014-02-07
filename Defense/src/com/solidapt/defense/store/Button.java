package com.solidapt.defense.store;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.StaticObject;
import com.solidapt.defense.Texture;
import com.solidapt.defense.TextureLoader;

public class Button extends StaticObject {

	Texture[] textures = new Texture[2];
	private boolean enabled = true;

	public Button(int xCoord, int yCoord, int width, int height, Texture firstTex, Texture secondTex) {
		super(xCoord, yCoord, width, height);
		this.myTexture = firstTex;
		textures[0] = firstTex;
		textures[1] = secondTex;
	}
	
	@Override
	public void gameRenderLoop(GL10 gl) {
		synchronized(this) {
			if (!enabled) gl.glColor4f(.5f, .5f, .5f, 1f);
			super.gameRenderLoop(gl);
			gl.glColor4f(1f, 1f, 1f, 1f);
		}
	}
	
	public boolean getPressed(float x, float y) {
		if (enabled) {
			float locX = this.getXCoord();
			float locY = this.getYCoord();
			int halfWidth = this.getWidth() / 2;
			int halfHeight = this.getHeight() / 2;

			if (x > locX - halfWidth && x < locX + halfWidth) {
				if (y > locY - halfHeight && y < locY + halfHeight) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void setButtonEnabled(boolean value) {
		enabled = value;
	}
	
	public void setHover(boolean set) {
		synchronized(this) {
			if (set) {
				this.myTexture = textures[1];
			}
			else {
				this.myTexture = textures[0];
			}
		}
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub
		
	}

}
