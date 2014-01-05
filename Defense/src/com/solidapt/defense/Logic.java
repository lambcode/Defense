package com.solidapt.defense;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.mainMenu.TopMenu;

public abstract class Logic {

	static Logic myLogic;

	public static void logicLoop(double time) {
		if (myLogic == null)
			TopMenu.load();
		
		myLogic.doLogicLoop(time);
	}

	public static void renderLoop(GL10 gl) {
		if (myLogic == null)
			TopMenu.load();
		
		myLogic.doRenderLoop(gl);
	}
	public static void touchEvent(MotionEvent e, float x, float y) {
		if (myLogic == null)
			TopMenu.load();
		
		myLogic.doTouchEvent(e, x, y);
	}

	public static void load() {
		myLogic = new TopMenu();
	}

	public static void release() {
		myLogic = null;
	}
	
	public abstract void doLogicLoop(double time);
	public abstract void doRenderLoop(GL10 gl);
	
	/**
	 * Process a touch event
	 * @param e
	 * @param x scaled x position
	 * @param y scaled y position
	 */
	public abstract void doTouchEvent(MotionEvent e, float x, float y);
}