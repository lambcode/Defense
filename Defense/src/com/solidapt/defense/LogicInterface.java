package com.solidapt.defense;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

public interface LogicInterface {
	public void doLogicLoop(double time);
	public void doRenderLoop(GL10 gl);

	/**
	 * Process a touch event
	 * @param e
	 * @param x scaled x position
	 * @param y scaled y position
	 */
	public void doTouchEvent(MotionEvent e, float x, float y);
}
