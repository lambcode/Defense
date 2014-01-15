package com.solidapt.defense.overlayMenu;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.defense.GameState;
import com.solidapt.defense.Logic;
import com.solidapt.defense.LogicInterface;
import com.solidapt.defense.Util;

public abstract class OverlayMenu implements LogicInterface {
	
	GameObject dimmer;
	
	Collection<OverlayButton> buttons = new ConcurrentLinkedQueue<OverlayButton>();
	private volatile OverlayButtonListener buttonListener;

	private static final int TITLE_HEIGHT = 60;
	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_WIDTH = 300;
	private static final int PAD = 10;
	
	private int totalHeight = 0;

	private ColorSquare menuBox;
	private String menuTitle;
	private Logic myLoader;
	
	
	public OverlayMenu(Logic myLoader, String title, String... buttonText ) {
		int largerSize = Util.getWidth() > Util.getHeight() ? Util.getWidth() : Util.getHeight();
		largerSize += 10; // add extra padding to size of dimmer box
		dimmer = new ColorSquare(0, 0, largerSize, largerSize, 0f, 0f, 0f, .6f);
		this.myLoader = myLoader;
		this.menuTitle = title;
		
		totalHeight += PAD;
		totalHeight += PAD;
		
		totalHeight += TITLE_HEIGHT;
		
		for (String i : buttonText) {
			totalHeight += PAD;
			totalHeight += BUTTON_HEIGHT;
			buttons.add(new OverlayButton(i, 0, totalHeight - (BUTTON_HEIGHT / 2), BUTTON_WIDTH, BUTTON_HEIGHT, .5f, .5f, .5f, 1));
		}
		
		totalHeight += PAD;
		totalHeight += PAD;
		
		menuBox = new ColorSquare(0, totalHeight / 2, BUTTON_WIDTH + (PAD * 4), totalHeight, .2f, .2f, .2f, .8f);
	}
	
	public void setListener(OverlayButtonListener listener) {
		this.buttonListener = listener;
	}

	@Override
	public void doRenderLoop(GL10 gl) {
		gl.glTranslatef(Util.getWidth() / 2,  (Util.getHeight() / 2) - (totalHeight / 2), 0);
		
		dimmer.gameRenderLoop(gl);
		menuBox.gameRenderLoop(gl);
		Util.textRenderer.begin(.9f, .9f, .9f, 1);
		Util.textRenderer.drawC(menuTitle, 0, (PAD * 2) - (Util.textRenderer.getHeight() / 2));
		Util.textRenderer.end();
		for (OverlayButton i : buttons) {
			i.gameRenderLoop(gl);
		}
		
		gl.glTranslatef(-Util.getWidth() / 2, -((Util.getHeight() / 2) - (totalHeight / 2)), 0);
	}
	
	@Override
	public void doTouchEvent(MotionEvent e, float x, float y) {
		float newX = x - (Util.getWidth() / 2);
		float newY = y - (Util.getHeight() / 2) + (totalHeight / 2);
		
		int count = 0;
		float halfWidth = BUTTON_WIDTH / 2;
		float halfHeight = BUTTON_HEIGHT / 2;
		
		for (OverlayButton i : buttons) {
			i.changeColor(.5f, .5f, .5f, 1);
			if (newX > i.getXCoord() - halfWidth && newX < i.getXCoord() + halfWidth) {
				if (newY > i.getYCoord() - halfHeight && newY < i.getYCoord() + halfHeight) {
					i.changeColor(.9f, .4f, 0, 1);
					if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
						if (buttonListener != null) buttonListener.buttonPressed(count);
					}
				}
			}
			count++;
		}
	}
	
	@Override
	public void doLogicLoop(double time) {
		
	}

	@Override
	public Logic getOverlay() {
		return null;
	}

	@Override
	public void removeOverlay() {
	}
	
	public void kill() {
		myLoader.kill();
	}

}
