package com.solidapt.mainMenu;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.HostileMissile;
import com.solidapt.defense.Logic;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.Util;

public class TopMenu extends Logic {
	
	GameObject background;
	ButtonMissile buttonMissile;
	
	public TopMenu() {
		background = new MainMenuBackground();
		buttonMissile = new ButtonMissile((int) (Util.getWidth()/2.5), Util.getHeight()+98, 48, 96, (int)(Util.getWidth() / 2.01), (int) (Util.getHeight()*.65), 400);
	}
	
	public void doRenderLoop(GL10 gl) {
		background.gameRenderLoop(gl);
		buttonMissile.gameRenderLoop(gl);
	}

	public void doLogicLoop(double time) {
		SoundLoader.startMusic(SoundLoader.menuMusic);
		background.gameLoopLogic(time);
		buttonMissile.gameLoopLogic(time);
	}

	@Override
	public void doTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		int halfRadius = buttonMissile.getCurrentExplosionRadius() / 2;
		
		if (x > buttonMissile.getXCoord() - halfRadius
				&& x < buttonMissile.getXCoord() + halfRadius) {
			if (y > buttonMissile.getYCoord() - halfRadius
					&& y < buttonMissile.getYCoord() + halfRadius) {
				buttonMissile.setClicked();
			}
		}
	}

}
