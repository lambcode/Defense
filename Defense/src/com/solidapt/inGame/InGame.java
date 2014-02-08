package com.solidapt.inGame;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.Building;
import com.solidapt.citydefense.objects.ChandelierMissile;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.HorizonMissile;
import com.solidapt.citydefense.objects.HostileMissile;
import com.solidapt.citydefense.objects.ObjectList;
import com.solidapt.citydefense.objects.Projectile;
import com.solidapt.citydefense.objects.RadioActiveMissile;
import com.solidapt.citydefense.objects.StandardMissile;
import com.solidapt.citydefense.objects.Structure;
import com.solidapt.citydefense.objects.TurretBase;
import com.solidapt.defense.CollisionDetector;
import com.solidapt.defense.ExplosionTracker;
import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;
import com.solidapt.defense.ScoreTracker;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.GameOverOverlayLoader;
import com.solidapt.defense.overlayMenu.PauseOverlayLoader;

public class InGame implements LogicInterface {

	private static final int BUILDING_COUNT = 6;


	private Collection<GameObject> hostileMissiles = new ConcurrentLinkedQueue<GameObject>();
	private Collection<GameObject> missiles = new ConcurrentLinkedQueue<GameObject>();
	private Collection<GameObject> buildings = new ConcurrentLinkedQueue<GameObject>();
	private Collection<GameObject> cursors = new ConcurrentLinkedQueue<GameObject>();
	private TurretBase turret;
	private GameObject overlayMenuButton;
	
	private Logic overlay;
	private SideBar sideBar;
	
	private double timeElapsed = 0;
	
	public InGame() {
		int bPlus2 = BUILDING_COUNT + 2;
		float eighthOfWidth = Util.getWidth() / (float)(bPlus2);
		for (int i = 1; i < bPlus2; i++) {
			if (i == bPlus2 / 2) {
				//place a turret in the center
				turret = new TurretBase(Util.getWidth()/2 + 45, Util.getHeight() - 20, 120, 82, 0, 0);
			}
			else {
				//place buildings around the turret
				buildings.add(new Building((int) (eighthOfWidth * i) + 45, Util.getHeight() - 60, 30, 120, 0, 0));
			}
		}
		
		overlayMenuButton = new OverlayMenuButton(Util.getWidth() - 30, 30, 40, 40);
		sideBar = new SideBar();
		ExplosionTracker.reset();
		ScoreTracker.reset();
	}

	@Override
	public void doLogicLoop(double time) {
		SoundLoader.startMusic(SoundLoader.gameMusic);
		timeElapsed += time;
		int maxMissiles = (int) Math.ceil(timeElapsed / 15);
		
		if (timeElapsed > 5) {
			if (hostileMissiles.size() < maxMissiles && Math.random() < .1) {
				
				int bPlus2 = BUILDING_COUNT + 2;
				float eighthOfWidth = (Util.getWidth()) / (float)(bPlus2);
				int targetIndex = (int) Math.ceil(Math.random() * (BUILDING_COUNT + 1));
				hostileMissiles.add(new HostileMissile((int)((Util.getWidth() - 90)* Math.random()) + 90, -50, 15, 30, (int) (eighthOfWidth * targetIndex) + 45, Util.getHeight(), 100));
			}
		}
		updateGameObjects(time);
	}

	private void updateGameObjects(double time) {
		//Remove objects marked for removal
		removeObjects(hostileMissiles);
		removeObjects(missiles);
		//removeObjects(buildings);
		removeObjects(cursors);
		
		//Run update code in each object
		for (GameObject i : hostileMissiles)
			if (i != null)i.gameLoopLogic(time);
		for (GameObject i : missiles)
			if (i != null)i.gameLoopLogic(time);
		for (GameObject i : buildings)
			if (i != null)i.gameLoopLogic(time);
		for (GameObject i : cursors)
			if (i != null)i.gameLoopLogic(time);
		if (turret != null) turret.gameLoopLogic(time);
		if (overlayMenuButton != null) overlayMenuButton.gameLoopLogic(time);
		
		//Check for building-missile collisions
		checkBuildingCollisions(hostileMissiles, buildings);
		checkBuildingCollisions(hostileMissiles, turret);
		ScoreTracker.gameLoopLogic(time);
		sideBar.gameLoopLogic(time);
		checkGameOver();
	}
	
