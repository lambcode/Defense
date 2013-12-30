package com.solidapt.defense;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import MainMenu.TopMenu;
import android.util.Log;

import com.solidapt.citydefense.objects.Building;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.HostileMissile;
import com.solidapt.citydefense.objects.Projectile;
import com.solidapt.citydefense.objects.ObjectList;
import com.solidapt.citydefense.objects.TurretBase;

class LogicLoop {
    static long lastUpdate;
    ArrayList<GameObject> removeList = new ArrayList<GameObject>();
	static boolean hasDoneThis = false;
	
	public static void doLoop() {
		double time = (double)(System.currentTimeMillis() - lastUpdate )/1000;
		lastUpdate = System.currentTimeMillis();

		if (!gameManage(time))
			return;
		
		if (Util.gameRunning) {
			if (GameState.isInGame()) {
				for (int x = 0; x < ObjectList.hostileMissiles.size(); x++)
					if (ObjectList.hostileMissiles.get(x) != null)ObjectList.hostileMissiles.get(x).gameLoopLogic(time);
				for (int x = 0; x < ObjectList.missiles.size(); x++)
					if (ObjectList.missiles.get(x) != null)ObjectList.missiles.get(x).gameLoopLogic(time);
				for (int x = 0; x < ObjectList.buildings.size(); x++)
					if (ObjectList.buildings.get(x) != null)ObjectList.buildings.get(x).gameLoopLogic(time);
				if (Util.turret != null) Util.turret.gameLoopLogic(time);
			}
			else if (GameState.isTopMenu()) TopMenu.logicLoop(time);
		}
	}
	
	//Returns true if the textures have been loaded
	private static boolean gameManage(double time) {
		//Do memory loading
    	if (!TextureLoader.hasLoaded() && Util.logoHasRendered){
    		TextureLoader.loadTextures();
    		SoundLoader.loadSounds();

    		return false;
    	}
		if (GameState.isInGame() && Util.gameRunning)
		{
			if (GameState.isInGame()) gameLoop();
			
		}
		else if (GameState.isSplash()) {
			GameState.updateSplash(time);
		}
		return true;
	}

	private static void gameLoop() {
		SoundLoader.startMusic(SoundLoader.gameMusic);
		if (!hasDoneThis) {
			ObjectList.buildings.add(new Building(100, 650, 50, 200, 0, 0));
			ObjectList.buildings.add(new Building(500, 650, 50, 200, 0, 0));
			ObjectList.buildings.add(new Building(900, 650, 50, 200, 0, 0));
			Util.turret = new TurretBase(Util.getWidth()/2, Util.getHeight()-20, 120, 120, 0, 0);
			hasDoneThis = true;
		}
		removeObjects(ObjectList.hostileMissiles);
		removeObjects(ObjectList.missiles);
		removeObjects(ObjectList.buildings);
		
		if (ObjectList.getItemCount(ObjectList.hostileMissiles) < 5 && Math.random() < .2) {
			ObjectList.addToList(ObjectList.hostileMissiles, new HostileMissile((int)(Util.getWidth()* Math.random()), -50, 15, 30, (int)(Util.getWidth()* Math.random()), Util.getHeight(), 100));
		}
	}
	

	private static void removeObjects(List<GameObject> list) {
		for (int x = 0; x < list.size(); x++) {
			//Set object to null if it is not already and needs removal
			if (list.get(x) != null && list.get(x).needsRemoval()) 
				list.set(x, null);
			
		}
	}
}