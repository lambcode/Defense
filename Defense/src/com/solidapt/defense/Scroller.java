package com.solidapt.defense;

import java.util.Collection;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.defense.overlayMenu.ColorSquare;
import com.solidapt.inGame.SideBarToggle;

public abstract class Scroller {
	
	private volatile float scroll;
	private float lastTouchY = 0;
	private volatile boolean isScrolling;
	
	private int lastAddedY = 0;
	private int topScroll = 0;
	private boolean snapBottom;
	private boolean snapAtItems;
	private int itemsAdded = 0;

	public void configureScroll(int viewHeight, boolean snapBottom) {
		configureScroll(viewHeight, snapBottom, false);
	}
	
	public void configureScroll(int viewHeight, boolean snapBottom, boolean snapAtItems) {
		
		this.snapBottom = snapBottom;
		this.snapAtItems = snapAtItems;
		int tmpTopScroll = lastAddedY - viewHeight;
		topScroll = tmpTopScroll < 0 ? 0 : tmpTopScroll;
		if (!snapBottom) scroll = topScroll;
	}
	
	public void addVerticalSpace(int y) {
		lastAddedY += y;
		itemsAdded++;
	}
	
	public int getVerticalSpace() {
		return lastAddedY;
	}
	
	public int getItemsAdded() {
		return itemsAdded;
	}
	
	public abstract void gameLoopLogic2(double time);

	public void gameLoopLogic(double time) {
		gameLoopLogic2(time);
		synchronized (this) {
			float localScroll = scroll;
			if (localScroll < 0 && !isScrolling) {
				scroll += .5 * (Math.abs(localScroll));
			}
			else if (localScroll > topScroll && !isScrolling) {
				scroll -= .5 * (Math.abs(localScroll - topScroll));
			}
			else if (snapAtItems && !isScrolling) performSnapAtItems(localScroll);
		}
	}
	
	private void performSnapAtItems(float localScroll) {
		float spaceBetween = lastAddedY / itemsAdded;
		float distancePastLast = ((localScroll + (spaceBetween / 2)) % spaceBetween) ;
		float distanceToClosest = distancePastLast % (spaceBetween / 2);
		
		if (distancePastLast < spaceBetween / 2) {
			scroll += .5 * (Math.abs((spaceBetween / 2) - distanceToClosest));
		}
		if (distancePastLast > spaceBetween / 2) {
			scroll -= .5 * (Math.abs(distanceToClosest));
		}
	}

	public abstract void gameRenderLoopInsideScroll(GL10 gl);
	
	public abstract void gameRenderLoop2(GL10 gl);
	
	public void gameRenderLoop(GL10 gl) {
		gameRenderLoop2(gl);
		float localScroll = scroll;

		gl.glTranslatef(0, localScroll, 0);
		gameRenderLoopInsideScroll(gl);
		gl.glTranslatef(0, -localScroll, 0);
	}
	
	public abstract void touchEvent2(float x, float y);
	
	public abstract boolean isOnScrollArea(float x, float y);
	
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
					float absScroll = Math.abs(scroll - topScroll);
					if (absScroll > 1) {
						change = change * (1 / (absScroll * .3f));
					}
				}
				scroll += change;
			}
			lastTouchY = y;
			
			return true;
		}
		if (isOnScrollArea(x, y)) {
			if ((e.getAction() & MotionEvent.ACTION_MASK) != MotionEvent.ACTION_DOWN) {
				if (y > lastTouchY + 5 || y < lastTouchY - 5) {
					isScrolling = true;
				}
			}
			if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
				touchEvent2(x, y);
			}
			return true;
		}
		return false;
	}
	
	public float getScroll() {
		return scroll;
	}
}
