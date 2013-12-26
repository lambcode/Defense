package com.solidapt.citydefense.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicReference;

public class ObjectList {
	public static List<GameObject> hostileMissiles = Collections.synchronizedList(new ArrayList<GameObject>());
	public static List<GameObject> missiles = Collections.synchronizedList(new ArrayList<GameObject>());
	public static List<GameObject> buildings = Collections.synchronizedList(new ArrayList<GameObject>());
	public static List<GameObject> explosions = Collections.synchronizedList(new ArrayList<GameObject>());
	public static List<GameObject> cursors = Collections.synchronizedList(new ArrayList<GameObject>());
	
	public static void initializeArrarys() {
	}
	
	public static void addToList(List<GameObject> toAddTo, GameObject objectToAdd) {
		ListIterator<GameObject> iterator = toAddTo.listIterator();
		boolean hasSet = false;
		while( iterator.hasNext()) {
			if (iterator.next() == null) {
				iterator.set(objectToAdd);
				hasSet = true;
				break;
			}	
		}
		if (!hasSet)
			toAddTo.add(objectToAdd);
	}
	
	public static int getItemCount(List<GameObject> list) {
		ListIterator<GameObject> iterator = list.listIterator();
		int count = 0;
		while (iterator.hasNext()) {
			if (iterator.next() != null) {
				count++;
			}
		}
		return count;
	}
}
