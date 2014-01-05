package com.solidapt.mainMenu;


import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.BlueFlame;
import com.solidapt.citydefense.objects.Explosion;
import com.solidapt.citydefense.objects.Projectile;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;

public class ButtonMissile extends Projectile {

	public ButtonMissile(int xCoord, int yCoord, int width, int height, int xTarget, int yTarget, double speedFactor) {
		super(xCoord, yCoord, width, height, xTarget, yTarget, speedFactor);
		this.myTexture = TextureLoader.MISSILE_TEXTURE;
		ButtonExplosion temp = new ButtonExplosion(0, 0, (int)(this.getWidth()*2.8), (int)(this.getHeight()*2.8), this);
		flame = new BlueFlame(0, (int) (height*.9), width, height);
		explosion = temp;
	}
	
	@Override
	public void gameLoopLogic(double time) {
		
		if (explosion.getCurrentFrame() >= ButtonExplosion.PAUSE_FRAME) {
			explosion.gameLoopLogic(time);
		}
		else {
			super.gameLoopLogic(time);
		}
	}

	@Override
	public void render(GL10 gl) {
        if (!(this.getExploding() && explosion.getCurrentFrame() >= ButtonExplosion.PAUSE_FRAME)) {
        	flame.gameRenderLoop(gl);
        	this.draw(gl);
        }
        explosion.gameRenderLoop(gl);

	}
	
	public boolean isAnimationDone() {
		return explosion.getCurrentFrame() == explosion.myTexture.getFrames();
	}
	
	public void setClicked() {
		((ButtonExplosion)this.explosion).setClicked();
	}
	
	@Override
	public void finishDraw(GL10 gl) {
		super.finishDraw(gl);

		//Render the text without rotation
		if (explosion.getCurrentFrame() == ButtonExplosion.PAUSE_FRAME) {
			gl.glPushMatrix();
			gl.glTranslatef(this.getXCoord(), this.getYCoord(), 0.0f);
			Util.textRenderer.begin(0, 0, 0, 1);
			Util.textRenderer.drawC("Play", 0, 0);
			Util.textRenderer.end();
			gl.glTranslatef(-this.getXCoord(), -this.getYCoord(), 0.0f);
			gl.glPopMatrix();
		}
	}
}
