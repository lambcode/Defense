package com.solidapt.defense.store;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.StaticObject;
import com.solidapt.defense.TextureLoader;

public class Button extends StaticObject {


	public Button(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
		this.myTexture = TextureLoader.OK_BUTTON_TEXTURE1;
	}
	
	@Override
	public void gameRenderLoop(GL10 gl) {
		synchronized(this) {
			super.gameRenderLoop(gl);
		}
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
	
	public void setHover(boolean set) {
		synchronized(this) {
			if (set) {
				this.myTexture = TextureLoader.OK_BUTTON_TEXTURE2;
			}
			else {
				this.myTexture = TextureLoader.OK_BUTTON_TEXTURE1;
			}
		}
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub
		
	}

}
