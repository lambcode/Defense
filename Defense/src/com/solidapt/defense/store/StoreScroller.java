package com.solidapt.defense.store;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.Scroller;
import com.solidapt.defense.Util;

public class StoreScroller extends Scroller {
	
	ArrayList<Product> products = new ArrayList<Product>();
	
	public StoreScroller() {
		for (int i = Util.missileInformation.length - 1; i >= 0; i--) {
			products.add(new Product(75, Util.getHeight() - 75 - getVerticalSpace(), Util.missileInformation[i]));
			this.addVerticalSpace(150);
			Util.saveMissileInformation();
		}
		
		this.configureScroll(Util.getHeight() - 150, true, false);
	}

	@Override
	public void gameLoopLogic2(double time) {
		for (Product i : products) {
			i.gameLoopLogic(time);
		}
	}

	@Override
	public void gameRenderLoopInsideScroll(GL10 gl) {
		for (Product i : products) {
			i.gameRenderLoop(gl);
		}
	}

	@Override
	public void gameRenderLoop2(GL10 gl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void touchEvent2(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOnScrollArea(float x, float y) {
		return y >= 150;
	}
	
	private int currentItem = -1;
	
	@Override
	public boolean doTouchEvent(MotionEvent e, float x, float y) {
		boolean touchCaptured = false;
		
		if (currentItem == -1) {
			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).doTouchEvent(e, x,  y - this.getScroll())) {
					touchCaptured = true;
					currentItem = i;
					break;
				}
			}
		}
		else if (currentItem >= 0){
			if (products.get(currentItem).doTouchEvent(e, x,  y - this.getScroll())) {
				touchCaptured = true;
			}
			else {
				currentItem = -1;
			}
		}
		
		if (!touchCaptured) {
			boolean scrollTouchValue = super.doTouchEvent(e, x, y);
			if (scrollTouchValue) {
				currentItem = -2;
			}
			else {
				currentItem = -1;
			}
			return scrollTouchValue;
		}
		return false;
		
	}

}
