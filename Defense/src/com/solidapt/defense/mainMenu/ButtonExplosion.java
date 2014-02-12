package com.solidapt.defense.mainMenu;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.AnimatedObject;
import com.solidapt.citydefense.objects.Explosion;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;

public class ButtonExplosion extends Explosion {
	
	boolean clicked = false;
	static final int PAUSE_FRAME = 25;

	public ButtonExplosion(int xCoord, int yCoord, int width, int height, GameObject parent) {
		super(xCoord, yCoord, width, height, parent);

		this.myTexture = TextureLoader.EXPLOSION_TEXTURE;
	}

	@Override
	public void gameLoopLogic(double time) {
		if (this.getCurrentFrame() < PAUSE_FRAME || clicked == true) {
			this.updateFrame(time);
		}
		else if (clicked == false) {
			this.setCurrentFrame(PAUSE_FRAME);
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