	private void checkGameOver() {
		boolean buildingAlive = false;
		for (GameObject i : buildings) {
			if (!i.needsRemoval()) {
				buildingAlive = true;
				break;
			}
		}
		
		if (!buildingAlive || turret.needsRemoval()) {
			synchronized (this) {
				overlay = new GameOverOverlayLoader();
				Util.saveMissileInformation(); 
			}
		}
	}

	private void checkBuildingCollisions(Collection<GameObject> projectileList,	Collection<GameObject> buildingList) {
		for (GameObject i : buildingList) {
			checkBuildingCollisions(projectileList, i);
		}
	}
	
	private void checkBuildingCollisions(Collection<GameObject> projectileList, GameObject building) {
		if (!((Structure)building).isExploding()) {
			for (GameObject i : projectileList) {
				if (CollisionDetector.collisionDetected(i, i.getXCoord(), i.getYCoord(), building, building.getXCoord(), building.getYCoord())) {
					((Projectile)i).createExplosionAndTrack();
				}
			}
		}
	}

	private static void removeObjects(Collection<GameObject> list) {
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
		for (GameObject i : buildings)
			if (i != null)i.gameRenderLoop(gl);
		for (GameObject i : cursors)
			if (i != null)i.gameRenderLoop(gl);
		for (GameObject i : hostileMissiles)
			if (i != null) i.gameRenderLoop(gl);
		for (GameObject i : missiles)
			if (i != null)i.gameRenderLoop(gl);
		if (turret != null) turret.gameRenderLoop(gl);
		ScoreTracker.gameRenderLoop();
		sideBar.gameRenderLoop(gl);
		if (overlayMenuButton != null) overlayMenuButton.gameRenderLoop(gl);
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		
		if (sideBar.doTouchEvent(e, x, y) == false) { //If we did not touch the side bar
			if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){

				if (x > Util.getWidth()-55 && y < 55) {
					synchronized (this) {
						overlay = new PauseOverlayLoader(this); 
					}
				}
				else {
					double radians = Math.atan2(Util.getHeight() - y, ((Util.getWidth()/2) + 45) - x);
					synchronized (turret) {
						if (turret != null && ableToFire()) turret.setRotation((float) Math.toDegrees(radians)-90);
					}

					Projectile newMissile = getNewOfSelected(x, y, radians);
					if (newMissile != null) {
						missiles.add(newMissile);
						cursors.add(new TargetCross((int)x, (int)y, 50, 50, newMissile));
					}
				}
			}
		}
	}
	
	private Projectile getNewOfSelected(float x, float y, double radians) {
		if (ableToFire()) {
			if (sideBar.getSelected() == 0 && Util.missileInformation[0].getCount() > 0) {
				Util.missileInformation[0].decreaseCount();
				turret.addHeatValue(Util.missileInformation[0]);
				return new StandardMissile(Util.getWidth()/2 + 45, Util.getHeight(), 15, 30, (int)(x + Math.cos(radians)*80), (int)(y + Math.sin(radians)*80), 250);
			}
			else if (sideBar.getSelected() == 1 && Util.missileInformation[1].getCount() > 0) {
				Util.missileInformation[1].decreaseCount();
				turret.addHeatValue(Util.missileInformation[1]);
				return new RadioActiveMissile(Util.getWidth()/2 + 45, Util.getHeight(), 15, 30, (int)(x + Math.cos(radians)*80), (int)(y + Math.sin(radians)*80), 250);
			}
			else if (sideBar.getSelected() == 2 && Util.missileInformation[2].getCount() > 0) {
				Util.missileInformation[2].decreaseCount();
				turret.addHeatValue(Util.missileInformation[2]);
				return new HorizonMissile(Util.getWidth()/2 + 45, Util.getHeight(), 15, 30, (int)(x + Math.cos(radians)*80), (int)(y + Math.sin(radians)*80), 250);
			}
			else if (sideBar.getSelected() == 3 && Util.missileInformation[3].getCount() > 0) {
				Util.missileInformation[3].decreaseCount();
				turret.addHeatValue(Util.missileInformation[3]);
				return new ChandelierMissile(Util.getWidth()/2 + 45, Util.getHeight(), 15, 30, (int)(x + Math.cos(radians)*80), (int)(y + Math.sin(radians)*80), 250);
			}
		}
		return null;
	}

	private boolean ableToFire() {
		return turret.ableToFire();
	}

	@Override
	public Logic getOverlay() {
		synchronized (this) {
			return overlay;
		}
	}

	@Override
	public void removeOverlay() {
		synchronized (this) {
			overlay = null;
		}
	}

}
