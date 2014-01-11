package com.solidapt.defense;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.mainMenu.TopMenu;

public abstract class Logic {

	private LogicInterface myLogic;

	public synchronized void logicLoop(double time) {
		if (myLogic == null)
			myLogic = this.load();
		
		//Run logic loop of overlay if one exists
		//If overlay does not exist run logic loop of underlying instance
		Logic myLogicOverlay = myLogic.getOverlay();
		if (myLogicOverlay != null) {
			myLogicOverlay.logicLoop(time);
		}
		else {
			myLogic.doLogicLoop(time);
		}
	}

	public synchronized void renderLoop(GL10 gl) {
		if (myLogic == null)
			myLogic = this.load();
		
		myLogic.doRenderLoop(gl);
		
		//Render Overlay if one exists
		Logic myLogicOverlay = myLogic.getOverlay();
		if (myLogicOverlay != null) {
			myLogicOverlay.renderLoop(gl);
		}
	}
	public synchronized void touchEvent(MotionEvent e, float x, float y) {
		if (myLogic == null)
			myLogic = this.load();
		
		//Run touch event loop of overlay if one exists
		//If overlay does not exist run touch event of underlying instance
		Logic myLogicOverlay = myLogic.getOverlay();
		if (myLogicOverlay != null) {
			myLogicOverlay.touchEvent(e, x, y);
		}
		else {
			myLogic.doTouchEvent(e, x, y);
		}
	}

	/**
	 * Override this class and return an instance of
	 * the concrete class that implements LogicInterface
	 */
	public abstract LogicInterface load();

	public void release() {
		myLogic = null;
	}
	
}