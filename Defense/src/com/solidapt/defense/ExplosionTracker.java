package com.solidapt.defense;

import java.util.ArrayList;

import android.util.Log;

import com.solidapt.citydefense.objects.Explosion;
import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.Projectile;

public class ExplosionTracker {
	private static ArrayList<GameObject> explosions = new ArrayList<GameObject>(); 
		
	public static void addExplosion(GameObject explosion) {
		explosions.add(explosion);
	}
	
	public static void removeExplosion(GameObject explosion) {
		explosions.remove(explosion);
	}
	
	public static boolean collisionDetected(GameObject collisionObject) {
		for (int x = 0; x < explosions.size(); x++) {
			if (CollisionDetector.collisionDetected(explosions.get(x), collisionObject))
				return true;
//			Projectile currentCompare = (Projectile)explosions.get(x);
//			int radius = currentCompare.getCurrentExplosionRadius();
//			if (collisionObject.getXCoord() <= currentCompare.getXCoord() + radius &&
//					collisionObject.getXCoord() >= currentCompare.getXCoord() - radius &&
//					collisionObject.getYCoord() <= currentCompare.getYCoord() + radius &&
//					collisionObject.getYCoord() >= currentCompare.getYCoord() - radius)
//				return true;
		}
		return false;
	}
}
