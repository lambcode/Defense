package com.solidapt.mainMenu;

import java.util.Iterator;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
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
	
	volatile GameObject background;
	volatile ButtonMissile buttonMissile;
	volatile ButtonMissile buttonMissileCredits;
	volatile ButtonMissile buttonMissileStore;
	volatile LinkedList<GameObject> missile = new LinkedList<GameObject>();
	
	private boolean firstLogicLoop = true;
	
	public TopMenu() {
		background = new MainMenuBackground();
		buttonMissile = new ButtonMissile((int) (Util.getWidth()/2.5), Util.getHeight()+98, 48, 96, (int)(Util.getWidth() / 2.01), (int) (Util.getHeight()*.65), 400, "Play");
		buttonMissileCredits = new ButtonMissile((int) (Util.getWidth()/2.5), Util.getHeight()+98, 48, 96, (int)(Util.getWidth() / 3.01), (int) (Util.getHeight()*.85), 400, "Credits");
		buttonMissileStore = new ButtonMissile((int) (Util.getWidth()/2.5), Util.getHeight()+98, 48, 96, (int)(Util.getWidth() / 1.6), (int) (Util.getHeight()*.85), 400, "Store");
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
		buttonMissileCredits.gameRenderLoop(gl);
		buttonMissileStore.gameRenderLoop(gl);
	}

	public void doLogicLoop(double time) {
		if (firstLogicLoop) {
			firstLogicLoop = false;
			time = 0;
		}
		else {
			buttonMissile.gameLoopLogic(time);
			buttonMissileCredits.gameLoopLogic(time);
			buttonMissileStore.gameLoopLogic(time);
		}
		SoundLoader.startMusic(SoundLoader.menuMusic);
		background.gameLoopLogic(time);
		
		if (buttonMissile.isAnimationDone()) { 
			GameState.setInGame();
		}
		else if (buttonMissileStore.isAnimationDone()) { 
			GameState.setInStore();
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
		
		if (Math.random() < .01) {
			missile.add(new FalseMissile(Util.getWidth() / 2, Util.getHeight() + (Util.getHeight()/5), 48, 96, (int) (Util.getWidth() * Math.random()), -Util.getHeight()/5, 400));
		}
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		
		//Test button clicks
		buttonMissile.touchEvent(x, y);
		buttonMissileCredits.touchEvent(x, y);
		buttonMissileStore.touchEvent(x, y);
	}

	@Override
	public Logic getOverlay() {
		return null;
	}

	@Override
	public void removeOverlay() {
	}

}
