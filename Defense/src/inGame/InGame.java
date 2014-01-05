package inGame;

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
import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;
import com.solidapt.defense.SoundLoader;
import com.solidapt.defense.Util;

public class InGame implements LogicInterface {
	private List<GameObject> hostileMissiles = new LinkedList<GameObject>();
	private List<GameObject> missiles = new LinkedList<GameObject>();
	private List<GameObject> buildings = new LinkedList<GameObject>();
	private List<GameObject> explosions = new LinkedList<GameObject>();
	private List<GameObject> cursors = new LinkedList<GameObject>();
	private GameObject turret;
	
	public InGame() {
		buildings.add(new Building(100, 650, 50, 200, 0, 0));
		buildings.add(new Building(500, 650, 50, 200, 0, 0));
		buildings.add(new Building(900, 650, 50, 200, 0, 0));
		turret = new TurretBase(Util.getWidth()/2, Util.getHeight() - (Util.getHeight() / 38), 120, 120, 0, 0);
	}

	@Override
	public void doLogicLoop(double time) {
		SoundLoader.startMusic(SoundLoader.gameMusic);
		
		if (hostileMissiles.size() < 5 && Math.random() < .2) {
			hostileMissiles.add(new HostileMissile((int)(Util.getWidth()* Math.random()), -50, 15, 30, (int)(Util.getWidth()* Math.random()), Util.getHeight(), 100));
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
		if (Util.turret != null) Util.turret.gameLoopLogic(time);
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
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		double radians = Math.atan2(Util.getHeight() - y, Util.getWidth()/2 - x);
		if (turret != null) turret.setRotation((float) Math.toDegrees(radians)-90);
		
		if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
			StandardMissile newMissile = new StandardMissile(Util.getWidth()/2, Util.getHeight(), 30, 30, (int)(x + Math.cos(radians)*80), (int)(y + Math.sin(radians)*80), 250);
			missiles.add(newMissile);
		}
		
		if (x > Util.getWidth()-40 && y < 40) {
			GameState.setTopMenu();
		}
	}

}
