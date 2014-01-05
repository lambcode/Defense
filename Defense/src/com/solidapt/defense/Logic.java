package com.solidapt.defense;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.mainMenu.TopMenu;

public abstract class Logic {

	private LogicInterface myLogic;

	public void logicLoop(double time) {
		if (myLogic == null)
			myLogic = this.load();
		
		myLogic.doLogicLoop(time);
	}

	public void renderLoop(GL10 gl) {
		if (myLogic == null)
			myLogic = this.load();
		
		myLogic.doRenderLoop(gl);
	}
	public void touchEvent(MotionEvent e, float x, float y) {
		if (myLogic == null)
			myLogic = this.load();
		
		myLogic.doTouchEvent(e, x, y);
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