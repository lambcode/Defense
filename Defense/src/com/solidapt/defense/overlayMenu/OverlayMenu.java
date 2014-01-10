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
	
	GameObject menuBox;
	volatile OverlayButton button1;
	volatile OverlayButton button2;
	volatile OverlayButton button3;
	
	OverlayLoader myLoader;
	
	int halfWidth = Util.getWidth() / 2;
	int halfHeight = Util.getHeight() / 2;
	int buttonHeight = 53;
	int buttonWidth = 210;
	int menuWidth = 240;
	int menuHeight = 180 + buttonHeight + 20;
	int menuLocX = halfWidth;
	int menuLocY = halfHeight - (buttonHeight/2);
	
	public OverlayMenu(OverlayLoader myLoader) {
		dimmer = new ColorSquare(Util.getWidth()/2, Util.getHeight() / 2, Util.getWidth()+10, Util.getHeight()+10, 0f, 0f, 0f, .6f);
		
		//Menu
		menuBox = new ColorSquare(menuLocX, menuLocY, menuWidth, menuHeight, .2f, .2f, .2f, .8f);
		button1 = new OverlayButton("Main Menu", halfWidth, halfHeight - buttonHeight - 5, buttonWidth, buttonHeight, .5f, .5f, .5f, 1);
		button2 = new OverlayButton("Resume", halfWidth, halfHeight, buttonWidth, buttonHeight, .5f, .5f, .5f, 1f);
		button3 = new OverlayButton("Dumb Button", halfWidth, halfHeight + buttonHeight + 5, buttonWidth, buttonHeight, .5f, .5f, .5f, 1);
		this.myLoader = myLoader;
	}

	@Override
	public void doLogicLoop(double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doRenderLoop(GL10 gl) {
		dimmer.gameRenderLoop(gl);
		menuBox.gameRenderLoop(gl);
		Util.textRenderer.begin(.9f, .9f, .9f, 1);
		Util.textRenderer.drawC("Paused", halfWidth, halfHeight - buttonHeight - buttonHeight - 10);
		Util.textRenderer.end();
		button1.gameRenderLoop(gl);
		button2.gameRenderLoop(gl);
		button3.gameRenderLoop(gl);
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		button1.changeColor(.5f, .5f, .5f, 1);
		button2.changeColor(.5f, .5f, .5f, 1);
		
		if (insideButton1(x, y)) {
			button1.changeColor(.9f, .4f, 0, 1);
		}
		if (insideButton2(x, y)) {
			button2.changeColor(.9f, .4f, 0, 1);
		}
		if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP){
			if (insideButton1(x, y)){
				GameState.setTopMenu();
			}
			else if (insideButton2(x, y)) {
				myLoader.kill();
			}
		}
	}
	
	private boolean insideButton1(float x, float y) {
		if (x >= halfWidth - (buttonWidth / 2) && x <= halfWidth + (buttonWidth / 2)) {
			if (y >= (halfHeight - buttonHeight - 5) - (buttonHeight / 2) && y <= (halfHeight - buttonHeight - 5) + (buttonHeight / 2)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean insideButton2(float x, float y) {
		if (x >= halfWidth - (buttonWidth / 2) && x <= halfWidth + (buttonWidth / 2)) {
			if (y >= (halfHeight) - (buttonHeight / 2) && y <= (halfHeight) + (buttonHeight / 2)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Logic getOverlay() {
		return null;
	}

	@Override
	public void removeOverlay() {
	}

}
