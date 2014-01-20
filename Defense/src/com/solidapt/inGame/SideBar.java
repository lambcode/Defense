package com.solidapt.inGame;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.StandardMissile;
import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.Texture;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;
import com.solidapt.defense.overlayMenu.PauseOverlayLoader;

public class SideBar {
	
	ColorSquare backPanel;
	ColorSquare panel;
	
	private int currentSelected = -1;
	private volatile int lastTouched = 0;
	
	
	private volatile float scroll;
	private float lastTouchY = 0;
	private volatile boolean isScrolling;
	
	private int lastAddedY = 0;
	private int topScroll = 0;
	

	private Collection<SideBarToggle> buttons = new ConcurrentLinkedQueue<SideBarToggle>();

	public SideBar() {
		backPanel =  new ColorSquare(40, Util.getHeight() / 2, 100, Util.getHeight() + 20, .6f, .6f, .6f, 1);
		panel = new ColorSquare(42, Util.getHeight() / 2, 90, Util.getHeight() + 20, .3f, .3f, .3f, 1);
		addButton(TextureLoader.MISSILE_TEXTURE, Util.missileInformation[0]);
		addButton(TextureLoader.RADIO_ACTIVE_MISSILE_TEXTURE, Util.missileInformation[1]);
		addButton(TextureLoader.HORIZON_MISSILE_TEXTURE, Util.missileInformation[2]);
		addButton(TextureLoader.HORIZON_MISSILE_TEXTURE, Util.missileInformation[3]);
		
		int tmpTopScroll = lastAddedY - Util.getHeight();
		topScroll = tmpTopScroll < 0 ? 0 : tmpTopScroll;
	}
	
	public void addButton(Texture texture, MissileInformation missileInformation) {
		buttons.add(new SideBarToggle(42, Util.getHeight() - 50 - lastAddedY, 80, 80, texture, missileInformation));
		lastAddedY += 90;
	}

	public void gameLoopLogic(double time) {
		int count = 0;
		for (SideBarToggle i : buttons) {
			i.gameLoopLogic(time);
			if (currentSelected != lastTouched) {
				if (lastTouched == count) {
					i.setToggled(true);
				}
				else {	
					i.setToggled(false);
				}
			}
			count++;
		}
		currentSelected = lastTouched;

		synchronized (this) {
			float localScroll = scroll;
			if (localScroll < 0 && !isScrolling) {
				scroll += .5 * (Math.abs(localScroll));
			}
			if (localScroll > topScroll && !isScrolling) {
				scroll -= .5 * (Math.abs(localScroll));
			}
		}
	}
	
	public void gameRenderLoop(GL10 gl) {
		backPanel.gameRenderLoop(gl);
		panel.gameRenderLoop(gl);
		
		float localScroll = scroll;

		gl.glTranslatef(0, localScroll, 0);
		for (SideBarToggle i : buttons) {
			i.gameRenderLoop(gl);
		}
		gl.glTranslatef(0, -localScroll, 0);
	}
	
	/**
	 * Perform action on side bar touch
	 * @param e
	 * @param x
	 * @param y
	 * @return false if the press was not on the side bar
	 */
	public boolean doTouchEvent(MotionEvent e, float x, float y) {

		if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
			lastTouchY = y;
		}
		if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
			if (isScrolling) {
				isScrolling = false;
				return true;
			}
		}
		if (isScrolling) {
			float change = y - lastTouchY;
			synchronized (this) {
				if (scroll < 0 && change < 0) {
					float absScroll = Math.abs(scroll);
					if (absScroll > 1) {
						change = change * (1 / (absScroll * .3f));
					}
				}
				if (scroll > topScroll && change > 0) {
					float absScroll = Math.abs(scroll);
					if (absScroll > 1) {
						change = change * (1 / (absScroll * .3f));
					}
				}
				scroll += change;
			}
			lastTouchY = y;
			
			return true;
		}
		if (x <= 90) {
			if ((e.getAction() & MotionEvent.ACTION_MASK) != MotionEvent.ACTION_DOWN) {
				if (y > lastTouchY + 5 || y < lastTouchY - 5) {
					isScrolling = true;
				}
			}
			if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
				int count = 0;
				for (SideBarToggle i : buttons) {
					if (i.getPressed(x, y + -scroll)) {
						this.lastTouched = count;
					}
					count++;
				}
			}
			return true;
		}
		return false;
	}
	
	public int getSelected() {
		return currentSelected;
	}

}
