package com.solidapt.defense.store;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.defense.MissileInformation;
import com.solidapt.defense.Scroller;
import com.solidapt.defense.Util;
import com.solidapt.defense.overlayMenu.ColorSquare;

public class AmountSpinner extends Scroller {
	
	private int[] spinnerValues = {1, 5, 10 ,25, 50, 100, 200, 500};
	
	private static final int SPACING = 70;
	
	private int height;
	private int width;
	
	private ColorSquare mask;

	public AmountSpinner(int width, int height) {
		for (int i = 0; i < spinnerValues.length; i++) {
			this.addVerticalSpace(SPACING);
		}
		
		this.configureScroll(SPACING, true, true, true);
		
		mask = new ColorSquare(0, 0, width, height, 1, 1, 1, 1);
		
		this.height = height;
		this.width = width;
	}
	
	@Override
	public void gameLoopLogic2(double time) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void gameRenderLoop(GL10 gl) {
		gl.glClear(GL10.GL_DEPTH_BUFFER_BIT);
		gl.glEnable(GL10.GL_STENCIL_TEST);
		gl.glColorMask(false, false, false, false);
		gl.glDepthMask(false);
		gl.glStencilFunc(GL10.GL_NEVER, 1, 0xFF);
		gl.glStencilOp(GL10.GL_REPLACE, GL10.GL_KEEP, GL10.GL_KEEP);
		
		gl.glStencilMask(0xFF);
		gl.glClear(GL10.GL_STENCIL_BUFFER_BIT);
		drawMask(gl); 

		gl.glColorMask(true, true, true, true);
		gl.glDepthMask(true);
		gl.glStencilMask(0x00);
		gl.glStencilFunc(GL10.GL_EQUAL, 1, 0xFF);
		//gl.glStencilOp(GL10.GL_KEEP, GL10.GL_KEEP, GL10.GL_KEEP);

		super.gameRenderLoop(gl);

		gl.glDisable(GL10.GL_STENCIL_TEST);
	}

	private void drawMask(GL10 gl) {
		mask.gameRenderLoop(gl);
	}

	@Override
	public void gameRenderLoopInsideScroll(GL10 gl) {
		Util.textRenderer.begin(1, 1, 1, 1);
		for (int i = 0; i < spinnerValues.length; i++) {
			Util.textRenderer.drawC(String.valueOf(spinnerValues[i]), 0, -SPACING * i);
		}
		Util.textRenderer.end();

	}

	@Override
	public void gameRenderLoop2(GL10 gl) {
	}

	@Override
	public void touchEvent2(float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOnScrollArea(float x, float y) {
		int halfHeight = this.height / 2;
		return ( (x < width && x > 0) && (y < halfHeight && y > -halfHeight));
	}

	public int getSelectedAmount() {
		int index = (int)Math.floor(this.getScroll() / this.getVerticalSpace() * this.getItemsAdded());
		if (index < 0) index = 0;
		if (index > spinnerValues.length - 1) index = spinnerValues.length - 1;
		return spinnerValues[index];
	}

}
