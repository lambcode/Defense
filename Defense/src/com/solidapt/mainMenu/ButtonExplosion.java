package com.solidapt.mainMenu;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.AnimatedObject;
import com.solidapt.citydefense.objects.Explosion;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;

public class ButtonExplosion extends Explosion {
	
	boolean clicked = false;

	public ButtonExplosion(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);

		this.myTexture = TextureLoader.EXPLOSION_TEXTURE;
	}

	@Override
	public void gameLoopLogic(double time) {
		if (this.getCurrentFrame() < 32 || clicked == true) {
			this.updateFrame(time);
		}
		else if (clicked == false) {
			this.setCurrentFrame(32);
		}

	}
	
	@Override
	public void render(GL10 gl) {
		this.draw(gl);
	}
	
	public void setClicked() {
		this.clicked = true;
	}

}
