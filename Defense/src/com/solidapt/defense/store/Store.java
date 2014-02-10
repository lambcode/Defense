package com.solidapt.defense.store;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;
import com.solidapt.defense.ScoreTracker;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;

public class Store implements LogicInterface {
	
	StoreScroller scroller = new StoreScroller();
	ColorSquare headerBackground;
	Button okButton;
	
	public Store() {
		headerBackground = new ColorSquare(Util.getWidth()/2, 0, Util.getWidth() + 10, 300, 0f, 0f, 0f, 1f);
		okButton = new Button(Util.getWidth() - 75, 75, 128, 116, TextureLoader.OK_BUTTON_TEXTURE1, TextureLoader.OK_BUTTON_TEXTURE2);
	}

	@Override
	public void doLogicLoop(double time) {
		scroller.gameLoopLogic(time);
	}

	@Override
	public void doRenderLoop(GL10 gl) {
		
		scroller.gameRenderLoop(gl);
		headerBackground.gameRenderLoop(gl);
		okButton.gameRenderLoop(gl);
		Util.textRenderer.begin(1f, 1f, 1f, 1f);
		Util.textRenderer.drawCY("My Points: " + ScoreTracker.getTotalScore(), 10, 75);
		Util.textRenderer.end();
	}

	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		if (!scroller.doTouchEvent(e, x, y)) {
			if (okButton.getPressed(x, y)) {
				if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
					GameState.setTopMenu();
				}
				else {
					okButton.setHover(true);
				}
			}
			else {
				okButton.setHover(false);
			}
		}
		
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
