package com.solidapt.defense.store;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.solidapt.citydefense.objects.StaticObject;
import com.solidapt.defense.GameState;
import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.TextureLoader;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;

public class Product extends StaticObject {
	
	private static final int SPINNER_POSITION = 600;
	private static final int BUY_BUTTON_POSITION = 900;
	
	MissileInformation info;
	ColorSquare backDrop;
	AmountSpinner amountSpinner;
	Button buyButton;
	
	public Product(int xCoord, int yCoord, MissileInformation info) {
		super(xCoord, yCoord, 150, 150);
		this.backDrop = new ColorSquare(Util.getWidth()/2, yCoord, Util.getWidth()+10, 140, .1f, .1f, .1f, 1);
		this.amountSpinner = new AmountSpinner(150, 50);
		this.myTexture = info.getTexture();
		this.info = info;
		this.buyButton = new Button(BUY_BUTTON_POSITION, 0, 156, 90, TextureLoader.BUY_BUTTON_TEXTURE1, TextureLoader.BUY_BUTTON_TEXTURE2);
		
		//Set buy button enabled state before rendering
		if (info.canBuy(amountSpinner.getSelectedAmount())) {
			buyButton.setButtonEnabled(true);
		}
		else {
			buyButton.setButtonEnabled(false);
		}
	}

	@Override
	public void gameLoopLogic(double time) {
		amountSpinner.gameLoopLogic(time);
		
		if (info.canBuy(amountSpinner.getSelectedAmount())) {
			buyButton.setButtonEnabled(true);
		}
		else {
			buyButton.setButtonEnabled(false);
		}
	}
	
	@Override
	public void gameRenderLoop(GL10 gl) {
		backDrop.gameRenderLoop(gl);
		super.gameRenderLoop(gl);
	}
	
	@Override 
	public void render(GL10 gl) {
		gl.glRotatef(45, 0, 0, 1);
		this.draw(gl);
		gl.glRotatef(-45, 0, 0, 1);
		
		Util.textRenderer.begin(1, 1, 1, 1);
		Util.textRenderer.draw(info.getName() + " x" + info.getCount(), 90, -10);
		Util.textRenderer.end();
		
		Util.textRenderer.begin(0, .8f, 0, 1);
		Util.textRenderer.setScale(.7f);
		Util.textRenderer.draw("Cost: " + info.getCost(), 90, 40);
		Util.textRenderer.end();
		
		buyButton.gameRenderLoop(gl);
		
		gl.glTranslatef(SPINNER_POSITION, 0, 0);
		amountSpinner.gameRenderLoop(gl);
		gl.glTranslatef(-SPINNER_POSITION, 0, 0);
	}
	
	public int getSelectedAmount() {
		return amountSpinner.getSelectedAmount();
	}
	
	private boolean pressed = false;
	
	public boolean doTouchEvent(MotionEvent e, float x, float y) {
		if (buyButton.getPressed(x - (buyButton.getWidth()/2), y - this.getYCoord())) {
			if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
				info.buy(amountSpinner.getSelectedAmount());
				buyButton.setHover(false);
			}
			else {
				buyButton.setHover(true);
				pressed = true;
			}
			return true;
		}
		else {
			buyButton.setHover(false);
			if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
				pressed = false;
			}
		}
		
		if (!pressed) {
			return amountSpinner.doTouchEvent(e, x - SPINNER_POSITION, y - this.getYCoord());
		}
		else {
			return true;
		}
	}

}
