package com.solidapt.inGame;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.StaticObject;
import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;

public class HeatBar extends StaticObject {
	ColorSquare heatBar;
	
	ColorSquare heatBarCap1;
	ColorSquare heatBarCap2;
	ColorSquare heatBarCap3;
	ColorSquare heatBarCap4;
	
	private float heatValue = 0;
	private static final float OVERHEAT_COUNT = 1;
	private boolean overheatedFlag = false;

	public HeatBar(int xCoord, int yCoord, int width, int height) {
		super(xCoord, yCoord, width, height);
		
		//The colors set here for heatBar are overriden later
		//Do not try to change the heatBar color here!
		this.heatBar = new ColorSquare(0, 0, width, height, 1f, .1f, .1f, .7f);
		
		int halfWidth = width/2;
		int halfHeight = height/2;
		this.heatBarCap1 = new ColorSquare(-halfWidth, 0, 4, height, .5f, .5f, .5f, .8f);
		this.heatBarCap2 = new ColorSquare(halfWidth, 0, 4, height, .5f, .5f, .5f, .8f);
		this.heatBarCap3 = new ColorSquare(0, -halfHeight, width + 2, 4, .5f, .5f, .5f, .8f);
		this.heatBarCap4 = new ColorSquare(0, halfHeight, width + 2, 4, .5f, .5f, .5f, .8f);
	}

	@Override
	public void gameLoopLogic(double time) {
		synchronized(this) {
			heatValue -= .2 * time;
			if (heatValue < 0) {
				heatValue = 0;
			}
			else if (overheatedFlag && heatValue < 1) {
				//Reset heatValue to 0 after timout value has been passed
				heatValue = 0;
				overheatedFlag = false;
			}
			else {

				if (!backFadeUp) {
					backFade -= .6 * time;
					if (backFade < 0) {
						backFadeUp = true;
						backFade = 0;
					}
				}
				else {
					backFade += .6 * time;
					if (backFade > .8) {
						backFadeUp = false;
						backFade = .8f;
					}
				}
			}
		}
	}
	
	@Override
	public void draw(GL10 gl) {
		float scaleValue = 0;
		synchronized(this) {
			scaleValue = heatValue;
		}
		
		if (scaleValue > 1) {
			scaleValue = (scaleValue - 1) / OVERHEAT_COUNT;
			heatBar.changeColor(1, 0, 0, .7f);
		}
		else {
			heatBar.changeColor(1, .8f, 0, .7f);
			backFade = 0;
			backFadeUp = true;
		}
		if (scaleValue > 0) {
			gl.glScalef(scaleValue, 1, 1);
			heatBar.gameRenderLoop(gl);
			gl.glScalef(1/scaleValue, 1, 1);
		}
		heatBarCap1.gameRenderLoop(gl);
		heatBarCap2.gameRenderLoop(gl);
		heatBarCap3.gameRenderLoop(gl);
		heatBarCap4.gameRenderLoop(gl);
	}
	
	public void addHeatValue(MissileInformation missileInformation) {
		synchronized(this) {
			if (heatValue < 1) {
				heatValue += missileInformation.getHeatValue();
				if (heatValue > 1) {
					heatValue = 1 + OVERHEAT_COUNT;
					overheatedFlag = true;
				}
			}
		}
	}
	
	private float backFade = 0;
	private boolean backFadeUp = true;
	public void backTextRenderLoop(GL10 gl) {
		synchronized(this) {
			Util.textRenderer.begin(1,0,0, backFade);
			Util.textRenderer.setScale(1.4f);
			Util.textRenderer.drawC("Turret Overheating", (Util.getWidth()/2) + 45, Util.getHeight()/2);
			Util.textRenderer.end();
		}
	}

	public boolean ableToFire() {
		return heatValue < 1;
	}

}
