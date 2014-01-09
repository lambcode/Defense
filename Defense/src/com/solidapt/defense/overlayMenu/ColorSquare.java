package com.solidapt.defense.overlayMenu;

import javax.microedition.khronos.opengles.GL10;

import com.solidapt.citydefense.objects.StaticObject;

public class ColorSquare extends StaticObject {
	
	private float red;
	private float green; 
	private float blue;
	private float alpha;

	public ColorSquare(int xCoord, int yCoord, int width, int height, float red, float green, float blue, float alpha) {
		super(xCoord, yCoord, width, height);

		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public void changeColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	@Override
	public void gameLoopLogic(double time) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void render(GL10 gl) {
		float xScale = 1f;
		float yScale = 1f;
		float width = this.getWidth();
		float height = this.getHeight();
		
		if (width > height) {
			yScale = height / width;
		}
		else {
			xScale = width / height;
		}
		
		gl.glColor4f(red, green, blue, alpha);
		gl.glScalef(xScale, yScale, 0f);
		this.draw(gl);
		gl.glScalef(1/xScale, 1/yScale, 0f);
		gl.glColor4f(1f, 1f, 1f, 1f);
	}

}
