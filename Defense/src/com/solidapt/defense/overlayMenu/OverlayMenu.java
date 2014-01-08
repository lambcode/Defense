package com.solidapt.defense.overlayMenu;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;
import com.solidapt.defense.Util;

public class OverlayMenu implements LogicInterface {
	
	GameObject dimmer;
	OverlayLoader myLoader;
	
	public OverlayMenu(OverlayLoader myLoader) {
		dimmer = new ColorSquare(Util.getWidth()/2, Util.getHeight() / 2, Util.getWidth(), Util.getHeight(), 0f, 0f, 0f, .6f);
		this.myLoader = myLoader;
	}

	@Override
	public void doLogicLoop(double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doRenderLoop(GL10 gl) {
		dimmer.gameRenderLoop(gl);

	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
			if (x < Util.getWidth() /2) {
				myLoader.kill();
			}
			else {
				GameState.setTopMenu();
			}
		}
	}

	@Override
	public Logic getOverlay() {
		return null;
	}

	@Override
	public void removeOverlay() {
	}

}
