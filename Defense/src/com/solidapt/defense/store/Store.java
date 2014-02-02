package com.solidapt.defense.store;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;

public class Store implements LogicInterface {
	
	StoreScroller scroller = new StoreScroller();

	@Override
	public void doLogicLoop(double time) {
		scroller.gameLoopLogic(time);
	}

	@Override
	public void doRenderLoop(GL10 gl) {

		scroller.gameRenderLoop(gl);
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		scroller.doTouchEvent(e, x, y);
	}

	@Override
	public Logic getOverlay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeOverlay() {
		// TODO Auto-generated method stub

	}

}
