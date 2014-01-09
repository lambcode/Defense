package com.solidapt.mainMenu;

import java.util.Iterator;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.HostileMissile;
import com.solidapt.citydefense.objects.StandardMissile;
import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.Util;

public class TopMenu implements LogicInterface{
	
	GameObject background;
	ButtonMissile buttonMissile;
	LinkedList<GameObject> missile = new LinkedList<GameObject>();
	
	private boolean firstLogicLoop = true;
	
	public TopMenu() {
		background = new MainMenuBackground();
		buttonMissile = new ButtonMissile((int) (Util.getWidth()/2.5), Util.getHeight()+98, 48, 96, (int)(Util.getWidth() / 2.01), (int) (Util.getHeight()*.65), 400);
		ExplosionTracker.reset();
	}
	
	public void doRenderLoop(GL10 gl) {
		//Render Background
		background.gameRenderLoop(gl);
		
		//Render random missiles
		for (GameObject i : missile) {
			i.gameRenderLoop(gl);
		}
		
		//Render buttons
		buttonMissile.gameRenderLoop(gl);
	}

	public void doLogicLoop(double time) {
		if (firstLogicLoop) {
			firstLogicLoop = false;
			time = 0;
		}
		SoundLoader.startMusic(SoundLoader.menuMusic);
		background.gameLoopLogic(time);
		buttonMissile.gameLoopLogic(time);
		
		if (buttonMissile.isAnimationDone()) { 
			GameState.setInGame();
		}
		
		updateBackgroundMissiles(time);
	}

	private void updateBackgroundMissiles(double time) {
		Iterator<GameObject> i = missile.iterator();
		while (i.hasNext()) {
			GameObject ob = i.next();
			if (ob.needsRemoval()) {
				i.remove();
			}
			else {
				ob.gameLoopLogic(time);
			}
		}
		
		if (Math.random() < .02) {
			missile.add(new FalseMissile(Util.getWidth() / 2, Util.getHeight() + (Util.getHeight()/5), 48, 96, (int) (Util.getWidth() * Math.random()), -Util.getHeight()/5, 400));
		}
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		int halfRadius = buttonMissile.getCurrentExplosionRadius() / 2;
		
		if (x > buttonMissile.getXCoord() - halfRadius
				&& x < buttonMissile.getXCoord() + halfRadius) {
			if (y > buttonMissile.getYCoord() - halfRadius
					&& y < buttonMissile.getYCoord() + halfRadius) {
				buttonMissile.setClicked();
			}
		}
	}

	@Override
	public Logic getOverlay() {
		return null;
	}

	@Override
	public void removeOverlay() {
	}

}
