package com.solidapt.inGame;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.GameObject;
import com.solidapt.citydefense.objects.StandardMissile;
import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.Scroller;
import com.solidapt.defense.Texture;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;
import com.solidapt.defense.overlayMenu.PauseOverlayLoader;

public class SideBar extends Scroller {
	
	ColorSquare backPanel;
	ColorSquare panel;
	
	private int currentSelected = -1;
	private volatile int lastTouched = 0;

	private Collection<SideBarToggle> buttons = new ConcurrentLinkedQueue<SideBarToggle>();

	public SideBar() {
		backPanel =  new ColorSquare(40, Util.getHeight() / 2, 100, Util.getHeight() + 20, .6f, .6f, .6f, 1);
		panel = new ColorSquare(42, Util.getHeight() / 2, 90, Util.getHeight() + 20, .3f, .3f, .3f, 1);
		addButton(TextureLoader.MISSILE_TEXTURE, Util.missileInformation[0]);
		addButton(TextureLoader.MISSILE_TEXTURE, Util.missileInformation[1]);
		addButton(TextureLoader.RADIO_ACTIVE_MISSILE_TEXTURE, Util.missileInformation[2]);
		addButton(TextureLoader.HORIZON_MISSILE_TEXTURE, Util.missileInformation[3]);
		addButton(TextureLoader.CHANDELIER_TEXTURE, Util.missileInformation[4]);

		this.configureScroll(Util.getHeight(), true, false);
	}
	
	public void addButton(Texture texture, MissileInformation missileInformation) {
		buttons.add(new SideBarToggle(42, Util.getHeight() - 50 - getVerticalSpace(), 80, 80, texture, missileInformation));
		this.addVerticalSpace(90);
	}

	public void gameLoopLogic2(double time) {
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
	}
	
	public void gameRenderLoop2(GL10 gl) {
		backPanel.gameRenderLoop(gl);
		panel.gameRenderLoop(gl);
	}
	
	public void gameRenderLoopInsideScroll(GL10 gl) {
		for (SideBarToggle i : buttons) {
			i.gameRenderLoop(gl);
		}
	}
	
	/**
	 * Perform action on side bar touch
	 * @param e
	 * @param x
	 * @param y
	 * @return false if the press was not on the side bar
	 */
	public void touchEvent2(float x, float y) {
		int count = 0;
		for (SideBarToggle i : buttons) {
			if (i.getPressed(x, y + -getScroll())) {
				this.lastTouched = count;
			}
			count++;
		}
	}

	public int getSelected() {
		return currentSelected;
	}

	@Override
	public boolean isOnScrollArea(float x, float y) {
		return (x <= 90);
	}

}
