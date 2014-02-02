package com.solidapt.defense.store;

import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.Scroller;
import com.solidapt.defense.Util;

public class StoreScroller extends Scroller {
	
	LinkedList<Product> products = new LinkedList<Product>();
	
	public StoreScroller() {
		for (MissileInformation i : Util.missileInformation) {
			products.add(new Product(75, Util.getHeight() - 75 - getVerticalSpace(), i));
			this.addVerticalSpace(150);
		}
		for (MissileInformation i : Util.missileInformation) {
			products.add(new Product(75, Util.getHeight() - 75 - getVerticalSpace(), i));
			this.addVerticalSpace(150);
		}
		
		this.configureScroll(Util.getHeight() - 45, false);
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
		return y >= 20 && x <= Util.getWidth() / 2;
	}

}
