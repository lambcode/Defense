package com.solidapt.defense;

import inGame.InGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.solidapt.citydefense.objects.Building;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.HostileMissile;
import com.solidapt.citydefense.objects.Projectile;
import com.solidapt.citydefense.objects.ObjectList;
import com.solidapt.citydefense.objects.TurretBase;
import com.solidapt.mainMenu.TopMenu;

class LogicLoop {
    static long lastUpdate;
    ArrayList<GameObject> removeList = new ArrayList<GameObject>();
	static boolean hasDoneThis = false;
	
	public static void doLoop() {
		double time = (double)(System.currentTimeMillis() - lastUpdate )/1000;
		lastUpdate = System.currentTimeMillis();

		if (!gameManage(time)) {
			lastUpdate = System.currentTimeMillis();
			return;
		}
		
		if (Util.gameRunning) {
			if (GameState.isInGame()) {
				Util.inGame.logicLoop(time);
			}
			else if (GameState.isTopMenu()) {
				Util.topMenu.logicLoop(time);
			}
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
    	
    	if (GameState.isSplash()) {
			GameState.updateSplash(time);
		}
		return true;
	}
	

	
}