package com.solidapt.inGame;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.Building;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.HostileMissile;
import com.solidapt.citydefense.objects.ObjectList;
import com.solidapt.citydefense.objects.StandardMissile;
import com.solidapt.citydefense.objects.TurretBase;
import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.OverlayLoader;
import com.solidapt.defense.overlayMenu.OverlayMenu;

public class InGame implements LogicInterface {

	private static final int BUILDING_COUNT = 6;
	
	private List<GameObject> hostileMissiles = new LinkedList<GameObject>();
	private List<GameObject> missiles = new LinkedList<GameObject>();
	private List<GameObject> buildings = new LinkedList<GameObject>();
	private List<GameObject> explosions = new LinkedList<GameObject>();
	private List<GameObject> cursors = new LinkedList<GameObject>();
	private GameObject turret;
	private GameObject overlayMenuButton;
	
	private Logic overlay;
	
	private double timeElapsed = 0;
	
	public InGame() {
		int bPlus2 = BUILDING_COUNT + 2;
		float eighthOfWidth = Util.getWidth() / (float)(bPlus2);
		for (int i = 1; i < bPlus2; i++) {
			if (i == bPlus2 / 2) {
				//place a turret in the center
				turret = new TurretBase(Util.getWidth()/2, Util.getHeight() - 20, 120, 120, 0, 0);
			}
			else {
				//place buildings around the turret
				buildings.add(new Building((int) (eighthOfWidth * i), Util.getHeight() - 60, 30, 120, 0, 0));
			}
		}
		
		overlayMenuButton = new OverlayMenuButton(Util.getWidth() - 20, 20, 20, 20);
		ExplosionTracker.reset();
	}

	@Override
	public void doLogicLoop(double time) {
		SoundLoader.startMusic(SoundLoader.gameMusic);
		timeElapsed += time;
		int maxMissiles = (int) Math.ceil(timeElapsed / 15);
		
		if (timeElapsed > 5) {
			if (hostileMissiles.size() < maxMissiles && Math.random() < .1) {
				
				int bPlus2 = BUILDING_COUNT + 2;
				float eighthOfWidth = Util.getWidth() / (float)(bPlus2);
				int targetIndex = (int) Math.ceil(Math.random() * (BUILDING_COUNT + 1));
				hostileMissiles.add(new HostileMissile((int)(Util.getWidth()* Math.random()), -50, 15, 30, (int) (eighthOfWidth * targetIndex), Util.getHeight(), 100));
			}
		}
		
		updateGameObjects(time);
	}

	private void updateGameObjects(double time) {
		//Remove objects marked for removal
		removeObjects(hostileMissiles);
		removeObjects(missiles);
		removeObjects(buildings);
		
		//Run update code in each object
		for (int x = 0; x < hostileMissiles.size(); x++)
			if (hostileMissiles.get(x) != null)hostileMissiles.get(x).gameLoopLogic(time);
		for (int x = 0; x < missiles.size(); x++)
			if (missiles.get(x) != null)missiles.get(x).gameLoopLogic(time);
		for (int x = 0; x < buildings.size(); x++)
			if (buildings.get(x) != null)buildings.get(x).gameLoopLogic(time);
		if (turret != null) turret.gameLoopLogic(time);
		if (overlayMenuButton != null) overlayMenuButton.gameLoopLogic(time);
	}
	
	private static void removeObjects(List<GameObject> list) {
		Iterator<GameObject> i = list.iterator();
		while (i.hasNext())	{
			GameObject ob = i.next();
			//Set object to null if it is not already and needs removal
			if (ob != null && ob.needsRemoval()) {
				i.remove();
			}
		}
	}

	@Override
	public void doRenderLoop(GL10 gl) {
		for (int x = 0; x < buildings.size(); x++)
			if (buildings.get(x) != null)buildings.get(x).gameRenderLoop(gl);
		for (int x = 0; x < hostileMissiles.size(); x++)
			if (hostileMissiles.get(x) != null) hostileMissiles.get(x).gameRenderLoop(gl);
		for (int x = 0; x < missiles.size(); x++)
			if (missiles.get(x) != null)missiles.get(x).gameRenderLoop(gl);
		if (turret != null) turret.gameRenderLoop(gl);
		if (overlayMenuButton != null) overlayMenuButton.gameRenderLoop(gl);
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		
		if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){

			if (x > Util.getWidth()-30 && y < 30) {
				overlay = new OverlayLoader(this); 
			}
			else {
				double radians = Math.atan2(Util.getHeight() - y, Util.getWidth()/2 - x);
				if (turret != null) turret.setRotation((float) Math.toDegrees(radians)-90);

				StandardMissile newMissile = new StandardMissile(Util.getWidth()/2, Util.getHeight(), 15, 30, (int)(x + Math.cos(radians)*80), (int)(y + Math.sin(radians)*80), 250);
				missiles.add(newMissile);
			}
		}
	}

	@Override
	public Logic getOverlay() {
		return overlay;
	}

	@Override
	public void removeOverlay() {
		overlay = null;
	}

}
